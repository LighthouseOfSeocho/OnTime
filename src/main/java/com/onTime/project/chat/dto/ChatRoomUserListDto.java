package com.onTime.project.chat.dto;

import com.onTime.project.user.User;

import io.vavr.collection.Set;

public class ChatRoomUserListDto {

	public final String roomKey;
	public final Set<User> users;

	public ChatRoomUserListDto(String roomKey, Set<User> users) {
		this.roomKey = roomKey;
		this.users = users;
	}
}