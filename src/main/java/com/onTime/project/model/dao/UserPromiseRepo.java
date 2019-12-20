package com.onTime.project.model.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.onTime.project.model.domain.UserPromise;

public interface UserPromiseRepo extends CrudRepository<UserPromise, Integer> {
	List<UserPromise> findByUserId(String userId);
	List<UserPromise> findByPromiseId(int promiseId);
	UserPromise findByUserIdAndPromiseId(String userId, int promiseId);
}
