package com.pptv;

import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pptv.dataoper.service.UserService;
import com.pptv.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class DaoTest {

	@Autowired
	private UserService userService;
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testInsert() {
	    User user = new User();
	    user.setUserName("liyd");
	    user.setUserAge(18);
	    user.setGmtCreate(new Date());
	    int userId = userService.save(user);
	    System.out.println("insert userId:" + userId);
	    /*Book book = new Book();
	    book.setBookName("Java教程");
	    book.setBookAuthor("liyd");
	    book.setGmtCreate(new Date());
	    int bookId = jdbcBaseDao.save(book);
	    System.out.println("insert bookId:" + bookId);*/
	}
}
