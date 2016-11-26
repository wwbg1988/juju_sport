<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>订场馆</title>
    <link href="/css/juju/predetermine_site.css" rel="stylesheet" />
    <script src="/js/juju/jquery-2.1.3.min.js"></script>
    <script src="/js/juju/layer/layer.min.js"></script>
    <script src="/js/juju/loadUserInfo.js"></script>

    
</head>
<body>
    <div class="">
        <!--头部-->
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
                        <div  class="change" onmouseover="this.style.backgroundColor='#cf0039';this.style.color='#ffffff'" onmouseout="this.style.backgroundColor='';this.style.color=''">
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

<!--                    <a  href="/app/404.html">
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
               <!--     <a  href="/app/404.html">
                        <div class="nav_text">
                            <div class="change" onmouseover="this.style.backgroundColor='#cf0039';this.style.color='#ffffff'" onmouseout="this.style.backgroundColor='';this.style.color=''">
                                聚论坛
                            </div>
                        </div>
                    </a>-->

                    <div class="sign">
                    	<div id="islogout" style="display:none;">
	                        <div onclick="_open();" style="    height:40px;padding-top: 10px;  margin-right:10px; vertical-align:middle; float:left; cursor:pointer;">
	                            <!--<a href="javacsript:void" style="font-family:微软雅黑; 	font-size:16px; color:#717171;">-->
	                            <img src="/images/sign_in.png" style="vertical-align:middle;" />&nbsp;登录
	                            <!--  </a>-->
	                        </div>
	                        <div onclick="window.location = '/app/user/register.html'" style="   height:40px;padding-top: 10px; vertical-align:middle;float:left; cursor:pointer;" >
	                            <!--<a href="404.html"  style="font-family:微软雅黑; 	font-size:16px; color:#717171;">-->
	                            <img src="/images/zhuce.png" style="vertical-align:middle;" />&nbsp;注册
	                            <!--</a>-->
	                        </div>
                    	</div>
                    	<div id="islogin" style="display:none;">
	                        <div onclick="userManage();" style="height:40px;padding-top:10px;margin-right:10px;vertical-align:middle;float:left;">
	                            <!--<a href="javacsript:void" style="font-family:微软雅黑; 	font-size:16px; color:#717171;">-->
	                        <div style='float:left;'>    <img src="/images/sign_in.png" style="vertical-align:middle;" />您好,&nbsp;</div><div id="showName" class="cls_hand" style="float:left; max-width:80px;white-space: nowrap;word-break:keep-all;/* 不换行 */overflow: hidden;text-overflow: ellipsis;"></div>&nbsp;
	                            <!--  </a>-->
	                        </div>
							<!-- 用于第三方登录-->
							<input type="hidden" id="thirdName"/>
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
 
 
 
		<input type="hidden" id="orderId" value="${orderId}"/>
		<input type="hidden" id="teamListId" />
		
		<input id="pageSize" type="hidden" name="pageSize" value="10"/>
         <input id="currPage" type="hidden" name="currPage" value="1"/>
		
		<div style="width:1140px; margin:0 auto; margin-bottom:-20px;   ">
			 <p class="bm">我要报名：</p>
                    <div class="djs" style="float:left;">
                        <span id="date_tiem">00:00:00</span> 
                    </div>
                    
                           <div style=" float:right;  margin-top:40px; ">
            
            <div class="bdsharebuttonbox"><a href="#" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a><a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a><a href="#" class="bds_tqq" data-cmd="tqq" title="分享到腾讯微博"></a><a href="#" class="bds_renren" data-cmd="renren" title="分享到人人网"></a><a href="#" class="bds_tqf" data-cmd="tqf" title="分享到腾讯朋友"></a></div>
<script>window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdMini":"2","bdMiniList":false,"bdPic":"","bdStyle":"0","bdSize":"32"},"share":{}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>
            
            </div>
		<div style="clear:both;"></div>
		</div>
		
        <!--场馆-->
        <div id="ordersInfos"></div>
        
        
      
        <!--场馆详情-->
        <div class="site_info">
            <p class="xq">
                详情
            </p>
            <p class="font">
                聚运动，集场地资源、在线预订、赛事运营、体育资讯于一体的O2O线上平台。
            </p>
            <p class="font">
                推行全新运动概念与生活方式。集合全市运动场地资源，独家校园场地，分类场馆信息，满足不同需求。
            </p>
            <p class="font">
                一体化功能设计，轻松查询，一键预订。
            </p>
            <p class="font">
            从团体组队到场地预订，实现个性化定制服务。
            </p>
            <p class="font">
    官方赛事报道与健康运动资讯，时时发布与分享。
            </p>
            <div style="text-align:center; margin-top:30px; margin-bottom:15px;">
                <img src="/app/predetermine_site/images/ewm.png" style="width:200px; max-width:1100px;" /> 
            </div>
        </div>

        <!--热门评论-->
        <div class="comment">
            <div class="comment_title">
                 热门评论 
            </div>
            <div id="pfAll">
					
			</div>
			                  		<div id="jiazai"></div>
           
           

