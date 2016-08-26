package com.pptv.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

/**
 * redis配置
 */
@Configuration
@ConfigurationProperties(locations = "classpath:redis.properties")  
public class RedisConfig {
	
	@Bean(name = {"MyRedisProperties"})  
    @ConditionalOnMissingBean  
    public MyRedisProperties redisProperties() {  
        return new MyRedisProperties();  
    }  
  
    @Autowired  
    @Qualifier("MyRedisProperties")  
    private MyRedisProperties properties;  
  
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
    	RedisTemplate<String, String> template = new RedisTemplate<String, String>();
    	template.setConnectionFactory(jedisConnectionFactory());
    	template.setKeySerializer(new StringRedisSerializer());
    	template.setValueSerializer(new RedisObjectSerializer());
    	return template;
    }
    
    @Bean(name="myRedisTemplate")  
    @ConditionalOnMissingBean(name = {"myRedisTemplate"})  
    public RedisTemplate redisTemplate(){  
    	RedisTemplate redisTemplate = new RedisTemplate();  
    	redisTemplate.setConnectionFactory(jedisConnectionFactory());  
    	redisTemplate.setKeySerializer(new StringRedisSerializer());
    	redisTemplate.setValueSerializer(new RedisObjectSerializer());
    	return redisTemplate;  
    }  
    
    @Bean  
    public RedisConnectionFactory jedisConnectionFactory(){  
        JedisPoolConfig poolConfig = new JedisPoolConfig();  
        poolConfig.setMaxTotal(properties.getPool().getMaxActive());  
        poolConfig.setMaxIdle(properties.getPool().getMaxIdle());  
        poolConfig.setMaxWaitMillis(properties.getPool().getMaxWait());  
        poolConfig.setTestOnBorrow(true);  
        poolConfig.setTestOnCreate(true);  
        poolConfig.setTestWhileIdle(true);  
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(poolConfig);  
        jedisConnectionFactory.setHostName(properties.getHost());  
        if(null != properties.getPassword()){  
            jedisConnectionFactory.setPassword(properties.getPassword());  
        }  
        jedisConnectionFactory.setPort(properties.getPort());  
        //其他配置，可再次扩展  
        return jedisConnectionFactory;  
    }  
}
