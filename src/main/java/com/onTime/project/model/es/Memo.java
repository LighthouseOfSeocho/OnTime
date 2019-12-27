package com.onTime.project.model.es;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
  private int memoId;
  private String user;
  private int promiseId; //미팅 제목, 날짜, 시간, 장소, 참여자 정보 등이 존재 
  private String note;
}