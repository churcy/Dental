package com.tinytree.dental.redis;

import redis.clients.jedis.Jedis;

/**
 * Created by 重 on 2016/3/11.
 */
public class RedisJava {
    public static void main(String [] args){
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");
        System.out.println("Connection to server sucessfully");
        //查看服务是否运行
        System.out.println("Server is running: "+jedis.ping());
        //设置redis字符串数据
        jedis.set("testRedis","My first RedisTest");
        //获取存储的数据并且输出
        System.out.println("Stored string in redis"+jedis.get("testRedis"));
    }
}
