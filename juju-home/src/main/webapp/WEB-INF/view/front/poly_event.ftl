<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>聚运动</title>
    <link href="/css/juju/poly_event.css" rel="stylesheet" />
    <script src="/js/juju/jquery-2.1.3.min.js"></script>
    <script src="/js/juju/layer/layer.min.js"></script>
    <script src="/js/juju/layer/layer.min.js"></script>
    <script src="/js/juju/poly_event.js" type="text/javascript"></script>
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
                            <div class="change" style="background-color:#cf0039;color:#ffffff" onmouseover="this.style.backgroundColor='#cf0039';this.style.color='#ffffff'">
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
                            <div class="change" onmouseover="this.style.backgroundColor='#cf0039';this.style.color='#ffffff'" onmouseout="this.style.backgroundColor='';this.style.color=''">
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
        <form id="_event_form" action="/race/poly_event.do" method="POST">
        	<input id="raceId" type="hidden" name="raceId" value="${(raceId)!}"/>
        </form>
        <div style="height:30px;">
        </div>
        <!--赛事推送-->
        <!--div class="container_block">
            <div id="container_block_l">
                <img src="/images/photo2.png" style="width:716px; height:426px;" />
            </div>
            <div id="container_block_r" style="">
                <div class="container_block_r_head">
                    <img id="container_block_r_head_img" src="/images/game_mark.png" />
                    <span id="container_block_r_head_text">赛事推送</span>
                </div>
                <ul class="container_block_ul" id="eventListId">
                    <li onmouseover="li_seover(this)" ; onmouseout="li_out(this)">
                        <div>2015中学生赛事</div>
                    </li>
                    <li onmouseover="li_seover(this)" ; onmouseout="li_out(this)">
                        <div>2015中学生赛事</div>
                    </li>
                    <li onmouseover="li_seover(this)" ; onmouseout="li_out(this)">
                        <div>2015中学生赛事</div>
                    </li>
                    <li onmouseover="li_seover(this)" ; onmouseout="li_out(this)">
                        <div>2015中学生赛事</div>
                    </li>
                    <li onmouseover="li_seover(this)" ; onmouseout="li_out(this)">
                        <div>2015中学生赛事</div>
                    </li>
                    <li onmouseover="li_seover(this)" ; onmouseout="li_out(this)">
                        <div>2015中学生赛事</div>
                    </li>
                    <li onmouseover="li_seover(this)" ; onmouseout="li_out(this)">
                        <div>2015中学生赛事</div>
                    </li>

                    <li>
                        <div class="container_block_div">
                            <div style="font-size:14px; width:100px;margin:0 auto; height:30px; line-height:30px; color:#ffffff; background-color:RGB(207,0,57); cursor:pointer; ">
                                2015.4.30
                            </div>
                        </div>
                    </li>
                </ul>
                <div class="bm">我要报名</div>
            </div>
            <div style="clear:both;"></div>
        </div-->
          

        <div style="border-radius:3px; border:1px solid rgb(182,182,184); padding:4px 0; width:1140px; margin:0 auto;   margin-bottom:15px;background-color:rgb(238,237,232); height:100%;">
            <div class="even_info">
            	
            </div>
            <div class="info_list">
                <div class="ph" style="text-align:center;">
                    <ul>
                        <li>小组排名</li>
                        	<!--
                        <li>淘汰赛</li>
                        <li>射手榜</li>
                        -->
                    </ul>
                </div>
                <div class="teamScoreList">
					
                </div>
                <!--
                <div style=" margin-bottom:30px; border:1px solid rgb(201,201,201);background-color:rgb(230,230,230); padding:5px 0; text-align:center; width:80px; margin:0 auto;">更多</div>

                <div style="width:275px; text-align:center; margin:0 auto; margin-top:20px;" >
                    <div style="color:#fff; width:100%; background-color:rgb(207,0,57);">
                        轮次：小组赛第三场
                    </div>
                    <div style="width:100%; background-color:rgb(230,230,230);">
                        <div style="padding:8px 0; font-size:18px; font-weight:bold; color:rgb(96,96,96); ">同济大学VS复旦大学</div>
                        <div style="padding:4px 0; font-size:14px;   color:rgb(0, 0, 0); ">2015.3.19 16:00-18:00</div>
                    </div>
                </div>
