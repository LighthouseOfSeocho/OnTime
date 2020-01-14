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
@Data
@NoArgsConstructor
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
}
