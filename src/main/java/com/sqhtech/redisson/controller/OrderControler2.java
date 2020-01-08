package com.sqhtech.redisson.controller;


import com.sqhtech.redisson.Entity.GlobalResponse;
import com.sqhtech.redisson.Lock.RedissonLocker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.TimeUnit;

@RequestMapping("/order")
public class OrderControler2 {

    @Autowired
    private RedissonLocker redissonLocker;


    @RequestMapping("/getRedLock")
    public GlobalResponse getOrder(){

        String lockKey = "userid";
        // 公平加锁，60秒后锁自动释放
        boolean isLocked = false;
        try {
            isLocked = redissonLocker.fairLock(lockKey , TimeUnit.SECONDS, 3, 60);
            if (isLocked) { // 如果成功获取到锁就继续执行
                // 执行业务代码操作
                return GlobalResponse.success("获取到锁");
            } else { // 未获取到锁
                return GlobalResponse.fail(500, "请勿重复点击！！");
            }
        } catch (Exception e) {
            return GlobalResponse.fail(500, e.getMessage());
        } finally {
            if (isLocked) { // 如果锁还存在，在方法执行完成后，释放锁
                redissonLocker.unLock(lockKey);
            }
        }
    }

}
