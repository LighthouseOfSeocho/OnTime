package com.onTime.project.model.es;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onTime.project.model.dao.WedulPlayRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class WedulPlayService {

	@Autowired
	private WedulPlayRepository wedulPlayRepository;

	public void save(Meeting example) {
		wedulPlayRepository.save(example);
	}

	public Iterable<Meeting> findAll() {
		return wedulPlayRepository.findAll();
	}

	public Meeting findByUser(String user) {
		return wedulPlayRepository.findByUser(user);
	}

}