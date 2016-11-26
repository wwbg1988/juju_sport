<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单管理-订单展示</title>
<%@ include file="/manager/inc/headroot.jsp" %>
<link rel="stylesheet" type="text/css" href="${contextPath}/manager/ext4/resources/css/ext-all.css" />
<script type="text/javascript" src="${contextPath}/manager/ext4/ext-all.gzjs"></script>
</head>
<script type="text/javascript">
Ext.onReady(function(){
	Ext.QuickTips.init();
    // setup the state provider, all state information will be saved to a cookie
    Ext.state.Manager.setProvider(Ext.create('Ext.state.CookieProvider'));
    
    var cm = new Ext.grid.ColumnModel([,  
  new Ext.grid.RowNumberer(), //自动添加行号  
  
     {  
     header: "房间编号",  
     dataIndex: "RoomNumber",  
     //可以进行排序   
     sortable: true  
  
 }, {  
     header: "户型结构",  
     dataIndex: "huxingjiegou",  
     //可以进行排序  
     isHidden: true,  
     sortable: true  
  
 }, {  
     header: "面积(M²)",  
     dataIndex: "area",  
     //可以进行排序  
     sortable: true  
  
 }, {  
     header: "单价(元/M²)",  
     dataIndex: "singlePrice",  
     //可以进行排序  
     sortable: true  
     //           editor: new Ext.grid.GridEditor(new Ext.form.NumberField({  
     //            allowBlank: false  
     //        }))  
  
 }, {  
     header: "总价(元)",  
     dataIndex: "totalPrice",  
     //可以进行排序  
     sortable: true  
  
 }, {  
     header: "面积(M²)",  
     dataIndex: "mianjiCC",  
     //可以进行排序  
     sortable: true  
  
 }, {  
     header: "单价(元/M²)",  
     dataIndex: "priceCCS",  
     //可以进行排序  
     sortable: true  
     //           editor: new Ext.grid.GridEditor(new Ext.form.NumberField({  
     //            allowBlank: false  
     //        }))  
  
 }, {  
     header: "总价(元)",  
     dataIndex: "totalPriceCCS",  
     //可以进行排序  
     sortable: true  
  
 }, {  
     header: "面积(M²)",  
     dataIndex: "mianjiCK",  
     //可以进行排序  
     sortable: true  
  
 }, {  
     //         header: "单价(元/M²)",  
     header: "总价(元/M²)",  
     dataIndex: "priceCK",  
     //可以进行排序  
     sortable: true  
     //           editor: new Ext.grid.GridEditor(new Ext.form.NumberField({  
     //            allowBlank: false  
     //        }))  
  
 },  
  
  
  {  
      header: "",  
      dataIndex: "totalPriceALL",  
      //可以进行排序  
      sortable: true  
  
  },  
  
  header: "户型图",  
  tooltip: "户型图",  
  
  width: 120,  
  locked: true,  
  menuDisabled: true,  
  sortable: false,  
  dataIndex: "huxingPic",  
  renderer: function (data, metadata, record, rowIndex, columnIndex, store) {  
      var picture = store.getAt(rowIndex).get('huxingPic');  
  
      return '<a href="' + picture + '">' + '<img src="' + picture + '"width=60 hight=50> </a>';  
  
  } 
  
 {  
     header: "订购",  
  
     renderer: function (value, meta, record) {  
  
         var formatStr = "<button  onclick='javscript:return false;' class='order_bit'>订购</button>";   
         var resultStr = String.format(formatStr);  
         return "<div class='controlBtn'>" + resultStr + "</div>";  
     } .createDelegate(this),  
     css: "text-align:center;",  
     //  width: 30,     
     sortable: false  
 }  
  
    ]);
    
    
    
    Ext.create('Ext.container.Viewport', {
		layout : 'fit',
		renderTo: 'order-list',
		items : [grid]		 
	});
	
	itemsTypeStore.load();
	propertyTypeStore.load();
});
</script>
<body>
<h1>This is the Order-list jsp</h1>
<div id="order-list"></div>
</body>
</html>