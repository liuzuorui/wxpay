package com.htw.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.htw.dao.IConfigDao;
import com.htw.entity.Config;

@Repository("configDao")
public class ConfigDao extends BaseDao<Config> implements IConfigDao{
	public Config getByProperty(String name, String value){
		String hql = "from " + Config.class.getSimpleName() 
			+ " where " + name + " = '"+value+"'";
		
		List<Config> beans =this.query(hql);
		if(beans == null || beans.size() == 0){
			return null;
		}
		else{
			return beans.get(0);
		}
	}
}
