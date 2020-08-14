package com.strandls.landscape.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.strandls.landscape.pojo.DownloadLog;

public class DownloadLogDao extends AbstractDao<DownloadLog, Long>{

	private static final String AUTHOR_ID = "authorId";
	private static final String SOURCE_TYPE = "sourceType";
	private static final Object LANDSCAPE = "Landscape";

	@Inject
	protected DownloadLogDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public DownloadLog findById(Long id) {
		Session session = sessionFactory.openSession();
		DownloadLog entity = null;
		try {
			entity = session.get(DownloadLog.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}

	public List<DownloadLog> getDownloadLogByAutherId(Long authorId) {
		String queryStr = "" +
			    "from "+daoType.getSimpleName()+" t " +
			    "where t."+AUTHOR_ID+" = "+" :authorId and "
			    		+ "t." + SOURCE_TYPE + " = " + " :sourceType" ;
		Session session = sessionFactory.openSession();
		org.hibernate.query.Query query = session.createQuery(queryStr);
		query.setParameter(AUTHOR_ID, authorId);
		query.setParameter(SOURCE_TYPE, LANDSCAPE);
		
		List<DownloadLog> downloadLogs;
		try {
			downloadLogs = query.getResultList();
		} catch(NoResultException e) {
			throw e;
		}
		session.close();
		return downloadLogs;
	}

	public List<DownloadLog> getAllDownloadLogs() {
		String queryStr = "" +
			    "from "+daoType.getSimpleName()+" t " +
			    "where t." + SOURCE_TYPE + " = " + " :sourceType" ;
		Session session = sessionFactory.openSession();
		org.hibernate.query.Query query = session.createQuery(queryStr);
		query.setParameter(SOURCE_TYPE, LANDSCAPE);
		
		List<DownloadLog> downloadLogs;
		try {
			downloadLogs = query.getResultList();
		} catch(NoResultException e) {
			throw e;
		}
		session.close();
		return downloadLogs;
	}
}
