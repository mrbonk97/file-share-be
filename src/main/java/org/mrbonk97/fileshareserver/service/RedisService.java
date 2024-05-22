//package org.mrbonk97.fileshareserver.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.redis.core.HashOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.Duration;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//
//@RequiredArgsConstructor
//@Service
//public class RedisService {
//    private final RedisTemplate<String, Object> redisTemplate;
//
//    public <T> void setValue(String key,T data) {
//        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
//        valueOperations.set(key, data);
//    }
//
//    public <T> void setValue(String key,T data, Duration duration) {
//        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
//        valueOperations.set(key, data, duration);
//    }
//
//    public String getValue(String key) {
//        ValueOperations<String, Object> valueOperations =redisTemplate.opsForValue();
//        if(valueOperations.get(key) == null) return "false";
//        return (String) valueOperations.get(key);
//    }
//
//    public void deleteValues(String key) {
//        redisTemplate.delete(key);
//    }
//
//    public void expireValues(String key, int timeout) {
//        redisTemplate.expire(key, timeout, TimeUnit.MILLISECONDS);
//    }
//
//    public void setHashOps(String key, Map<String, String> data) {
//        HashOperations<String, Object, Object> values = redisTemplate.opsForHash();
//        values.putAll(key, data);
//    }
//
//    @Transactional(readOnly = true)
//    public String getHashOps(String key, String hashKey) {
//        HashOperations<String, Object, Object> values = redisTemplate.opsForHash();
//        return Boolean.TRUE.equals(values.hasKey(key, hashKey)) ? (String) redisTemplate.opsForHash().get(key, hashKey) : "";
//    }
//
//    public void deleteHashOps(String key, String hashKey) {
//        HashOperations<String, Object, Object> values = redisTemplate.opsForHash();
//        values.delete(key, hashKey);
//    }
//
//    public boolean checkExistsValue(String value) {
//        return !value.equals("false");
//    }
//}
