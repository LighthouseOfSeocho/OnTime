package com.onTime.project.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonReq {
	private String userId;
	private String userName;
	private int promiseId;
	private String sender;
	private String recipient;
	private Float longitude;
	private Float latitude;
	private Integer arrival;
}
