package com.sqhtech.redisson;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BlogApplicationTests {

    @Resource
    private RedissonClient redissonClient;

    @Test
    public void RedissionTest(){
        RBucket<String> key = redissonClient.getBucket("newday");
        key.set("新的数据");
        System.out.println("获取到新存入的数据："+key.get());
        // 获取字符串格式的数据
        RBucket<String> keyObj = redissonClient.getBucket("myname");
        String s = keyObj.get();
        System.out.println("获取到昨天存入的数据："+s);
    }
}