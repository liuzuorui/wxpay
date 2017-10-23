package com.htw.dao;

import com.htw.entity.Config;
public interface IConfigDao  extends IBaseDao<Config> {
	public Config getByProperty(String name, String value);
}
