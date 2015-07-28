package org.ranestar.test.daotest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ranestar.test.dao.UserDao;
import org.ranestar.test.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import config.MvcConfig;
import config.MybatisConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { MvcConfig.class, MybatisConfig.class })
public class UserDaoTest {
	
	private static final Logger logger = LoggerFactory.getLogger(UserDaoTest.class);

	@Autowired UserDao userDao;
	
	@Test
	public void saveTest() {
		
		User user = new User();
		user.setId("test11");
		user.setName("테스트11");
		
		userDao.save(user);
		
	}

}
