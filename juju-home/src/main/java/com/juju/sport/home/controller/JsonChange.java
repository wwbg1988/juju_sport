/**
 * 
 */
package com.juju.sport.home.controller;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**		
 * <p>Title: JsonChange </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Lawliet	
 * @date 2015年6月9日 下午2:41:01	
 * @version 1.0
 * <p>修改人：Lawliet</p>
 * <p>修改时间：2015年6月9日 下午2:41:01</p>
 * <p>修改备注：</p>
 */
public class JsonChange
{

    // 创建JSONObject对象
    public  JSONObject createJSONObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", "huangwuyi");
        jsonObject.put("sex", "男");
        jsonObject.put("QQ", "413425430");
        jsonObject.put("score", new Integer(99));
        jsonObject.put("nickname", "梦中心境");
        return jsonObject;
    }
    
    public String jsonToString(){
        JSONObject aa = createJSONObject();
        AddressInfo addressInfo =   (AddressInfo) aa.toBean(aa, AddressInfo.class);
        String username = addressInfo.getUsername();
        System.out.println("username="+username);
        return username;
    }
    
    public JSONObject StringToJson(){
        AddressInfo addressInfo = new AddressInfo();
        addressInfo.setNickname("梦中心境");
        addressInfo.setUsername("huangwuyi");
        addressInfo.setQQ("413425430");
        addressInfo.setScore(99);
        addressInfo.setSex("男");
        JSONArray jarray = JSONArray.fromObject(addressInfo);
        System.out.println("jarray="+jarray);
        JSONObject pp =   jarray.getJSONObject(0);
        return pp;
    }
    
    
}

