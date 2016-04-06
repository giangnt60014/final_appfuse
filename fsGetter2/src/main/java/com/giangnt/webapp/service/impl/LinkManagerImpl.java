package com.giangnt.webapp.service.impl;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.giangnt.webapp.dao.LinkDao;
import com.giangnt.webapp.model.Link;
import com.giangnt.webapp.service.LinkManager;

@Service("linkManager")
@Transactional
public class LinkManagerImpl extends GenericManagerImpl<Link, Long> implements LinkManager {

	@Autowired
	LinkDao linkDao;
	
	@Override
	public void saveLink(Link link) {
		linkDao.save(link);
	}

}
