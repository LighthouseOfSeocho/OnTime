package com.onTime.project.model.domain;

import javax.persistence.Column;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "memo", type = "_doc")
public class Memo {
	@Id
	@Column(unique = true)
	private String _Id;
	private int promiseId; //미팅 제목, 날짜, 시간, 장소, 참여자 정보 등이 존재 
	private long userId;
	private String note;
	
	public Memo(int promiseId, long userId, String note) {
		this.promiseId = promiseId;
		this.userId = userId;
		this.note = note;
	}
	
}