package com.onTime.project.model.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.onTime.project.model.domain.Promise;

public interface PromiseRepo extends CrudRepository<Promise, Integer> {
	List<Promise> findByRoomHostIdOrderByPromiseTime(String RoomHostId);
}
