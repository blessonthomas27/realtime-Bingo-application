package com.example.zoho.bingoapp.service;

import com.example.zoho.bingoapp.model.Room;
import com.example.zoho.bingoapp.model.dto.ControlDto;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;

public interface GameService {
    Room setStrike(Room room, Integer strikeNo);
    Room setControls(Room room, ControlDto status);
    Room resetGame(Room room,ArrayList<ArrayList<Integer>> mat) throws JsonProcessingException;
}
