package com.example.zoho.bingoapp.service;


import com.example.zoho.bingoapp.model.Room;
import com.example.zoho.bingoapp.model.redisDto.RedisDto;
import com.example.zoho.bingoapp.model.User;
import com.example.zoho.bingoapp.repository.RedisDao;
import com.example.zoho.bingoapp.util.json.JsonUtility;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    RedisDao redisDao;
    @Override
    public User saveUser(User user) throws JsonProcessingException {
        // TODO Auto-generated method stub
       ArrayList<Integer> arr=isValidMatrix(user.getMat());
        if (arr!=null){
            //create combo
           ArrayList<ArrayList<Integer>> comBoarr =comboCreater(arr);

           if(comBoarr!=null){
               user.setComb(comBoarr);
               user.setMat(user.getMat());
               String str= JsonUtility
                       .toJsonString(user);
               RedisDto redisDto =new RedisDto();
               redisDto.setString(str);
               var userTemp=redisDao.save(redisDto);
               user.setId(userTemp.getId());
               return user;
           }
        }
    return null;
    }

    @Override
    public User updateUser(User user,String id) throws JsonProcessingException {
        ArrayList<Integer> arr=isValidMatrix(user.getMat());
        if (arr!=null){
            //create combo
            ArrayList<ArrayList<Integer>> comBoarr =comboCreater(arr);
            if(comBoarr!=null){
                user.setComb(comBoarr);
                user.setMat(user.getMat());
                String str= JsonUtility
                        .toJsonString(user);
                RedisDto redisDto =new RedisDto();
                redisDto.setString(str);
                redisDto.setId(id);
                var userTemp=redisDao.save(redisDto);
                return user;
            }
        }
        return null;
    }

    public User getUserById(String id){
        RedisDto user=redisDao.findById(id).get();
        User userJson=JsonUtility.toObject(user.getString(), User.class);
        userJson.setId(user.getId());
        return userJson;
    }

    //is valid matrix
    public ArrayList<Integer> isValidMatrix(ArrayList<ArrayList<Integer>>  matrix){
        ArrayList<Integer> array=new  ArrayList<Integer>();
        if (matrix.size()==5){
            for(int i=0;i<5;i++){
                var arrInd=matrix.get(i);
                if(arrInd.size()==5) {
                    for (int j = 0; j < 5; j++) {
                        array.add(arrInd.get(j));
                    }
                }else{
                    return null;
                }
            }
            if (array.size()==25){
                Set<Integer> s =
                        new HashSet<Integer>(array);
                for (Integer temp : array) {
                    if (temp > 25 || temp < 0) {
                        return null;
                    }
                }
                if(s.size() == 25){
                    return array;
                }
            }
        }
        return null;
    }
    //combo creater
    public ArrayList<ArrayList<Integer>> comboCreater(ArrayList<Integer> arr){
        int[][] twoD_arr = new int[5][5];
        for (int k=0;k<arr.size();) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    twoD_arr[i][j]=arr.get(k++);
                }
            }
        }
        ArrayList<ArrayList<Integer>> combo=new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> temp_combo_Leftdiag=new ArrayList<Integer>();
        ArrayList<Integer> temp_combo_Rightdiag=new ArrayList<Integer>();
        //cloumn
        for (int i = 0; i < 5; i++) {
            ArrayList<Integer> temp_combo_col=new ArrayList<Integer>();
            ArrayList<Integer> temp_combo_row=new ArrayList<Integer>();
            for (int j = 0; j < 5; ++j) {
                temp_combo_col.add(twoD_arr[i][j]);
                temp_combo_row.add(twoD_arr[j][i]);
                if(i==j){
                    temp_combo_Leftdiag.add(twoD_arr[i][j]);
                }
                if(i+j == 4){
                    temp_combo_Rightdiag.add(twoD_arr[i][j]);
                }
            }
            combo.add(temp_combo_col);
            combo.add(temp_combo_row);
        }
        combo.add(temp_combo_Leftdiag);
        combo.add(temp_combo_Rightdiag);
        if (combo.size()==12){
            return combo;
        }
        return null;
    }

    @Autowired
    private JsonUtility jsonUtility;
}

