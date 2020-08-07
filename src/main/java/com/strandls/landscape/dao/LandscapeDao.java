package com.strandls.landscape.dao;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;

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
	
	/**
	 * Overridden just to sort it by site number rather than id.
	 */
	@Override
	public List<Landscape> findAll() {
		return findAll(-1, -1);
	}
	@Override
	public List<Landscape> findAll(int limit, int offset) {
		String queryStr = "" +
			    "from "+daoType.getSimpleName()+" t " +
			    " order by siteNumber";
		Session session = sessionFactory.openSession();
		org.hibernate.query.Query query = session.createQuery(queryStr);

		List<Landscape> resultList = new ArrayList<Landscape>();
		try {
			if(limit>0 && offset >= 0)
				query = query.setFirstResult(offset).setMaxResults(limit);
			resultList = query.getResultList();
			
		} catch (NoResultException e) {
			throw e;
		}
		session.close();
		return resultList;
	}
}
