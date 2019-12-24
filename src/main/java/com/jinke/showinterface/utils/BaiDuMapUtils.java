package com.jinke.showinterface.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import com.alibaba.fastjson.JSONObject;
import com.jinke.showinterface.enums.ComprehensionEnum;
import com.jinke.showinterface.enums.ConfidenceEnum;
import com.jinke.showinterface.enums.TypeEnum;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaiDuMapUtils {
	
	final static String AK = "xkuYIjuL20ajqavdZG2u4m74B5r8ZMYo";
	final static String BAIDU_URL = "http://api.map.baidu.com/geocoder?";
	final static String BAIDU_URL_V3 = "http://api.map.baidu.com/geocoding/v3/?";
	final static int ZF_HIGHT = 8848;
	
	public static String getDistance(String addStart,String addEnd) {
		String add1s = getCoordinateV3(addStart);
		double add1J = Double.valueOf(add1s.split(",")[0]);
		double add1W = Double.valueOf(add1s.split(",")[1]);
		String add2s = getCoordinateV3(addEnd);
		double add2J = Double.valueOf(add2s.split(",")[0]);
		double add2W = Double.valueOf(add2s.split(",")[1]);
		double distance = getDistance(add1J,add1W,add2J,add2W);
		int bs = IntString(distance)/ZF_HIGHT;
		return bs + "";
	}
	
	public static double getDistanceDouble(String addStart,String addEnd) {
		log.info("起点："+addStart+"----终点："+addEnd);
		String add1s = getCoordinateV3(addStart);
		double add1J = Double.valueOf(add1s.split(",")[0]);
		double add1W = Double.valueOf(add1s.split(",")[1]);
		String add2s = getCoordinateV3(addEnd);
		double add2J = Double.valueOf(add2s.split(",")[0]);
		double add2W = Double.valueOf(add2s.split(",")[1]);
		double distance = getDistance(add1J,add1W,add2J,add2W);
		log.info("距离"+distance+"Km");
		return distance;
	}
	
	//往返里程数除以珠峰高度
	public static String getDistance(double distanceSum) {
		double paperCountHg = DoubleUtil.mul(double2String(distanceSum), 2);
		double value = DoubleUtil.div(paperCountHg, ZF_HIGHT, 2);
		String result = DicimalToFractionUtil.dicimalToFractionALB(value);
		return  result;
	}
	
	// 调用百度地图API根据地址，获取坐标V2,不精确
    public static String getCoordinateV2(String address) {
        if (address != null && !"".equals(address)) {
            address = address.replaceAll("\\s*", "").replace("#", "栋");
            String url = BAIDU_URL + "address=" + address + "&output=json&ak=" + AK;
            String json = loadJSON(url);
            if (json != null && !"".equals(json)) {
                JSONObject obj = JSONObject.parseObject(json);
                if ("OK".equals(obj.getString("status"))) {
                    double lng = obj.getJSONObject("result").getJSONObject("location").getDouble("lng"); // 经度
                    double lat = obj.getJSONObject("result").getJSONObject("location").getDouble("lat"); // 纬度
                    DecimalFormat df = new DecimalFormat("#.######");
                    return df.format(lng) + "," + df.format(lat);
                }
            }
        }
        return null;
    }
    
    public static String getCoordinateV3(String address) {
        if (address != null && !"".equals(address)) {
            address = address.replaceAll("\\s*", "").replace("#", "栋");
            String url = BAIDU_URL_V3 + "address=" + address + "&output=json&ak=" + AK;
            String json = loadJSON(url);
            if (json != null && !"".equals(json)) {
            	log.info("百度API返回经纬度报文：{}",json);
                JSONObject obj = JSONObject.parseObject(json);
                if ("0".equals(obj.getString("status"))) {
                	JSONObject result = obj.getJSONObject("result");
                    double lng = result.getJSONObject("location").getDouble("lng"); // 经度
                    double lat = result.getJSONObject("location").getDouble("lat"); // 纬度
                    //log.info("地点：[" + address + "],经纬度：[" + lng + "," + lat + "]"+ ConfidenceEnum.byCode(result.getIntValue("confidence")).getMessage() + "---" + ComprehensionEnum.byCode(result.getIntValue("comprehension")).getMessage() + "----地点类型：" + result.getString("level"));
                    DecimalFormat df = new DecimalFormat("#.######");
                    return df.format(lng) + "," + df.format(lat);
                }else if("302".equals(obj.getString("status"))) {
                	log.info("百度地图调用服务次数超限，请前往服务台提升！");
                	return null;
                }
            }
        }
        return null;
    }

    public static String loadJSON(String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {} catch (IOException e) {}
        return json.toString();
    }
    
    public static double getDistance(double lon1, double lat1, double lon2, double lat2) {
    	double radLat1 = rad(lat1);
    	double radLat2 = rad(lat2);
    	double a = radLat1 - radLat2;
    	double b = rad(lon1) - rad(lon2);
    	double c = 2 * Math.asin(Math.sqrt(
    	Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
    	c = c * 6378.137;// 6378.137赤道半径
    	return Math.round(c * 10000d) / 10000d;
    }
    
    private static double rad(double d) {
    	return d * Math.PI / 180.0;
    }
    
    public static int IntString(double d){  
        BigDecimal bg = new BigDecimal(d * 1000);
        int intValue = bg.setScale(2,BigDecimal.ROUND_HALF_UP).intValue();  
        log.info("距离：" + bg + "米");
        return intValue;  
    } 
    
    public static double double2String(double d){  
        BigDecimal bg = new BigDecimal(d * 1000);
        double intValue = bg.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();  
        log.info("距离：" + bg + "米");
        return intValue;  
    } 
    
    public static void main(String[] args) {
		System.out.println(getDistanceDouble("南京","句容"));
	}
}
