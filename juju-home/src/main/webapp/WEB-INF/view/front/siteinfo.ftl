<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>聚运动</title>
    <link href="/css/juju/siteinfo.css" rel="stylesheet" />
    <script src="/js/juju/jquery-2.1.3.min.js"></script>
    <script src="/js/juju/layer/layer.min.js"></script>    
    <script src="/js/juju/loadUserInfo.js"></script>
    <script src="/js/juju/loadSpace.js"></script>
</head>
<body>
    <div class="container">
        <script src="/js/juju/loadUserInfo.js"></script>
    	<input id='sjWeek' name='sjWeek' type='hidden' value='${sjWeek}'/>
    	<input id='sjTime' name='sjTime' type='hidden' value=''/>
		<input id="orderTime" name="orderTime" type="hidden" value="${nowDate}"/>
		<input id="totalPrice" name="totalPrice" type='hidden' value="0"/>
		<input id="ownerAccountId" name="ownerAccountId" type="hidden" value="${ownerAccountId}"/>
        <div id="whole" style="background-color:rgb(244,245,244);">
            <div id="top"><img src="/images/top.png"></div>
            <div class="logo_long">
                <div class="logo_nav">
                    <div class="logo"><a href="/app/index.html"><img style="border:0px" src="/images/logo.png"></a></div>
                </div>
            </div>

            <div id="logo_right">
                <div id="notice">
                    <!--<h3>新闻公告栏：聚运动官网即将正式运行，内容丰富，精彩多样，敬请期待......</h3>-->
                </div>

                <div id="main_nav">
                    <a href="/app/index.html">
                        <div class="nav_text">
                            <div class="change" onmouseover="this.style.backgroundColor='#cf0039';this.style.color='#ffffff'" onmouseout="this.style.backgroundColor='';this.style.color=''">
                                首页
                            </div>
                        </div>
                    </a>
                    <a href="/app/event_list.html">
                        <div class="nav_text">
                            <div class="change" onmouseover="this.style.backgroundColor='#cf0039';this.style.color='#ffffff'" onmouseout="this.style.backgroundColor='';this.style.color=''">
                                聚赛事
                            </div>
                        </div>
                    </a>

                    <a href="/app/sitelist.html">
                        <div class="nav_text">
                            <div style="background-color:#cf0039;color:#ffffff" class="change" onmouseover="this.style.backgroundColor='#cf0039';this.style.color='#ffffff'">
                                订场馆
                            </div>
                        </div>
                    </a>

                 <!--   <a  href="/app/404.html">
                        <div class="nav_text">
                            <div class="change" onmouseover="this.style.backgroundColor='#cf0039';this.style.color='#ffffff'" onmouseout="this.style.backgroundColor='';this.style.color=''">
                                玩约战
                            </div>
                        </div>
                    </a>-->
                    <a  href="/app/consultation/play_index.html">
                        <div class="nav_text">
                            <div class="change" onmouseover="this.style.backgroundColor='#cf0039';this.style.color='#ffffff'" onmouseout="this.style.backgroundColor='';this.style.color=''">
                                聚咨询
                            </div>
                        </div>
                    </a>
                    <!--<a  href="/app/404.html">
                        <div class="nav_text">
                            <div class="change" onmouseover="this.style.backgroundColor='#cf0039';this.style.color='#ffffff'" onmouseout="this.style.backgroundColor='';this.style.color=''">
                                聚论坛
                            </div>
                        </div>
                    </a>-->

                    <div class="sign">
                    <input type="hidden" id="thirdName"/>
                    	<div id="islogout" style="display:none;">
	                        <div onclick="_open();" class='cls_hand' style="height:40px;padding-top: 10px;  margin-right:10px; vertical-align:middle; float:left;">
	                            <!--<a href="javacsript:void" style="font-family:微软雅黑; 	font-size:16px; color:#717171;">-->
	                            <a href="#"><img src="/images/sign_in.png" style="vertical-align:middle;" />&nbsp;登录</a>
	                            <!--  </a>-->
	                        </div>
	                        <div onclick="window.location = '/app/user/register.html'" class='cls_hand' style="height:40px;padding-top: 10px; vertical-align:middle;float:left;">
	                            <!--<a href="404.html"  style="font-family:微软雅黑; 	font-size:16px; color:#717171;">-->
	                            <a href="#"><img src="/images/zhuce.png" style="vertical-align:middle;" />&nbsp;注册</a>
	                            <!--</a>-->
	                        </div>
                    	</div>
                    	<div id="islogin" style="display:none;">
	                        <div onclick="userManage();" class='cls_hand' style="height:40px;padding-top:10px;margin-right:10px;vertical-align:middle;float:left;">
	                            <!--<a href="javacsript:void" style="font-family:微软雅黑; 	font-size:16px; color:#717171;">-->
	                            <img src="/images/sign_in.png" style="vertical-align:middle;" />您好,&nbsp;<span id="showName"></span>
	                            <!--  </a>-->
	                        </div>
	                        <div onclick="loginOut();" class='cls_hand' style="height:40px;padding-top: 10px; vertical-align:middle;float:left;">
	                            <!--<a href="404.html"  style="font-family:微软雅黑; 	font-size:16px; color:#717171;">-->
	                            <a href="#"><img src="/images/zhuce.png" style="vertical-align:middle;" />&nbsp;退出</a>
	                            <!--</a>-->
	                        </div>                    		
                    	</div>
                    </div>
                </div>
            </div>

            <div style="clear:both;"></div>
        </div>
	    <form id="payForm" action="/api/orderBuy/payWait.do" method="POST">
	    	<input type="hidden" name="orderId" id="orderId" value=""/>
	    	<input type="hidden" name="orderItemId" id="orderItemId" value=""/>
	    </form>
        <!--<iframe src="head.html" style=" width:100%; height:144px;" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe>-->
        <!--赛事推送-->
        <!--<div class="container_block">
            <div id="container_block_l">
                <img src="/images/716-426-01.jpg" style=" width:716px; height:426px; cursor:pointer;" onclick="jump('poly_event.html')" />
            </div>
            <div id="container_block_r" style="">
                <div class="container_block_r_head">
                    <img id="container_block_r_head_img" src="/images/game_mark.png" />
                    <span id="container_block_r_head_text">赛事推送</span>
                </div>
                <ul class="container_block_ul">
                    <li onclick="jump('poly_event.html')" onmouseover="li_seover(this)" ; onmouseout="li_out(this)">
                        <div>2015中学生联赛</div>
                    </li>
                    <li onclick="jump('poly_event.html')" onmouseover="li_seover(this)" ; onmouseout="li_out(this)">
                        <div>2015上海市校园足球联盟杯赛</div>
                    </li>
                    <li onclick="jump('poly_event.html')" onmouseover="li_seover(this)" ; onmouseout="li_out(this)">
                        <div>6国国际联赛</div>
                    </li>
                    <li onclick="jump('poly_event.html')" onmouseover="li_seover(this)" ; onmouseout="li_out(this)">
                        <div>上海市学生联赛</div>
                    </li>


                    <li>
                        <div class="container_block_div">
                            <div style="font-size:14px; width:100px;margin:0 auto; height:30px; line-height:30px; color:#ffffff; background-color:RGB(207,0,57); cursor:pointer; ">
                                2015.4.30
                            </div>
                        </div>
                    </li>
                </ul>
                <div class="bm">赛事详情</div>

            </div>
            <div style="clear:both;"></div>
        </div>-->
        <!--定场馆-->
        <div class="centre_1">
            <div class="centre1_head">
                <div class="type">
                    订场馆
                </div>
                <!--
                <div class="gd">
                    更&nbsp;&nbsp;多
                </div>
                -->
            </div>
            <div class="centre1_block">
                <div style="width:100%;  height:100%; position:relative;">
                    <img src="/images/bg.png" style="height: auto;width: auto\9;width: 100%;" />
                    <div style="padding:10px; position:absolute;  left:90px; bottom:4px; background-color:rgba(0, 0, 0, 0.59);">
                        <p style="line-height:25px;">
                            <span style="font-size:24px; color:#fff;">${(venusInfoDto.nickName)!}</span>
                            <img src="/images/1.png" />
                            <img src="/images/1.png" />
                            <img src="/images/1.png" />
                            <img src="/images/1.png" />
                            <img src="/images/1.png" />
                            <span style="color:#fff; font-size:14px;">10分</span>
                        </p>
                        <p style="line-height:30px;">
                            <img src="/images/db.png" />
                            <span style="color:#fff; font-size:14px;">${(venusInfoDto.address)!}</span>
                            <img src="/images/dh.png" />
                            <span style="color:#fff; font-size:14px;">${(venusInfoDto.mobileNo)!}</span>
                        </p>
                        <p style="line-height:25px;">
                            <span style="color:#fff; font-size:14px;"><!--学校入驻中，暂时不接受预订--></span>
                        </p>
                    </div>

                    <div style="padding:10px; position:absolute;  right:0px; bottom:4px;  background-color:rgba(0, 0, 0, 0.59);">
                        <p style="line-height:20px;">
                            <span style="color:#FFF; font-size:20px;">P</span>
                            <span style="color:#fff; font-size:12px; padding-right:15px;">停车场</span>
                            <img src="/images/ly.png" />
                            <span style="color:#fff; font-size:12px; padding-right:15px;">淋浴</span>
                            <img src="/images/gz.png" />
                            <span style="color:#fff; font-size:12px; padding-right:15px;">柜子</span>
                        </p>
                        <p style="line-height:20px;">
                            <span style="color:#FFF; font-size:20px;">V</span>
                            <span style="color:#fff; font-size:12px;  padding-right:7px;"> VIP包房</span>
                            <img src="/images/kt.png" />
                            <span style="color:#fff; font-size:12px;  padding-right:13px;">空调</span>
                            <img src="/images/wifi.png" />
                            <span style="color:#fff; font-size:12px;">WIFI</span>
                        </p>
                        <p style="line-height:20px;">
                            <img src="/images/spgm.png" />
                            <span style="color:#fff; font-size:12px;">商品购买</span>
                            <img src="/images/qxzp.png" />
                            <span style="color:#fff; font-size:12px;">器材租借</span>
                        </p>
                    </div>

                </div>

                <div class="info">
                    <div class="info_l">
                        <img src="/images/cg.png" />
                    </div>
                    <div class="info_r" style="max-height:100px;overflow-x:hidden;">
                    	${(venusInfoDto.descs)!}
                    </div>
                </div>

                <div class="data_list" style="position:relative;">
                    <div class="data_list_l" style="position:relative;"> 
                            <div style="width:100%; position:relative; height:30px; padding:10px 0; background-color:rgb(247, 122, 66); ">
                                <div style="float:left;margin-left:15px; position:relative;">
                                    <span style="float:left; position:relative; height:30px; line-height:30px; color:#FFF;padding:0 5px;">日期</span>
                                    <!--<div style="float:left; position:relative; background-color:#fff; padding:5px; border-radius:3px; outline: none; cursor:pointer;" tabindex='-1' onfocus="show_data_time('datatime')" onblur="hide_data_time('datatime')">-->
                                    <div id="_datatime" style="float:left; position:relative; background-color:#fff; padding:5px; border-radius:3px; outline: none; cursor:pointer;">
                                        <div id="dt" style="float:left; position:relative; width:180px;overflow:hidden; text-overflow:ellipsis; white-space: nowrap;word-break:keep-all;">
                                            2015-02-02 星期四
                                        </div>
                                        <img src="/images/rq.png" style="float:right;" />

                                        <!--日期-->
                                        <div class="datatime" id="datatime">
                                            <table style=" border-collapse: collapse;">
                                                <tr class="head_tr">
                                                    <td colspan="7">
                                                        <span id="y" style="color:#fff; font-size:16px;">2015</span>年
                                                        <span id="m" style="color:#fff; font-size:16px;">4</span>月
                                                    </td>
                                                </tr>
                                                <tr class="tr" id="week_id">
													
                                                </tr>
                                                <tr class="tr" id="d">
                                                	
                                                </tr>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                                <div style="float:left; margin-left:45px;">
                                    <span style="float:left; height:30px; line-height:30px; color:#FFF;padding:0 5px;">时间</span>
                                    <!--<div style="float:left; background-color:#fff; padding:5px; height:100%;  border-radius:3px;outline: none; cursor:pointer;  border:1px solid red;" tabindex='-1'  onfocus="show_data_time('sj_node')" onblur="    hide_data_time('sj_node')" >-->
                                    <div id="_sj_node" style="float:left; background-color:#fff; padding:5px; height:100%;  border-radius:3px;outline: none; cursor:pointer;  " >
                                        <div id="sj" style="float:left; width:180px;overflow:hidden; text-overflow:ellipsis; white-space: nowrap;word-break:keep-all;">
                                            09:00-10:00
                                        </div>
                                        <img src="/images/nz.png" style="float:right;" />

                                        <!--时间-->
                                        <div class="sj_node" id="sj_node">
                                            <p>
                                                <img src="/images/x.png" style="vertical-align:middle; margin-top:-1px;" />
                                                <span style="font-size:10px;">预订开始时间（1小时为时间段）</span>
                                            </p>
                                            <div style="width:100%;">
                                                <table class="sj_node_tab" style=" border-collapse: collapse;">
                                                    <tr id="t1" style="background-color:rgb(247, 122, 66);">
                                                    </tr>
                                                    <tr id="t2">
                                                    </tr>
                                                    <tr id="t3">
                                                    </tr>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div style="float:left;margin-left:45px; position:relative;">
                                    <span style="float:left; height:30px; line-height:30px; color:#FFF;padding:0 5px;">场地</span>
                                    <!--<div id="_cd" style="float:left; position:relative; background-color:#fff; padding:5px; border-radius:3px; outline: none; cursor:pointer; " tabindex='-1' onfocus="show_data_time('cd')" onblur="    hide_data_time('cd')">-->
                                    <div id="_cd" style="float:left; position:relative; background-color:#fff; padding:5px; border-radius:3px; outline: none; cursor:pointer; " >
                                        <div id="cd_text" style="float:left; width:180px;overflow:hidden; text-overflow:ellipsis; white-space: nowrap;word-break:keep-all;">选择场地</div>
                                        <img src="/images/qz.png" style="float:right;" />
                                        <!--场地-->
                                        <div class="cd" id="cd">
                                        	
                                        </div>
                                        <input  type="hidden" id="spaceType"/>
                                    </div>
                                </div>
                                <div style="clear:both;"></div>

                            </div>
                            <table id="dd_tab">
                                <thead>
                                    <tr style="">
                                        <td width="350px">场地名称</td>
                                        <!--td width="200px">场地当前预订量/总数量</td-->
                                        <td width="200px">预订场地时间</td>
                                        <td width="150px">价格</td>
                                        <td width="150px">操作</td>
                                    </tr>
                                </thead>
                                <tbody id="cd_tbody">
                                     
                                </tbody>

                            </table> 
                    </div>
                    <div class="data_list_r">
 						<div style="height:80px;width:170px; margin:0 auto; ">
                            <div style="text-align:center; width:50px; height:46px; float:left; margin-right:5px;">
                                <img id='weatherId' src="http://tianqi.911cha.com/pic/day/${(weathInfo.weather_id.fa)!}.gif" width="50" height="46" />
                                <p style="font-size:12px;" id="weather">${(weathInfo.weather)!}</p>
                            </div>
                            <div style="width:110px; float:left;">
                                <p style="font-size:20px;" id="temperature">${(weathInfo.temperature)!}</p>
                                <div style="float:left;">
                                    <p style="font-size:14px;word-wrap: break-word; word-break: normal; word-break:break-all;" id="wind">${(weathInfo.wind)!}</p>
                                    <p style="font-size:14px;word-wrap: break-word; word-break: normal; word-break:break-all;"></p>
                                </div>
                            </div>
                        </div>
                        <div style="margin-top:20px;">
                        	<p style="line-height:30px;color: #999">输入接收短信的手机号码</p>
                            <p style="line-height:30px;">
                                <span>手机号</span>
                                <input id="telephone" name="telephone" type="text" style="width:120px;" maxlength='11'/>
                            </p>
