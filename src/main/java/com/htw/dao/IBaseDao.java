package com.htw.dao;

import java.util.List;

/**
 * @author Liuzuorui
 * 2014�?�?8�?上午11:44:14
 */
public interface IBaseDao<T> {
	/**
	 * 添加
	 * @author Liuzuorui
	 * @param t
	 * @throws Exception
	 * 2014�?�?8�?上午11:48:02
	 */
	public void add(T t) throws Exception;
	/**
	 * 修改
	 * @author Liuzuorui
	 * @param t
	 * @throws Exception
	 * 2014�?�?8�?上午11:48:07
	 */
	public void update(T t) throws Exception;
	/**
	 * 按照对象删除
	 * @author Liuzuorui
	 * @param t
	 * @throws Exception
	 * 2014�?�?8�?上午11:48:16
	 */
	public void delete(T t) throws Exception;
	/**
	 * 按照id删除
	 * @author Liuzuorui
	 * @param id
	 * @throws Exception
	 * 2014�?�?8�?上午11:48:26
	 */
	public void deleteById(java.io.Serializable id) throws Exception;
	/**
	 * 查询�?��
	 * @author Liuzuorui
	 * @throws Exception
	 * 2014�?�?8�?上午11:48:35
	 */
	public List<T> findAll() throws Exception;

	public T findById(java.io.Serializable id) throws Exception;
	/**
	 * 以对象作为查询条件进行查�?
	 * @author Liuzuorui
	 * @param entity 作为条件的对�?
	 * @return
	 * 2014�?�?1�?下午4:50:14
	 */
	public T findForObject(final T entity);
	/**
	 * 以对象作为查询条件进行查�?
	 * @author Liuzuorui
	 * @param entity 作为条件的结果集
	 * @param pageBean 分页信息对象
	 * @return
	 * 2014�?�?1�?下午2:08:09
	 */
	
	
	public List<T> query(String hql);
}
