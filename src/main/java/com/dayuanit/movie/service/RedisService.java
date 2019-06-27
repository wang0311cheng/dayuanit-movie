package com.dayuanit.movie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    RedisTemplate redisTemplate;

    public boolean cacheSeatInfo(int sceneId,int row,int col){
        String key = "movie:ticket:"+ sceneId + ":" + row + ":" + col;
        // 这样就可以通过valueOperations操作字符串
        ValueOperations valueOperations = redisTemplate.opsForValue();
//        boolean flag = valueOperations.setIfAbsent(key, "", 5, TimeUnit.SECONDS);
        return valueOperations.setIfAbsent(key, "", 15, TimeUnit.SECONDS);
    }
}
