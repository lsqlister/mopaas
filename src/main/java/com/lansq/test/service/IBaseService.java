package com.lansq.test.service;

import java.io.Serializable;
import java.util.List;

public interface IBaseService<T> {
	void save(T t);

	void update(T t);

	void delete(Serializable id);

	T get(Serializable id);

	List<T> getAll();

	// List findByHql(String hql, Object... objects);

}
