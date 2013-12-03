package com.giangnt.webapp.service;

import java.util.List;

import org.appfuse.service.GenericManager;

import com.giangnt.webapp.model.Fsaccount;

public interface FsaccountManager extends GenericManager<Fsaccount, Long> {
	 List<Fsaccount> findByAccount(String account);

}
