/**
 * 
 */
package com.juju.sport.home.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * <p>
 * Title: QinJiaConnectInfo
 * </p>
 * <p>
 * Description: 类描述
 * </p>
 * <p>
 * Copyright (c) 2015
 * </p>
 * <p>
 * Company: 上海天坊信息科技有限公司
 * </p>
 * 
 * @author Lawliet
 * @date 2015年6月10日 上午10:11:03
 * @version 1.0
 *          <p>
 *          修改人：Lawliet
 *          </p>
 *          <p>
 *          修改时间：2015年6月10日 上午10:11:03
 *          </p>
 *          <p>
 *          修改备注：
 *          </p>
 */
public class QinJiaConnectInfo
{
    protected static final Log logger = LogFactory.getLog(AlipayController.class);

    // 亲加通讯云接口连接信息

    // 推送信息
    public JSONObject pushInformation(Map map)
    {

        TestQinJia aa = new TestQinJia();
        // 构造参数
        JSONObject param = new JSONObject();

        String email = (String) map.get("email");
        String devpwd = (String) map.get("devpwd");
        String appkey = (String) map.get("appkey");
        String from = (String) map.get("from");
        String to_type = (String) map.get("to_type");
        List to_id = (List) map.get("to_id");
        String save = (String) map.get("save");
        String msg_type = (String) map.get("msg_type");
        String text = (String) map.get("text");

        param.put("email", email);
        param.put("devpwd", devpwd);
        param.put("appkey", appkey);
        param.put("from", from);
        param.put("to_type", to_type);
        JSONArray jarray_id = JSONArray.fromObject(to_id);
        param.put("to_id", jarray_id);
        param.put("save", save);
        param.put("msg_type", msg_type);
        param.put("text", text);

        logger.info("----------------------------log----------------");
        logger.info("-------param=" + param);

        JSONObject hh = aa.requestHttps("https://qplusapi.gotye.com.cn:8443/api/SendMsg", param.toString());

        logger.info("-------hh=" + hh);

        return hh;
        // return null;
    }

    // 获取历史数据
    public JSONObject getHisData()
    {

        TestQinJia aa = new TestQinJia();
        // 构造参数
        JSONObject param = new JSONObject();

        param.put("email", "wuweitree@163.com");
        param.put("devpwd", "wuwei123");
        param.put("appkey", "733a1dfc-d717-49e6-8ea9-3d700e1988b2");
        // param.put("end_date", "");
        param.put("receiver_type", "0");
        param.put("receiver_id", "asdf");
        param.put("sender_id", "123456");
        param.put("index", "0");
        param.put("count", "20");
        System.out.println("param=" + param);

        JSONObject hh =
            aa.requestHttps("https://qplusapi.gotye.com.cn:8443/api/GetMsgHistory", param.toString());
        System.out.println("hh==" + hh);
        return hh;
    }

}
