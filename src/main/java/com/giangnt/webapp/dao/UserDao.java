package com.giangnt.webapp.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.giangnt.webapp.model.User;

public interface UserDao extends GenericDao<User, Long> {
	
	public List<User> findByUsername(String username);
}
