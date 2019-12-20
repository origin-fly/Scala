package day1219;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @ClassName RedisUtils
 * @Description 使用java创建redis连接池
 * Jedis连接Redis三种模式
 *
 * 这里说的三种工作模式是指：
 * 1、单机模式
 * 2、分片模式
 * 3、集群模式（since 3.0）
 *
 * @Author nieyafei
 * @Date 2019-12-19 16:25
 * Version 1.0
 **/
public class RedisUtils {

    private  static JedisPool jedisPool = null;

    static {
        try{
            JedisPoolConfig config = new JedisPoolConfig();
            //可用连接实例的最大数目,默认值为8，如果值为-1则表示不限制
            config.setMaxTotal(1024);
            //config.setMaxActive(1024);  高版本已经弃用
            //控制一个pool最多有多少个状态为idle(空闲的)jedis实例，默认值为8
            config.setMaxIdle(200);
            //等待可用连接的最大时间,单位 毫秒 默认值为-1 表示永不超时
            config.setMaxWaitMillis(10000);
            //在borrow一个jedis实例时，是否提前进行calidate操作，如果为true，则得到的jedis实例均是可用的
            config.setTestOnBorrow(true);
            jedisPool = new JedisPool(config, "10.50.12.55", 6379);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public synchronized static Jedis getJedis(){
        try {
            if (jedisPool != null) return jedisPool.getResource();

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static void returnResource(final Jedis jedis){
        if (jedis != null) jedisPool.returnResource(jedis);
    }
}
