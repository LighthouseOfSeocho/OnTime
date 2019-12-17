package com.onTime.project.controller;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.onTime.project.loginAPI.GoogleLoginApi;
import com.onTime.project.loginAPI.LoginAPI;
import com.onTime.project.loginAPI.NaverLoginApi;

@CrossOrigin(origins = "http://localhost:9000")
@Controller
public class OnTimeController {
	@Value("${kakao.key}")
	private String kakaoKey;
	
	@Autowired
	public LoginAPI loginAPI;
	@Autowired
	private NaverLoginApi naverLoginApi;
	@Autowired
	private GoogleLoginApi googleLoginApi;

	@RequestMapping(value = "/login")
	public String login() {
		return "redirect:https://kauth.kakao.com/oauth/authorize?client_id=" + kakaoKey
				+ "&redirect_uri=http://localhost:9000/oauth&response_type=code";
	}

	@RequestMapping(value = "/oauth")
	@ResponseBody
	public String getUserInfo(@RequestParam("code") String code) {
		System.out.println("aaa");
		System.out.println("code : " + code);
//	    String accessKey = loginAPI.getAccessKakaoToken(code);
		JsonObject userInfo = loginAPI.getUserInfo(loginAPI.getAccessKakaoToken(code));
		for (String k : userInfo.keySet()) {
			System.out.println(userInfo.get(k));
		}
		return userInfo.toString();
	}

	/* Naver Login */
	// 로그인 첫 화면 요청 메소드
	@RequestMapping(value = "/loginNaver")
	public String loginNaver(HttpSession session) {
		return "redirect:" + naverLoginApi.getAuthorizationUrl(session);
	}

	// 네이버 로그인 성공시 callback호출 메소드
	@RequestMapping(value = "/callback")
	@ResponseBody
	public String callbackNaver(@RequestParam String code, @RequestParam String state, HttpSession session) throws IOException, ParseException, InterruptedException, ExecutionException {
		// 로그인 사용자 정보를 읽어온다.
		JSONObject apiResult = naverLoginApi.getUserProfile(naverLoginApi.getAccessToken(session, code, state)); // String형식의 json데이터

//		//유저 id를 세션으로 저장
//		session.setAttribute("sessionId", apiResult.get("id").toString()); //세션 생성
//		// 테스트 용 세션들 초기화 - 내가 직접 임시로 넣은 테스트 상황 전용 코드
//		session.invalidate();

		return apiResult.toString();
	}

	/* Google Login */
	@RequestMapping(value = "/loginGoogle")
	public String loginGoogle(HttpSession session) {
		return "redirect:" + googleLoginApi.getAuthorizationUrl(session);
	}

	@RequestMapping(value = "/googleCallback")
	@ResponseBody
	public String callbackGoogle(@RequestParam String code, @RequestParam String state, HttpSession session) throws IOException, ParseException, InterruptedException, ExecutionException {
		JSONObject apiResult = googleLoginApi.getUserProfile(googleLoginApi.getAccessToken(session, code, state));
		System.out.println(apiResult.toString());
		
		return apiResult.toString();
	}
}
