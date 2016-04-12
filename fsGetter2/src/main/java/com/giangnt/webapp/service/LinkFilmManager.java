package com.giangnt.webapp.service;

import java.util.List;

import org.appfuse.service.GenericManager;

import com.giangnt.webapp.model.LinkFilm;

public interface LinkFilmManager extends GenericManager<LinkFilm, Long> {

	List<LinkFilm> getAllLinkFilm();
}
