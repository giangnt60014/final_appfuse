package com.giangnt.webapp.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.giangnt.webapp.model.Fsaccount;

public interface FsaccountDao extends GenericDao<Fsaccount, Long> {

	@Transactional
	public List<Fsaccount> findByAccount(String account);
	
	public List<Fsaccount> getAllAccount();
}
