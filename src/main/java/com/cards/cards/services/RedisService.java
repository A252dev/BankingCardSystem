package com.cards.cards.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    @Autowired
    private RedisOperations<String, String> redis;

    public Optional<String> setExData(String key, String value) {

        redis.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                if (!((StringRedisConnection) connection).get(key).isEmpty())
                    return null;
                else
                    return ((StringRedisConnection) connection).setEx(key, 7200, value); // 2 hours session
            }
        });
        return null;
    }

    public Optional<String> setData(String key) {

        redis.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                return ((StringRedisConnection) connection).get(key);
            }
        });
        return null;
    }
}
