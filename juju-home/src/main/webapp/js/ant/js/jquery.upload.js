/// <reference path="jquery-1.8.3.js" />
/// <reference path="ajaxForm/jquery.form.js" />

/*!
 * jQuery upload
 * 用于上传单个文件，上传成功后,返回文件路径。
 * 本插件依赖jQuery，jquery.form 请在使用时引入依赖的文件
 *
 * Date: 2014/4/23
 */
/*
使用:
html:
<div style="width: 100%; float: left;">
    <input type="hidden" id="hfThumbnail" value="/uploads/2014/04/23/635338686611432716.jpg" />
    <div class="imgdiv"></div>
    <span class="img_span">
        <input type="file" name="file" />
    </span>
     
    <input type="button" value="上传" class="upload" />
</div>
<div style="width: 100%; float: left;">
    <input type="hidden" id="hfThumbnail" value="/uploads/2014/04/23/635338686611432716.jpg" />
    <div class="imgdiv"></div>
    <span class="img_span">
        <input type="file" name="file" />
    </span>
     
    <input type="button" value="上传" class="upload" />
</div>

js:

$(document).ready(function () {
        
    //$(".upload").upload({
    //    uploadData: { id: "12233" },
    //    successFn: function (response, statusText, xhr, $this) {
    //        alert(response.Data.RelativePath)
    //    },
    //    deleteData: { id: function () { return "asdfasdf" } }
    //});
  
    $(".upload").upload({
        uploadData: { id: "12233" },
        successFn: "success",           //可以是字符串
        deleteData: { id: function () { return "asdfasdf" } }
    });
});

//上传成功后执行该函数
function success(response, statusText, xhr, $this) {
    //比如插入编辑器
    alert(response.Data.RelativePath + $this.attr("value"))
}
*/

