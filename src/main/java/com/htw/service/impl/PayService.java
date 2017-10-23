package com.htw.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.htw.dao.IAttentionDao;
import com.htw.dao.ISceneDao;
import com.htw.entity.Attention;
import com.htw.entity.Scene;
import com.htw.service.IPayService;
import com.tencent.common.Configure;
import com.tencent.common.HttpsRequest;
import com.tencent.common.Signature;
import com.tencent.common.XMLParser;

@Service("payService")
@Transactional
public class PayService implements IPayService {

	@Resource
	private IAttentionDao attentionDao;
	
	
	@Resource
	private ISceneDao sceneDao;

	public String payCoupon(String postData,String actName) throws Exception {

		Map<String, Object> dataMap = XMLParser.getMapFromXML(postData);

		String openId = dataMap.get("FromUserName").toString();

		List<Attention> list = this.find(openId);

		if (list != null && list.size() > 0) {
			System.out.println(openId +" 已关注并领用红包！");
		} else {
//			if(halfRandom()){
			if(true){
				System.out.println(openId +" 50%幸运星，抽到红包");
				Map<String, Object> signMap = new HashMap<String, Object>();
				signMap.put("nonce_str", Signature.getRandomStringByLength(32)); // /随机字符串
				signMap.put("mch_billno", this.getBillNo()); // 商户订单号
				signMap.put("mch_id", Configure.getMchid()); // 商户号
				signMap.put("wxappid", Configure.getAppid()); // 公众账号appid
				signMap.put("send_name", "好胎屋"); // 商户名称
				signMap.put("re_openid", openId); // 用户openid
				int amount = this.getRandomAmount(100,150);
				signMap.put("total_amount", amount); // 付款金额
				signMap.put("total_num", 1); // 红包发放总人数
				signMap.put("wishing", "花少钱，养好车，猴年你最旺！"); // 红包祝福语
				signMap.put("client_ip", "127.0.0.1"); // ip地址
				signMap.put("act_name", actName); // 活动签名
				signMap.put("remark", "关注公众号抢红包"); // 备注
				String sign = Signature.getSign(signMap);
				signMap.put("sign", sign);// 签名

				HttpsRequest httpRequest = new HttpsRequest();

				String result = httpRequest
						.sendPostMap(
								"https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack",
								signMap);

				System.out.println(result);
				Map<String, Object> resultMap = XMLParser.getMapFromXML(result);

				if (resultMap.get("return_code").equals("SUCCESS")) {
					Attention attention = new Attention();
					attention.setAmount(Double.parseDouble(String.valueOf(amount)));
					attention.setOpenId(openId);
					attention.setWin(Attention.STATUS_WIN);
					this.saveAttention(attention);
				} else {
					String error = (String) resultMap.get("return_code")
							+ (String) resultMap.get("err_code")
							+ (String) resultMap.get("err_code_des");
					return error;
				}
			}else{
				System.out.println(openId +" 很遗憾，50%没有红包，也不能再领了");
				Attention attention = new Attention();
				attention.setAmount(0.0);
				attention.setOpenId(openId);
				attention.setWin(Attention.STATUS_NOTWIN);
				this.saveAttention(attention);
			}

		}

		return null;
	}

	public int getRandomAmount(int min,int max) {
		Random random = new Random();
		int amount = random.nextInt(max) % (max - min + 1) + min;
		return amount;
	}
	 
	public String getBillNo(){
		SimpleDateFormat sdf  = new SimpleDateFormat("yyyyMMddHHmmss");
        System.out.println(sdf.format(new Date()));
       Random random = new Random();
        String billNo = sdf.format(new Date()) + random.nextInt(10);
        return billNo;
	}

	public List<Attention> find(String openId) {

		List<Attention> list = attentionDao
				.query(" from Attention where openId='" + openId + "'");

		return list;
	}

