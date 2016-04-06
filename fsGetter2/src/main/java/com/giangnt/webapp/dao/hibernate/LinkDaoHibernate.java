package com.giangnt.webapp.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.appfuse.dao.SearchException;
import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.appfuse.model.User;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.giangnt.webapp.dao.LinkDao;
import com.giangnt.webapp.model.Fsaccount;
import com.giangnt.webapp.model.Link;

@Repository("linkDao")
public class LinkDaoHibernate extends GenericDaoHibernate<Link, Long> implements LinkDao {

	public LinkDaoHibernate() {
		super(Link.class);
	}

	@Override
	public List<Link> findByUser(User user) {
		
		List<Link> links = new ArrayList<Link>();
		links = getSession().createCriteria(Link.class).add(Restrictions.eq("user", user)).list();
		return links;
	}

	@Override
	public List<Link> getAllLink() {
		return getSession().createQuery("from "+Link.class.getName()).list();
	}

	@Override
	public void saveLink(Link link) {
		getSession().save(link);
	}

}