(function ($) {
    $.extend($.fn, {
        upload: function (settings) {

            var options = $.extend({
                fileType: "doc|docx",                       //允许的文件格式
               uploadUrl: "/juju-home/api/temp/upload.do",      //上传URL地址
                //uploadUrl: "/api/temp/upload.do",      //上传URL地址
                //deleteUrl: "/ajax/handler.ashx?action=deletefile",      //删除URL地址
                width: "100",                                              //图片显示的宽度
                height: 100,                                            //图片显示的高度
                imgSelector: ".imgdiv",                                  //图片选择器
                uploadData: {},                                         //上传时需要附加的参数
                deleteData: {},                                         //删除时需要附加的参数
                deleteFn: function ($parent, showMessage) {             //删除图片的方法(默认方法使用POST提交)
                    methods.deleteImage($parent, showMessage);
                },
                beforeSubmitFn: "beforeUpload",                         //上传前执行的方法 原型 beforeSubmit(arr, $form, options);
                successFn: "uploadSuccess",                             //上传成功后执行的方法 uploadSuccess(response, statusText, xhr, $this)
                errorFn: "uploadError"                                  //上传失败后执行的方法
            }, settings);
            //console.log(settings.uploadData.id);
            //上传准备函数
            var methods = {
                //验证文件格式
                checkFile: function (filename) {
                    var pos = filename.lastIndexOf(".");
                    var str = filename.substring(pos, filename.length);
                    var str1 = str.toLowerCase();
                    if (typeof options.fileType !== 'string') { options.fileType = "doc|docx"; }
                    var re = new RegExp("\.(" + options.fileType + ")$");
                    return re.test(str1);
                },
                //创建表单
                createForm: function () {
                    var $form = document.createElement("form");
                    $form.action = options.uploadUrl;
                    $form.method = "post";
                    $form.enctype = "multipart/form-data";
                    $form.style.display = "none";
                    //将表单加当document上，
                    document.body.appendChild($form);  //创建表单后一定要加上这句否则得到的form不能上传。document后要加上body,否则火狐下不行。
                    return $($form);
                },
                //创建图片
                createImage: function () {
                    //不能用 new Image() 来创建图片，否则ie下不能改变img 的宽高
                    var img = $(document.createElement("img"));
                    //img.attr({ "title": "！" });
                    if (options.width !== "") {
                        img.attr({ "width": options.width });
                    }
                    if (options.height !== "") {
                        img.attr({ "height": options.height });
                    }
                    return img;
                },
                showImage: function (filePath, objId) {
                    var $img = methods.createImage();
                    //$parent.find(options.imgSelector).find("img").remove();
                    //$("#"+objId).remove();
                    //要先append再给img赋值，否则在ie下不能缩小宽度。
                    //$img.appendTo($parent.find(options.imgSelector));
                    $("#"+objId).attr("src", filePath);
                    //this.bindDelete($parent);
                },
                bindDelete: function ($parent) {
                    $parent.find(options.imgSelector).find("img").bind("dblclick", function () {
                        //options.deleteFn($parent, true);
                    });
                },
                deleteImage: function ($parent, showMessage) {
                    var $fileInput = $parent.find("input:hidden");
                    if ($fileInput.val() !== "") {
                        var data = $.extend(options.deleteData, { filePath: $fileInput.val(), t: Math.random() });
                        $.post(options.deleteUrl, data, function (response) {
                            if (showMessage) { 
                            	alert(response.MessageContent);
                            	fileBox.html('');
                            }
                            if (response.MessageType == 1) {
                                $fileInput.val("");
                                $parent.find(options.imgSelector).find("img").remove();
                            }
                        }, "JSON");
                    }
                },
                onload: function ($parent) {
                    var hiddenInput = $parent.find("input:hidden");
                    if (typeof hiddenInput !== "undefined" && hiddenInput.val() !== "") {
                        var img = methods.createImage();
                        if ($parent.find(options.imgSelector).find("img").length > 0) { $parent.find(options.imgSelector).find("img").remove(); }
                        //要先append再给img赋值，否则在ie下不能缩小宽度。 
                        img.appendTo($parent.find(options.imgSelector));
                        img.attr("src", hiddenInput.val());
                        //methods.bindDelete($parent);
                    }
                }
            };
            //上传主函数
            this.each(function () {
                var $this = $(this);
                methods.onload($this.parent());
                $this.bind("click", function () {
                    var $fileInput = $(this).parent().find("input:file");
                    var fileBox = $fileInput.parent();

                    if ($fileInput.val() === "") {
                        alert("请选择要上传的文件！");
                        return false;
                    }
                    //验证图片
                    if (!methods.checkFile($fileInput.val())) {
                        alert('文件格式不正确，只能上传格式为： .doc或 .docx结尾的文件。');
                        return false;
                    }
                    //若隐藏域中有图片，先删除图片
                    if ($fileInput.val() !== "") {
                        //options.deleteFn($this.parent(), false);
                        //methods.deleteImage($this.parent(), false);
                    }

                    //创建表单
                    var $form = methods.createForm();

                    //把上传控件附加到表单
                    $fileInput.appendTo($form);
                    //fileBox.html("<img src=\"/js/ant/images/loading.gif\" />   正在上传...  ");
                    fileBox.html("<img src=\"/juju-home/js/ant/images/loading.gif\" />   ");
                    
                    $this.attr("disabled", true);

                    //构建ajaxSubmit参数
                    var data = {};
                    data.data = options.uploadData;
                    data.type = "POST";
                    data.dataType = "text";
                    //上传前
                    data.beforeSubmit = function (arr, $form, options) {
                    	//console.log("-----1-----"+arr);
                        var beforeSubmitFn;
                        try { beforeSubmitFn = eval(options.beforeSubmitFn) } catch (err) { };
                        if (beforeSubmitFn) {
                            var $result = beforeSubmitFn(arr, $form, options);
                            if (typeof ($result) == "boolean")
                                return $result;
                        }
                    };
                    //上传失败
                    data.error = function (response, statusText, xhr, $form) {
                    	//console.log("-----2-----"+response);
                        var errorFn;
                        try { errorFn = eval(options.errorFn) } catch (err) { };
                        if (errorFn) {
                            errorFn(response.responseText, statusText, xhr, $this);
                        }
                    };
                    //上传成功
                    data.success = function (response, statusText, xhr, $form) {
                    	var myObject = eval('(' + response + ')');
                    	//console.log("-----3-----"+myObject.status);
                        //response = eval("(" + response + ")");
                        if (myObject.status == 200) {
                        	//console.log("--"+myObject.message);
                           // methods.showImage(myObject.data.url, "userImageS");
                          // layer.alert('上传成功!',1,1);
                            fileBox.html("   上传成功!  ");
                            alert("上传成功!");
                            location.reload();
                            
                            $("#hfThumbnail").val(myObject.data.url);
                            //$this.parent().find("input:hidden").val(myObject.message);

                            var successFn;
                            try { successFn = eval(options.successFn) } catch (err) { };
                            //console.log("successFn"+successFn);
                            if (successFn) {
                                $.ajaxSetup({ cache: false });//这个不能少，否则ie下会提示下载
                                successFn(response, statusText, xhr, $this);
                            }
                        } else {
                        	fileBox.html("");
                        	alert(myObject.message);
                        	location.reload();
                        	//layer.msg(myObject.message, 1, 1);//alert("系统错误！");
                            //alert(myObject.MessageContent);
                        }
                        $this.attr("disabled", false);
                        //fileBox.html("<input type=\"file\" name=\"file\"/>");
                        //<a href="javascript:;" class="file">选择文件<input type="file" name="file" id=""></a>
                       // var objHtml = "选择文件<input type='file' name='file' id=''>";
                        //fileBox.html(objHtml);
                        $form.remove();
                    };

                    try {
                        //开始ajax提交表单
                        $form.ajaxSubmit(data);
                    } catch (e) {
                        alert(e.message);
                    }
                });
            });
        }
    });
})(jQuery)

