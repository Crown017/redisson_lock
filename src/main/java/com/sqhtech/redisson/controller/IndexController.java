package com.sqhtech.redisson.controller;

import org.omg.CORBA.TIMEOUT;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RestController
public class IndexController {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private StringRedisTemplate redisTemplate;


    @RequestMapping("/decrement")
    public String deductStock(){

        String lockKey = "redis_lock_key";

        //性能不高
        //获取锁要设置过期的时间,例如Tomcat进程挂掉无法释放锁,
        //setIfPresent的时候一次性设置过期时间 不要分开set NX  然后 expire
        //timeout 的时间不好把控
        //很多线程无法获取锁，就会直接返回无法等待再次获取锁,获取锁失败需要手动实现入队列

        String clientId = UUID.randomUUID().toString();
        Boolean getLock = redisTemplate.opsForValue().setIfPresent(lockKey, clientId, 2, TimeUnit.SECONDS);

        if (getLock){
            try {
                int stock = Integer.parseInt(redisTemplate.opsForValue().get("stock"));
                //没结束一直给LockKey 续命 直到释放Key,主线程如果结束，这个也会结束
                Runnable runnable = () -> redisTemplate.expire(lockKey, 5, TimeUnit.SECONDS);
                ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
                scheduledExecutorService.scheduleAtFixedRate(runnable,30,30,TimeUnit.SECONDS);

                //多个请求都判断stock == 0
                if (stock > 0){
                    int realStock = stock - 1 ;
                    redisTemplate.opsForValue().set("stock",realStock+"");
                    return "扣减成功,剩余库存："+realStock;
                }else {
                    return "已经被抢完";
                }


            }finally {
                //Key失效的时候 防止删除其他客户端的Key
                if (clientId.equals(redisTemplate.opsForValue().get(lockKey))){
                    redisTemplate.delete(lockKey);
                }
            }
        }
        return "获取锁失败,请重试!";
    }

}
