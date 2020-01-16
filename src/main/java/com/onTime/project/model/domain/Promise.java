package com.onTime.project.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="PROMISE")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class Promise {
	@Id
	@JsonProperty(value = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int promiseId;
	private String promiseName;
	private String roomHostId;
	private String placeName;
	@Column(name="place_x")
	private Double placeX;
	@Column(name="place_y")
	private Double placeY;
	private String address;
	private String promiseTime;
	private String invitation;
	public int getPromiseId() {
		return promiseId;
	}
	public void setPromiseId(int promiseId) {
		this.promiseId = promiseId;
	}
	public String getPromiseName() {
		return promiseName;
	}
	public void setPromiseName(String promiseName) {
		this.promiseName = promiseName;
	}
	public String getRoomHostId() {
		return roomHostId;
	}
	public void setRoomHostId(String roomHostId) {
		this.roomHostId = roomHostId;
	}
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	public Double getPlaceX() {
		return placeX;
	}
	public void setPlaceX(Double placeX) {
		this.placeX = placeX;
	}
	public Double getPlaceY() {
		return placeY;
	}
	public void setPlaceY(Double placeY) {
		this.placeY = placeY;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPromiseTime() {
		return promiseTime;
	}
	public void setPromiseTime(String promiseTime) {
		this.promiseTime = promiseTime;
	}
	public String getInvitation() {
		return invitation;
	}
	public void setInvitation(String invitation) {
		this.invitation = invitation;
	}
	public Promise(int promiseId, String promiseName, String roomHostId, String placeName, Double placeX, Double placeY,
			String address, String promiseTime, String invitation) {
		super();
		this.promiseId = promiseId;
		this.promiseName = promiseName;
		this.roomHostId = roomHostId;
		this.placeName = placeName;
		this.placeX = placeX;
		this.placeY = placeY;
		this.address = address;
		this.promiseTime = promiseTime;
		this.invitation = invitation;
	}
	public Promise() {
	}
	
}
