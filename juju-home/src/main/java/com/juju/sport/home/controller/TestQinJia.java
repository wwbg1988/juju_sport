/**
 * 
 */
package com.juju.sport.home.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import net.sf.json.JSONObject;
import net.sf.json.JSONArray;

/**
 * <p>
 * Title: TestQinJia
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
 * @date 2015年6月9日 上午11:34:45
 * @version 1.0
 *          <p>
 *          修改人：Lawliet
 *          </p>
 *          <p>
 *          修改时间：2015年6月9日 上午11:34:45
 *          </p>
 *          <p>
 *          修改备注：
 *          </p>
 */
public class TestQinJia
{

    public static JSONObject requestHttps(String httpsUrl, String reqParamer)
    {

        try
        {

            // // 构造参数
            // JSONObject param = new JSONObject();
            //
            //
            // param.put("email", "xxx");
            // param.put("devpwd", "xxx");
            //
            // // 发送请求
            //
            // requestHttps("https://qplusapi.gotye.com.cn:8443/api/command", param.toString());

            URL url = new URL(httpsUrl);

            TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager()
            {
                public java.security.cert.X509Certificate[] getAcceptedIssuers()
                {
                    return null;
                }

                public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType)
                {
                }

                public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType)
                {
                }
            } };
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            // HttpsURLConnection.setDefaultHostnameVerifier(new
            // TrustAnyHostnameVerifier());
            HostnameVerifier hv = new HostnameVerifier()
            {
                public boolean verify(String urlHostName, SSLSession session)
                {
                    return true;
                }
            };
            HttpsURLConnection.setDefaultHostnameVerifier(hv);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "text/xml");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setReadTimeout(300000);
            if (reqParamer == null)
            {
                connection.setRequestMethod("GET");
            }
            else
            {
                // 设置请求方式为post
                connection.setRequestMethod("POST");
                connection.setUseCaches(false);
                OutputStream out = connection.getOutputStream();
                out.write(reqParamer.getBytes());
                out.flush();
                out.close();
            }
            BufferedReader reader =
                new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            StringBuffer stb = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null)
            {
                stb.append(line);
            }

            Integer statusCode = connection.getResponseCode();
            if (statusCode == 200)
            {
                System.out.println("" + stb.toString());
                return JSONObject.fromObject(stb.toString());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
