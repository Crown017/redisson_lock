package com.sqhtech.redisson.Lock;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

public interface RedissonLocker {
    RLock lock(String lockKey);
    RLock lock(String lockKey, int timeout);
    RLock lock(String lockKey, TimeUnit unit, int timeout);
    boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime);
    boolean fairLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime);
    void unLock(String lockKey);
    void unLock(RLock lock);

}
