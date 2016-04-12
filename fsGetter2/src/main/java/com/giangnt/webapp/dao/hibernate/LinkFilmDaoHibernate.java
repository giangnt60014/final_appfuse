package com.giangnt.webapp.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.appfuse.model.User;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.giangnt.webapp.dao.LinkFilmDao;
import com.giangnt.webapp.model.Link;
import com.giangnt.webapp.model.LinkFilm;

@Repository("linkFilmDao")
public class LinkFilmDaoHibernate extends GenericDaoHibernate<LinkFilm, Long> implements LinkFilmDao {

	public LinkFilmDaoHibernate() {
		super(LinkFilm.class);
	}


	@Override
	public List<LinkFilm> getAllLinkFilm() {
		return getSession().createQuery("from "+LinkFilm.class.getName()).list();
	}

	@Override
	public void saveLinkFilm(LinkFilm link) {
		getSession().save(link);
	}

}
