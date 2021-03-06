package com.lansq.test.service.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lansq.test.dao.BaseDao;
import com.lansq.test.service.IBaseService;

@Service
@Transactional
public abstract class BaseServiceImpl<T> implements IBaseService<T> {
	@Autowired
	private BaseDao<T> baseDao;
	private Class<T> entityClass;

	public BaseServiceImpl() {
		Class clazz = getClass();
		Type type = clazz.getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) type;
			entityClass = (Class) parameterizedType.getActualTypeArguments()[0];
		}
		System.out.println("BaseServiceImpl:" + entityClass);
	}

	public void save(T t) {
		baseDao.save(t);
	}

	public void update(T t) {
		baseDao.update(t);
	}

	public void delete(Serializable id) {
		baseDao.delete(entityClass, id);
	}

	public T get(Serializable id) {
		return baseDao.get(entityClass, id);
	}

	public List<T> getAll() {
		return baseDao.getAll(entityClass);
	}

	// public List findByHql(String hql, Object... objects) {
	// return baseDao.findByHql(hql, objects);
	// }

}
