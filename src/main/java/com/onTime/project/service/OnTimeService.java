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
	
	public boolean createUser(String userId, String userName) {
		boolean flag = false;
		System.out.println("11111111");
		List<User> list = userRepo.findUserByuserIdEquals(userId);
		System.out.println("2222222222");
		System.out.println(list);
        if (list.size() == 0) {
        	System.out.println("333333333");
        	userRepo.save(new User(userId,userName));
        	System.out.println("44444444444");
        	flag = true;
        	System.out.println("6666666666666");
        } else {
        	return flag;
        }
        return flag;
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
}
