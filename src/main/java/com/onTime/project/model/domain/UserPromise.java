package com.onTime.project.model.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="USER_PROMISE")
//@Data
//@NoArgsConstructor
public class UserPromise {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String userId;
	private int promiseId;
	private Float longitude;
	private Float latitude;
	private Integer arrival;
	public UserPromise(String userId, int promiseId) {
		super();
		this.userId = userId;
		this.promiseId = promiseId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getPromiseId() {
		return promiseId;
	}
	public void setPromiseId(int promiseId) {
		this.promiseId = promiseId;
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
	public UserPromise(int id, String userId, int promiseId, Float longitude, Float latitude, Integer arrival) {
		super();
		this.id = id;
		this.userId = userId;
		this.promiseId = promiseId;
		this.longitude = longitude;
		this.latitude = latitude;
		this.arrival = arrival;
	}
	public UserPromise() {
	}
	
}
