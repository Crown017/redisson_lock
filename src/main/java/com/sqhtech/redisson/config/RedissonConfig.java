package com.sqhtech.redisson.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        //单机模式  依次设置redis地址和密码
        config.useSingleServer().
                setAddress("redis://192.168.0.8:6379").
                setPassword("123456").setDatabase(11);
         config.setCodec(new JsonJacksonCodec());
        return Redisson.create(config);
    }


// Cluster
//    @Bean
//    public RedissonClient redissonClient2(){
//        Config config = new Config();
//        config.useClusterServers()
//                .setScanInterval(2000)
//                .addNodeAddress("redis://192.168.0.8:6379","redis://192.168.0.8:6378");
//        config.setCodec(new JsonJacksonCodec());
//        RedissonClient redissonClient = Redisson.create(config);
//        return redissonClient;
//    }

//    @Bean
//    public RedissonClient redissonClient3(){
//        Config config = new Config();
//        config.useSentinelServers().setMasterName("mymaster")
//                .addSentinelAddress("redis://192.168.0.8:6379","redis://192.168.0.8:6378");
//
//        config.setCodec(new JsonJacksonCodec());
//        RedissonClient redissonClient = Redisson.create(config);
//        return redissonClient;
//    }

}
