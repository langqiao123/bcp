package com.pptv.dataoper.dao;

import org.springframework.stereotype.Repository;

import com.pptv.entity.User;

@Repository
public class UserDaoImpl extends JdbcBaseDaoImpl<User> implements UserDao{

}
