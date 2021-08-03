package com.strandls.landscape.dao;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.strandls.landscape.pojo.PageField;

public class PageFieldDao extends AbstractDao<PageField, Long>{

	public static final String PROTECTED_AREA_ID = "protectedAreaId";
	private static final String TEMPLATE_ID = "templateId";
	@Inject
	protected PageFieldDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public PageField findById(Long id) {
		Session session = sessionFactory.openSession();
		try {
			return session.get(PageField.class, id);
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public PageField getPageField(Long protectedAreaId, Long templateId) {
		String queryStr = "" +
			    "from "+daoType.getSimpleName()+" t " +
			    "where t."+PROTECTED_AREA_ID+" = "+" :protectedAreaId and "
			    		+ "t." + TEMPLATE_ID + " = " + " :templateId" ;
		Session session = sessionFactory.openSession();
		Query<PageField> query = session.createQuery(queryStr);
		query.setParameter(PROTECTED_AREA_ID, protectedAreaId);
		query.setParameter(TEMPLATE_ID, templateId);
		
		try {
			return query.getSingleResult();
		} finally {
			session.close();
		}
	}
}
