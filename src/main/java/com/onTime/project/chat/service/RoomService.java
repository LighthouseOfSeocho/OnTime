package com.onTime.project.chat.service;

import org.springframework.stereotype.Service;

import com.onTime.project.app.AppError;
import com.onTime.project.chat.domain.Room;
import com.onTime.project.chat.dto.ChatRoomUserListDto;
import com.onTime.project.chat.dto.SimpleRoomDto;
import com.onTime.project.chat.dto.UserRoomKeyDto;
import com.onTime.project.user.User;

import io.vavr.collection.List;
import io.vavr.control.Either;

@Service
public class RoomService {

    private List<Room> roomList;

    public RoomService() {
        this.roomList = List.of(defaultRoom());
    }

    public List<SimpleRoomDto> roomList() {
        return getRoomList()
                .map(room -> room.asSimpleRoomDto());
    }

    public SimpleRoomDto addRoom(String roomName) {
        final Room room = new Room(roomName);
        final List<Room> roomList = addRoom(room);
        System.out.println("========== 새로운 방 셍성 ");
        return room.asSimpleRoomDto();
    }

    public Either<AppError, ChatRoomUserListDto> usersInChatRoom(String roomKey) {
        return getRoomList()
                .find(room -> room.key.equals(roomKey))
                .map(room -> new ChatRoomUserListDto(room.key, room.users))
                .toEither(AppError.INVALID_ROOM_KEY);
    }

    public Either<AppError, ChatRoomUserListDto> addUserToRoom(UserRoomKeyDto userRoomKey) {
        final User user = new User(userRoomKey.userName);
        this.roomList
                .find(room -> room.key.equals(userRoomKey.roomKey))
                .map(oldRoom -> {
                    final Room newRoom = oldRoom.subscribe(user);
                    updateRoom(oldRoom, newRoom);
                    return newRoom;
                });
        return usersInChatRoom(userRoomKey.roomKey);
    }

    public Either<AppError, ChatRoomUserListDto> removeUserFromRoom(UserRoomKeyDto userRoomKey) {
        final User user = new User(userRoomKey.userName);
        this.roomList
                .find(room -> room.key.equals(userRoomKey.roomKey))
                .map(oldRoom -> {
                    final Room newRoom = oldRoom.unsubscribe(user);
                    updateRoom(oldRoom, newRoom);
                    return newRoom;
                });
        return usersInChatRoom(userRoomKey.roomKey);
    }

    public List<Room> disconnectUser(User user) {
        final List<Room> userRooms = this.roomList
                .filter(room -> room.users.contains(user))
                .map(oldRoom -> {
                    final Room newRoom = oldRoom.unsubscribe(user);
                    updateRoom(oldRoom, newRoom);
                    return newRoom;
                });

        return userRooms;
    }

    private Room defaultRoom() {
        return new Room("Main room");
    }

    private synchronized List<Room> getRoomList() {
        return this.roomList;
    }

    private synchronized List<Room> addRoom(Room room) {
        return this.roomList = this.roomList.append(room);
    }

    private synchronized List<Room> updateRoom(Room oldRoom, Room newRoom) {
        return this.roomList = this.roomList
                .remove(oldRoom)
                .append(newRoom);
    }
}