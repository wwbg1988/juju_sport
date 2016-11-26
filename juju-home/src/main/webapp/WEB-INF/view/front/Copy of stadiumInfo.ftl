<!Doctype html>
<html>
<head>
<script src="/js/module/jquery-1.11.1.min.js"></script>
<script src="/js/layer/layer.min.js"></script>
</head>
<body>
	<div style="border:1px double blue;text-align:center;height:20px;">
		head 头部 logo 菜单部分
	</div>
	
	<div style="border:1px double blue;text-align:center;height:40px;">
		轮播图
	</div>
<div style="width:100%">
	<div style="border:1px double blue;text-align:center;height:50px;">
		<table>
			<tr id="dateList" style="border:1px double red;width:30px;height:30px;">

			</tr>
		</table>
	</div>
	<div>
		<table>
			<tr>
				<td>输入手机号码</td>
				<td><input id="telephone" name="telephone" type="text"/></td>
			</tr>
		</table>		
	</div>
	<!--此处如何获取map 就不说了。以下是循环map-->
	<div>
		<table>
			<style>
				.showClass{
				}
				.hiddenClass{
					background:gray;
				}
			</style>
			<tr id="hourList" style='text-align:center;'>
			</tr>
			<thead id="spaceList"> 
			</thead>
		</table>
	</div>
	<div id="choose"><!--列出选择的信息-->
	</div>
	<div><!--列出选择的信息-->
		总价：<span id="totalPrice">0</span>
	</div>
	<div id="resultInfo" style="display:none;">
		您已成功预订;赶快和你小伙伴一起运动吧。
	</div>	
	<div>
		<form action="" method="POST">
			<input type="hidden" name="" value=""/>
			<input type="hidden" id="orderTime" name="orderTime" value="${nowDate}"/>
			<input id="ownerAccountId" name="ownerAccountId" type="hidden" value="${ownerAccountId}"/>
			<input id="orderSave" type="button" value="提交订单"/>
		</form>
	</div>
	



	<script>
		$(document).ready(function() {
			loadDateList();
			loadHourList();
			loadSpaceList();

			$("#orderSave").click(function(){
				var orderItems = "";
				var itemPrices = "";
				var itemSpaceName = "";
				$(".imgFocus").each(function(index,domEle){
					orderItems+=domEle.id+",";
					itemPrices+=$(domEle).attr("data-price")+",";
					itemSpaceName+=$(domEle).attr("data-spacename")+",";
				});
				var ownerAccountId = $("#ownerAccountId").val();
				var telephone = $("#telephone").val();
				var orderTime = $("#orderTime").val();
				var orderTotal = $("#totalPrice").html();
				var jsonData = {orderTotal:orderTotal,ownerAccountId:ownerAccountId,telephone:telephone,orderTime:orderTime,orderItems:orderItems,itemPrices:itemPrices,itemSpaceName:itemSpaceName};
			    $.ajax({
			        url: "/api/orderBuy/buy.do",
			        data: jsonData,
			        type: 'POST',
			        dataType: 'json',
			        success: function (data) {
			        	 var dObj = eval('(' + data + ')');
			        	 console.log(dObj.success);
			        	  if(dObj.success){
			        	  	$("#resultInfo").css("display","block");
			        	  	alert("保存成功");
			        	  }else{
			        	  	alert("用户未登录,请重新处理"+dObj.message);
			        	  }
					      console.log(data);
			        }
			    });				
				console.log(orderItems);
			});
		});
		
		function loadDateList(){
			var ownerAccountId = $("#ownerAccountId").val();
			var postUrl = "/space/dateList/"+ownerAccountId+".do";
		    $.ajax({
		        url: postUrl,
		        //data: jsonData,
		        type: 'POST',
		        dataType: 'json',
		        success: function (data) {
		          var dateListObj = $("#dateList");
		          dateListObj.empty();
				  $.each(data, function(index, item) {
				  		var objHtml = "";
					  	if(index==0){
					  		objHtml += "<td style='width:40px;height:40px;border:1px double gray;background:#6bb156;' data-value="+item['date']+" onclick=chooseDay(this);>";
					  	}else{
					  		objHtml += "<td style='width:40px;height:40px;border:1px double gray;' data-value="+item['date']+" onclick=chooseDay(this);>";
					  	}
		  				objHtml += "<table><tr>";
						objHtml += "<td>"+item['day']+"</td>";
						objHtml += "<td>"+item['week']+"</td>";
						objHtml += "</tr></table></td>";
				     	dateListObj.append(objHtml);
				  });
		        }
		    });
		};
		
		function loadHourList(){
			var ownerAccountId = $("#ownerAccountId").val();
			var orderTime = $("#orderTime").val();
			var postUrl = "/space/hourList/"+ownerAccountId+".do";
			var jsonData = {ownerAccountId:ownerAccountId,orderTime:orderTime} 
		    $.ajax({
		        url: postUrl,
		        data: jsonData,
		        type: 'POST',
		        dataType: 'json',
		        success: function (data) {
	          		var hourListObj = $("#hourList");
	          		hourListObj.empty();
			  		$.each(data, function(index, item) {
			  			var objHtml = "";
			  			console.log("data"+data); 
					  	if(index==0){
					  		objHtml += "<td style='text-align:left;'></td>";
					  	}
						objHtml += "<td style='text-align:left;'>"+item+"</td>";
			     		hourListObj.append(objHtml);
			  		});
		        }
		    });
		};
		
		function loadSpaceList(){
			var ownerAccountId = $("#ownerAccountId").val();
			var orderTime = $("#orderTime").val();
			var postUrl = "/space/spaceList/"+ownerAccountId+".do";
			var jsonData = {ownerAccountId:ownerAccountId,orderTime:orderTime} 
		    $.ajax({
		        url: postUrl,
		        data: jsonData,
		        type: 'POST',
		        dataType: 'json',
		        success: function (data) {
		        	console.log(data.length);
		        	var spaceListObj = $("#spaceList");
		        	spaceListObj.empty();
				  	$.each(data, function(key, value) {
				  	var objHtml = "";
						objHtml += "<tr style='text-align:center;'>";
						objHtml += "<td style='width:40px;height:40px;border:1px double gray;'>";
						objHtml += ""+key+"";
						objHtml += "</td>";
				  		$.each(value, function(index, item) {
							objHtml += "<td style='width:40px;height:40px;border:1px double gray;' data-price="+item['price']+"  data-space="+item['spaceId']+" data-spacename="+key+" data-config="+item['isUser']+" data-space="+key+" data-start="+item['startHour']+" data-end="+item['endHour']+"";
							if(item['isUser']==1){
								objHtml += " class='showClass' onclick='change(this)'";
							}else{
								objHtml += " class='hiddenClass'";
							}
							objHtml += ">";
							objHtml += "<span>"+item['price']+"元</span>";<!--其它值雷同-->
							objHtml += "</td>";				  			
					  		console.log(key+"--"+item);	
				  		});
				  		objHtml += "</tr>";
				  		spaceListObj.append(objHtml);
			  		});
		        }
		    });
		};

		function chooseDay(thisObj){
			var tdObj = $(thisObj).parent().children();
			$.each(tdObj, function(i,val){
				val.style.background = "";
			});
			thisObj.style.background = "#0ca500";
			$("#orderTime").val($(thisObj).attr("data-value"));

			$("#choose").empty();

			loadHourList();

			loadSpaceList();
		};

		function change(_this){
			var obj = _this.style.background;
			console.log(_this);
			var objObj = $(_this);
			console.log(objObj);
			if(obj=="" || obj==null){
				var showInfo = "<div class='imgFocus' id="+objObj.attr('data-space')+"_"+objObj.attr('data-start')+" data-price="+objObj.attr('data-price')+" data-spacename="+objObj.attr('data-spacename')+">"+objObj.attr('data-start')+"-"+objObj.attr('data-end')+" "+objObj.attr('data-spacename')+"</div>";
				$("#choose").append(showInfo);
				_this.style.background = "#0ca500";
				objObj.addClass("checkSeat");//.background = "#0ca500";
			}else{
				$("#"+objObj.attr('data-space')+"_"+objObj.attr('data-start')).remove();
				_this.style.background = "";
			}
			var itemPrices=0;
			$(".imgFocus").each(function(index,domEle){
				itemPrices+= parseInt($(domEle).attr("data-price"));
			});
			$("#totalPrice").html(itemPrices);			
		};
	</script>

</div>
</body>
</html><!--Valid: Wed Mar 25 16:51:30 CST 2015 -->