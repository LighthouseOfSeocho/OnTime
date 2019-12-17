package com.onTime.project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onTime.project.model.dao.PromiseRepo;
import com.onTime.project.model.dao.UserRepo;
import com.onTime.project.model.domain.Promise;
import com.onTime.project.model.domain.User;

@Service
public class OnTimeService {
	@Autowired
	public UserRepo userRepo;
	@Autowired
	public PromiseRepo promiseRepo;
	
	// User CRUD
	public Optional<User> findUserById(String id) {
		return userRepo.findById(id);
	}
	
	public boolean createUser(String id, String name) {
		try {
			userRepo.findById(id).get();
			return false;
		} catch (Exception e) {
			try {
				userRepo.save(new User(id,name));
				return true;
			} catch (Exception e2) {
				return false;
			}
		}
	}
	
	
	//Promise CRUD
	//내가 방장인 방들
	public List<Promise> findPromisesById(String userId) {
		return promiseRepo.findByRoomHostIdOrderByPromiseTime(userId);
	}
	
	public List<Promise> getMyPromises(String userId){
		try {
			return userRepo.findById(userId).get().getPromises();
		}catch (Exception e) {
			return new ArrayList<Promise>(); 
		}
	}
	
	public boolean createPromise(Promise promise) {
		try {
			promiseRepo.save(promise);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public User test() {
		return userRepo.findById("aaa").get();
	}
}
