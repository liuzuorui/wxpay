package com.htw.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.htw.dao.IConfigDao;
import com.htw.entity.Config;
import com.htw.service.IConfigService;

@Service("configService")
@Transactional
public class ConfigService implements IConfigService {

	@Resource
	private IConfigDao configDao;

	@Override
	public Config getByProperty(String name, String value){
		return configDao.getByProperty(name, value);
	}

}
