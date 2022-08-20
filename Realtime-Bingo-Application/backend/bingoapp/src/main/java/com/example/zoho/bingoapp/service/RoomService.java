package com.example.zoho.bingoapp.service;

import com.example.zoho.bingoapp.model.Room;
import com.example.zoho.bingoapp.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface RoomService {
    String createRoom() throws JsonProcessingException;
    Room getRoomById(String id) throws JsonProcessingException;
    String joinRoom(User user, String roomId) throws JsonProcessingException;
    Room updateRoom(Room room);
}
