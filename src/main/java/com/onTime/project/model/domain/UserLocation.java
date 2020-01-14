package com.onTime.project.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLocation {
	private String userId;
	private String userEmail;
	private String userName;
	private String userGender;
	private String userAge;
	private String userAccount;
	private String userBirthday;
	private String userPhone;
	private Float longitude;
	private Float latitude;
	private Integer arrival;
	
	public UserLocation(User user) {
		this.userId=user.getUserId();
		this.userEmail=user.getUserEmail();
		this.userName=user.getUserName();
		this.userGender=user.getUserGender();
		this.userAge=user.getUserAge();
		this.userAccount=user.getUserAccount();
		this.userBirthday=user.getUserBirthday();
		this.userPhone=user.getUserPhone();
	}
}
