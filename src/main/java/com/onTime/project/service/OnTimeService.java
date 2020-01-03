package com.onTime.project.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onTime.project.model.dao.InvitationRepo;
import com.onTime.project.model.dao.PromiseRepo;
import com.onTime.project.model.dao.UserPromiseRepo;
import com.onTime.project.model.dao.UserRepo;
import com.onTime.project.model.domain.Invitation;
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
	@Autowired
	public InvitationRepo invitationRepo;
	
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
		System.out.println(tempList);
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
	
	public boolean invite(Invitation invitation) throws IllegalArgumentException,SQLException {
		System.out.println(userPromiseRepo.findByUserIdAndPromiseId(invitation.getRecipient(), invitation.getPromiseId()));
		if(userPromiseRepo.findByUserIdAndPromiseId(invitation.getRecipient(), invitation.getPromiseId()).equals(null)) {
			invitation.setInviteTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
			invitationRepo.save(invitation);
			return true;
		}else {
			// Already joined exception으로
			return false;
		}
	}
	
	//초대받은 방
	public List<Promise> getInvitedPromises(String userId){
		List<Promise> promises = new ArrayList<>();
		List<Invitation> tempList = invitationRepo.findByRecipient(userId);
		if(!tempList.isEmpty()) {
			for(Invitation invitation : tempList) {
				Optional<Promise> temp = promiseRepo.findById(invitation.getPromiseId());
				if(temp.isPresent()) {
					promises.add(temp.get());
				}
			}
		}
		return promises;
	}
	
	public String sha256(String promiseId) throws NoSuchAlgorithmException {
		String sha = "";
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(promiseId.getBytes());
		byte byteData[] = md.digest();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        sha = sb.toString();
        return sha;
	}
	
	//방에 참여중인 멤버
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
			System.out.println(promise.getPromiseId());
			promise.setInvitation(sha256(promise.getPromiseId()+""));
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
	
	
	//promiseId로 방전체 정보 검색
	public Promise findPromiseByPromiseId(int promiseId) {
		return promiseRepo.findPromiseByPromiseId(promiseId);
	}
	
	//미팅 참가자와 미팅ID 저장
	public UserPromise createUserPromise(UserPromise userPromise) {
		return userPromiseRepo.save(userPromise);
	}
}
