package com.giangnt.webapp.dao.hibernate;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.giangnt.webapp.dao.FsaccountDao;
import com.giangnt.webapp.model.Fsaccount;

@Repository("fsaccountDao")
@Transactional
public class FsaccountDaoHibernate extends GenericDaoHibernate<Fsaccount, Long> implements FsaccountDao{

	public FsaccountDaoHibernate() {
		super(Fsaccount.class);
	}

	public List<Fsaccount> findByAccount(String account) {
		return getSession().createCriteria(Fsaccount.class).add(Restrictions.eq("account", account)).list();
	}



}
