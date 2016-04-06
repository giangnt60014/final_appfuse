package com.giangnt.webapp.service;

import org.appfuse.service.GenericManager;

import com.giangnt.webapp.model.Link;

public interface LinkManager extends GenericManager<Link, Long> {

	void saveLink(Link link);
}
