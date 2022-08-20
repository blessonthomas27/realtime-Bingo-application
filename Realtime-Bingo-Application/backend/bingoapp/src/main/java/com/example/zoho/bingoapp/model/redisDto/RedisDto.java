package com.example.zoho.bingoapp.model.redisDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("redis")
public class RedisDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id private String Id;
    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    private String string;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }


}
