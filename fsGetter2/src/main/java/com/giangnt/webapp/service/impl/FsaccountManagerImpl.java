package com.giangnt.webapp.service.impl;

import java.util.List;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.giangnt.webapp.dao.FsaccountDao;
import com.giangnt.webapp.model.Fsaccount;
import com.giangnt.webapp.service.FsaccountManager;

@Service("fsaccountManager")
public class FsaccountManagerImpl extends GenericManagerImpl<Fsaccount, Long> implements FsaccountManager {

	FsaccountDao fsaccountDao;
	
	@Autowired
	public FsaccountManagerImpl(FsaccountDao fsaccountDao) {
	    super(fsaccountDao);
	    this.fsaccountDao = fsaccountDao;
	}
	
	@Override
	public List<Fsaccount> findByAccount(String account) {
		// TODO Auto-generated method stub
		return fsaccountDao.findByAccount(account);
	}

}
