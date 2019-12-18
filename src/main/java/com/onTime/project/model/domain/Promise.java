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
@Data
@AllArgsConstructor
@NoArgsConstructor
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
	private String promiseTime;
	private int amount;
	
	public Promise(String promiseName, String roomHostId, String placeName, Double placeX, 
			Double placeY, String promiseTime, int amount){
		this.promiseName=promiseName;
		this.roomHostId=roomHostId;
		this.placeName=placeName;
		this.placeX=placeX;
		this.placeY=placeY;
		this.promiseTime=promiseTime;
		this.amount=amount;
	}
}
