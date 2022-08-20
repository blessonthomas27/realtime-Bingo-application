package com.example.zoho.bingoapp.controller.socketController;

import com.example.zoho.bingoapp.model.dto.*;
import com.example.zoho.bingoapp.model.Room;
import com.example.zoho.bingoapp.service.GameService;
import com.example.zoho.bingoapp.service.RoomService;
import com.example.zoho.bingoapp.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;

@Controller
public class webSocketController {

    @Autowired
    RoomService roomService;

    @Autowired
    UserService userService;

    @Autowired
    GameService gameService;

    @Autowired
    private SimpMessagingTemplate template;

    public webSocketController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/room/{roomId}")
    public void getUser(@DestinationVariable String roomId) throws JsonProcessingException {
        Room rm=roomService.getRoomById(roomId);
        if(rm!=null){
            this.template.convertAndSend("/topic/"+roomId,ResponseEntity.ok(rm));
        }else{
            this.template.convertAndSend("/topic/"+roomId,"failed");
           }
    }

    @MessageMapping("/room/strike/{roomId}")
    public void   strike(@DestinationVariable String roomId, StrikeDto strikeDto) throws JsonProcessingException {
        var room=roomService.getRoomById(roomId);
        if(room.getTurnId().equals(strikeDto.getUserId())){
        var mroom=gameService.setStrike(room,Integer.parseInt(strikeDto.getStrike()));
        if(mroom!=null){
            this.template.convertAndSend("/topic/"+roomId,ResponseEntity.ok(mroom));
        }else{
            this.template.convertAndSend("/topic/"+roomId,"failed");
        }
        }else{
            this.template.convertAndSend("/topic/"+roomId,"failed");
        }
    }

    @MessageMapping("/room/control/{roomId}")
    public void playControls(@DestinationVariable String roomId, ControlDto status) throws JsonProcessingException {
        Room rm=roomService.getRoomById(roomId);
        if(rm!=null){
           var room= gameService.setControls(rm,status);
            this.template.convertAndSend("/topic/"+roomId,room);
        }else{
            this.template.convertAndSend("/topic/"+roomId,"failed");
        }
    }

    @MessageMapping("/room/reset/{roomId}")
    public void resetGame(@DestinationVariable String roomId, ResetDto resetDto) throws JsonProcessingException {
        Room rm=roomService.getRoomById(roomId);
        if(rm!=null){
            Room room= gameService.resetGame(rm,resetDto.getMat());
            if(room!=null) {
                this.template.convertAndSend("/topic/" + roomId, room);
            }else{
            this.template.convertAndSend("/topic/"+roomId,"failed");}
        }else{
            this.template.convertAndSend("/topic/"+roomId,"failed");
        }
    }

    @MessageMapping("/room/message/{roomId}")
    public void messenger(@DestinationVariable String roomId, Messenger msg) throws JsonProcessingException {
        this.template.convertAndSend("/topic/message/"+roomId,msg);
    }

}
