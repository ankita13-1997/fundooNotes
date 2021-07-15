package com.microusers.userserver.service.implementation;

import com.microusers.userserver.dto.ReddishDto;
import com.microusers.userserver.model.RedisUserModel;
import com.microusers.userserver.repository.UserRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class RedisService {

    @Autowired
    private UserRedisRepository redisRepository;


    public String saveData(ReddishDto redisDto) {
        RedisUserModel redisUserModel = new RedisUserModel(redisDto);
        redisRepository.save(redisUserModel);
        return "ADDED SUCCESSFULLY";
    }

    public String getData(String token) {
        Map<String, String> userToken = redisRepository.findByToken(token);
        System.out.println(userToken);
        String user = userToken.entrySet().stream().filter(stringStringEntry -> token.equals(stringStringEntry.getValue()))
                .map(Map.Entry::getKey).findFirst().get();

        return user;
    }


}
