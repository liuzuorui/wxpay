package com.htw.service;

import java.util.Map;

import com.htw.entity.Attention;
import com.htw.entity.Scene;

public interface IPayService {

	
	public String payCoupon(String postData,String actName) throws Exception;
	
	
	public void save() throws Exception;
	
	public void saveAttention(Attention attention) throws Exception;
	
	
	public void saveScene(Map<String, Object> dataMap,String scanId) throws Exception;
	
	public void updateScene(Scene scene) throws Exception;
	
	public Scene  findScene(String openId);
}
