package com.strandls.landscape.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;
import com.strandls.landscape.pojo.FieldTemplate;

public class FieldTemplateDao extends AbstractDao<FieldTemplate, Long>{

	@Inject
	protected FieldTemplateDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public FieldTemplate findById(Long id) {
		Session session = sessionFactory.openSession();
		FieldTemplate entity = null;
		try {
			entity = session.get(FieldTemplate.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}
}
