package com.onTime.project.model.domain;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="INVITATION")
@IdClass(InvitationKey.class)
@Data
@NoArgsConstructor
public class Invitation {
	@Id
	private String sender;
	@Id
	private String recipient;
	@Id
	private int promiseId;
	private String inviteTime;
	
	public Invitation(String sender, String recipient, int promiseId) {
		this.sender = sender;
		this.recipient = recipient;
		this.promiseId = promiseId;
		this.inviteTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
	}

	public String getRecipient() {
		return null;
	}

	public Integer getPromiseId() {
		return null;
	}

	public void setInviteTime(String format) {
	}
}
