package com.strandls.landscape.dao;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.strandls.landscape.pojo.FieldContent;

public class FieldContentDao extends AbstractDao<FieldContent, Long> {

	private static final String FIELD_ID = "fieldId";
	private static final String LANGUAGE_ID = "languageId";

	@Inject
	protected FieldContentDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public FieldContent findById(Long id) {
		Session session = sessionFactory.openSession();
		try {
			return session.get(FieldContent.class, id);
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public FieldContent getFieldContent(Long fieldId, Long languageId) {
		String queryStr = "" + "from " + daoType.getSimpleName() + " t " + "where t." + FIELD_ID + " = "
				+ " :fieldId and " + "t." + LANGUAGE_ID + " = " + " :languageId";
		Session session = sessionFactory.openSession();
		Query<FieldContent> query = session.createQuery(queryStr);
		query.setParameter(FIELD_ID, fieldId);
		query.setParameter(LANGUAGE_ID, languageId);

		try {
			return query.getSingleResult();
		} finally {
			session.close();
		}
	}
}
