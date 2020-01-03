package com.onTime.project.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.onTime.project.loginApi.GoogleLoginApi;
import com.onTime.project.loginApi.KakaoLoginApi;
import com.onTime.project.loginApi.NaverLoginApi;
import com.onTime.project.model.domain.Invitation;
import com.onTime.project.model.domain.JsonReq;
import com.onTime.project.model.domain.Memo;
import com.onTime.project.model.domain.Promise;
import com.onTime.project.model.domain.User;
import com.onTime.project.model.domain.UserPromise;
import com.onTime.project.service.MemoService;
import com.onTime.project.service.OnTimeService;

@CrossOrigin(origins = "http://localhost:9000")
@Controller
public class OnTimeController {

	@Value("${kakao.key}")
	private String kakaoKey;

	@Autowired
	public KakaoLoginApi kakaoLoginApi;
	@Autowired
	private NaverLoginApi naverLoginApi;
	@Autowired
	private GoogleLoginApi googleLoginApi;
	@Autowired
	private OnTimeService service;

	/* Kakao Login */
	@RequestMapping(value = "/login")
	public String kakaoLogin() {
//		System.out.println(kakaoLoginApi.getAuthUrl());
		return "redirect:" + kakaoLoginApi.getAuthUrl();
	}

	@RequestMapping(value = "/oauth")
	@ResponseBody
	public ModelAndView getUserInfo(@RequestParam("code") String code, ModelAndView model) {
//		System.out.println(((JsonObject) kakaoLoginApi.getUserInfo(kakaoLoginApi.getAccessKakaoToken(code))).toString()); // {"id":"1243182388","nickname":"한우석"}
		JSONObject kakaoUser = ((JSONObject) kakaoLoginApi.getUserInfo(kakaoLoginApi.getAccessKakaoToken(code)));
		System.out.println("test1\n");
		boolean flag = service.createUser(kakaoUser.get("id").toString(), kakaoUser.get("nickname").toString());
		System.out.println("test2\n");
		if(flag == true) {
			System.out.println("test3\n");
			model.addObject("PI",kakaoUser);
			System.out.println("test4\n");
		}else {
			System.out.println("test5\n");
			model.addObject("PI",kakaoUser);
			model.setViewName("app");
		}
		
		model.setViewName("app");
		System.out.println("test7\n");
		System.out.println(model);
		return model;
	}
	
	/* Naver Login */
	@RequestMapping(value = "/loginNaver")
	public String loginNaver(HttpSession session) {
//		System.out.println(naverLoginApi.getAuthorizationUrl(session));
		return "redirect:" + naverLoginApi.getAuthorizationUrl(session);
	}

	@RequestMapping(value = "/callback")
	@ResponseBody
	public ModelAndView callbackNaver(@RequestParam String code, @RequestParam String state, HttpSession session, ModelAndView model)
			throws IOException, ParseException, InterruptedException, ExecutionException {
//		System.out.println(((JSONObject) naverLoginApi.getUserProfile(naverLoginApi.getAccessToken(session, code, state))).getClass()); // class org.json.simple.JSONObject
//		System.out.println(((JSONObject) naverLoginApi.getUserProfile(naverLoginApi.getAccessToken(session, code, state))));
// {"birthday":"01-08","profile_image":"https:\/\/ssl.pstatic.net\/static\/pwe\/address\/img_profile.png","gender":"M","nickname":"hanwo","name":"한우석","id":"34508534","age":"20-29","email":"gazzari@hanmail.net"}
		JSONObject naverUser = ((JSONObject) naverLoginApi.getUserProfile(naverLoginApi.getAccessToken(session, code, state)));
		model.addObject("PI",naverUser);
		model.setViewName("app");
		return model;
	}
	// return값을 JSONObject에 저장해놓고 유저 id를 세션으로 저장 - 추후 로그아웃 구현시 사용
//		JSONObject apiResult = naverLoginApi.getUserProfile(naverLoginApi.getAccessToken(session, code, state));
//		session.setAttribute("sessionId", apiResult.get("id").toString());  

	/* Google Login */
	@RequestMapping(value = "/loginGoogle")
	public String loginGoogle(HttpSession session) {
		return "redirect:" + googleLoginApi.getAuthorizationUrl(session);
	}

	@RequestMapping(value = "/googleCallback")
	@ResponseBody
	public ModelAndView callbackGoogle(@RequestParam String code, @RequestParam String state, HttpSession session, ModelAndView model)
			throws IOException, ParseException, InterruptedException, ExecutionException {
//		System.out.println(((JSONObject) googleLoginApi.getUserProfile(googleLoginApi.getAccessToken(session, code, state))).toString());
// email : {"sub":"106490326651663334165","email_verified":true,"picture":"https:\/\/lh3.googleusercontent.com\/a-\/AAuE7mA_yoNKgoEfzvS9vauvZedDndxiiZ8uww_x4yPUTw","email":"hanwo2052@gmail.com"}
// profile : {"sub":"106490326651663334165","name":"han wop","given_name":"han","locale":"ko","family_name":"wop","picture":"https:\/\/lh3.googleusercontent.com\/a-\/AAuE7mA_yoNKgoEfzvS9vauvZedDndxiiZ8uww_x4yPUTw"}
		JSONObject googleUSer = ((JSONObject) googleLoginApi.getUserProfile(googleLoginApi.getAccessToken(session, code, state)));
		model.addObject("PI", googleUSer);
		model.setViewName("redirect : app.html");
		return model;
	}

