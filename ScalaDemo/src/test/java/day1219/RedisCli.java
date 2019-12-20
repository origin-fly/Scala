package day1219;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Transaction;

/**
 * @ClassName RedisCli
 * @Description 使用 Redis操作
 * @Author nieyafei
 * @Date 2019-12-19 16:26
 * Version 1.0
 **/
public class RedisCli {

    /**
     *@Author nieyafei
     *@Description ** Redis客户端操作
     *@Date 2019/12/19  19:19
     *@Param []
     *@return void
     **/

    @Test
    public void createRedisCli(){

        //创建redis运行环境
        Jedis jedis = new Jedis("10.50.12.55", 6379);

        //操作redis

        //set添加数据 get获取数据
        jedis.set("name","张三");

        System.out.println(jedis.get("name"));

        //append 追加数据
        jedis.append("name","正在学习redis客户端操作");
        System.out.println(jedis.get("name"));


        //设置多个键值对
        jedis.mset("name","zhangsan","age","18","sex","male");
        //对年龄自增1
        jedis.incr("age");

        System.out.println(jedis.get("name")+" "+jedis.get("sex")+" "+jedis.get("age"));

        //关闭客户端
        jedis.disconnect();


    }


    /**
     *@Author nieyafei
     *@Description  Redis 事务
     *@Date 2019/12/19  19:18
     *@Param []
     *@return void
     **/
    @Test
    public void RedisTransaction(){
        Jedis jedis = new Jedis("10.50.12.55", 6379);
        jedis.set("tom","1000");
        jedis.set("mike","1000");
        Transaction tc = null;
        try {
            //开启事务
            tc = jedis.multi();
            tc.decrBy("tom",500);
            tc.incrBy("mike",500);
            //提交事务
            tc.exec();
            System.out.println("tom: "+jedis.get("tom"));
            System.out.println("mike: "+jedis.get("mike"));
        }catch (Exception e){
            e.printStackTrace();
            //回滚事务
            tc.discard();
        }
        //断开连接
        jedis.disconnect();
    }

    /**
     *@Author nieyafei
     *@Description Redis锁
     *@Date 2019/12/19  19:28
     *@Param
     *@return
     **/
    @Test
    public void RedisLock(){
        Jedis jedis = new Jedis("10.50.12.55", 6379);

        jedis.set("tom","1000");
        jedis.set("ticket","1");

        //对ticket加锁，如果事务执行过程中，该值有变化，则抛出异常
        jedis.watch("ticket");
        Transaction tc =null;
        try {
            //开启事务
            tc = jedis.multi();
            //车票减1
            tc.decr("ticket");

            Thread.sleep(3000);
            //等待5秒 扣tom500买票钱
            tc.decrBy("tom",500);
            //提交事务
            tc.exec();

            System.out.println("tom: "+jedis.get("tom"));
            System.out.println("ticket: "+jedis.get("ticket"));

        }catch (Exception e){
            e.printStackTrace();
            tc.discard();
        }
        jedis.disconnect();
    }


    /**
     *@Author nieyafei
     *@Description Redis消息机制
     *@Date 2019/12/19  19:45
     *@Param
     *@return
     **/

    @Test
    public void RedisMessage(){

        Jedis jedis = new Jedis("10.50.12.55", 6379);

        //订阅名字为 channel的消息
       // jedis.subscribe(new myListener(),"channel");

        //使用通配符订阅消息
        jedis.psubscribe(new myListener(),"channel*");
    }

    class myListener extends JedisPubSub{

        public void onMessage(String channel,String message){
            System.out.println("onMessage channel is "+ channel +" message is "+message);
        }

        public void onPMessage(String pattern,String channel,String message){
            System.out.println("onPMessage pattern is "+pattern);
            System.out.println("onPMessage channel is "+channel);
            System.out.println("onPMessage message is "+message);
        }
        public void onPSubscribe(String arg0,int arg1){}
        public void onPUnsubscrible(String arg0,int arg1){}
        public void onSubscribe(String arg0,int arg1){}
        public void onUnsubscrible(String arg0,int arg1){}
    }



}

