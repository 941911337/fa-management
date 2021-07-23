package cn.john.utils;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Redis缓存辅助类
 *
 * @author ShenHuaJie
 * @version 2016年4月2日 下午4:17:22
 */
public final class RedisUtil {

    private static RedisTemplate<Serializable, Serializable> redisTemplate;
//    private static Integer EXPIRE = PropertiesUtil.getInt("redis.expiration");


    public static void setRedisTemplate(RedisTemplate<Serializable, Serializable> redisTemplate) {
        RedisUtil.redisTemplate =  redisTemplate;
    }


    public static final Serializable get(final String key) {
//        expire(key, EXPIRE);
        return redisTemplate.opsForValue().get(key);
    }

    public static final Serializable get(final String key, Integer mils) {
        expire(key, mils);
        return redisTemplate.opsForValue().get(key);
    }

    public static final void set(final String key, final Serializable value) {
        redisTemplate.boundValueOps(key).set(value);
//        expire(key, EXPIRE);
    }

    public static final void setWithExpire(final String key, final Serializable value,Integer expire) {
        redisTemplate.boundValueOps(key).set(value);
        expire(key, expire);
    }

    public static final Boolean exists(final String key) {
//        expire(key, EXPIRE);
        return existsNoFresh(key);
    }

    public static final Boolean existsNoFresh(final String key) {
        return redisTemplate.hasKey(key);
    }

    public static final void del(final String key) {
        redisTemplate.delete(key);
    }

    public static final void delAll(final String pattern) {
        redisTemplate.delete(redisTemplate.keys(pattern));
    }

    public static final String type(final String key) {
//        expire(key, EXPIRE);
        return redisTemplate.type(key).getClass().getName();
    }

    /**
     * 在某段时间后失效
     */
    public static final Boolean expire(final String key, final int seconds) {
        return redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }

    /**
     * 在某个时间点失效
     */
    public static final Boolean expireAt(final String key, String value, final long unixTime) {
        redisTemplate.boundValueOps(key).set(value);
        return expireAt(key, unixTime);
    }

    /**
     * 在某个时间点失效
     */
    public static final Boolean expireAt(final String key, final long unixTime) {
        return redisTemplate.expireAt(key, new Date(unixTime));
    }

    public static final Long ttl(final String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    public static final void setrange(final String key, final long offset, final String value) {
//        expire(key, EXPIRE);
        redisTemplate.boundValueOps(key).set(value, offset);
    }

    public static final String getrange(final String key, final long startOffset, final long endOffset) {
//        expire(key, EXPIRE);
        return redisTemplate.boundValueOps(key).get(startOffset, endOffset);
    }

    public static final Serializable getSet(final String key, final String value) {
//        expire(key, EXPIRE);
        return redisTemplate.boundValueOps(key).getAndSet(value);
    }

    /** 递增 */
    public static Long incr(final String redisKey) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.incr(redisKey.getBytes());
            }
        });
    }

    public static void hPut(final String redisKey,final String attr,final Serializable value) {
         redisTemplate.opsForHash().put(redisKey,attr,value);
    }

    public static final Object hGet(final String redisKey,final String attr) {
        return  redisTemplate.opsForHash().get(redisKey,attr);
    }

    // 未完，待续...
}
