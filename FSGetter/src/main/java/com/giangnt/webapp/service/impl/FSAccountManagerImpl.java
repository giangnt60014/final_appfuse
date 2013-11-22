package com.giangnt.webapp.service.impl;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.giangnt.webapp.dao.FSAccountDao;
import com.giangnt.webapp.model.FSAccount;
import com.giangnt.webapp.service.FSAccountManager;

@Service("fSAccountManager")
public class FSAccountManagerImpl extends GenericManagerImpl<FSAccount, Long> implements FSAccountManager {

	private FSAccountDao fsAccountDao;
	
	@Autowired
	public FSAccountManagerImpl(FSAccountDao fsAccountDao) {
		super(fsAccountDao);
		this.fsAccountDao = fsAccountDao;
	}
/*	
	@Autowired
    public void setFsAccountDao(FSAccountDao fsAccountDao) {
        this.fsAccountDao = fsAccountDao;
    }
*/

	@Override
	public FSAccount findByAccName(String accName) {
		// TODO Auto-generated method stub
		return fsAccountDao.findByAccName(accName);
	}

}
