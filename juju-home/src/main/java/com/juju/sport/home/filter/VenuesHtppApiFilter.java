/**
 * 
 */
package com.juju.sport.home.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import com.juju.sport.admin.home.cache.CacheDataLoad;
import com.juju.sport.common.constants.HttpApiReturnCode;
import com.juju.sport.common.util.DateUtils;
import com.juju.sport.common.util.JsonUtil;
import com.juju.sport.home.dto.RestRespone;
import com.juju.sport.stadium.dto.VenuesPwdDto;

/**		
 * <p>Title: VenuesHtppApiFilter </p>
 * <p>Description: 场馆HTTP接口设备权限校验</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Vincent	
 * @date 2015年5月21日 下午5:53:08	
 * @version 1.0
 */
public class VenuesHtppApiFilter implements Filter
{

    /**   
    * 页面编码
    */
    private String encode;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        encode = filterConfig.getInitParameter("encoding");
    }

    /** 
    * 校验外围设备请求是否合法
    * 获取设备编号sid，序列号seq，序列号与密码组合后MD5的值（用于身份认证）。根据设备编号从系统中获取密码，将密码与序列号组合进行MD5。如果与接收到的MD5值相同。则认为该请求为合法请求。  
    * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)   
    */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException
    {
        request.setCharacterEncoding(encode);
        RestRespone<String> res=new RestRespone<String>();
        res=checkParms(request);

        if (!StringUtils.isEmpty(res.getReturnCode()))
        {
            response.getWriter().print(JsonUtil.getJsonStr(res));
            response.getWriter().flush();
            return;
        }

        String sid = StringUtils.trimToEmpty(request.getParameter("sid"));

        String seq = StringUtils.trimToEmpty(request.getParameter("seq"));

        String sidPwd = StringUtils.trimToEmpty(request.getParameter("sidPwd"));

        //IVenuesApiService venuesApiService = SpringContextUtil.getBean("venuesApiService");
        
        //CacheDataLoad cache = new CacheDataLoad();
        //查询设备密码信息  
        VenuesPwdDto venuesPwdDto = CacheDataLoad.getVenuesPwd(sid);
        //VenuesPwdDto venuesPwdDto = venuesApiService.queryVenuesPwd(sid);
        
        if(null ==venuesPwdDto){
            //设备校验失败 不存在
            res.setDescribe("访问外围设备失败—参数校验、鉴权等原因");
            res.setReturnCode(HttpApiReturnCode.PERPHERAL_REQ_FAIL);
            response.getWriter().print(JsonUtil.getJsonStr(res));
            response.getWriter().flush();
            return;
        }

        if (DateUtils.afterHourDate(venuesPwdDto.getValidity(), venuesPwdDto.getLastUpdateTime()))
        {
            //设备密码过期
            res.setDescribe("外围设备密码过期");
            res.setReturnCode(HttpApiReturnCode.SIDPWD_IS_OVERDUE);
            response.getWriter().print(JsonUtil.getJsonStr(res));
            response.getWriter().flush();
            return;
        }

        String md5SidPwd = StringUtils.trimToEmpty(DigestUtils.md5Hex(seq + venuesPwdDto.getPwd()));

        //认证设备签名
        if (StringUtils.equals(md5SidPwd, sidPwd))
        {
            chain.doFilter(request, response);
        }   
        else
        {
            res.setDescribe("外围设备访问密码错误");
            res.setReturnCode(HttpApiReturnCode.SIDPWD_IS_ERROR);
            response.getWriter().print(JsonUtil.getJsonStr(res));
            response.getWriter().flush();
            return;
        }
    }


    /**     
     * checkParms：校验外围信息接口参数
     * @param request
     * @return
     * @author Vincent
     * @date 2015年5月25日 上午9:31:40	 
     */
    private RestRespone<String> checkParms(ServletRequest request)
    {
        RestRespone<String> vat = new RestRespone<String>();
        if (StringUtils.isEmpty(request.getParameter("sid")))
        {
            vat.setReturnCode(HttpApiReturnCode.SID_IS_NULL);

        }
        if (StringUtils.isEmpty(request.getParameter("seq")))
        {
            vat.setReturnCode(HttpApiReturnCode.SEQ_IS_NULL);

        }
        if (StringUtils.isEmpty(request.getParameter("sidPwd")))
        {
            vat.setDescribe("外围设备设备密码错误！");
            vat.setReturnCode(HttpApiReturnCode.SIDPWD_IS_ERROR);

        }
        return vat;
    }

    @Override
    public void destroy()
    {
    }

}
