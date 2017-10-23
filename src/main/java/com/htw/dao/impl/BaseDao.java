package com.htw.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.htw.dao.IBaseDao;

/**
 * @author Liuzuorui
 * 2014�?�?8�?上午11:49:03
 */
@Repository("baseDao")
public class BaseDao<T> implements IBaseDao<T> {
	@Autowired
	private HibernateTemplate hibernateTemplate;

	public void add(T t) throws Exception {
		hibernateTemplate.save(t);
	}

	public void update(T t) throws Exception {
		hibernateTemplate.update(t);
	}
	public void delete(T t) throws Exception {
		hibernateTemplate.delete(t);
	}

	public void deleteById(Serializable id) throws Exception {
		String hql = "delete " + getEntityClassName() + " where "+getPkColunmName()+"=?";
		executeUpdate(hql,id);
	}
	
	
	@SuppressWarnings("unchecked")
	public T findById(Serializable id) throws Exception {
		return (T)hibernateTemplate.get(getEntityClassName(), id);
		
	}
	
	
	
	


	/**
	 * 执行查询以外的操�?
	 * @author Liuzuorui
	 * @param hql
	 * @param objs
	 * 2014�?�?1�?上午11:42:19
	 */
	protected void executeUpdate(final String hql, final Object ... objects){
		hibernateTemplate.execute(new HibernateCallback<Integer>() {
			public Integer doInHibernate(Session session) throws HibernateException {
				Query createQuery = createQuery(session, hql, objects);
				return createQuery.executeUpdate();
			}
		});
	}
	/**
	 * 得到泛型中的实体类型
	 * @author Liuzuorui
	 * @return
	 * 2014�?�?8�?下午2:32:35
	 */
	protected Class<T> getEntityClass(){
		@SuppressWarnings("unchecked")
		Class<T> entityClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		return entityClass;
	}
	/**
	 * @Author: Charles
	 * @Description: 获取表主键类�?
	 * @param clazz
	 * @return Type:
	 */
	public Type getPkType() {
		ClassMetadata meta = hibernateTemplate.getSessionFactory().getClassMetadata(getEntityClass());
		return meta.getIdentifierType();
	}
	/**
	 * 获取主键�?
	 * @author Liuzuorui
	 * @return
	 * 2014�?�?1�?下午2:42:49
	 */
	public String getPkColunmName(){
		ClassMetadata meta = hibernateTemplate.getSessionFactory().getClassMetadata(getEntityClass());
		return meta.getIdentifierPropertyName();
	}
	
	
	/**
	 * 获取实体类型�?
	 * @author Liuzuorui
	 * @return
	 * 2014�?�?8�?下午2:33:01
	 */
	protected String getEntityClassName() {
		ClassMetadata meta = hibernateTemplate.getSessionFactory().getClassMetadata(getEntityClass());
		return meta.getEntityName();
	}
	/**
	 * 返回设置好参数的查询对象
	 * @author Liuzuorui
	 * @param query
	 * @param objects
	 * 2014�?�?1�?下午2:07:56
	 */
	private Query createQuery(Session session,String hql, Object ... objects) {
		System.out.println(hql);
		Query query = session.createQuery(hql);
		for (int i = 0; i < objects.length; i++) {
			query.setParameter(i, objects[i]);
		}
		return query;
	}

	public List<T> findAll() throws Exception {
		// TODO Auto-generated method stub
		
		String hql = "from " + getEntityClassName();
		System.out.println(hql);
		return hibernateTemplate.find(hql);
	
	}

	public T findForObject(T entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<T> query(String hql) {
		 
		return hibernateTemplate.find(hql);
	}
}
