package com.microusers.userserver.model;

import com.microusers.userserver.dto.ReddishDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;

@Getter
@Setter
public class RedisUserModel {

    private UUID userID;
    private String token;

    public RedisUserModel(ReddishDto redisDto) {
        this.userID=redisDto.userID;
        this.token= redisDto.token;
    }

}
