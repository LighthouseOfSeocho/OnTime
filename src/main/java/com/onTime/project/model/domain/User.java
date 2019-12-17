package com.onTime.project.model.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

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
	private String userId;
	private String userEmail;
	private String userName;
	private String userGender;
	private String userAge;
	private String userAccount;
	private String userBirthday;
	private String userPhone;
	
	@ManyToMany
	@JoinTable(name="user_promise", 
				joinColumns=@JoinColumn(name="userId"), 
				inverseJoinColumns=@JoinColumn(name="promiseId")
	)
	private List<Promise> promises = new ArrayList<Promise>();
	
	public User(String userId, String userName) {
		this.userId = userId;
		this.userName = userName;
	}
}
