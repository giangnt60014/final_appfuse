package com.giangnt.webapp.service.impl;

import java.util.List;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.giangnt.webapp.dao.LinkFilmDao;
import com.giangnt.webapp.model.LinkFilm;
import com.giangnt.webapp.service.LinkFilmManager;

@Service("linkFilmManager")
@Transactional
public class LinkFilmManagerImpl extends GenericManagerImpl<LinkFilm, Long> implements LinkFilmManager {

	@Autowired
	LinkFilmDao linkFilmDao;

	@Override
	public List<LinkFilm> getAllLinkFilm() {
		return linkFilmDao.getAllLinkFilm();
	}
	

}
