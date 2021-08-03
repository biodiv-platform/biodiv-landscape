package com.strandls.landscape.service;

import java.util.List;

import com.strandls.landscape.dao.AbstractDao;

public abstract class AbstractService<T> {

	protected AbstractDao<T, Long> dao;

	protected AbstractService(AbstractDao<T, Long> dao) {
		this.dao = dao;
	}

	public T save(T entity) {
		this.dao.save(entity);
		return entity;
	}

	public T update(T entity) {
		this.dao.update(entity);
		return entity;
	}

	public T delete(Long id) {
		T entity = this.dao.findById(id);
		this.dao.delete(entity);
		return entity;
	}

	public T findById(Long id) {
		return this.dao.findById(id);
	}

	public List<T> findAll() {
		return this.dao.findAll();
	}

	public List<T> findAll(int limit, int offset) {
		return this.dao.findAll(limit, offset);
	}
}
