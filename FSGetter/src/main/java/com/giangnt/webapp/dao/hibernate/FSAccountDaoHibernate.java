package com.giangnt.webapp.dao.hibernate;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.giangnt.webapp.dao.FSAccountDao;
import com.giangnt.webapp.model.FSAccount;

@Repository("fsAccountDao")
public class FSAccountDaoHibernate extends GenericDaoHibernate<FSAccount, Long> implements FSAccountDao {

	public FSAccountDaoHibernate() {
		super(FSAccount.class);
	}

	@Override
	public FSAccount findByAccName(String accName) {
		// TODO Auto-generated method stub
		List<FSAccount> list = getSession().createCriteria(FSAccount.class).add(Restrictions.eq("acc_name", accName)).list(); 
		if (!list.isEmpty() && list.size()>0){
			return list.get(0);
		}
		return null;
		
	}

}
