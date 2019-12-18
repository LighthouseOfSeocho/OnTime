package com.onTime.project.model.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
	@GeneratedValue
	private int id;
	private String userId;
	private int promiseId;
	
	public UserPromise(String userId, int promiseId) {
		this.userId = userId;
		this.promiseId = promiseId;
	}
}
