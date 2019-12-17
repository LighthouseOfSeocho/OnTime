package com.onTime.project.controller;

import java.util.HashMap;
import java.util.List;

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

import com.onTime.project.loginAPI.LoginAPI;
import com.onTime.project.model.domain.JsonReq;
import com.onTime.project.model.domain.Promise;
import com.onTime.project.model.domain.User;
import com.onTime.project.service.OnTimeService;

@CrossOrigin(origins = "http://localhost:9000")
@Controller
public class OnTimeController {
	@Value("${kakao.key}")
	private String kakaoKey;
	
	@Autowired
	public LoginAPI loginAPI;
	
	@Autowired
	public OnTimeService service;
	
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
	    HashMap<String,Object> userInfo = loginAPI.getUserInfo(loginAPI.getAccessKakaoToken(code));
	    for(String k : userInfo.keySet()) {
	    	System.out.println(userInfo.get(k));
	    }
	    return userInfo.toString();
	}
	
	@GetMapping(value="/test")
	@ResponseBody
	public User findMyHostedPromise(String userId){
		return service.test();
	}
	
	@GetMapping(value="/promise")
	@ResponseBody
	public List<Promise> getMyPromises(@RequestBody JsonReq jsonReq){
		System.out.println(jsonReq.getUserId());
		return service.getMyPromises(jsonReq.getUserId());
	}
	
	@PostMapping(value="/promise")
	@ResponseBody
	public boolean createPromise() {
		Promise p = new Promise("술술술", "aaa", "종각", 0.0, 0.0, "2019-12-17 11:43:19", 0);
		return service.createPromise(p);
	}
	
	@PostMapping(value="/user")
	@ResponseBody
	public boolean createUser(@RequestBody JsonReq jsonReq) {
		return service.createUser(jsonReq.getUserId(), jsonReq.getUserName());
	}
}
