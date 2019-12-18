package com.onTime.project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onTime.project.model.dao.PromiseRepo;
import com.onTime.project.model.dao.UserPromiseRepo;
import com.onTime.project.model.dao.UserRepo;
import com.onTime.project.model.domain.Promise;
import com.onTime.project.model.domain.User;
import com.onTime.project.model.domain.UserPromise;

@Service
public class OnTimeService {
	@Autowired
	public UserRepo userRepo;
	@Autowired
	public PromiseRepo promiseRepo;
	@Autowired
	public UserPromiseRepo userPromiseRepo;
	
	// User CRUD
	public User findUserById(String id) {
		Optional<User> temp = userRepo.findById(id);
		if(temp.isPresent()) {
			return temp.get();
		}else {
			return null;
		}
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
	//내가 참여중인 방들
	public List<Promise> getMyPromises(String userId){
		List<Promise> promises = new ArrayList<>();
		List<UserPromise> tempList = userPromiseRepo.findByUserId(userId);
		if(!tempList.isEmpty()) {
			for(UserPromise up : tempList) {
				Optional<Promise> temp = promiseRepo.findById(up.getPromiseId());
				if(temp.isPresent()) {
					promises.add(temp.get());
				}
			}
		}
		return promises;
	}
	
	//내가 참여중인 방들
	public List<User> getMembers(int promiseId){
		List<User> users = new ArrayList<>();
		List<UserPromise> tempList = userPromiseRepo.findByPromiseId(promiseId);
		if(!tempList.isEmpty()) {
			for(UserPromise up : tempList) {
				Optional<User> temp = userRepo.findById(up.getUserId());
				if(temp.isPresent()) {
					users.add(temp.get());
				}
			}
		}
		return users;
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
