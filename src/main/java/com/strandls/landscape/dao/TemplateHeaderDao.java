package com.strandls.landscape.dao;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

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
		try {
			return session.get(TemplateHeader.class, id);
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public TemplateHeader getHeader(Long templateId, Long languageId) {
		String queryStr = "" +
			    "from "+daoType.getSimpleName()+" t " +
			    "where t."+TEMPLATE_ID+" = "+" :templateId and "
			    		+ "t." + LANGUAGE_ID + " = " + " :languageId" ;
		Session session = sessionFactory.openSession();
		Query<TemplateHeader> query = session.createQuery(queryStr);
		query.setParameter(TEMPLATE_ID, templateId);
		query.setParameter(LANGUAGE_ID, languageId);
		
		TemplateHeader entity = null;
		try {
			entity = query.getSingleResult();
		} finally {
			session.close();
		}
		return entity;
	}

	@SuppressWarnings("unchecked")
	public List<TemplateHeader> getByLanguageId(Long languageId) {
		String queryStr = "" +
			    "from TemplateHeader t " +
			    " where t.languageId = :languageId";
		Session session = sessionFactory.openSession();
		Query<TemplateHeader> query = session.createQuery(queryStr);
		query.setParameter(LANGUAGE_ID, languageId);
		try {
			return query.getResultList();
		} finally {
			session.close();
		}
	}
}
