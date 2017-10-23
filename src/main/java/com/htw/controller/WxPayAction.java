package com.htw.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.htw.entity.Config;
import com.htw.service.IConfigService;
import com.htw.service.IPayService;
import com.tencent.common.HttpsRequest;
import com.tencent.common.XMLParser;

@Controller
public class WxPayAction {

	@Resource
	private IPayService payService;

	@Resource
	private IConfigService configService;

	@RequestMapping(value = "/scan")
	public void scan(HttpServletRequest request, HttpServletResponse response) {

		InputStream inputStream;
		try {
			inputStream = request.getInputStream();
			String postData = IOUtils.toString(inputStream, "UTF-8");
			System.out.println(postData);

			if (postData != null) {
				Map<String, Object> dataMap = XMLParser.getMapFromXML(postData);

				Config actConfig = configService.getByProperty("key", "act");// ///活动开关

				if (actConfig != null && actConfig.getValue().equals("1")) {
					String event = (String) dataMap.get("Event");
					System.out.println("event===========================" + event);
					if (event != null && event.equals("subscribe")) {
						// /搜索公众号关注事件（通过场景二维码扫描关注也会触发关注事件）
						
						 String eventkey = (String) dataMap.get("EventKey");
						 if (eventkey != null && isQrNum(eventkey)) {
							 String scanId = eventkey.substring(eventkey.indexOf("qrscene_")+8);
							 payService.saveScene(dataMap,scanId);
							 //payService.payCoupon(postData,"抢啊");
							 getDefauleResponse(dataMap, response,scanId);
						 }else{
							 getEmptyResponse(response);
						 }
					} else if (event != null && event.equals("SCAN")) {
						// /扫描公众号关注事件
						String eventkey = (String) dataMap.get("EventKey");
						if (eventkey != null && isNum(eventkey)) {
							payService.saveScene(dataMap,eventkey);
							//payService.payCoupon(postData,"抢啊");
							getDefauleResponse(dataMap, response,eventkey);
						} else {
							getEmptyResponse(response);
						}

					} else {
						getEmptyResponse(response);
					}

				} else if (actConfig != null
						&& actConfig.getValue().equals("0")) {
					String msgType = (String) dataMap.get("MsgType");

					if (msgType != null && msgType.equals("text")) {
						String content = (String) dataMap.get("Content");
						if (content != null && content.equals("美猴王")) {
							
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							Date beginDate = sdf.parse("2016-02-04");
							Date endDate = sdf.parse("2016-02-10");
							Date nowDate = new Date();
							if(nowDate.after(beginDate)&&nowDate.before(endDate)){
								payService.payCoupon(postData, "美猴王");
							}else{
								System.out.println("活动没开始或过期来 ");
							}
							

						}
					}
					getEmptyResponse(response);
				} else {
					getEmptyResponse(response);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			getEmptyResponse(response);
		}

		System.out.println("weixin  work");
	}

	public static void main(String[] args) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date beginDate = sdf.parse("2016-02-03");
		Date endDate = sdf.parse("2016-02-04");
		Date nowDate = new Date();
		if(nowDate.after(beginDate)&&nowDate.before(endDate)){
			System.out.println("22");
		}else{
			System.out.println("111");
		}
	}
	public void getDefauleResponse(Map<String, Object> dataMap,
			HttpServletResponse response,String scanId) {

		Map<String, Object> replyMap = new HashMap<String, Object>();
		 
		replyMap.put("ToUserName", dataMap.get("FromUserName").toString());
		replyMap.put("FromUserName", dataMap.get("ToUserName").toString());
		replyMap.put("CreateTime", String.valueOf(System.currentTimeMillis()));
		replyMap.put("MsgType", "text");
		String url = "http://www.91htw.com/weixin/insurance/?sid=" + scanId;
		replyMap.put("Content", "欢迎您，点击" + url
				+ "立即报名，抢先免费领取“回家险”，限量9988份，先到先得。");

		try {
			System.out.println(HttpsRequest.mapToXML(replyMap));
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			response.setContentType("application/json;charset=UTF-8");
			response.setHeader("Cache-Control", "no-store");
			response.getWriter().print(HttpsRequest.mapToXML(replyMap));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public void getEmptyResponse(HttpServletResponse response) {

		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			response.setContentType("application/json;charset=UTF-8");
			response.setHeader("Cache-Control", "no-store");
			response.getWriter().print("");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	private boolean isNum(String numStr) {

		try {
			
			Double num = Double.parseDouble(numStr);
			if (num > 99 && num < 1001) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			return false;
		}

	}
	private boolean isQrNum(String qrStr) {

		try {
			String numStr = qrStr.substring(qrStr.indexOf("qrscene_")+8);
			
			Double num = Double.parseDouble(numStr);
			if (num > 99 && num < 1001) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			return false;
		}

	}
	 
}
