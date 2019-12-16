package com.onTime.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.onTime.project.loginAPI.LoginAPI;

@CrossOrigin(origins = "http://localhost:9000")
@Controller
public class OnTimeController {
	@Value("${kakao.key}")
	private String kakaoKey;
	
	@Autowired
	public LoginAPI loginAPI;
	
	@RequestMapping(value="/login")
	public String login() {
		return "redirect:https://kauth.kakao.com/oauth/authorize?client_id="+kakaoKey+"&redirect_uri=http://localhost:9000/oauth&response_type=code";
	}
	
	@RequestMapping(value="/oauth")
	@ResponseBody
	public String getUserInfo(@RequestParam("code") String code) {
		System.out.println("aaa");
	    System.out.println("code : " + code);
//	    String accessKey = loginAPI.getAccessKakaoToken(code);
	    JsonObject userInfo = loginAPI.getUserInfo(loginAPI.getAccessKakaoToken(code));
	    for(String k : userInfo.keySet()) {
	    	System.out.println(userInfo.get(k));
	    }
	    return userInfo.toString();
	}
}
