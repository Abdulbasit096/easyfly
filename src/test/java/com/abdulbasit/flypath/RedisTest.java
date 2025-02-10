package com.abdulbasit.flypath;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {


    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Test
    void testRedis(){
        redisTemplate.opsForValue().set("name","Basit");
        String name = redisTemplate.opsForValue().get("name");
        System.out.println(name);
    }

}
