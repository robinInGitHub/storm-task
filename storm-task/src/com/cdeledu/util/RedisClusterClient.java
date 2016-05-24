package com.cdeledu.util;
import java.util.HashSet;
import java.util.Set;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class RedisClusterClient {
	
	private static JedisCluster jedisClient = null;
    
    static {
          Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
          /**
           * Jedis Cluster will attempt to discover cluster nodes automatically
           * 
           */    
          jedisClusterNodes.add( new HostAndPort( "10.223.0.99", 6379));
          jedisClusterNodes.add( new HostAndPort( "10.223.0.100", 6379));
          jedisClusterNodes.add( new HostAndPort( "10.223.0.101", 6379));
          jedisClusterNodes.add( new HostAndPort( "10.223.0.99", 7379));
          jedisClusterNodes.add( new HostAndPort( "10.223.0.100", 7379));
          jedisClusterNodes.add( new HostAndPort( "10.223.0.101", 7379));
          
          jedisClient = new JedisCluster(jedisClusterNodes);
    }

	public static JedisCluster getJedisClient() {
		return jedisClient;
	}

	public static void setJedisClient(JedisCluster jedisClient) {
		RedisClusterClient.jedisClient = jedisClient;
	}
}