-->
            </div>
            <div style="clear:both;"></div>
        </div>
 <!--评论区-->
        <div class="centre_1">
            <div class="centre1_head">
                <div class="type">
                </div>
                <!--<div class="gd">
                    更&nbsp;&nbsp;多
                </div>-->
            </div>
            <div class="centre1_block" style="position:relative;">
                <div style="position:absolute;left:85px; top:20px; ">
                    <img src="/images/jyd.png" />
                </div>
                <div style="width:938px; margin:0 auto;  margin-bottom:15px; margin-top:15px;height:100%; ">
                   <!-- <div style="width:100%;">
                         <p id="pf">
                         <span onmouseover="setImgSrc()">我要评分</span>
                            <img  id="_img" onmousemove="pf(this);stars(1);" src="/images/hx.png" />
                            <img onmousemove="pf(this);stars(2);"  src="/images/hx.png" />
                            <img onmousemove="pf(this);stars(3);" src="/images/hx.png" />
                            <img onmousemove="pf(this);stars(4);"  src="/images/hx.png" />
                            <img onmousemove="pf(this);stars(5);"  src="/images/hx.png" />
							
                        </p>
                        <input type="hidden" id="starScore"/>
                    </div> -->
                    <div style="width:100%;height:67px; background-image:url('/images/kk.png');background-repeat:no-repeat;background-size:cover;-moz-background-size:cover;-webkit-background-size:cover;">
                        <textarea id="pfText" rows="2" cols="20" style="width:98%; height:87%; margin-left:10px; margin-top:3px; border:0px;outline: none;resize:none;"></textarea>
                    </div>
                   <a id="pfBut"> <div style="width:100%; margin-top:5px;   height:30px; "> 
                        <div class="cls_hand" style="width:80px; height:30px; line-height:30px; border-radius:3px; color:#fff; background-color:rgb(207,0,57); text-align:center; float:right;">
                            发布
                        </div>
                    </div></a>
		<input type="hidden" id="pageSize" value="10"/>
		<input type="hidden" id="currPage" value="1"/>
					<div id="pfAll">
					
					
					</div>
                    
                    		<div id="jiazai"></div>
                    <div style="clear:both;"></div>
                </div>
            </div>
        </div>

		<!--网站公共头部 START-->
		<#include "/common/footer.ftl">
		<!--网站公共头部 END-->
    </div>
    
    <script src="/js/juju/jquery_event.js"></script>
		<!-- 统计js代码 -->
	<script src="/js/juju/totalPage.js"></script>
	    <script>
    $(document).ready(function(){
		var raceId = $("#raceId").val();
    	var ownerAccountId = $("#ownerAccountId").val();
		var pageSize =$("#pageSize").val();
		var currPage = $("#currPage").val();
		$("#pfAll").html('');
		jQuery.ajax({
			url: '/api/message/findMessByOwner.do',  
                type: 'POST',
                dataType: 'json', 
				data:{msgToId:raceId,pageSize:pageSize,currPage:currPage},
                success: function(data){
                	if(data==''){
                		$("#pfAll").html(" <div style=\"width:100%; margin-top:5px;   height:100%; min-height:50px; overflow:hidden; border-top:1px solid #808080; padding:5px 0 10px 0 ; \" >暂无数据</div>")
                	}else{
                		var myObject = data.data;
						//console.log(myObject);
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
							//a+="<div style=\" float:left;\"><img onerror=this.src='/images/default.png' src=\""+Obj[i].userImg+"\" width=\"43px\" height=\"43px\" /></div> <div style=\"width:888px; float:left;\"><span style=\"color:rgb(207,0,57);padding-left:10px;\">"+Obj[i].userAccount+"</span>";
							//a+="&nbsp;&nbsp;&nbsp;<span>"+Obj[i].msgContent+"</span><p> <span style=\"font-size:12px; color:#cecaca;padding-left:10px;\">"+Obj[i].showMsgTime+" "+b+"</span></p></div></div>";
						a+="<div style=\" float:left;\"><img onerror=this.src='/images/default.png' src=\""+Obj[i].userImg+"\" width=\"43px\" height=\"43px\" /></div>";
							a+="<div style=\"width:880px; margin-left:8px; float:left; word-wrap: break-word; word-break: normal;  word-break:break-all;\"><span style=\"color:rgb(207,0,57);\">"+Obj[i].userAccount+"</span>&nbsp;&nbsp;&nbsp;&nbsp;";
							//a+="<span>"+Obj[i].msgContent+"</span><p> <span style=\"font-size:12px; color:#cecaca;padding-left:10px;\">"+Obj[i].showMsgTime+" "+b+"</span><div style='float:right;'><img src=\"/images/xing"+Obj[i].msgScore+".png\"/></div></p></div></div>";
							a+=""+Obj[i].msgContent+"  <p> <span style=\"font-size:12px; color:#cecaca;padding-left:10px;\">"+Obj[i].showMsgTime+" "+b+"</span</p></div></div>";
						
						}
						//console.log(a);
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
						var b="<a href=\"javascript:void(0)\" onClick=\"findMore()\">共有<span id='totalLists'>"+myObject.total+"</span>条,点击加载更多</a>";
						$("#jiazai").html('');
						$("#jiazai").html(b);
						}
						$("#currPage").val(myObject.currPage);
                	}
			    }
		});
    });
    </script>
    <script>
    
    	function setImgSrc(){
    		$("#_img").attr("src","/images/hx.png"); 
    		$("#starScore").val('');
    	}
		
        function li_seover(t) {
            $(t).css("backgroundColor", "rgba(255, 255, 255, 0.58)");
            $("div", $(t)).css("color", "red");
            $("#container_block_l").html("<img src="+$(t).attr("data-pic")+" style='width:716px; height:426px;'>");
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

    </script>
	
	<script>
    	function stars(id){
    		$("#starScore").val(id);
    	}
    	
    	
    </script>
</body>
</html>
