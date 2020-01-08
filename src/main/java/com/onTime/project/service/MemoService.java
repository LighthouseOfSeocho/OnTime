package com.onTime.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onTime.project.model.dao.MemoRepository;
import com.onTime.project.model.domain.Memo;

@Service
public class MemoService {

	@Autowired
	private MemoRepository memoRepository;

	public void save(Memo example) {
		Memo n = memoRepository.save(example);
		System.out.println(n);
	}

	public Iterable<Memo> findAll() {
		return memoRepository.findAll();
	}

	public Memo findByUser(long userId) {
		return memoRepository.findByUserIdEquals(userId);
	}
	
	public List<Memo> findByKwdAndUserId(String kwd, long userId) {
		List<Memo> mList = new ArrayList<>();
		List<Memo> result = memoRepository.findByNoteContainingAndUserId(kwd, userId);
		for(Memo mUnit : result) {
			mList.add(mUnit);
		}
		return mList;
	}

}