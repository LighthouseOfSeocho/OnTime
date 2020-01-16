package com.onTime.project.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.onTime.project.loginApi.GoogleLoginApi;
import com.onTime.project.loginApi.KakaoLoginApi;
import com.onTime.project.loginApi.NaverLoginApi;
import com.onTime.project.model.domain.JsonReq;
import com.onTime.project.model.domain.Promise;
import com.onTime.project.model.domain.User;
import com.onTime.project.model.domain.UserLocation;
import com.onTime.project.model.domain.UserPromise;
import com.onTime.project.service.OnTimeService;

@CrossOrigin(origins = "http://192.168.22.109:9000")
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

	@RequestMapping(value = "/")
	public ModelAndView index(HttpSession sess, ModelAndView mv) {
		JSONObject user = (JSONObject) sess.getAttribute("PI");
		if(user == null) {
			sess.removeAttribute("PI");
			sess.invalidate();
			mv.setViewName("login");
		} else {
			mv.addObject("PI", user);
			mv.setViewName("app");
		}
		return mv;
	}
	
	/* Kakao Login */
	@RequestMapping(value = "/login")
	public String kakaoLogin() {
		return "redirect:" + kakaoLoginApi.getAuthUrl();
	}

	@RequestMapping(value = "/oauth")
//	@ResponseBody
	public String getUserInfo(@RequestParam("code") String code, ModelAndView model, HttpSession sess) {
		JSONObject kakaoUser = ((JSONObject) kakaoLoginApi.getUserInfo(kakaoLoginApi.getAccessKakaoToken(code)));
		kakaoUser.put("id", "k" + kakaoUser.get("id"));
		service.createUser(kakaoUser);
		model.addObject("PI", kakaoUser);
		sess.setAttribute("PI", kakaoUser);
		model.setViewName("app");
		return "redirect:/";
	}
	
	/* Naver Login */
	@RequestMapping(value = "/loginNaver")
	public String loginNaver(HttpSession session) {
		return "redirect:" + naverLoginApi.getAuthorizationUrl(session);
	}

	@RequestMapping(value = "/callback")
//	@ResponseBody
	public String callbackNaver(@RequestParam String code, @RequestParam String state, HttpSession sess, ModelAndView model)
			throws IOException, ParseException, InterruptedException, ExecutionException {
		JSONObject naverUser = ((JSONObject) naverLoginApi.getUserProfile(naverLoginApi.getAccessToken(sess, code, state)));
		JSONObject pi = new JSONObject();
		pi.put("id", "n" + naverUser.get("id").toString());
		pi.put("nickname", naverUser.get("name").toString());
		service.createUser(pi);
		sess.setAttribute("PI", pi);
		model.addObject("PI",pi);
		model.setViewName("app");
		return "redirect:/";
	}

	/* Google Login */
	@RequestMapping(value = "/loginGoogle")
	public String loginGoogle(HttpSession session) {
		return "redirect:" + googleLoginApi.getAuthorizationUrl(session);
	}

	@RequestMapping(value = "/googleCallback")
//	@ResponseBody
	public String callbackGoogle(@RequestParam String code, @RequestParam String state, HttpSession sess, ModelAndView model)
			throws IOException, ParseException, InterruptedException, ExecutionException {
		JSONObject googleUser = ((JSONObject) googleLoginApi.getUserProfile(googleLoginApi.getAccessToken(sess, code, state)));
		JSONObject pi = new JSONObject();
		pi.put("id", "g" + googleUser.get("sub").toString());
		pi.put("nickname", googleUser.get("name").toString());
		service.createUser(pi);
		sess.setAttribute("PI", pi);
		model.addObject("PI", pi);
		model.setViewName("app");
		return "redirect:/";
	}

	@GetMapping(value = "/user")
	@ResponseBody
	public User findUserById(@RequestBody JsonReq jsonReq) {
		return service.findUserById(jsonReq.getUserId());
	}
	
	@PutMapping(value ="/user")
	@ResponseBody
	public boolean updateUser(@RequestBody User userInfo) {
		return service.updateUser(userInfo);
	}
	
	@PutMapping(value="/user/position")
	@ResponseBody
	public boolean updateUserLocation(@RequestBody JsonReq jsonReq) {
		return service.updateUserLocation(jsonReq);
	}

	@PutMapping(value = "/user/arrival")
	@ResponseBody
	public boolean checkArrival(@RequestBody JsonReq jsonReq) {
		return service.checkArrival(jsonReq);
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
	public List<UserLocation> getMembers(@RequestParam int promiseId){
		return service.getMembers(promiseId);
	}

	@GetMapping(value="/{code}")
	@ResponseBody
	public ModelAndView inviteCode(@PathVariable String code, ModelAndView model, HttpSession session){
		JSONObject user = (JSONObject) session.getAttribute("PI");
		if(user == null) {
			session.removeAttribute("PI");
			session.invalidate();
			model.setViewName("login");
		} else {
			joinPromise(user.get("id").toString(), service.getCodePromise(code).getPromiseId());
			model.addObject("PI", user);
			model.setViewName("app");
		}
		return model;
	}
	
	//모임에 다른 사람 초대 완료시 그 사람 ID와 모임ID mapping
	public void joinPromise(@RequestParam("userId") String userId, @RequestParam("promiseId") int promiseId) {
		try {
			service.createUserPromise(new UserPromise(userId, promiseId));
		} catch (Exception e) {
			System.out.println("이미 해당 약속이 존재함");
		}
	}

	@GetMapping(value="/logout")
	@ResponseBody
	public Boolean logout(HttpSession sess, ModelAndView mv) {
		sess.removeAttribute("PI");
		mv.setViewName("redirect:http://192.168.22.109:9000/login");
		return true;
	}
	
}
