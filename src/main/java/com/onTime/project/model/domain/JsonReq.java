package com.onTime.project.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class JsonReq {
	private String userId;
	private String userName;
	private int promiseId;
	private String sender;
	private String recipient;
	private Float longitude;
	private Float latitude;
	private Integer arrival;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getPromiseId() {
		return promiseId;
	}
	public void setPromiseId(int promiseId) {
		this.promiseId = promiseId;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public Float getLongitude() {
		return longitude;
	}
	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}
	public Float getLatitude() {
		return latitude;
	}
	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}
	public Integer getArrival() {
		return arrival;
	}
	public void setArrival(Integer arrival) {
		this.arrival = arrival;
	}
	public JsonReq(String userId, String userName, int promiseId, String sender, String recipient, Float longitude,
			Float latitude, Integer arrival) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.promiseId = promiseId;
		this.sender = sender;
		this.recipient = recipient;
		this.longitude = longitude;
		this.latitude = latitude;
		this.arrival = arrival;
	}
	public JsonReq() {
	}
	
}
