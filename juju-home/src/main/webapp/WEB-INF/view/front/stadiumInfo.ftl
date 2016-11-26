<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>推荐场馆</title> 
    <link href="/css/juju/siteinfo.css" rel="stylesheet" />
    <link href="/css/juju/star.css" rel="stylesheet" />
    <script src="/js/juju/jquery-2.1.3.min.js"></script>
    <script src="/js/juju/loadSpace.js"></script>
    
    <style>
		.showClass{
		}
		.hiddenClass{
			background:#d8d8d8;
		}    
    </style>
   
</head>
<body>
    <div class="container">
        <iframe src="/app/head.html" style=" width:100%; height:82px;" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe>
		<div id="bg">
            <img class="bg_img" src="/images/siteinfo.jpg">
            <div id="bg1">
                <p style="text-align:center; width:100%; font-size:18px;color:#ffffff; margin-top:10px;">普陀区第一中学</p>
                <p style="text-align:center;    height:20px; line-height:20px; width:100%; font-size:12px;color:#ffffff;  margin-bottom:4px; ">
                    <img src="/images/dz.jpg" width="15" height="20" style="vertical-align:middle;">普陀区梅川路  1425号
                </p>
                <p style="text-align:center;   height:20px; line-height:20px; width:100%; font-size:12px;color:#ffffff;  ">
                    <img src="/images/dh.jpg" width="15" height="20" style="vertical-align:middle;">普陀区梅川路  1425号
                </p>
            </div>
            <div id="bg2">
                <p style="text-align:center;   height:20px; line-height:20px; width:100%; font-size:12px;color:#ffffff; margin-top:10px; ">
                    <img src="/images/xin.jpg" height="20" style="vertical-align:middle;">
                    10分
                </p>
                <p style="text-align:center;   height:20px; line-height:20px; width:100%; font-size:12px;color:#ffffff; margin-top:10px; "> 学校入驻中,暂时不接受预订
                </p>
            </div>
        </div>
		<form>
			<input type="hidden" id="orderTime" name="orderTime" value="${nowDate}"/>
			<input id="ownerAccountId" name="ownerAccountId" type="hidden" value="${ownerAccountId}"/>		
		</form>
        <!--场地预订信息-->
        <div style="height:100%; overflow:hidden; background-color:#ffffff;  margin-bottom:20px;">

            <div class="contents">
                <div class="content_list">
                    <div class="date_info">
                        <table id="date_info_tab">
                            <tr id="dateList">
                            	
                            </tr>
                        </table>
                    </div>

                    <div class="time_slot">
                        <ul id="hourList">
							
                        </ul>
                    </div>
                    <div id="spaceList" class="content_info">

                    </div>
                </div>
            </div>

            <div class="contents1">
                <div class="top">
                    <div id="state">
                        <p> 立即预订&nbsp;</p><p class="bg_color1"></p> <p>&nbsp;&nbsp;&nbsp;&nbsp;可预订&nbsp;</p> <p class="bg_color2"></p> <p>&nbsp;&nbsp;不可预订&nbsp;</p> <p class="bg_color3"></p>
                    </div>
                    <p>体育项目：篮球</p>
                    <p>日期：<span id="showDate">${nowDate}</span></p>
                    <p>场次：</p>
                    <div id="choose">
                    </div>
                </div>
                <div class="botton">
                                                            输入接受进场的手机 
                    <p>手机号&nbsp;&nbsp;<input id="telephone" name="telephone" type="text" /></p>
                    <p>验证码&nbsp;&nbsp;<input style="width:50px;" id="Text1" type="text" />&nbsp;&nbsp;<img src="/umanages/drawRandom.do" onclick="refreshImage(this);"/></p>
                    <a id="orderSave" herf="#">提交</a>
                </div>
            </div>
        </div>

		<!-- 评论-->
		<div class="www_zzjs_net">
		<span class="zzjs_net">我要评分:</span>
		<ul class="_zzjs_net" id="www_zzjs_">
		<li><a href="javascript:void(0);" class="one-star" star:value="1">1</a></li>
		<li><a href="javascript:void(0);" class="two-stars" star:value="2">2</a></li>
		<li><a href="javascript:void(0);" class="three-stars" star:value="3">3</a></li>
		<li><a href="javascript:void(0);" class="four-stars" star:value="4">4</a></li>
		<li><a href="javascript:void(0);" class="five-stars" star:value="5">5</a></li>
		</ul>
		<span id="stars2-tips" class="result"></span>
		<input type="hidden" class="stars2" id="stars2-input" name="b" value="" size="2" />
		</div>
		<div>
		评论:<textarea id="contents" rows="3" cols="200" ></textarea>最多140字
		<input type="button" value="发布" id="subBtn"/>
		</div>
		<input type="hidden" id="pageSize" value="10"/>
		<input type="hidden" id="currPage" value="1"/>
		<div id="pinlunDiv">

			
			
		</div>
		<div id="jiazai"></div>
	


        <!--约战区-->
        <div class="centre1">
            <div class="centre1_head">
                约战区<br />
                <span>——同样预订该产地的会员邀您来对战——</span>
                <div class="centre1_h">
                    <img src="/images/h.jpg" />
                </div>
            </div>
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
                        <a id="query" herf="#">挑战高手</a>
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
                        <a id="query" herf="#">挑战高手</a>
                    </div>
                </div>
                <div class="centre1_foot_block1">
                    <img src="/images/yz1.jpg"  />
                    <div class="tag">
                        <p id="tag_left">天下第一</p>
                        <p id="tag_right">羽毛球</p>
                        <div style="clear:both"></div>
                        <p>胜177平99负12</p>
                        <p>嘉定第一中学</p>
                        <p>2015-12-12 17:00-20:00</p>
                        <a id="query" herf="#">挑战高手</a>
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
                        <a id="query" herf="#">挑战高手</a>
                    </div>
                </div>
            </div>
        </div>

        <div id="_foot">
            <iframe src="/app/foot.html" style=" width:100%;" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe>
        </div>

    </div>
    	<!-- 统计js代码 -->
	<script src="/js/juju/totalPage.js"></script>
        <script>
			var TB = function() {
			var T$ = function(id) { return document.getElementById(id) }
			var T$$ = function(r, t) { return (r || document).getElementsByTagName(t) }
			var Stars = function(cid, rid, hid, config) {
			var lis = T$$(T$(cid), 'li'), curA;
			for (var i = 0, len = lis.length; i < len; i++) {
			lis[i]._val = i;
			lis[i].onclick = function() {
			T$(rid).innerHTML = '<em>' + (T$(hid).value = T$$(this, 'a')[0].getAttribute('star:value')) + '分</em> - ' + config.info[this._val];
			curA = T$$(T$(cid), 'a')[T$(hid).value / config.step - 1];
			};
			lis[i].onmouseout = function() {
			curA && (curA.className += config.curcss);
			}
			lis[i].onmouseover = function() {
			curA && (curA.className = curA.className.replace(config.curcss, ''));
			}
			}
			};
			return {Stars: Stars}
			}().Stars('www_zzjs_', 'stars2-tips', 'stars2-input', {
			'info' : ['不推荐，不考虑', '凑合，别无选择可考虑', '不错值得考虑', '很棒，大家推荐去', '最佳，绝对首选'],
			'curcss': ' current-rating',
			'step': 1
			});
	</script>
	<script>
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
							a+="<p><img src=\""+Obj[i].userImg+"\"/><img src=\"/images/xing"+Obj[i].msgScore+".png\"/></br>"+Obj[i].userAccount+":"+Obj[i].msgContent+"</p>";
						}
						$("#pinlunDiv").append(a);
						var b="<a href=\"javascript:void(0)\" onClick=\"findMore()\">共有"+myObject.total+"条,点击加载更多</a>";
						$("#jiazai").html('');
						$("#jiazai").html(b);
						$("#currPage").val(myObject.currPage);
			    }
		});
		}
	</script>
	 

<script src="/js/module/require.js" data-main="/js/source/comments"></script>
    <script src="/js/juju/jquery_event.js"></script> 
</body>
</html>
