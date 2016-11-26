package com.juju.sport.home.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: TicketInfoDto </p>
 * <p>Description: 出票信息</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Vincent	
 * @date 2015年5月21日 下午4:04:28	
 * @version 1.0
 */
@SuppressWarnings("serial")
public class TicketInfoDto implements Serializable
{

    /**   
    * orderno：订单流水号 第一位大写字母 后四位0-9数字
    */
    @Setter
    @Getter
    private String orderno;

    /**   
    *  venuesname：场馆名称(含场地信息) 例：上海市普陀区真如中学 足球场-1
    */
    @Setter
    @Getter
    private String venuesname; 

    /**   
    * pretime：预订场地日期  例：5月4日11：00-12：00
    */
    @Getter
    @Setter
    private String pretime;

    /**
     * headnum：人数
     */
    @Getter
    @Setter
    private int headnum;

    /**   
    * logoUrl：Logo图片地址
    */
    @Getter
    @Setter
    private String logoUrl;

    /**   
    * returnCode: 返回码 
    */
    @Getter
    @Setter
    private String returnCode;

}
