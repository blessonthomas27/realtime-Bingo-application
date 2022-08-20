package com.example.zoho.bingoapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserMeta {

    private String id;
    private String name;
    private String imageName;
    private Integer totalSucess;
    private PlayerStatus playerStatus;

    @Override
    public String toString() {
        return "UserMeta{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", imageName='" + imageName + '\'' +
                ", totalSucess=" + totalSucess +
                ", playerStatus='" + playerStatus + '\'' +
                '}';
    }


    public Integer getTotalSucess() {
        return totalSucess;
    }

    public PlayerStatus getPlayerStatus() {
        return playerStatus;
    }

    public void setPlayerStatus(PlayerStatus playerStatus) {
        this.playerStatus = playerStatus;
    }




    public void setTotalSucess(Integer totalSucess) {
        this.totalSucess = totalSucess;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public UserMeta(String id, String name, String imageName,Integer totalSucess ,PlayerStatus playerStatus) {
        this.id = id;
        this.name = name;
        this.imageName = imageName;
        this.totalSucess=totalSucess;
        this.playerStatus=playerStatus;
    }

    public UserMeta(){super();}
}
