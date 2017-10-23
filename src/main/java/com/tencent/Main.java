package com.tencent;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.util.EntityUtils;

import com.tencent.common.Configure;
import com.tencent.common.HttpsRequest;
import com.tencent.common.Signature;
import com.tencent.common.Util;
import com.tencent.common.XMLParser;

public class Main {

    public static void main(String[] args) {

        try {

            //--------------------------------------------------------------------
            //温馨提示，第一次使用该SDK时请到com.tencent.common.Configure类里面进行配置
            //--------------------------------------------------------------------



            //--------------------------------------------------------------------
            //PART One:基础组件测试
            //--------------------------------------------------------------------

            //1）https请求可用性测试
            //HTTPSPostRquestWithCert.test();

            //2）测试项目用到的XStream组件，本项目利用这个组件将Java对象转换成XML数据Post给API
            //XStreamTest.test();


            //--------------------------------------------------------------------
            //PART Two:基础服务测试
            //--------------------------------------------------------------------

            //1）测试被扫支付API
            //PayServiceTest.test();

            //2）测试被扫订单查询API
            //PayQueryServiceTest.test();

            //3）测试撤销API
            //温馨提示，测试支付API成功扣到钱之后，可以通过调用PayQueryServiceTest.test()，将支付成功返回的transaction_id和out_trade_no数据贴进去，完成撤销工作，把钱退回来 ^_^v
            //ReverseServiceTest.test();

            //4）测试退款申请API
            //RefundServiceTest.test();

            //5）测试退款查询API
            //RefundQueryServiceTest.test();

            //6）测试对账单API
            //DownloadBillServiceTest.test();


            //本地通过xml进行API数据模拟的时候，先按需手动修改xml各个节点的值，然后通过以下方法对这个新的xml数据进行签名得到一串合法的签名，最后把这串签名放到这个xml里面的sign字段里，这样进行模拟的时候就可以通过签名验证了
           // Util.log(Signature.getSignFromResponseString(Util.getLocalXMLString("/test/com/tencent/business/refundqueryserviceresponsedata/refundquerysuccess2.xml")));

            //Util.log(new Date().getTime());
            //Util.log(System.currentTimeMillis());
        	
        	
        	
        	////htw test
        	

            Map<String, Object> signMap = new HashMap<String, Object>();
			/*signMap.put("nonce_str", Signature.getRandomStringByLength(32)); ///随机字符串
			signMap.put("mch_billno", "1234567890"); //商户订单号
			signMap.put("mch_id", Configure.getMchid()); //商户号
			signMap.put("wxappid", Configure.getAppid()); //公众账号appid
			signMap.put("send_name", "好胎屋汽车生活网"); //商户名称
			signMap.put("re_openid", "oFnLXt_KUxeOzevZXGfR2QlqKaUE"); //用户openid
			signMap.put("total_amount", 100); //付款金额
			signMap.put("total_num", 1); //红包发放总人数
			signMap.put("wishing", "测试红包"); //红包祝福语
			signMap.put("client_ip", "127.0.0.1"); //ip地址
			signMap.put("act_name", "测试公众号"); //活动签名
			signMap.put("remark", "测试"); //备注
			String sign = Signature.getSign(signMap);
			signMap.put("sign", sign);//签名
*/			
          /* System.out.println(URLEncoder.encode("好胎屋汽车生活网", "UTF-8")); 
            String openId = "oFnLXt73VzHqWsoI26AaEsE4ovP4";
			signMap.put("nonce_str", Signature.getRandomStringByLength(32)); // /随机字符串
			signMap.put("mch_billno", "1234567891"); // 商户订单号
			signMap.put("mch_id", Configure.getMchid()); // 商户号
			signMap.put("wxappid", Configure.getAppid()); // 公众账号appid
			signMap.put("send_name", "好胎屋汽车生活网"); // 商户名称
			signMap.put("re_openid", openId); // 用户openid
			signMap.put("total_amount", 100); // 付款金额
			signMap.put("total_num", 1); // 红包发放总人数
			signMap.put("wishing", "猴年你最幸运!!!"); // 红包祝福语
			signMap.put("client_ip", "127.0.0.1"); // ip地址
			signMap.put("act_name", "抢啊"); // 活动签名
			signMap.put("remark", "关注公众抢红包"); // 备注
			String sign = Signature.getSign(signMap);
			signMap.put("sign", sign);// 签名
            
//        	 String postDataXML = HttpsRequest.mapToXML(signMap);
        	 
//        	 System.out.println(postDataXML);
        	 
        	 HttpsRequest httpRequest = new HttpsRequest();
        	 
        	 
        	 String result  =  httpRequest.sendPostMap("https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack", signMap);
        	 
			 System.out.println(result);
				Map<String, Object> resultMap = XMLParser.getMapFromXML(result);
				
				for (String key : resultMap.keySet()) {
					System.out.println(key + "----" + resultMap.get(key));
				}*/
            
            
            ///qrcode
        		
        		/*for(int i=100;i<120;i++){
        			String jsonData = "{\"expire_seconds\": 604800, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": "+i+"}}}";
        	        
        			String  TOKEN ="rBcWTF_06S0sklHhAmsfZ2DIxHWaw4sZJJjF7gO5De1-EZQDNBS1_mIyW6yoXVPGIxRRY37Qxw4zToyCIsLdTMt3e-sd9z8-4oRSCpv2HZkRQTfAIAJIL";
        			String httpUrl  = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+TOKEN;
        			
        			String result = "";
        			 
        			HttpPost httppost = new HttpPost(httpUrl);
        			
        			// 设置参数的编码UTF-8
        			try {
        				httppost.setEntity(new StringEntity(jsonData, "utf-8"));
        				HttpClient httpclient = new DefaultHttpClient();
        				
        				HttpEntity entity = httpclient.execute(httppost).getEntity();
        				
        				if(entity != null && entity.getContentLength() != -1) {
        					result=EntityUtils.toString(entity);
        				}
        				httpclient.getConnectionManager().shutdown();
        			} catch (Exception e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			} 
        		
        			System.out.println(result);
        		}*/
        		
            
          ///qrcode
				
        	////htw test

        } catch (Exception e){
            Util.log(e.getMessage());
        }

    }

}
