package com.jinke.showinterface.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiMediaUploadRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiMediaUploadResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.taobao.api.ApiException;
import com.taobao.api.FileItem;
import lombok.extern.slf4j.Slf4j;

/**
 * 钉钉工具类
 * */
@Slf4j
public class DingDing {
	
	final static String DINGDING_TOKEN_URL = "https://oapi.dingtalk.com/gettoken"; 
	final static String DINGDING_USER_URL = "https://oapi.dingtalk.com/user/getuserinfo";
	final static String DINGDING_MESSAGE_URL = "https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2";
	final static String METHOD_TYPE = "GET";
	
	// 钉钉配置项
//	final static String APPKEY = "dingzzrrh78d7br0tl9s";// 应用ID
//	final static String APPSECRET = "qXXf96L3A3UyX7NUnpI1nsLWP_aqbgYfVzwa1UfUmlZKMr4Esr9-uszjEKwl2FId";// 微应用ID
//	final static String CORPID = "ding396474b44d3d0e5735c2f4657eb6378f";//企业ID
//	final static Long AGENTID = 332219077L;// agentid
//  final static String MESSAGE_LOGO_PNG = "@lALPDeC2uvbosADMt80BbA"; // 消息图片

	// 正式环境
	final static String APPKEY = "dingx4g2kurojbmkanrq";// 应用ID
	final static String APPSECRET = "YL-kMBVUsH9hoH6rJY8XbN6cCZcqLqob1kC4D7KHUACXYBUuYQ6kVypOF4gTUuN4";// 微应用ID
	final static String CORPID = "ding880ca0f78f31e06b35c2f4657eb6378f";//企业ID
	final static Long AGENTID = 336784215L;// agentid
	final static String MESSAGE_LOGO_PNG = "@lALPDeC2uxRWr3fMt80BbA"; // 消息图片

	final static String MESSAGE_TYPE = "oa";// 消息类型
	final static String DINGDING_APP_URL = "http://222.180.169.87:8050/page";

	
	public static String getAccessTokenByApp(String appkey, String appsecret) throws Exception{
	    DefaultDingTalkClient client = new DefaultDingTalkClient(DINGDING_TOKEN_URL);
	    OapiGettokenRequest request = new OapiGettokenRequest();
	    request.setAppkey(appkey);
	    request.setAppsecret(appsecret);
	    request.setHttpMethod(METHOD_TYPE);
	    OapiGettokenResponse response = client.execute(request);
	    return response.getAccessToken();
	 }
	
	public static String getAccessTokenByApp() throws Exception{
	    DefaultDingTalkClient client = new DefaultDingTalkClient(DINGDING_TOKEN_URL);
	    OapiGettokenRequest request = new OapiGettokenRequest();
	    request.setAppkey(APPKEY);
	    request.setAppsecret(APPSECRET);
	    request.setHttpMethod(METHOD_TYPE);
	    OapiGettokenResponse response = client.execute(request);
	    return response.getAccessToken();
	 }
	
	public static String getUser(String code) {
		try {
			return getDingUser(getAccessTokenByApp(),code);
		} catch (Exception e) {
			log.error("钉钉用户信息获取失败:" + e.getMessage());
			return "";
		}
	}
	
	public static String getDingUser(String accessToken, String code) throws Exception{
	    DingTalkClient client = new DefaultDingTalkClient(DINGDING_USER_URL);
	    OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
	    request.setCode(code);
	    request.setHttpMethod(METHOD_TYPE);
	    OapiUserGetuserinfoResponse response = client.execute(request, accessToken);
	    String userId = response.getUserid();
	    return userId;
	 }
	
