package com.strandls.landscape.dao;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.strandls.landscape.pojo.Landscape;

public class LandscapeDao extends AbstractDao<Landscape, Long> {

	@Inject
	protected LandscapeDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Landscape findById(Long id) {
		Session session = sessionFactory.openSession();
		try {
			return session.get(Landscape.class, id);
		} finally {
			session.close();
		}
	}

	/**
	 * Overridden just to sort it by site number rather than id.
	 */
	@Override
	public List<Landscape> findAll() {
		return findAll(-1, -1);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Landscape> findAll(int limit, int offset) {
		String queryStr = "" + "from " + daoType.getSimpleName() + " t " + " order by siteNumber";
		Session session = sessionFactory.openSession();
		Query<Landscape> query = session.createQuery(queryStr);

		try {
			if (limit > 0 && offset >= 0)
				query = query.setFirstResult(offset).setMaxResults(limit);
			return query.getResultList();

		} finally {
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public Landscape findBySiteNumber(Long siteNumber) {
		String queryStr = "" + "from Landscape t " + " where t.siteNumber = :siteNumber";
		Session session = sessionFactory.openSession();
		Query<Landscape> query = session.createQuery(queryStr);
		query.setParameter("siteNumber", siteNumber);
		try {
			return query.getSingleResult();
		} finally {
			session.close();
		}
	}
}
