<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>聚咨询</title> 
    <link href="/css/juju/paly_index.css" rel="stylesheet" />
    <script src="/js/juju/jquery-2.1.3.min.js"></script>
    <script src="/js/juju/layer/layer.min.js"></script>
    <script src="/js/juju/loadUserInfo.js"></script>

</head>
<body>
    <div class="container">

         <!--网站公共头部 START-->
<div id="whole" style="background-color: rgb(244, 245, 244);">
	<div id="top">
		<img src="/images/top.png">
	</div>
	<div class="logo_long">
		<div class="logo_nav">
			<div class="logo">
				<a href="/app/index.html"><img style="border:0px" src="/images/logo.png"></a>
			</div>
		</div>
	</div>

	<div id="logo_right">
		<div id="notice">
			<h3>新闻公告栏：聚运动官网即将正式运行，内容丰富，精彩多样，敬请期待......</h3>
		</div>

		<div id="main_nav">
			<a href="/app/index.html">
				<div class="nav_text">
					<div class="change"
						onmouseover="this.style.backgroundColor='#cf0039';this.style.color='#ffffff'"
						onmouseout="this.style.backgroundColor='';this.style.color=''">
						首页</div>
				</div>
			</a> <a href="/app/event_list.html">
				<div class="nav_text">
					<div class="change"
						onmouseover="this.style.backgroundColor='#cf0039';this.style.color='#ffffff'"
						onmouseout="this.style.backgroundColor='';this.style.color=''">
						聚赛事</div>
				</div>
			</a> <a href="/app/sitelist.html">
				<div class="nav_text">
					<div class="change"
						onmouseover="this.style.backgroundColor='#cf0039';this.style.color='#ffffff'"
						onmouseout="this.style.backgroundColor='';this.style.color=''">
						订场馆</div>
				</div>
			</a><!--  <a href="/app/404.html">
				<div class="nav_text">
					<div class="change"
						onmouseover="this.style.backgroundColor='#cf0039';this.style.color='#ffffff'"
						onmouseout="this.style.backgroundColor='';this.style.color=''">
						玩约战</div>
				</div>
			</a> --> <a href="/app/consultation/play_index.html">
				<div class="nav_text">
					<div class="change" style="background-color: #cf0039; color: #ffffff"
						onmouseover="this.style.backgroundColor='#cf0039';this.style.color='#ffffff'"
						onmouseout="this.style.backgroundColor='';this.style.color=''">
						聚咨询</div>
				</div>
			</a> <!-- <a href="/app/404.html">
				<div class="nav_text">
					<div class="change"
						onmouseover="this.style.backgroundColor='#cf0039';this.style.color='#ffffff'"
						onmouseout="this.style.backgroundColor='';this.style.color=''">
						聚论坛</div>
				</div>
			</a> -->

			<div class="sign">
				<div id="islogout">
					<div onclick="_open();" class='cls_hand'
						style="height: 40px; padding-top: 10px; margin-right: 10px; vertical-align: middle; float: left;">
						<!--<a href="javacsript:void" style="font-family:微软雅黑; 	font-size:16px; color:#717171;">-->
						<img src="/images/sign_in.png" style="vertical-align: middle;" />&nbsp;登录
						<!--  </a>-->
					</div>
					<div onclick="window.location = '/app/user/register.html'"
						class='cls_hand'
						style="height: 40px; padding-top: 10px; vertical-align: middle; float: left;">
						<!--<a href="404.html"  style="font-family:微软雅黑; 	font-size:16px; color:#717171;">-->
						<img src="/images/zhuce.png" style="vertical-align: middle;" />&nbsp;注册
						<!--</a>-->
					</div>
				</div>
				<div id="islogin" style="display: none;">
					<div onclick="userManage();" class='cls_hand'
						style="height: 40px; padding-top: 10px; margin-right: 10px; vertical-align: middle; float: left;">
						<!--<a href="javacsript:void" style="font-family:微软雅黑; 	font-size:16px; color:#717171;">-->
						<img src="/images/sign_in.png" style="vertical-align: middle;" />您好,&nbsp;<span
							id="showName"></span>&nbsp;
						<!--  </a>-->
					</div>
					<div onclick="loginOut();" class='cls_hand'
						style="height: 40px; padding-top: 10px; vertical-align: middle; float: left;">
						<!--<a href="404.html"  style="font-family:微软雅黑; 	font-size:16px; color:#717171;">-->
						<img src="/images/zhuce.png" style="vertical-align: middle;" />&nbsp;退出
						<!--</a>-->
					</div>
				</div>
			</div>
		</div>
	</div>

	<div style="clear: both;"></div>
</div>
		<!--网站公共头部 END-->

        <!--<iframe src="head.html" style=" width:100%; height:144px;" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe>-->
         
        
        <!--导航栏--> 
        <div class="dhl"  > 
            <ul>
                <li><a href="/app/consultation/play_index.html" target="_self">新闻中心</a></li>
               <!-- <li id="jztd" onclick="li_check(this,'parent.html')">家长天地</li> -->
			    <li><a href="/app/consultation/parent.html" target="_self">家长天地</a></li>
                <li><a href="/app/consultation/health.html" target="_self">健康知识</a></li>
                <li style="background-color:#CF0036; color:#FFFFFF">运动教学</li>
                <li><a href="/app/consultation/sxzl.html" target="_self">升学体育</a></li>
                
            </ul>
        </div>

        <!--内容-->
        <div id="c" style="width:85%; margin:0 auto; margin-bottom:15px;">
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>运动教学详细信息</title>
</head>
<body>
    <p style="font-size:16px; font-weight:bold; border-bottom:2px solid #dedede; margin-top:20px; ">
        运动介绍
    </p>
    ${lsidto.descde}
	${lsidto.videoUrl}

</body>
</html>
 </div>

		<!--网站公共头部 START-->
		<#include "/common/footer.ftl">
		<!--网站公共头部 END-->
    </div>


    <script src="/js/juju/jquery_event.js"></script>
    
	<!-- 统计js代码 -->
	<script src="/js/juju/totalPage.js"></script>
    
    <script>
        //头部导航栏点击事件
        function li_check(t,url) {
            $(".dhl ul li").each(function () {
                $(this).css({
                    "background-color": "#fff",
                    "border": "1px solid #ebeae9",
                    "color": "#333"
                });
            }); 
            $(t).css({
                "background-color":"rgb(207,0,57)",
                "border":"1px solid rgb(207,0,57)",
                "color":"#FFF"
            });
            load(url);
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


        function jump(url) {
            window.location = url;
        }

        function load(url) { 
            $("#c").load(url);
        }
         

    </script>
</body>
</html>

