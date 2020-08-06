package com.strandls.landscape.dao;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
		PageField entity = null;
		try {
			entity = session.get(PageField.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}

	public PageField getPageField(Long protectedAreaId, Long templateId) {
		String queryStr = "" +
			    "from "+daoType.getSimpleName()+" t " +
			    "where t."+PROTECTED_AREA_ID+" = "+" :protectedAreaId and "
			    		+ "t." + TEMPLATE_ID + " = " + " :templateId" ;
		Session session = sessionFactory.openSession();
		org.hibernate.query.Query query = session.createQuery(queryStr);
		query.setParameter("protectedAreaId", protectedAreaId);
		query.setParameter("templateId", templateId);
		
		PageField entity = null;
		try {
			entity = (PageField) query.getSingleResult();
		} catch(NoResultException e) {
			throw e;
		}
		session.close();
		return entity;
	}
}
