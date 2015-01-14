package com.lansq.test.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 
 * 处理所有持久层操作,需要实例化
 * 
 */
@Repository
public class BaseDao<T> {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void save(T t) {
		getSession().save(t);
	}

	public void update(T t) {
		getSession().update(t);
	}

	public void delete(Class<T> entityClass, Serializable id) {
		T t = get(entityClass, id);
		if (t != null) {
			getSession().delete(t);
		} else {
			throw new RuntimeException("id==null");
		}
	}

	public T get(Class<T> entityClass, Serializable id) {
		return (T) getSession().get(entityClass, id);
	}

	public List<T> getAll(Class<T> entityClass) {
		return getSession().createCriteria(entityClass).list();
	}
	// public List findByHql(String hql, Object... objects) {
	// System.out.println("findByHql hql:" + hql);
	// System.out.println("findByHql objects:" + Arrays.toString(objects));
	// return getSession().find(hql, objects);
	// }

}