<!--<form  id="_pageOpt" name="paginationoptions" style="display:none;">
							<input type="text" value="0" name="maxitems" id="maxitems" class="numeric"/>
							<input type="text" value="10" name="items_per_page" id="items_per_page" class="numeric"/>
							<input type="text" value="10" name="num_display_entries" id="num_display_entries" class="numeric"/>
							<input type="text" value="2" name="num_edge_entries" id="num_edge_entries" class="numeric"/>
							<input type="text" value="上一页" name="prev_text" id="prev_text"/>
							<input type="text" value="下一页" name="next_text" id="next_text"/>
				</form> 
				<div id="Pagination" class="pagination"></div>
        </div>-->



        <!--尾部-->
        <div id="_foot" style="width:1140px; margin:0 auto;">
		<!--网站公共头部 START-->
		<#include "/common/footer.ftl">
		<!--网站公共头部 END-->        
            <!--iframe src="/app/foot.html" style=" width:1140px" height="200" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe-->
        </div>
    </div>
	<!-- 统计js代码 -->
	<script src="/js/juju/totalPage.js"></script>



<!--<div class="site">
            <div class="site_l">
                <p id="title">无兄弟，不运动</p>
                <p class="add_message">地址:<span>普陀区</span></p>
                <p class="add_message">场馆:<span>曹阳中学</span></p>
                <p class="add_message">时间:<span>2015.04.03 19:00--20:00</span></p>
                <p class="add_message">发起者:<span>天下第一</span></p>
            </div>
            <div class="site_r">
                <div>
                    
                    <div style="clear:both;"></div>
                    <div class="bm_btn" onclick="_open1();">
                        我要报名
                    </div>
                </div>
            </div>
        </div>-->

	<script>
		  $(document).ready(function(){
			     findOnload();
			     findImg();
		  });

		  function findOnload(){
		  			   var orderId = $("#orderId").val();
			   	$.ajax({
			        url: "/order/showRaceInfos.do",
			        data: {orderId:orderId},
			        type: 'POST',
			        dataType: 'json',
			        async : false,
			        success: function (data) {
			       // console.log(data);
			       		var a ="";
			       		if(data!=null&&data.data!=null){
			       		var jsonData = data.data;
			       		//console.log(jsonData);
			       			for(var i =0;i<jsonData.length;i++){
			       				a+="<div class=\"site\"><div class=\"site_l\"><p id=\"title\">无兄弟，不运动</p><p class=\"add_message\">地址:<span>"+jsonData[i].address+"</span></p>";
			       				a+=" <p class=\"add_message\">场馆:<span>"+jsonData[i].nickName+"</span></p><p class=\"add_message\">时间:<span>"+jsonData[i].orderTime+"</span></p> <p class=\"add_message\">发起者:<span>"+jsonData[i].userNickName+"</span></p>";
			       				a+="</div><div class=\"site_r\"><div><div style=\"clear:both;\"></div>";
			       				if(jsonData[i].diffTime=='0'&&jsonData[i].diffSecond=='0'){
			       					a+="<div class=\"bm_btn\" >已结束</div>"
			       				}else if(jsonData[i].orderStat=='3'&&jsonData[i].countsFlag=='true'){
			       				a+="<div class=\"bm_btn\" onclick=\"_open1('"+jsonData[i].itemId+"');\">我要报名</div>"
			       				}else if(jsonData[i].countsFlag=='false'){
			       					a+="<div class=\"bm_btn\");\">报名人数已满 </div>"
			       				}else{
			       					a+="<div class=\"bm_btn\" >已结束</div>"
			       				}
			       				
			       				a+="</div></div> </div>";
			       				f=jsonData[i].diffTime;
			       				m = jsonData[i].diffSecond;
			       				if(jsonData[i].teamListId!=null&&jsonData[i].teamListId!=''){
			       				$("#teamListId").val(jsonData[i].teamListId);
			       				}
			       			}
			       			$("#ordersInfos").html(a);
			       			
			       		}
			        }
			     });  
		  
		  }
		  
		  function findImg(){
		  	var orderId = $("#orderId").val();
						var pageSize =$("#pageSize").val();
						 var currPage = $("#currPage").val();
			     
						     $.ajax({
						     	url: "/api/message/findOrderByMess.do",
						        data: {orderId:orderId,pageSize:pageSize,currPage:currPage},
						        type: 'POST',
						        dataType: 'json',
						        async : false,
						        success: function (data) {
						     	//console.log(data);
						     	if(data!=''&&data.data.results!=''){
							     		var a="";
							     		var jsonData =data.data.results;
							     		$("#maxitems").val(data.data.total);
							     		for(var i=0;i<jsonData.length;i++){
							     		a+="<div class=\"comment_text\"><div class=\"topImg\">";
							     		a+="<img src=\""+jsonData[i].userImg+"\" width=\"100\" height=\"100\" /> ";
							     		a+="</div><div class=\"text\"> <p class=\"t_title\">"+jsonData[i].userAccount+"</p>";
							     		a+="<p class=\"t_content\">"+jsonData[i].msgContent+"</p>";
							     		var b = '';
										if(jsonData[i].msgResource==1){
											b="来自web客户端";
										} else if(jsonData[i].msgResource==2){
											b="来自手机客户端";
										}
							     		a+="<p class=\"t_come\">b</p>";
							     		a+=" </div></div>";
						     		}
						     		//console.log(a);
						     		$("#pfAll").append(a);
						     		var totals = data.data.total;
									var currPages = data.data.currPage;
									var tempCurr =0;
									if(totals%10==0){
									 tempCurr = parseInt(totals/10);
									}else{
									tempCurr = parseInt(totals/10+1);
									}
									if(tempCurr>currPages){
									var b="<a href=\"javascript:void(0)\" onClick=\"findImg()\">共有"+data.data.total+"条,点击加载更多</a>";
									$("#jiazai").html('');
									$("#jiazai").html(b);
									}else{
										$("#jiazai").html('');
									}
									$("#currPage").val(parseInt(data.data.currPage+1));
						
						     	}else{
						     		layer.msg("查询错误!", 1, 1);
						     	}
						     	
						     }
						     });
		  
		  }
	</script>
	
    <script> 
	    //倒计时
        var a=$("#diffTime").val(); 
        var s=0;
        var f = 0;
        var m = 0;
        function time_() {
            if (m == 0) {
                if (f != 0) {
                    f = f - 1;
                    m = 59;
                    $("#date_tiem").html((parseInt(s) < 10 ? "0" + s : s)+":"+(parseInt(f) < 10 ? "0" + f : f) + ":" + (parseInt(m) < 10 ? "0" + m : m));
                   
                } else {
                    if (s != 0) {
                        s = s - 1;
                        f = 59;
                        m = 59;
                        $("#date_tiem").html((parseInt(s) < 10 ? "0" + s : s) + ":" + (parseInt(f) < 10 ? "0" + f : f) + ":" + (parseInt(m) < 10 ? "0" + m : m));
                    } else {
                       // alert("时间到");
                        clearInterval(timeid);
                    }
                }
            } else {
                m = m - 1;
                $("#date_tiem").html((parseInt(s) < 10 ? "0" + s : s) + ":" + (parseInt(f) < 10 ? "0" + f : f) + ":" + (parseInt(m) < 10 ? "0" + m : m));
            }
        }
        var timeid = setInterval("time_() ", 1000); 
  		var _index="";
        function _open() { 
           _index= $.layer({
                type: 2,
                title: false, //不显示默认标题栏
                shade: [0.5, '#000'], //不显示遮罩
                shift: 'top', //从头动画弹出
                shadeClose: true, //开启点击遮罩关闭层
                area: ['500px', '396px'],
                iframe: { src: '/app/login.html', scrolling: 'no' }
            });
        }

        function close(){
        	layer.close(_index);
        }
        
        function _open1(id) {	
        var teamListId = $("#teamListId").val();
        if(teamListId!=null&&teamListId!=''){
        	$.ajax({
			        url: "/user/team/join.do",
			        data: {teamId:teamListId},
			        type: 'POST',
			        dataType: 'json',
			        async : false,
			        success: function (data) {
			        	if(data.status=='500'){
			        		layer.msg(data.message,1,1);
			        		return false;
			        	}
			        }
			     });   
        }else{
        	teamListId='-1';
        }
          	$.ajax({ 
			        url: "/order/registration.do",
			        data: {orderItems:id,teamListId:teamListId},
			        type: 'POST',
			        dataType: 'json',
			        async : false,
			        success: function (data) {
			      	// console.log(data);
			        if(data!=''){
			        	if(data.status=='500'){
			        		if(data.data!=''&&data.data=='402'){
			        			location.reload();
			        		}
			        		layer.msg(data.message, 1, 1);
			        		return false;
			        	}else{
			        	  $.layer({
					                type: 2,
					                title: false, //不显示默认标题栏
					                shade: [0.5, '#000'], //不显示遮罩
					                shift: 'top', //从头动画弹出
					                shadeClose: true, //开启点击遮罩关闭层
					                closeBtn: [1],
					                border: [0],
					                area: ['550px', '356px'],
					                time: 3,
					                end:function() { findOnload();
			    					 findImg();},
					                iframe: { src: '/app/predetermine_site/open.html', scrolling: 'no' }
					            });
			        	}
			        }
			        }
			    });
        }
    </script>
</body>
</html>
