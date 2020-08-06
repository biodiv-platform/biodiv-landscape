package com.strandls.landscape.dao;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.strandls.landscape.pojo.Language;

public class LanguageDao extends AbstractDao<Language, Long>{

	@Inject
	protected LanguageDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Language findById(Long id) {
		Session session = sessionFactory.openSession();
		Language entity = null;
		try {
			entity = session.get(Language.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}
}
