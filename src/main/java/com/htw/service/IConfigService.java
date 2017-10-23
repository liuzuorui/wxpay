package com.htw.service;

import com.htw.entity.Config;

public interface IConfigService {

	
	public Config getByProperty(String name,String value);
	
}
