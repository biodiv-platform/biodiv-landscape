package com.strandls.landscape.dao;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;
import com.strandls.landscape.pojo.TemplateHeader;

public class TemplateHeaderDao extends AbstractDao<TemplateHeader, Long>{

	private static final String TEMPLATE_ID = "templateId";
	private static final String LANGUAGE_ID = "languageId";

	@Inject
	protected TemplateHeaderDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public TemplateHeader findById(Long id) {
		Session session = sessionFactory.openSession();
		TemplateHeader entity = null;
		try {
			entity = session.get(TemplateHeader.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}

	public TemplateHeader getHeader(Long templateId, Long languageId) {
		String queryStr = "" +
			    "from "+daoType.getSimpleName()+" t " +
			    "where t."+TEMPLATE_ID+" = "+" :templateId and "
			    		+ "t." + LANGUAGE_ID + " = " + " :languageId" ;
		Session session = sessionFactory.openSession();
		org.hibernate.query.Query query = session.createQuery(queryStr);
		query.setParameter("templateId", templateId);
		query.setParameter("languageId", languageId);
		
		TemplateHeader entity = null;
		try {
			entity = (TemplateHeader) query.getSingleResult();
		} catch(NoResultException e) {
			throw e;
		}
		session.close();
		return entity;
	}
}
