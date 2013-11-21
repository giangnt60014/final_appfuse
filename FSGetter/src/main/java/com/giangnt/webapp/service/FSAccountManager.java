package com.giangnt.webapp.service;

import org.appfuse.service.GenericManager;

import com.giangnt.webapp.model.FSAccount;

public interface FSAccountManager extends GenericManager<FSAccount, Long>{

	FSAccount findByAccName(String accName);
}
