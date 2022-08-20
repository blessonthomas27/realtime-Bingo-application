package com.example.zoho.bingoapp.util.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class JsonUtility {

    public static String toJsonString(Object obj) {
        String jsonString="";
        ObjectMapper objectMapper=new ObjectMapper();

        try {
            jsonString=objectMapper.writeValueAsString(obj);
            System.out.println(jsonString);
        } catch (JsonProcessingException e) {
                e.printStackTrace();
        }
        return jsonString;
    }

    public static <I> I toObject(String jsonstring,Class<I> classObj) {
        I result=null;
        ObjectMapper objectMapper =new ObjectMapper();
        try {
            result=objectMapper.readValue(jsonstring,classObj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public   JsonUtility(){

    }
}
