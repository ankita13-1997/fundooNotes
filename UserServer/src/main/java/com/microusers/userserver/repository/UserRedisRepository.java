package com.microusers.userserver.repository;

import com.microusers.userserver.model.RedisUserModel;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

import java.util.Map;
import java.util.UUID;

@Repository
public class UserRedisRepository {

    private HashOperations hashOperations;

    private RedisTemplate redisTemplate;
    private Jedis jedis=new Jedis();
    private String token="jwtToken";

    public UserRedisRepository(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = this.redisTemplate.opsForHash();
    }


    public void save(RedisUserModel user) {
        System.out.println(user.getUserID().toString());

        jedis.hset(token, String.valueOf(user.getUserID()),user.getToken());

    }

    public Map<String, String> findByToken(String userToken) {
        Map<String, String> user = jedis.hgetAll(token);
        return user;
    }




}
