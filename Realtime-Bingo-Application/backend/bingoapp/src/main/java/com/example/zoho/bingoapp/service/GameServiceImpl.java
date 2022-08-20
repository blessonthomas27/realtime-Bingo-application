package com.example.zoho.bingoapp.service;

import com.example.zoho.bingoapp.model.*;
import com.example.zoho.bingoapp.model.dto.ControlDto;
import com.example.zoho.bingoapp.model.redisDto.RedisDto;
import com.example.zoho.bingoapp.repository.RedisDao;
import com.example.zoho.bingoapp.util.json.JsonUtility;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GameServiceImpl implements GameService{

    @Autowired
    RedisDao redisDao;

    @Autowired
    UserService userService;

    @Autowired
    RoomService roomService;

    @Override
    public Room setStrike(Room room, Integer strikeNo) {
        try{
            ArrayList<Integer> str=new ArrayList<>(room.getStrike());
            if(!str.contains(strikeNo)){
                str.add(strikeNo);
                return setStrikes(room,str);
            }
        }
        catch (Exception e){
            ArrayList<Integer> str=new ArrayList<>();
            str.add(strikeNo);
            return setStrikes(room,str);
        }
        return null;
    }

    public Room setStrikes(Room room ,ArrayList<Integer> str){
        int index=0;
        room.setStrike(str);
        int totalUser=room.getUser().size();
        for(int i=0;i<totalUser;i++){
            String userId=room.getUser().get(i).getId();
            User user= JsonUtility.toObject(redisDao.findById(userId).get().getString(),User.class);
            int count=0;
            for (var j:user.getComb()){
                for(var k:j){
                    if(!room.getStrike().contains(k)){
                        count+=1;
                        break;
                    }
                }
            }
            //save to roomInfo
            room.getUser().get(i).setTotalSucess(12-count);
            if(room.getUser().get(i).getId().equals(room.getTurnId())){
                if(i<room.getUser().size()-1){
                    index=i+1;
                }else if(i==room.getUser().size()-1){
                    index=0;
                }
            }
        }
        room.setTurnId(room.getUser().get(index).getId());
        System.out.println("\n\n\n\n\n\n"+room.getTurnId()+"\n\n\n");
        RedisDto redisRoom_room=new RedisDto();
        redisRoom_room.setString(JsonUtility.toJsonString(room));
        redisRoom_room.setId(room.getId());
        redisDao.save(redisRoom_room);

        return room;
    }

    public Room setControls(Room room, ControlDto status){
        ArrayList<UserMeta> arrayList=room.getUser();
        for(var each:arrayList){
            System.out.println(each.getId()+status.getUserId());
            if(each.getId().equals(status.getUserId())){
            if (status.getStatus().equals("READY")) {
                each.setPlayerStatus(PlayerStatus.READY);
            }
            if(status.getStatus().equals("NOT_READY")){
                each.setPlayerStatus(PlayerStatus.NOT_READY);
            }
        }
        }
        int count=0;
        for (var each:arrayList){
            if(PlayerStatus.READY.equals(each.getPlayerStatus())){
                count+=1;
            }
        }
        if(count==arrayList.size()){
            room.setRoomStatus(RoomStatus.INGAME);
        }
        room.setUser(arrayList);
        RedisDto redisRoom=new RedisDto();
        redisRoom.setId(room.getId());
        redisRoom.setString(JsonUtility.toJsonString(room));
        redisDao.save(redisRoom);
        System.out.println(room);
        return room;
    }

    public Room resetGame(Room room,ArrayList<ArrayList<Integer>> mat) throws JsonProcessingException {
        ArrayList<UserMeta> userMeta= room.getUser();
        for(var each:userMeta){
            if(each.getTotalSucess()>=5){
                System.out.println("userMeta");
                return resetUserandRoom(room,mat);
            }
        }
        return null;
    }

    public  Room resetUserandRoom(Room room,ArrayList<ArrayList<Integer>> mat) throws JsonProcessingException {
        ArrayList<UserMeta> userMeta= room.getUser();
        for(var each:userMeta){
            each.setPlayerStatus(PlayerStatus.NOT_READY);
            each.setTotalSucess(0);
            User tuser=userService.getUserById(each.getId());
            tuser.setMat(mat);
            userService.updateUser(tuser,tuser.getId());
        }
        room.setUser(userMeta);
        room.setStrike(null);
        room.setRoomStatus(RoomStatus.JOIN);
        return roomService.updateRoom(room);
    }
}
