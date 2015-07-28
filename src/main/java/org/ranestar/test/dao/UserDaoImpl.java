package org.ranestar.test.dao;

import org.apache.ibatis.session.SqlSession;
import org.ranestar.test.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired SqlSession sqlSession;
	
	@Override
	public void save(User user) {
		sqlSession.insert("save", user);
	}

}
