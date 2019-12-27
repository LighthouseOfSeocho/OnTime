package com.onTime.project.model.es;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onTime.project.model.dao.MemoRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
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

	public Memo findByUser(String user) {
		return memoRepository.findByUserEquals(user);
	}
	
	public List<Memo> findByKwd(String kwd) {
		List<Memo> list = new ArrayList<>();
		List<Memo> result = memoRepository.findByNoteContaining(kwd);
		for(Memo unit : result) {
			list.add(unit);
		}
		return list;
	}

}