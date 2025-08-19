package com.strandls.landscape.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.strandls.landscape.pojo.FieldTemplate;

import jakarta.inject.Inject;

public class FieldTemplateDao extends AbstractDao<FieldTemplate, Long> {

	@Inject
	protected FieldTemplateDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public FieldTemplate findById(Long id) {
		Session session = sessionFactory.openSession();
		try {
			return session.get(FieldTemplate.class, id);
		} finally {
			session.close();
		}
	}
}
