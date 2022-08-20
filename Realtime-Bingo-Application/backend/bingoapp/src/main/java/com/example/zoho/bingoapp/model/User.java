package com.example.zoho.bingoapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

@Data  @JsonIgnoreProperties(ignoreUnknown = true)
public class User  {

    public User(){
        super();
    }

    public User(String id, String name, String imageName, ArrayList<ArrayList<Integer>> mat, ArrayList<ArrayList<Integer>> comb) {
        this.id = id;
        this.name = name;
        this.imageName = imageName;
        this.mat = mat;
        this.comb = comb;
    }

    private String  id;
    private String name;
    private String imageName;
    private ArrayList<ArrayList<Integer>> mat;
    private ArrayList<ArrayList<Integer>> comb;


    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", imageName='" + imageName + '\'' +
                ", mat='" + mat + '\'' +
                ", comb='" + comb + '\'' +
                '}';
    }


    public ArrayList<ArrayList<Integer>> getMat() {
        return mat;
    }
    public void setMat(ArrayList<ArrayList<Integer>> mat) {
            this.mat=mat;
    }
    public ArrayList<ArrayList<Integer>> getComb() {
        return comb;
    }
    public void setComb(ArrayList<ArrayList<Integer>> comb) {
            this.comb=comb;
    }
    public String getId() {
        return id;
    }
    public void setId(String  id) {
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
}