	/**---------------消息推送-------------------
	 * @throws Exception */
	public static String sendWorkMessage(String toUser) throws Exception{
		DingTalkClient client = new DefaultDingTalkClient(DINGDING_MESSAGE_URL);
		OapiMessageCorpconversationAsyncsendV2Request request = getMessage(MESSAGE_TYPE);
		request.setToAllUser(false);
		if(StringUtils.isNoneBlank(toUser)) {
			request.setUseridList(toUser);
		}else {
			request.setToAllUser(true);
		}
		request.setAgentId(AGENTID);
		String accessToken = getAccessTokenByApp();
		OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request,accessToken);
		if(response.getErrcode() == 0) {
			log.info("消息通知成功,响应报文：{}", response.getBody());
		}
        return "";
    }
	
	public static void postFile(String imgPath) throws ApiException, Exception {
		DingTalkClient  client = new DefaultDingTalkClient("https://oapi.dingtalk.com/media/upload");
		OapiMediaUploadRequest request = new OapiMediaUploadRequest();
		request.setType("image");
		request.setMedia(new FileItem(imgPath));
		OapiMediaUploadResponse response = client.execute(request,getAccessTokenByApp());
		if(response.getErrcode() == 0) {
			log.info("图片上传成功，{}",response.getBody());
		}
	}
	
	/**
	 * 消息生成
	 * @param messageType 消息类型
	 * */
	public static OapiMessageCorpconversationAsyncsendV2Request getMessage(String messageType) {
		OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
		OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
		msg.setMsgtype(messageType);
		switch (messageType) {
		case "text":
			msg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
			msg.getText().setContent("test123");
			request.setMsg(msg);
			break;
		case "image":
			msg.setImage(new OapiMessageCorpconversationAsyncsendV2Request.Image());
			msg.getImage().setMediaId("@lADOdvRYes0CbM0CbA");
			request.setMsg(msg);
			break;
		case "file":
			msg.setFile(new OapiMessageCorpconversationAsyncsendV2Request.File());
			msg.getFile().setMediaId("@lADOdvRYes0CbM0CbA");
			request.setMsg(msg);
			break;
		case "link":
			msg.setLink(new OapiMessageCorpconversationAsyncsendV2Request.Link());
			msg.getLink().setTitle("test");
			msg.getLink().setText("test");
			msg.getLink().setMessageUrl("test");
			msg.getLink().setPicUrl("test");
			request.setMsg(msg);
			break;
		case "markdown":
			msg.setMarkdown(new OapiMessageCorpconversationAsyncsendV2Request.Markdown());
			msg.getMarkdown().setText("##### text");
			msg.getMarkdown().setTitle("### Title");
			request.setMsg(msg);
			break;
		case "oa":
			msg.setOa(new OapiMessageCorpconversationAsyncsendV2Request.OA());
			msg.getOa().setHead(new OapiMessageCorpconversationAsyncsendV2Request.Head());
			msg.getOa().getHead().setText("个人年底数字报表");
			msg.getOa().setBody(new OapiMessageCorpconversationAsyncsendV2Request.Body());
			msg.getOa().getBody().setContent("个人年底数字报表，点击查看");
			msg.getOa().getBody().setImage(MESSAGE_LOGO_PNG);
			msg.getOa().setMessageUrl(DINGDING_APP_URL);
			msg.getOa().setPcMessageUrl(DINGDING_APP_URL);
			request.setMsg(msg);
			break;
		case "action_card":
			msg.setActionCard(new OapiMessageCorpconversationAsyncsendV2Request.ActionCard());
			msg.getActionCard().setTitle("个人年底数字报表");
			msg.getActionCard().setMarkdown("### 测试123111");
			msg.getActionCard().setSingleTitle("测试测试");
			msg.getActionCard().setSingleUrl("https://www.baidu.com");
			request.setMsg(msg);
			break;
		default:
			break;
		}
		return request;
	}

	public static void queryFwList() throws Exception{
		DingTalkClient  client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/serviceaccount/list");
		OapiGettokenRequest request = new OapiGettokenRequest();
		OapiGettokenResponse response = client.execute(request,getAccessTokenByApp());
		log.info(JSON.toJSONString(response));
	}


	public static void main(String[] args) throws Exception {
		Long a = 332219077L;

		queryFwList();
		//04316736141038443
		//消息推送
		/*String str[] = {"liyang21","hejunl","lihui03","penggj","wanglq","chentao5","hug","zhouqiang2","luozw","chenchaoy",
				"xiajin","lic02","yangdey","liyanxin","wangping7","tanhf","caip","chenliang18","chenrongq","liziz","wangjian25",
				"lanjiac","daimengy","lijunw1","yaox","wangmeil2","suy4"
		};
		for (int i = 0; i < str.length; i++) {
			if(StringUtils.isNotBlank(str[i])){
				sendWorkMessage(str[i]);
			}
		}*/
		//图片log上传
		//postFile("C:\\Users\\DELL\\Desktop\\ddd\\lALPDgQ9rWahsr3Mt80BbA_364_183.png");
		//System.out.println(getUser("65d6423bc4fd3bbb8c214f3a88a9ab88"));
	}
}