<!--                             <p style="line-height:30px;">
                            	<div id="sendCheckCode" style="background-color:rgb(207,0,57);  border-radius:2px; text-align:center; margin:0 auto; width:100px; color:#fff; height:25px; line-height:25px; font-size:12px;">
                            		发送验证码
                            	</div>
                            </>
                             -->
                             	<p style="line-height:30px;">
                                <span>验证码</span>
                                <input id="validCode" name="validCode" type="text" style="width:60px;" /><img src="/draw/drawRandom.do" onclick="refreshImage(this);"/>
                            </p>
                            <div id="orderSave" class="yuding cls_hand" style="background-color:rgb(207,0,57);  border-radius:2px; text-align:center; margin:0 auto; margin-top:20px;margin-bottom:15px; width:100px; color:#fff; height:25px; line-height:25px; font-size:12px;">提交订单</div>
                        </div>
                    </div>
                </div>

            </div>
        </div>

        <!--评论区-->
        <div class="centre_1">
            <div class="centre1_head">
                <div class="type">
                    评论区
                </div>
                <!--<div class="gd">
                    更&nbsp;&nbsp;多
                </div>-->
            </div>
            <div class="centre1_block">
                <div style="position:absolute;left:50px; top:35px; ">
                    <img src="/images/jyd.png" />
                </div>
                <div style="width:938px; margin:0 auto;  margin-bottom:15px; margin-top:15px;height:100%; ">
                    <div style="width:100%;">
                         <p id="pf">
                            <span onmouseover="setImgSrc()">我要评分</span>
                            <img id="_img" onmousemove="pf(this);stars(1);" src="/images/hx.png" />
                            <img onmousemove="pf(this);stars(2);"  src="/images/hx.png" />
                            <img onmousemove="pf(this);stars(3);" src="/images/hx.png" />
                            <img onmousemove="pf(this);stars(4);"  src="/images/hx.png" />
                            <img onmousemove="pf(this);stars(5);"  src="/images/hx.png" />
                        </p>
                        <input type="hidden" id="starScore" value="0"/>
                    </div>
                    <div style="width:100%;height:67px; background-image:url('/images/kk.png');background-repeat:no-repeat;background-size:cover;-moz-background-size:cover;-webkit-background-size:cover;">
                        <textarea id="pfText" rows="2" cols="20" style="width:98%; height:87%; margin-left:10px; margin-top:3px; border:0px;outline: none;resize:none;"></textarea>
                    </div>
                   <a id="pfBut"> <div style="width:100%; margin-top:5px;   height:30px; "> 
                        <div class='cls_hand' style="width:80px; height:30px; line-height:30px; border-radius:3px; color:#fff; background-color:rgb(207,0,57); text-align:center; float:right;">
                            发布
                        </div>
                    </div>
                    </a>
						<input type="hidden" id="pageSize" value="10"/>
						<input type="hidden" id="currPage" value="1"/>
					<div id="pfAll">
					
					</div>
                    <div id="jiazai"></div>
                    <div style="clear:both;"></div>
                </div>
            </div>
        </div>



        <!--玩约战-->
        <!--
        <div class="centre_1">
            <div class="centre1_head">
                <div class="type">
                    玩约战
                </div>
                <div class="gd">
                    <img src="/images/shuaxin.png" />
                </div>

            </div>
            <div class="centre1_block">
                <div style="height:70px; width:100%; text-align:center; font-size:25px; color:#808080;">
                    ——同样预订该产地的会员邀您来对战——
                </div>
                <div style="width:98%; margin:0 auto; height:100%;">
                    <div class="centre1_foot">
                        <div class="centre1_foot_block1">
                            <img src="/images/yz1.jpg" />
                            <div class="tag">
                                <p id="tag_left">天下第一</p>
                                <p id="tag_right">羽毛球</p>
                                <div style="clear:both"></div>
                                <p>胜177平99负12</p>
                                <p>嘉定第一中学</p>
                                <p>2015-12-12 17:00-20:00</p>
                                <a id="query1" herf="#">挑战高手</a>
                            </div>

                        </div>
                        <div class="centre1_foot_block1">
                            <img src="/images/yz1.jpg" />
                            <div class="tag">
                                <p id="tag_left">天下第一</p>
                                <p id="tag_right">羽毛球</p>
                                <div style="clear:both"></div>
                                <p>胜177平99负12</p>
                                <p>嘉定第一中学</p>
                                <p>2015-12-12 17:00-20:00</p>
                                <a id="query1" herf="#">切磋一下</a>
                            </div>
                        </div>
                        <div class="centre1_foot_block1">
                            <img src="/images/yz1.jpg" />
                            <div class="tag">
                                <p id="tag_left">天下第一</p>
                                <p id="tag_right">羽毛球</p>
                                <div style="clear:both"></div>
                                <p>胜177平99负12</p>
                                <p>嘉定第一中学</p>
                                <p>2015-12-12 17:00-20:00</p>
                                <a id="query1" herf="#">我要虐菜</a>
                            </div>
                        </div>
                        <div class="centre1_foot_block1">
                            <img src="/images/yz1.jpg" />
                            <div class="tag">
                                <p id="tag_left">天下第一</p>
                                <p id="tag_right">羽毛球</p>
                                <div style="clear:both"></div>
                                <p>胜177平99负12</p>
                                <p>嘉定第一中学</p>
                                <p>2015-12-12 17:00-20:00</p>
                                <a id="query1" herf="#">我要虐菜</a>
                            </div>
                        </div>
                    </div>

                    <div style="clear:both;"></div>
                </div>
            </div>
        </div>-->

        <div id="_foot" style="width:1140px; margin:0 auto;">
		<!--网站公共头部 START-->
		<#include "/common/footer.ftl">
		<!--网站公共头部 END-->        
            <!--iframe src="/app/foot.html" style=" width:1140px" height="200" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe-->
        </div>
    </div>


    <script src="/js/juju/jquery_event.js"></script>
    
    	<!-- 统计js代码 -->
	<script src="/js/juju/totalPage.js"></script>
    
    <script>
    $(document).ready(function(){
    	var ownerAccountId = $("#ownerAccountId").val();
		var pageSize =$("#pageSize").val();
		var currPage = $("#currPage").val();
		$("#pfAll").html('');		
		jQuery.ajax({
			url: '/api/message/findMessByOwner.do',  
                type: 'POST',
                dataType: 'json', 
				data:{msgToId:ownerAccountId,pageSize:pageSize,currPage:currPage},
                success: function(data){
                
                	if(data==''){
                		$("#pfAll").html("<div style=\"width:100%; margin-top:5px;   height:100%; min-height:50px; overflow:hidden; border-top:1px solid #808080; padding:5px 0 10px 0 ; \" >暂无数据</div>")
                	}else{
                		var myObject = data.data;
                		if(myObject!=null){
                		var Obj = myObject.results;
						var a='';
						for(var i =0;i<Obj.length;i++){
						a+="<div style=\"width:100%; margin-top:5px;   height:100%; min-height:50px; overflow:hidden; border-top:1px solid #808080; padding:5px 0 10px 0 ; \" >";
						var b = '';
							if(Obj[i].msgResource==1){
								b="来自web客户端";
							} else if(Obj[i].msgResource==2){
								b="来自手机客户端";
							}
							a+="<div style=\" float:left;\"><img onerror=this.src='/images/default.png' src=\""+Obj[i].userImg+"\" width=\"43px\" height=\"43px\" /></div>";
							a+="<div style=\"width:880px; margin-left:8px; float:left; word-wrap: break-word; word-break: normal;  word-break:break-all;\"><span style=\"color:rgb(207,0,57);\">"+Obj[i].userAccount+"</span>&nbsp;&nbsp;&nbsp;&nbsp;";
							//a+="<span>"+Obj[i].msgContent+"</span><p> <span style=\"font-size:12px; color:#cecaca;padding-left:10px;\">"+Obj[i].showMsgTime+" "+b+"</span><div style='float:right;'><img src=\"/images/xing"+Obj[i].msgScore+".png\"/></div></p></div></div>";
							a+=""+Obj[i].msgContent+"  <p> <span style=\"font-size:12px; color:#cecaca;padding-left:10px;\">"+Obj[i].showMsgTime+" "+b+"</span><div style='float:right;'><img src=\"/images/xing"+Obj[i].msgScore+".png\"/></div></p></div></div>";
						}
						$("#pfAll").append(a);
						var totals = myObject.total;
						var currPages = myObject.currPage;
						var tempCurr =0;
						if(totals%10==0){
						 tempCurr = parseInt(totals/10);
						}else{
						tempCurr = parseInt(totals/10+1);
						}
						if(tempCurr>currPages){
						var b="<a href=\"javascript:void(0)\" onClick=\"findMore()\">共有"+myObject.total+"条,点击加载更多</a>";
						$("#jiazai").html('');
						$("#jiazai").html(b);
						}
						
						$("#currPage").val(myObject.currPage);
                		}
                	}
			    }
		});
    		
    });
    </script>
    <script>
    
    	function setImgSrc(){
    		$("#_img").attr("src","/images/hx.png");
    		$("#starScore").val('0');
    	}
		
        function li_seover(t) {
            $(t).css("backgroundColor", "rgba(255, 255, 255, 0.58)");
            $("div", $(t)).css("color", "red");
        }

        function li_out(t) {
            $(t).css("backgroundColor", "");
            $("div", $(t)).css("color", "rgb(114,113,113)");
        }

        var index="";
        function _open() {
        	index = $.layer({
                type: 2,
                title: false, //不显示默认标题栏
                shade: [0.5, '#000'], //不显示遮罩
                shift: 'top', //从头动画弹出
                area: ['550px', '446px'],
                iframe: { src: '/app/login.html' }
            });
        }

        function close(){
        	layer.close(index);
        }

        ////定场馆
        //function dcg_click(id, id1, li1, li2) {
        //    $("#" + li1).css({ "backgroundColor": "RGB(232,232,232)", "color": "#000000" });
        //    $("#" + li2).css({ "backgroundColor": "rgb(207,0,57)", "color": "#fff" });
        //    $("#" + id).css({ "display": "block" });
        //    $("#" + id1).css({ "display": "none" });
        //}

        function jump(url) {
            window.location = url;
        }

        //日期
        var _flag1 = false; // 全局变量，用于记住鼠标是否在DIV上
        document.getElementById('_datatime').onmouseover = function () {
            _flag1 = true;
        };

        document.getElementById('_datatime').onmouseout = function () {
            _flag1 = false;
        };

        //时间
        var _flag2 = false; // 全局变量，用于记住鼠标是否在DIV上
        document.getElementById('_sj_node').onmouseover = function () {
            _flag2 = true;
        };

        document.getElementById('_sj_node').onmouseout = function () {
            _flag2 = false;
        };

        //场地
        var _flag3 = false; // 全局变量，用于记住鼠标是否在DIV上
        document.getElementById('_cd').onmouseover = function () {
            _flag3 = true;
        };

        document.getElementById('_cd').onmouseout = function () {
            _flag3 = false;
        };

        document.body.onclick = function () {
            if (_flag1) {
                show_data_time('datatime');
            } else {
                hide_data_time('datatime');
            }
            if (_flag2) {
                show_data_time('sj_node');
            } else {
                hide_data_time('sj_node');
            }

            if (_flag3) {
                show_data_time('cd');
            }
            else {
                hide_data_time('cd');
            }
        };

        function show_data_time(id) {
            $("#" + id).slideDown(100);
        }
        function hide_data_time(id) {
            $("#" + id).slideUp(100);
        }
    </script>
    <script>
    	function stars(id){
    		$("#starScore").val(id);
    	}
    	
    	function findMore(){
			var currPage =parseInt($("#currPage").val())+1;
			var pageSize = parseInt($("#pageSize").val());
			var ownerAccountId = $("#ownerAccountId").val();
			
			jQuery.ajax({
			url: '/api/message/findMessByOwner.do',  
                type: 'POST',
                dataType: 'json', 
				data:{msgToId:ownerAccountId,pageSize:pageSize,currPage:currPage},
                success: function(data){
						var myObject = data.data;
						var Obj = myObject.results;
						var a='';
						for(var i =0;i<Obj.length;i++){ 
							a+="<div style=\"width:100%; margin-top:5px;   height:100%; min-height:50px; overflow:hidden; border-top:1px solid #808080; padding:5px 0 10px 0 ; \" >";
						var b = '';
							if(Obj[i].msgResource==1){
								b="来自web客户端"
							} else if(Obj[i].msgResource==2){
								b="来自手机客户端"
							}
							//a+="<div style=\" float:left;\"><img onerror=this.src='/images/default.png' src=\""+Obj[i].userImg+"\" width=\"43px\" height=\"43px\" /></div> <div style=\"width:888px; float:left;\"> <span style=\"color:rgb(207,0,57);\">"+Obj[i].userAccount+"</span>";
							//a+="<span>"+Obj[i].msgContent+"</span><p><span style=\"font-size:12px; color:#cecaca;padding-left:10px;\">"+Obj[i].showMsgTime+" "+b+"</span><div style='float:right;'><img src=\"/images/xing"+Obj[i].msgScore+".png\"/></div></p></div></div>";
							a+="<div style=\" float:left;\"><img onerror=this.src='/images/default.png' src=\""+Obj[i].userImg+"\" width=\"43px\" height=\"43px\" /></div>";
							a+="<div style=\"width:880px; margin-left:8px; float:left; word-wrap: break-word; word-break: normal;  word-break:break-all;\"><span style=\"color:rgb(207,0,57);\">"+Obj[i].userAccount+"</span>&nbsp;&nbsp;&nbsp;&nbsp;";
							//a+="<span>"+Obj[i].msgContent+"</span><p> <span style=\"font-size:12px; color:#cecaca;padding-left:10px;\">"+Obj[i].showMsgTime+" "+b+"</span><div style='float:right;'><img src=\"/images/xing"+Obj[i].msgScore+".png\"/></div></p></div></div>";
							a+=""+Obj[i].msgContent+"  <p> <span style=\"font-size:12px; color:#cecaca;padding-left:10px;\">"+Obj[i].showMsgTime+" "+b+"</span><div style='float:right;'><img src=\"/images/xing"+Obj[i].msgScore+".png\"/></div></p></div></div>";
							
						}
						$("#pfAll").append(a);
						var totals = myObject.total;
						var currPages = myObject.currPage;
						var tempCurr =0;
						if(totals%10==0){
						 tempCurr = parseInt(totals/10);
						}else{
						tempCurr = parseInt(totals/10+1);
						}

						if(tempCurr>currPages){
						var b="<a href=\"javascript:void(0)\" onClick=\"findMore()\">共有"+myObject.total+"条,点击加载更多</a>";
						$("#jiazai").html('');
						$("#jiazai").html(b);
						}else{
							$("#jiazai").html('');
						}
						$("#currPage").val(myObject.currPage);
			    }
		});
		}
    </script>
</body>
</html>
