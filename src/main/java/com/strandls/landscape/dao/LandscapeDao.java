package com.strandls.landscape.dao;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.strandls.landscape.pojo.Landscape;

public class LandscapeDao extends AbstractDao<Landscape, Long>{

	@Inject
	protected LandscapeDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Landscape findById(Long id) {
		Session session = sessionFactory.openSession();
		Landscape entity = null;
		try {
			entity = session.get(Landscape.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}
}
