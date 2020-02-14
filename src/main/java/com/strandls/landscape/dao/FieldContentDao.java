package com.strandls.landscape.dao;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;
import com.strandls.landscape.pojo.FieldContent;

public class FieldContentDao extends AbstractDao<FieldContent, Long>{

	private static final String FIELD_ID = "fieldId";
	private static final String LANGUAGE_ID = "languageId";

	@Inject
	protected FieldContentDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public FieldContent findById(Long id) {
		Session session = sessionFactory.openSession();
		FieldContent entity = null;
		try {
			entity = session.get(FieldContent.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}

	public FieldContent getFieldContent(Long fieldId, Long languageId) {
		String queryStr = "" +
			    "from "+daoType.getSimpleName()+" t " +
			    "where t."+FIELD_ID+" = "+" :fieldId and "
			    		+ "t." + LANGUAGE_ID + " = " + " :languageId" ;
		Session session = sessionFactory.openSession();
		org.hibernate.query.Query query = session.createQuery(queryStr);
		query.setParameter("fieldId", fieldId);
		query.setParameter("languageId", languageId);
		
		FieldContent entity = null;
		try {
			entity = (FieldContent) query.getSingleResult();
		} catch(NoResultException e) {
			throw e;
		}
		session.close();
		return entity;
	}
}
