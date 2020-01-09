package com.onTime.project.chat.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import com.onTime.project.chat.domain.Room;
import com.onTime.project.chat.dto.ChatRoomUserListDto;
import com.onTime.project.chat.dto.NewRoomDto;
import com.onTime.project.chat.dto.SimpleRoomDto;
import com.onTime.project.chat.dto.UserRoomKeyDto;
import com.onTime.project.chat.service.RoomService;
import com.onTime.project.message.Message;
import com.onTime.project.message.MessageTypes;
import com.onTime.project.user.User;

import io.vavr.collection.HashSet;
import io.vavr.collection.List;

import static java.lang.String.format;

@Controller
public class ChatController {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private final RoomService roomService;
    @Autowired
    private final SimpMessageSendingOperations messagingTemplate;
    
    public ChatController(RoomService roomService, SimpMessageSendingOperations messagingTemplate) {
        this.roomService = roomService;
        this.messagingTemplate = messagingTemplate;
    }
    
    @SubscribeMapping("/chat/roomList")	 // 새로운 유저가 서버에 접속시 방 리스트 return 해줌.. 기존 유저가 방 추가시 어떻게 전달 받지? -> computed : getter로 받는다
    public List<SimpleRoomDto> roomList() {// 해당 메소드는 새로운 유저가 진입할 때 기존 방들을 넘겨주기 위해 존재 함
    	System.out.println("============ /chat/roomList -> roomService.roomList() 리턴해줌 =================");
        return roomService.roomList();
    }
    
    @MessageMapping("/chat/addRoom")
    @SendTo("/chat/newRoom")
    public SimpleRoomDto addRoom(NewRoomDto newRoom) {
        return roomService.addRoom(newRoom.roomName);
    }
    
    @MessageMapping("/chat/{roomId}/join")
    public ChatRoomUserListDto userJoinRoom(UserRoomKeyDto userRoomKey, SimpMessageHeaderAccessor headerAccessor) {
//        with enabled spring security
//        final String securityUser = headerAccessor.getUser().getName();
        final String username = (String) headerAccessor.getSessionAttributes().put("username", userRoomKey.userName);
		final Message joinMessage = new Message(MessageTypes.JOIN, userRoomKey.userName, "");
		return roomService.addUserToRoom(userRoomKey)
                .map(userList -> {
                    messagingTemplate.convertAndSend(format("/chat/%s/userList", userList.roomKey), userList);
                    sendMessage(userRoomKey.roomKey, joinMessage);
                    return userList;
                })
                .getOrElseGet(appError -> {
                    log.error("invalid room id...");
                    return new ChatRoomUserListDto(userRoomKey.roomKey, HashSet.empty());
                });
    }
    
    @CrossOrigin("*")
    @RequestMapping("chat/{roomId}/joinCode")
	public ChatRoomUserListDto userJoinCode(UserRoomKeyDto userRoomKey, SimpMessageHeaderAccessor headerAccessor) {
//      with enabled spring security
//      final String securityUser = headerAccessor.getUser().getName();
    	System.out.println("========================= 접수");
    	UserRoomKeyDto test = new UserRoomKeyDto("test", "tester");
		final String username = (String) headerAccessor.getSessionAttributes().put("username", test.userName);
		final Message joinMessage = new Message(MessageTypes.JOIN, test.userName, "");
		return roomService.addUserToRoom(test)
				.map(userList -> { 
					messagingTemplate.convertAndSend(format("/chat/%s/userList", userList.roomKey), userList);
					sendMessage(test.roomKey, joinMessage);
					return userList;
				})
				.getOrElseGet(appError -> {
					log.error("invalid room id...");
					return new ChatRoomUserListDto(test.roomKey, HashSet.empty());
				});
	}
    
    @MessageMapping("/chat/{roomId}/leave")
    public ChatRoomUserListDto userLeaveRoom(UserRoomKeyDto userRoomKey, SimpMessageHeaderAccessor headerAccessor) {
        final Message leaveMessage = new Message(MessageTypes.LEAVE, userRoomKey.userName, "");
        return roomService.removeUserFromRoom(userRoomKey)
                .map(userList -> {
                    messagingTemplate.convertAndSend(format("/chat/%s/userList", userList.roomKey), userList);
                    sendMessage(userRoomKey.roomKey, leaveMessage);
                    return userList;
                })
                .getOrElseGet(appError -> {
                    log.error("invalid room id...");
                    return new ChatRoomUserListDto(userRoomKey.roomKey, HashSet.empty());
                });
    }
    
    @MessageMapping("chat/{roomId}/sendMessage")
    public Message sendMessage(@DestinationVariable String roomId, Message message) {
        messagingTemplate.convertAndSend(format("/chat/%s/messages", roomId), message);
        return message;
    }
    
    public void handleUserDisconnection(String userName) {
        final User user = new User(userName);
        final Message leaveMessage = new Message(MessageTypes.LEAVE, userName, "");
        List<Room> userRooms = roomService.disconnectUser(user);
        userRooms
                .map(room -> new ChatRoomUserListDto(room.key, room.users))
                .forEach(roomUserList -> {
                    messagingTemplate.convertAndSend(format("/chat/%s/userList", roomUserList.roomKey), roomUserList);
                    sendMessage(roomUserList.roomKey, leaveMessage);
                });
    }
    
}
