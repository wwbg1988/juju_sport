<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title></title>
    <link href="/css/juju/pay.css" rel="stylesheet" /> 
    <script src="/js/juju/jquery-2.1.3.min.js"></script>
	<script src="/js/juju/layer/layer.min.js"></script>
    <script src="/js/juju/payIndex.js"></script>
    <script src="/js/juju/loadUserInfo.js"></script>
</head>
<body>
    <div class="container">
        <!--网站公共头部 START-->
		<#include "/common/header.ftl">
		<!--网站公共头部 END-->
        <!--赛事-->
        <div class="centre1">
            <div class="centre1_head">
                <div class="type">赛事</div>
                <!-- <div class="gd">更&nbsp;&nbsp;多 </div> -->
            </div>
        </div>
        <div class="head"> 
              <img src="/images/bar.png" />
        </div>
        <div class="qp" >
            <!-- <span style="margin-left:25px;">请选择取票方式</span> -->
        </div>

        <div style=" width:1140px; margin:0 auto;">
            <div style="padding-bottom:10px;background-color:#ffffff;">
                <div class="info">
                    <div id="block1">
                        <p>预订场馆：${(venusInfoDto.nickName)!}</p>
                        <p>详细地址：${(venusInfoDto.address)!}</p>
                        <!--
                        <p>公交信息：82 83 170</p>
                        <p>停车信息：免费车位</p>
                        -->
                    </div>
                    <div id="block2">
                        <span>订单号：${(payWaitDto.orderNo)!}</span>
                        <p>预订日期：${(payWaitDto.date)!}</p>
                        <p>预订场次：</p>
							<#list orderItemsList as orderList>
								<p><span style="border:1px solid #333; padding:0 5px 0 5px;">${(orderList.spaceName)!}&nbsp;&nbsp;${(orderList.orderTime)!}:00 - ${(orderList.endTime)!}:00</span>
								</p>
							</#list>
                        <!--14：00-15：00-->
                        <p>应付金额：<span style="color:rgb(223,147,98); font-size:20px;">${(payWaitDto.orderTotal)!}</span>元（总金额：${(payWaitDto.orderTotal)!}元）</p>

                    </div>
                    <div id="block3">
                        <div class="block3_bg">
                            <div style="font-size:12px;">
                                		请在30分钟内完成付款,超时系统将自动释放已选场次。<!--，支付中如遇到问题请致电：########-->
                            </div>
                            <div id="date_tiem" style="width:100%; font-weight:100; color:rgb(207,0,57); font-size:40px; padding:15px 0; text-align:center;  ">
	                        	<#if isdisabled == 1>
	                        		<!-- ${(minutes)!} -->
	                        	<#else>
	                        		订单超时
	                        	</#if>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="type_list">
            <ul>
            	<li class="li" onclick="li_click(this)">网上支付&nbsp;&nbsp;</li>
            	<!--
                <li class="li" onclick="li_click(this)">聚金币支付&nbsp;&nbsp;</li>
                <li style="padding-left:10px;">●</li>
                <li onclick="li_click(this)" class="li" style="background-color:#fff;">网上支付&nbsp;&nbsp;</li>
                <li style="padding-left:10px;">●</li>
                <li onclick="li_click(this)" class="li">信用卡支付&nbsp;&nbsp;</li>
                <li style="padding-left:10px;">●</li>
                <li onclick="li_click(this)" class="li">扫码支付&nbsp;&nbsp;</li>
                -->
            </ul>
        </div>


        <div style=" width:1140px; margin:0 auto;"> 
            <div id="tab" >
            <div id="type">请选择付款方式</div>
            <table class="table">
                <tr>
                    <td class="zfpt_l">支付平台：</td>
                    <td class="zfpt_r">
                        <div style="margin:30px 10px 10px 0;  float:left;">
                            <input id="rd2" type="radio" value="zhifubao" name="rd2" checked/>
                            <label for="rd2">
                                <img src="/images/zhifubao.jpg" style="width:99px;height:36px;vertical-align:middle;" />
                            </label>
                        </div>
                    </td>
                </tr>

                <tr>
                    <td ></td>
                    <td class="td_r">
                        <div class="td_r_container" >
                            <div style="padding:10px;">
                                <input id="ck1" type="checkbox"/><label for="ck1" style="color:#333; font-weight:bold;">服务条款</label>(重要提示：请仔细阅读服务条款,同意并勾选后付款)<br />
                                <span style="font-size:12px;">
                                    1、请仔细核对您的预订信息，本次预订的服务一但支付成功，将不予退换。<br />
                                </span>
                                <span style="font-size:12px;">
                                    2、在极少数情况下，由于设备或网络等原因导致您预订的场地需要调整，允许聚运动帮您调换其他最佳场地
                                </span>
                            </div>
                        </div>
                        <div style="margin-bottom:60px;">
                        	<!-- <a id="query" herf="#" class="cls_hand">确认无误，支付</a>&nbsp;&nbsp;&nbsp; -->
                        	<#if isdisabled == 1>
                        		<a id="query" herf="#" class="cls_hand">确认无误，支付</a>&nbsp;&nbsp;&nbsp;
                        	<#else>
                        		<a id="queryDsiable" herf="#" class="cls_hand">确认无误，支付</a>&nbsp;&nbsp;&nbsp;	
                        	</#if>
                            <!-- <a herf="#" style="color:rgb(207,0,57);">返回重新选择优惠方式>></a> -->
                        </div>
                    </td>
                </tr>
            </table>
        </div>
        </div>
        	<input type="hidden" id="payMinutes" name="payMinutes" value="${(minutes)!}"/>
        	<form id="nodifyForm" action="/api/success.do" method="post">
        		<input type="hidden" id="out_trade_no" name="out_trade_no" value="${(payWaitDto.orderNo)!}"/>
        	</form>
	        <form id="alipayment" name=alipayment action=/pay/index.do method=post target="_blank">
	        	<input type="hidden" id="out_trade_no" name="out_trade_no" value="${(payWaitDto.orderNo)!}"/>
	        	<input type="hidden" id="subject" name="subject" value="${(payWaitDto.orderNo)!}"/>
	        	<input type="hidden" id="total_fee" name="total_fee" value="${(totalPrice)!}"/>
	        </form>
        <div id="alipayForm">
        </div>
		<input type="hidden" name="orderItemId" id="orderItemId" value="${(orderItemsDto.id)!}"/>
		<!--网站公共头部 START-->
		<#include "/common/footer.ftl">
		
			<!-- 统计js代码 -->
	<script src="/js/juju/totalPage.js"></script>
		<!--网站公共头部 END-->
		<script type="text/javascript">
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
		</script>
    </div>
</body>
</html>
