package com.example.zoho.bingoapp.controller;

import com.example.zoho.bingoapp.model.redisDto.RedisDto;
import com.example.zoho.bingoapp.model.Room;
import com.example.zoho.bingoapp.model.User;
import com.example.zoho.bingoapp.repository.RedisDao;
import com.example.zoho.bingoapp.service.RoomService;
import com.example.zoho.bingoapp.util.json.JsonUtility;
import com.example.zoho.bingoapp.util.jwt.JwtUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private JsonUtility jsonUtility;

    @PostMapping("/room")
    public ResponseEntity<?> createRoom() throws JsonProcessingException {
        String  roomId=roomService.createRoom();
        HashMap<String, String> map = new HashMap<>();
        map.put("roomId", roomId);
        if (roomId!=null) {
            return ResponseEntity.ok(map);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/room/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable("id") String id) throws JsonProcessingException {
        Room rm=roomService.getRoomById(id);
        System.out.println(rm.getUser());
        if(rm!=null){
            return ResponseEntity.ok(rm);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/room/{id}")
    public ResponseEntity<?>  joinRoom(@PathVariable("id") String id,@RequestBody User user) throws JsonProcessingException {
        String token=roomService.joinRoom(user,id);
        HashMap<String, String> map = new HashMap<>();
        map.put("token", token);
        if (token==null||token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(map);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") String id) throws JsonProcessingException {
        RedisDto redisuser= redisDao.findById(id).get();
        var user=JsonUtility.toObject(redisuser.getString(),User.class);
        System.out.println("\n\n\n\n\n\n\n\n\n"+redisuser.getString()+"\n\n\n\n\n\n\n\n\n");
        user.setId(redisuser.getId());
        if(user!=null){
            return ResponseEntity.ok(user);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
