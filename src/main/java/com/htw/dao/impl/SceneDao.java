package com.htw.dao.impl;

import org.springframework.stereotype.Repository;

import com.htw.dao.ISceneDao;
import com.htw.entity.Scene;

@Repository("sceneDao")
public class SceneDao extends BaseDao<Scene> implements ISceneDao{

}
