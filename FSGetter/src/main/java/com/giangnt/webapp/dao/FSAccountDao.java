package com.giangnt.webapp.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.giangnt.webapp.model.FSAccount;

public interface FSAccountDao extends GenericDao<FSAccount, Long>{

	public FSAccount findByAccName(String accName);
}
