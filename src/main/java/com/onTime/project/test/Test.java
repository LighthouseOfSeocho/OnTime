package com.onTime.project.test;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onTime.project.model.dao.PromiseRepo;
import com.onTime.project.model.dao.UserRepo;
import com.onTime.project.model.domain.Promise;
import com.onTime.project.model.domain.User;

@Service
public class Test {
//	@Autowired
//	public static PromiseRepo promiseRepo;
	@Autowired
	public UserRepo userRepo;
	public void test() {
		User user = new User("K1234", "최성국");
//		Promise promise = new Promise(1,"술모임",user,"부천",null,null,null,1);
//		userRepo.save(user);
//		promiseRepo.save(promise);
		Optional<User> user2 = userRepo.findById("aaa");
		System.out.println(user2);
	}
}
