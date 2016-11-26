<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>新增战队</title>
    <link href="/css/juju/user/add_corps.css" rel="stylesheet" />
    <script src="/js/juju/jquery-2.1.3.min.js"></script>
	<script src="/ant/js/jquery.form.js"></script>
	<script src="/ant/js/jquery.upload.js"></script>
	<script type="text/javascript">
	$(function(){
	    $.ajax({
	        url: '/sportType/findAll.do',
	        type: 'POST',
	        dataType: 'json',
	        success: function (data) {
				$("#sportTypeSel").html('');
				var myObject = data.data;//eval('(' + data + ')');
	            for (var i = 0; i < myObject.length; i++) {
					 $("#sportTypeSel").append("<option value=\'"+myObject[i].id+"\'>"+myObject[i].sportName+"</option>");
	            }
	        }
	    });
    
		$("#saveTeamBtn").click(function(){
			var dataParams =  $("#_formSer").serialize();
		    $.ajax({
		        url: '/user/team/save.do',
		        type: 'POST',
		        data : dataParams, 
		        dataType: 'json',
		        success: function (data) {
		        	parent.layer.msg('修改战队成功', 1, 1);
		        	//console.log(data);
		        }
		    });
		});    
	});
	</script>
	<script type="text/javascript">
	    $(document).ready(function () {
	        $("#upload").upload({
	            uploadData: { id: "12233" },
	            successFn: "success",
	            deleteData: { id: function () { return "asdfasdf" } }
	        });
	    });
	
	    //上传成功后执行该函数
	    function success(response, statusText, xhr, $this) {
	        //比如插入编辑器
	        //alert(response.Data.RelativePath + $this.attr("value"))
	    }
	</script>
</head>
<body>
	<form id="_formSer">
    <table class="tab">
<!--         <tr>
            <td>创建战队</td> 
        </tr> -->
		<input type="hidden" id="id" name="id" value="${teamDto.id}"/>        
        <tr>
            <td>战队名</td>
            <td><input type="text" id="teamName" name="teamName" value="${teamDto.teamName}"/></td>
        </tr>
        <tr>
        	<td>最大加入人数</td>
        	<td><input type="text" id="maxNum" name="maxNum" value="${teamDto.maxNum}"/></td>
        </tr>
        <tr>
            <td>战队logo</td>
            <td>
            	<div style="float:left;">
	            	<input type="hidden" name="thumbnail" id="hfThumbnail" value="${teamDto.thumbnail}" />
				    <span class="img_span">
				        <input id="uploadObjId" type="file" name="file" style="width:180px;"/>
				    </span>           
            		<a id="upload" class="a" herf="#">上传</a>
            	</div>
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
			    <div class="imgdiv" style="padding-top:10px;">
			    	<img id="userImageS" alt="" src="${teamDto.thumbnail}" style="width:80px;height:80px;"/>
			    </div>
            </td>
        </tr>
        <tr>
            <td>体育分类</td>
            <td>
				<select id="sportTypeSel" name="sportId">
					<option value="-1">请选择</option>
				</select>
            </td>
        </tr>
        <tr>
            <td>联系方式</td>
            <td><input type="text" id="contact" name="contact" value="${teamDto.contact}"/></td>
        </tr>
        <tr>
            <td>战队备注</td>
            <td><input type="text" id="warDesc" name="warDesc" value="${teamDto.warDesc}"/></td>
        </tr>
        <tr>
            <td></td>
            <td>
                 <a id="saveTeamBtn" class="a" herf="#">修改战队</a> &nbsp; 
            </td>
        </tr> 
    </table>
    </form>
</body>
</html>
