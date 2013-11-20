package com.giangnt.webapp.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.appfuse.dao.SearchException;
import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.criterion.Restrictions;

import com.giangnt.webapp.dao.UserDao;
import com.giangnt.webapp.model.User;

public class UserDaoHibernate extends GenericDaoHibernate<User, Long> implements UserDao {

	public UserDaoHibernate(Class<User> persistentClass) {
		super(persistentClass);
	}

	@Override
	public List<User> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getAllDistinct() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> search(String searchTerm) throws SearchException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User get(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User save(User object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(User object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<User> findByNamedQuery(String queryName,
			Map<String, Object> queryParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void reindex() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reindexAll(boolean async) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<User> findByUsername(String username) {
		// TODO Auto-generated method stub
		return getSession().createCriteria(User.class).add(Restrictions.eq("lastName", username)).list();
	}

}
