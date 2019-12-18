package com.onTime.project.model.domain;

import lombok.Data;

@Data
public class JsonReq {
	private String userId;
	private String userName;
	private int promiseId;
	private String sender;
	private String recipient;
}
