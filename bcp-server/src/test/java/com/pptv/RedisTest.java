package com.pptv;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class RedisTest {
	/*@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Test
	public void test() throws Exception {

		// 保存字符串
		redisTemplate.opsForValue().set("aaa", "111");
		Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));

		// 保存对象
//		User user = new User();
//		redisTemplate.opsForValue().set(user.getUsername(), user);
//
//		user = new User("蝙蝠侠", 30);
//		redisTemplate.opsForValue().set(user.getUsername(), user);
//
//		user = new User("蜘蛛侠", 40);
//		redisTemplate.opsForValue().set(user.getUsername(), user);
//
//		Assert.assertEquals(20, redisTemplate.opsForValue().get("超人").getAge().longValue());
//		Assert.assertEquals(30, redisTemplate.opsForValue().get("蝙蝠侠").getAge().longValue());
//		Assert.assertEquals(40, redisTemplate.opsForValue().get("蜘蛛侠").getAge().longValue());

	}*/
	
	@Autowired  
    @Qualifier("myRedisTemplate")  
    private RedisTemplate myRedisTemplate;  
      
    @Test  
    public void test() throws Exception {  
        String key = "key1";  
        String value = "value1";  
        set(key, value);  
        System.out.println(get(key));  
    }  
      
    public  String get(String key){  
        return myRedisTemplate.opsForValue().get(key).toString();  
    }  
  
    public  void set(String key, String value){  
        myRedisTemplate.opsForValue().set(key, value);  
    }  

}
