package com.example.zoho.bingoapp.service;

import com.example.zoho.bingoapp.model.*;
import com.example.zoho.bingoapp.model.redisDto.RedisDto;
import com.example.zoho.bingoapp.repository.RedisDao;
import com.example.zoho.bingoapp.util.json.JsonUtility;
import com.example.zoho.bingoapp.util.jwt.JwtUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RoomServiceImpl implements RoomService {

    @Override
    public String createRoom() {
        Room room=new Room();
        RedisDto redisRoom=new RedisDto();
        //userMeta boiler
        UserMeta userMeta=new UserMeta("","","",0, PlayerStatus.NOT_READY);
        ArrayList<UserMeta> als=new ArrayList<>();
        als.add(userMeta);
        room.setUser(als);
        //setTurnId boiler
        room.setTurnId("");
        //roomJoinStatus
        if(room.getRoomStatus()==null) {
            room.setRoomStatus(RoomStatus.JOIN);
        }
        redisRoom.setString(JsonUtility.toJsonString(room));
        RedisDto redisRoom1=redisDao.save(redisRoom);
        room.setId(redisRoom1.getId());
        return  room.getId();
    }


    public Room updateRoom(Room room){
        RedisDto redisRoom=new RedisDto();
        room.setTurnId(room.getUser().get(0).getId());

        redisRoom.setString(JsonUtility.toJsonString(room));
        room.setId(room.getId());
        var redisDto=redisDao.save(redisRoom);
        return JsonUtility.toObject(redisDto.getString(),Room.class);
    }

    @Override
    public Room getRoomById(String id) throws JsonProcessingException {
        RedisDto redisDto= redisDao.findById(id).get();
        return JsonUtility.toObject(redisDto.getString(),Room.class);
    }

    @Override
    public String joinRoom(User user, String roomId) throws JsonProcessingException {
        //save user
        User muser=userService.saveUser(user);
        RedisDto redisDto= redisDao.findById(roomId).get();
        Room mroom=JsonUtility.toObject(redisDto.getString(),Room.class);
        mroom.setId(redisDto.getId());
        if(muser==null || mroom==null){
            return null;
        }
        try{
            int size=mroom.getUser().size();
            if(mroom.getRoomStatus()==RoomStatus.JOIN && size<=4) {
                return addUsertoRoom(muser,mroom);
            }
            mroom.setRoomStatus(RoomStatus.INGAME);
            RedisDto redisRoom=new RedisDto();
            redisRoom.setId(mroom.getId());
            mroom.setTurnId(mroom.getUser().get(0).getId().toString());
            redisRoom.setString(JsonUtility.toJsonString(mroom));
            redisDao.save(redisRoom);
        }catch (Exception e){
           return addUsertoRoom(muser,mroom);
        }
        return null;
    }

    public String addUsertoRoom(User muser,Room mroom) {
        String token=null;
        UserMeta userMeta=new UserMeta(muser.getId(),muser.getName(), muser.getImageName(),12-muser.getComb().size(),PlayerStatus.NOT_READY);
        ArrayList<UserMeta> arrayList=new ArrayList<>(mroom.getUser());
        if (arrayList.get(0).getId().equals("")||arrayList.get(0).getName().equals("")||arrayList.get(0).getImageName().equals("")){
            arrayList.set(0,userMeta);
        }else{
            arrayList.add(userMeta);
        }
        mroom.setUser(arrayList);
        RedisDto redisRoom=new RedisDto();
        redisRoom.setId(mroom.getId());
        mroom.setTurnId(mroom.getUser().get(0).getId());
        redisRoom.setString(JsonUtility.toJsonString(mroom));
        RedisDto rm= redisDao.save(redisRoom);
        token = jwtUtils.generateJwt(rm.getId(), muser.getId());

        //generate JWT here
        if (token == null || token.isEmpty()) {
            return null;
        }
        return token;
    }


    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RedisDao redisDao;
    @Autowired
    private UserService userService;

    @Autowired
    private JsonUtility jsonUtility;
}
