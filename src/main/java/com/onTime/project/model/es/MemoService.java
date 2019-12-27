package com.onTime.project.model.es;

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
		memoRepository.save(example);
	}

	public Iterable<Memo> findAll() {
		return memoRepository.findAll();
	}

	public Memo findByUser(String user) {
		return memoRepository.findByUserEquals(user);
	}
	
	public List<Memo> findByKwd(String kwd) {
		return memoRepository.findByNoteContaining(kwd);
	}

}