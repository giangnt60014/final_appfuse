package com.giangnt.webapp.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;
import org.appfuse.model.User;
import org.springframework.transaction.annotation.Transactional;

import com.giangnt.webapp.model.Link;

public interface LinkDao extends GenericDao<Link, Long> {

	@Transactional
	public List<Link> findByUser(User user);
	
	public List<Link> getAllLink();

	public void saveLink(Link link);

}