package com.giangnt.webapp.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.giangnt.webapp.model.Fsaccount;

public interface FsaccountDao extends GenericDao<Fsaccount, Long> {

	public List<Fsaccount> findByAccount(String account);
}
