package com.htw.dao.impl;

import org.springframework.stereotype.Repository;

import com.htw.dao.IAttentionDao;
import com.htw.entity.Attention;

@Repository("attentionDao")
public class AttentionDao extends BaseDao<Attention> implements IAttentionDao{

}
