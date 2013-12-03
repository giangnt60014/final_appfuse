package com.giangnt.webapp.service.impl;

import java.util.List;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.giangnt.webapp.dao.FsaccountDao;
import com.giangnt.webapp.model.Fsaccount;
import com.giangnt.webapp.service.FsaccountManager;

@Service("fsaccountManager")
@Transactional
public class FsaccountManagerImpl extends GenericManagerImpl<Fsaccount, Long> implements FsaccountManager {

	FsaccountDao fsaccountDao;
	
	/**
	 * @param fsaccountDao the fsaccountDao to set
	 */
	@Autowired
	public void setFsaccountDao(FsaccountDao fsaccountDao) {
		this.fsaccountDao = fsaccountDao;
	}

	public List<Fsaccount> findByAccount(String account) {
		return fsaccountDao.findByAccount(account);
	}

}
