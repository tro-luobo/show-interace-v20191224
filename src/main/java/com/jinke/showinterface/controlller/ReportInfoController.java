package com.jinke.showinterface.controlller;

import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;
import com.jinke.showinterface.result.Result;
import com.jinke.showinterface.service.UserInfoService;
import com.jinke.showinterface.utils.DingDing;
import com.jinke.showinterface.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * 员工信息获取
 * @author luobo
 * */

@RestController
@Slf4j
@Api(value = "可视报表信息获取接口")
public class ReportInfoController {
	
	@Autowired
	UserInfoService userInfoService;

	@Autowired
    private RedisUtils redisUtils;

	/**
	 * 获取用户报表信息
	 * @param oaAccount
	 * @return Result
	 */
	@RequestMapping(value = "/report/getData", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ApiOperation(value = "可视报表信息获取", notes = "可视报表信息获取")
	public Result getUserInfo(@ApiParam(name = "oaAccount", value = "员工OA账号", required = true) @RequestParam String oaAccount) {
		log.info("前端传回USERCODE:{}",oaAccount);
		oaAccount = DingDing.getUser(oaAccount);
		if(StringUtils.isBlank(oaAccount)) {
			return new Result();
		}
		log.info("钉钉转换后，OA用户号:{}", oaAccount);
		
		// 测试随机获取UID,上线需注释
		//oaAccount = "luobo888";
		//log.info("请求参数，测试随机获取UID-OA用户号:{}", oaAccount);
		
		// 进行redis缓存查询
        boolean hasKey = redisUtils.exists(oaAccount);
        Result result = null;
        if(hasKey){
        	//获取缓存
            result =  (Result) redisUtils.get(oaAccount);
            log.info("从缓存获取的数据" + JSON.toJSONString(result));
            // 如缓存数据被间接性删除,数据库获取数据
            if(result == null) {
            	result = userInfoService.getReportData(oaAccount);
            	if(result != null) {
        			log.info("数据库获取数据存入缓存：{}", JSON.toJSONString(result));
        			redisUtils.set(oaAccount, result);
        		}
            }
        }else {
        	// 不存在数据库获取
        	result = userInfoService.getReportData(oaAccount);
    		//存入redis
    		if(result != null) {
    			log.info("数据库获取数据存入缓存：{}", JSON.toJSONString(result));
    			redisUtils.set(oaAccount, result);
    		}
        }
		return result;
	}
	
	/**
	 *测试随机获取UID
	 */
	final static String[] FileName = {"luobo","luobo888","liyang21","chentao5"};
	
	public static String RandomFile(){
		Random random = new Random();
		int random_index = random.nextInt(3);
        String str = FileName[random_index];
        return str;
    }
	
}
