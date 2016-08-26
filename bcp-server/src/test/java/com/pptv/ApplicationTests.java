package com.pptv;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pptv.dataoper.service.BcpResourceService;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class ApplicationTests {

	@Autowired
	private BcpResourceService bcpResourceService;

	@Test
	public void test1() {
		bcpResourceService.spliderEpgList();
	}
	/*@Autowired  
	private UrlProperties urlProperties;  
	
	@Test
	public void test1() {
		System.out.println(urlProperties.getListapi());
	}*/
	/*//@Before
	public void setUp() {
		// 准备，清空user表
		userService.deleteAllUsers();
	}

	@Test
	public void test1() {
		System.out.println("测试开始");
		System.out.println("表中记录为:"+userService.getAllUsers());
	}
	
	@Test
	public void test() throws Exception {
		// 插入5个用户
		userService.create("a", 1);
		userService.create("b", 2);
		userService.create("c", 3);
		userService.create("d", 4);
		userService.create("e", 5);

		// 查数据库，应该有5个用户
		Assert.assertEquals(5, userService.getAllUsers().intValue());

		// 删除两个用户
		userService.deleteByName("a");
		userService.deleteByName("e");

		// 查数据库，应该有5个用户
		Assert.assertEquals(3, userService.getAllUsers().intValue());

	}*/


}
