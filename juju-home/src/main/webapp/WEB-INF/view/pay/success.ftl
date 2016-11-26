<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title></title> 
    <link href="/css/juju/pay_success.css" rel="stylesheet" />
    <script src="/js/juju/jquery-2.1.3.min.js"></script>
    <script src="/js/juju/layer/layer.min.js"></script>
    <script src="/js/juju/loadUserInfo.js"></script>
</head>
<body>

    <div class="container">

        <!--网站公共头部 START-->
		<#include "/common/header.ftl">
		<!--网站公共头部 END-->


        <!--<iframe src="../head.html" style=" width:100%; height:82px;" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe>-->
        <!--赛事-->
        <div class="centre1">
            <div class="centre1_head">
                <div class="type">订单</div>
            </div>

        </div>

        <div class="head"> 
            <img src="/images/zfcg.png" />
        </div>
         

        <div style=" width:1140px; margin:0 auto;    margin-bottom:20px;">
            <div style="background-color:#ffffff;">
                <div class="info">
                    <div id="block1">
                       <div style="margin-top:30px; float:left;">
                           <img src="/images/g.png" />
                       </div>
                        <div style="margin-top:30px; float:left; margin-left:10px;">
                            <p style="font-size:22px;">支付成功!</p>
                            <p style=" color:#909090;">订单正在处理中，成功后30分钟内将收到取票短信</p>
                            <p style=" color:#909090;">短信发送可能会有延迟或被手机安全软件拦截，入场时间未收到，</p>
                            <p style=" color:#909090;">请致电聚运动客户电话4000-xxx-xxx</p>
                        </div>
                    </div>
                    <!--<div id="block2">
                        <span>订单号：20150409000001</span>
                        <p>预订项目：羽毛球 &nbsp;&nbsp;预订日期：2015-04-09</p>
                        <p>预订场次：<span style="border:1px solid #333; padding:0 5px 0 5px;">塑胶一号篮球场14：00-15：00</span></p>
                        <p>应付金额：<span style="color:rgb(223,147,98); font-size:20px;">65</span>元（总金额：65元）</p>

                    </div>-->
                    <div id="block3">
                        <div class="block3_bg">
                            <div style="font-size:22px; margin-top:40px; ">订单详情:</div>
                            <div id="date_tiem" style="width:100%; font-weight:100; color:rgb(207,0,57);  padding:15px 0;  ">
							<#list itemDtoList as orderList>
								<p style="font-size:16px;">${(orderList.spaceName)!}&nbsp;&nbsp;${orderList.date?string('yyyy-MM-dd')} &nbsp;&nbsp; ${(orderList.orderTime)!}:00 - ${(orderList.endTime)!}:00</p>
							</#list>                            
                                <!-- <p style="font-size:16px;">篮球  宝山篮球体育中心1号场地 2015-04-03 19:00</p> -->
                                <p style="font-size:12px; margin-top:5px; color:#909090;">请合理安排到场时间以便开始活动</p>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
 
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
