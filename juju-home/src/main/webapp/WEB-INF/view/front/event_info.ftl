<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>聚运动赛事新闻分享</title>
    <link href="/css/juju/site_content.css" rel="stylesheet" />
    <script src="/js/juju/jquery-2.1.3.min.js"></script>
    <script src="/js/juju/layer/layer.min.js"></script>    
    <script src="/js/juju/event_info.js"></script>
    <script src="/js/juju/loadUserInfo.js"></script>
</head>
<body>
    <div class="container">

        <!--网站公共头部 START-->
        <div id="whole" style="background-color:rgb(244,245,244);">
            <div id="top"><img src="/images/top.png"></div>
            <div class="logo_long">
                <div class="logo_nav">
                    <div class="logo"><a href="/app/index.html"><img style="border:0px" src="/images/logo.png"></a></div>
                </div>
            </div>

            <div id="logo_right">
                <div id="notice">
                    <h3>新闻公告栏：聚运动官网即将正式运行，内容丰富，精彩多样，敬请期待......</h3>
                </div>

                <div id="main_nav">
					<a  href="/app/index.html">
	                    <div class="nav_text">
	                        <div class="change" onmouseover="this.style.backgroundColor='#cf0039';this.style.color='#ffffff'" onmouseout="this.style.backgroundColor='';this.style.color=''">
	                            首页
	                        </div>
	                    </div>
					</a>
                    <a  href="/app/event_list.html">
                        <div class="nav_text">
                            <div class="change" onmouseover="this.style.backgroundColor='#cf0039';this.style.color='#ffffff'" onmouseout="this.style.backgroundColor='';this.style.color=''">
                                聚赛事
                            </div>
                        </div>
                    </a>

                    <a  href="/app/sitelist.html">
                        <div class="nav_text">
                            <div class="change" onmouseover="this.style.backgroundColor='#cf0039';this.style.color='#ffffff'" onmouseout="this.style.backgroundColor='';this.style.color=''">
                                订场馆
                            </div>
                        </div>
                    </a>

<!--                     <a  href="/app/404.html">
                        <div class="nav_text">
                            <div class="change" onmouseover="this.style.backgroundColor='#cf0039';this.style.color='#ffffff'" onmouseout="this.style.backgroundColor='';this.style.color=''">
                                玩约战
                            </div>
                        </div>
                    </a> -->
                    <a  href="/app/consultation/play_index.html">
                        <div class="nav_text">
                            <div style="background-color: #cf0039; color: #ffffff" class="change" onMouseOver="this.style.backgroundColor='#cf0039';this.style.color='#ffffff'">
                                聚咨询
                            </div>
                        </div>
                    </a>
<!--                     <a  href="/app/404.html">
                        <div class="nav_text">
                            <div class="change" onmouseover="this.style.backgroundColor='#cf0039';this.style.color='#ffffff'" onmouseout="this.style.backgroundColor='';this.style.color=''">
                                聚论坛
                            </div>
                        </div>
                    </a> -->

                    <div class="sign">
                    	<div id="islogout" style="display:none;">
	                        <div onclick="_open();" class="cls_hand"  style="height:40px;padding-top: 10px;  margin-right:10px; vertical-align:middle; float:left;">
	                            <!--<a href="javacsript:void" style="font-family:微软雅黑; 	font-size:16px; color:#717171;">-->
	                            <img src="/images/sign_in.png" style="vertical-align:middle;" />&nbsp;登录
	                            <!--  </a>-->
	                        </div>
	                        <div onclick="window.location = '/app/user/register.html'" class="cls_hand" style="   height:40px;padding-top: 10px; vertical-align:middle;float:left;">
	                            <!--<a href="404.html"  style="font-family:微软雅黑; 	font-size:16px; color:#717171;">-->
	                            <img src="/images/zhuce.png" style="vertical-align:middle;" />&nbsp;注册
	                            <!--</a>-->
	                        </div>
                    	</div>
                    	<div id="islogin" style="display:none;">
	                        <div onclick="userManage();" class="cls_hand" style="height:40px;padding-top:10px;margin-right:10px;vertical-align:middle;float:left;">
	                            <!--<a href="javacsript:void" style="font-family:微软雅黑; 	font-size:16px; color:#717171;">-->
	                            <img src="/images/sign_in.png" style="vertical-align:middle;" />您好,&nbsp;<span id="showName"></span>&nbsp;
	                            <!--  </a>-->
	                        </div>
	                        <div onclick="loginOut();" class="cls_hand" style="   height:40px;padding-top: 10px; vertical-align:middle;float:left;">
	                            <!--<a href="404.html"  style="font-family:微软雅黑; 	font-size:16px; color:#717171;">-->
	                            <img src="/images/zhuce.png" style="vertical-align:middle;" />&nbsp;退出
	                            <!--</a>-->
	                        </div>                    		
                    	</div>
                    </div>
                </div>
            </div>
            <div style="clear:both;"></div>
        </div>
		<!--网站公共头部 END-->

        <!--<iframe src="head.html" style=" width:100%; height:144px;" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe>-->
        
        <div style="border-radius:3px; border:1px solid rgb(182,182,184); padding:15px 0 20px 0; width:1140px; margin:0 auto; margin-top:15px;  margin-bottom:15px;background-color:rgb(238,237,232); height:100%;">
          
            <div style="width:90%; margin:0 auto; background-color:#fff; border-radius:3px; padding:10px;">
            	<input type="hidden" id="infoId" value="${(infoId)!}"/>
                <div style="width:100%; padding:4px 0; border-bottom:2px dashed red;">
                    <p style="float:left;">
                        <img src="/images/xiangji.png"style="vertical-align:middle; " /><span style="font-size:18px; color:#eb0606;">&nbsp;精彩瞬间</span> 
                    </p>
                    <p class="c_title" style="float:left;width:850px; font-size:24px; color:rgb(182,182,184); text-align:center;  white-space: nowrap; word-break:keep-all;/* 不换行 */overflow: hidden;text-overflow: ellipsis;">
                        	嘉定一中夺得联赛冠军
                    </p>
                    <!--
                    <p style="float:right;margin-top:7px; font-size:16px; color:rgb(182,182,184);">
                     	打印  &nbsp;&nbsp;
                    </p>
                    -->
                    <div style="clear:both;"></div>
                </div>
				<!--
                <div style="width:100%; padding:4px 0; text-align:center;">
                    <img src="/images/photo.png" style="max-width:1020px;" /> 
                </div>-->
                <div class="c_context" style="width:95%; margin:0 auto; border-bottom:2px dashed red;">
                
                </div>
                 <div style="clear:both;"></div>              
            </div>
        </div>

		<!--网站公共头部 START-->
		<#include "/common/footer.ftl">
		<!--网站公共头部 END-->
    </div>
	<!-- 统计js代码 -->
	<script src="/js/juju/totalPage.js"></script>
    <script>
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
</body>
</html>
