package com.giangnt.webapp.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;
import org.appfuse.model.User;
import org.springframework.transaction.annotation.Transactional;

import com.giangnt.webapp.model.Link;
import com.giangnt.webapp.model.LinkFilm;

public interface LinkFilmDao extends GenericDao<LinkFilm, Long> {

	public List<LinkFilm> getAllLinkFilm();

	public void saveLinkFilm(LinkFilm link);

}