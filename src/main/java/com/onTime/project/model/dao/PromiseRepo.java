package com.onTime.project.model.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.onTime.project.model.domain.Promise;

public interface PromiseRepo extends CrudRepository<Promise, Integer> {
	List<Promise> findByRoomHostIdOrderByPromiseTime(String RoomHostId);
	Promise findPromiseByPromiseId(int promiseId);
	Promise findPromiseByInvitation(String code);
}
