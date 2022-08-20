package com.example.zoho.bingoapp.model;


import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import java.util.ArrayList;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Room  {
    private String id;
    private ArrayList<Integer> Strike;
    private RoomStatus roomStatus;
    private ArrayList<UserMeta> user;
    private String turnId;

    public Room(){
        super();
    }

    public Room(String id, ArrayList<Integer> strike, RoomStatus roomStatus, ArrayList<UserMeta> user, String turnId) {
        this.id = id;
        this.Strike = strike;
        this.roomStatus = roomStatus;
        this.user = user;
        this.turnId=turnId;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id='" + id + '\'' +
                ", Strike=" + Strike + '\'' +
                ", roomStatus='" + roomStatus + '\'' +
                ", user=" + user + '\'' +
                '}';
    }


    public ArrayList<UserMeta> getUser() {
        return user;
    }


    public String getTurnId() {
        return turnId;
    }

    public void setTurnId(String turnId) {
        this.turnId = turnId;
    }


    public void setUser(ArrayList<UserMeta> user) {
        this.user=user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Integer> getStrike() {
        return Strike;
    }

    public void setStrike(ArrayList<Integer> strike) {
        Strike = strike;
    }

    public RoomStatus getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }
}

