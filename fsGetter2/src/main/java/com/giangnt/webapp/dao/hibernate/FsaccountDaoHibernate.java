package com.giangnt.webapp.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.giangnt.webapp.dao.FsaccountDao;
import com.giangnt.webapp.model.Fsaccount;

@Repository("fsaccountDao")
public class FsaccountDaoHibernate extends GenericDaoHibernate<Fsaccount, Long> implements FsaccountDao{

	public FsaccountDaoHibernate() {
		super(Fsaccount.class);
	}

	public List<Fsaccount> findByAccount(String account) {
		@SuppressWarnings("unchecked")
		List<Fsaccount> fsaccounts = new ArrayList<Fsaccount>();
		fsaccounts = (List<Fsaccount>) getSession().createCriteria(Fsaccount.class).add(Restrictions.eq("account", account)).list(); 
		fsaccounts = getSession().createQuery("from Fsaccount").list();
		return fsaccounts;
	}



}
