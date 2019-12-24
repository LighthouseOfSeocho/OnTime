package com.onTime.project.model.es;

import java.util.Date;

import javax.persistence.TemporalType;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.jpa.repository.Temporal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@Document(indexName = "ontime_meeting", type = "_doc")
public class Meeting {
  @Id
  private String id;
  
  private String title;
  
  //https://gmlwjd9405.github.io/2019/08/11/entity-mapping.html
  @Temporal(TemporalType.TIMESTAMP)
  private Date date;
  private long place;
  private long endAt;
}