	public void save() throws Exception {

		List<Attention> list = attentionDao
				.query(" from Attention where openId='" + 111 + "'");

		if (list != null && list.size() != 0) {
			System.out.println("已有勿扰");
		} else {
			System.out.println("22222");
			Attention attention = new Attention();

			attention.setAmount(100.0d);
			attention.setOpenId("111");
			attention.setCreateTime(new Date());

			attentionDao.add(attention);
		}

	}

	/**
	 * 获取一定长度的随机字符串
	 * 
	 * @param length
	 *            指定字符串长度
	 * @return 一定长度的字符串
	 */
	private String getRandomBillByLength(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	public void saveAttention(Attention attention) throws Exception {

		attention.setCreateTime(new Date());
		attention.setUpdateTime(new Date());
		attentionDao.add(attention);

	}
	/**
	 * 50%概率算法
	 * @return
	 */
	public boolean halfRandom(){
		Random random = new Random();
		int s =random.nextInt(2);
		if(s==1){
			return true ;
		}else{
			return false ;
		}
	}
	
	/**
	 * 单个中奖概率
	 * @param probability
	 * @return
	 */
	public static boolean randomProbability(int probability){
		Random random = new Random();
		int s =random.nextInt(100);
		if(s<probability){
			return true ;
		}else{
			return false ;
		}
	}

	/**
	 * 多个中奖概率
	 * @param result 概率组合
	 * @param probability  中奖概率
	 * @return
	 */
	public static boolean winProbability(Map<String,String> result,int probability){
		
		int random = (int)(Math.random()*100);
		
		if(result.get(String.valueOf(random)).equals(String.valueOf(probability))){
			return true ;
		}
	     
		return false;
		
	}
	/**
	 * 
	 * @param probability概率数组，数组内总和为100
	 * @return
	 */
	public static Map<String,String> probabilityMap(int[] probability){
		
		Map<String,String> result = new LinkedHashMap<String,String>();
		
		int length = probability.length;
		
		int begin = 0;
		int end = 0;
		for(int i=0;i<length;i++){
			end = end + probability[i];
			for(int k=begin; k<end; k++){  
	            result.put(String.valueOf(k), String.valueOf(probability[i]));  
			}
			begin = end ;
		}
		return result;
	}
	
	public static void main(String[] args) {
		
	   int m = 0;
 	   int n = 0 ;
 	   int b = 0;
	  int[] p = {10,70,20};
	  Map<String,String> result = probabilityMap(p);
	  System.out.println("Result:"+result);    
 	  for(int i=0;i<100000000;i++){
 		/*if(randomProbability(70)){
 			m++;
 		}else{
 			n++;
 		}*/
 		  
 		  
 		  if(winProbability(result,70)){
  			m++;
 		  }
 		  if(winProbability(result,20)){
  			n++;
	  	   }
 		 if(winProbability(result,10)){
	  			b++;
	  		} 
 	  }
      	 
      System.out.println("m===="+m);
	  System.out.println("n===="+n);
	  System.out.println("b===="+b);
	}
	
	public Scene  findScene(String openId) {
		List<Scene> list = sceneDao
				.query(" from Scene where openId='" + openId + "'");

		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	
	@Override
	public void saveScene(Map<String, Object> dataMap,String scanId) throws Exception {
	 
		String openId = dataMap.get("FromUserName").toString();
		Scene dbscene = this.findScene(openId);
		
		if(dbscene!=null){
			dbscene.setSceneId(scanId);
			dbscene.setUpdateTime(new Date());
			sceneDao.update(dbscene);
		}else{
			Scene scene = new Scene();
			scene.setSceneId(scanId);
			scene.setOpenId(openId);
			scene.setCreateTime(new Date());
			scene.setUpdateTime(new Date());
			sceneDao.add(scene);
		}
	}

	@Override
	public void updateScene(Scene scene) throws Exception {
		sceneDao.update(scene);
	}

 
}
