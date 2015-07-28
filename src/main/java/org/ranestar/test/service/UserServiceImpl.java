package org.ranestar.test.service;

import org.ranestar.test.dao.UserDao;
import org.ranestar.test.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired UserDao userDao;
	
	@Override
	public void addUser(User user) {
		userDao.save(user);
	}

}
