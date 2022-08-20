package com.example.zoho.bingoapp.service;
import com.example.zoho.bingoapp.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;

public interface UserService {

    User saveUser(User user) throws JsonProcessingException;
    User  updateUser(User user,String id) throws JsonProcessingException;
    User getUserById(String id);
    ArrayList<Integer> isValidMatrix(ArrayList<ArrayList<Integer>> matrix);
}