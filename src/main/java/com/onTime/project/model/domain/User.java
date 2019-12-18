package com.onTime.project.model.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="USER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	@JsonProperty(value = "id")
	private String userId;
	private String userEmail;
	private String userName;
	private String userGender;
	private String userAge;
	private String userAccount;
	private String userBirthday;
	private String userPhone;
	
	public User(String userId, String userName) {
		this.userId = userId;
		this.userName = userName;
	}
}