	@GetMapping(value = "/user")
	@ResponseBody
	public User findUserById(@RequestBody JsonReq jsonReq) {
		return service.findUserById(jsonReq.getUserId());
	}

	@PostMapping(value = "/user")
	@ResponseBody
	public boolean createUser(@RequestBody JsonReq jsonReq) {
		return service.createUser(jsonReq.getUserId(), jsonReq.getUserName());
	}

	@GetMapping(value = "/user/invitation")
	@ResponseBody
	public List<Promise> getMyInvitation(@RequestBody JsonReq jsonReq) {
		return service.getInvitedPromises(jsonReq.getUserId());
	}

	@PostMapping(value = "/promise/invitation")
	@ResponseBody
	public boolean invite(@RequestBody Invitation invitation) {
		try {
			return service.invite(invitation);
		} catch (Exception e) {
			return false;
		}
	}

	@PostMapping(value = "/test")
	public String findMyHostedPromise(@RequestBody JsonReq jsonReq) {
		System.out.println(jsonReq);
		return jsonReq.getUserId();
	}

	@GetMapping(value = "/promise")
	@ResponseBody
	public List<Promise> getMyPromises(@RequestParam String userId) {
		return service.getMyPromises(userId);
	}

	@PostMapping(value="/promise")
	@ResponseBody
	public boolean createPromise(@RequestBody Promise promise) {
		return service.createPromise(promise);
	}

	@GetMapping(value="/promise/members")
	@ResponseBody
	public List<String> getMembers(@RequestBody JsonReq jsonReq){
		return service.getMembers(jsonReq.getPromiseId());
	}
	
	// test data1: Memo(promiseId=8, user="nsy", note="서초역 제주도그릴 강추. 주변에 커피 마실 곳이 없음. 업체 사장님 딸 생일은 5월 15일, 사장님 딸 올해 고3.")
	// test data1: Memo(promiseId=11, user="nsy", note="문부장님 부대찌개 별로 안좋아하셨음. 내년 1월초에 부서별 사업계획 확정, 내년 2월말에 계약 가능성 내비침.")
	// test data1: Memo(promiseId=12, user="nsy", note="소주 마시고 싶을 때 오기 좋은 곳, 대표메뉴 마늘닭똥집. 주말에 사람 많아서 예약 필수. 영동이 첫째 내년 4월 출산 예정")
	
	@GetMapping(value="/createMemo")
	@ResponseBody
	public String createMemo(@RequestParam("promiseId") int promiseId, @RequestParam("user") String user, String note) {
		String result;
		Memo instance = new Memo();
		instance.setPromiseId(promiseId);
		instance.setUser(user);
		instance.setNote(note);
		try {
			esService.save(instance);
			result = "메모 저장 성공";
		} catch (Exception e) {
			result = "메모 저장 실패";
		}
		return result; 
	}
	
	//모임에 다른 사람 초대 완료시 그 사람 ID와 모임ID mapping
	@GetMapping(value="/joinPromise")
	@ResponseBody
	public String joinPromise(@RequestParam("userId") String userId, @RequestParam("promiseId") int promiseId) {
		String result;
		UserPromise instance = new UserPromise();
		instance.setUserId(userId);
		instance.setPromiseId(promiseId);
		try {
			service.createUserPromise(instance);
			System.out.println();
			result = "미팅 초대 성공";
		} catch (Exception e) {
			result = "미팅 초대 실패";
		}
		return result;
	}
	
	
	@SuppressWarnings("null")
	@GetMapping(value="/searchKwd")
	@ResponseBody
	public String searchKwd(@RequestParam("kwd") String kwd){
		String result;
		String note;
		Promise promise;
		List<String> members;
		List<Memo> mList;
		
		List<Object> allList = new ArrayList<>();
		
		try {
			mList = esService.findByKwd(kwd);
			for(Memo mUnit : mList) {

				note = mUnit.getNote();	
				promise = service.findPromiseByPromiseId(mUnit.getPromiseId());
				members = service.getMembers(mUnit.getPromiseId());
				
				List<Object> unitList = new ArrayList<>();
				
				unitList.add(promise.getPromiseId());
				unitList.add(promise.getPlaceName());
				unitList.add(promise.getPlaceX());
				unitList.add(promise.getPlaceY());
				unitList.add(members);
				unitList.add(note);
				allList.add(unitList);
			}
			result = allList.toString();
			
		} catch (Exception e) {
			result = "키워드 조회 실패";
		}
		return result;
	}
	
}
