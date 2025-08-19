package com.strandls.landscape.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.strandls.landscape.pojo.Language;

import jakarta.inject.Inject;

public class LanguageDao extends AbstractDao<Language, Long> {

	@Inject
	protected LanguageDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Language findById(Long id) {
		Session session = sessionFactory.openSession();
		try {
			return session.get(Language.class, id);
		} finally {
			session.close();
		}
	}
}
