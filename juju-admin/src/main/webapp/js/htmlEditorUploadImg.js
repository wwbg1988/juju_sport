Ext.ns('zc');
/**
* 获取项目根路径
* */
zc.bp = function () {
    var curWwwPath = window.document.location.href;
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    var localhostPath = curWwwPath.substring(0, pos);
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    var imgPath=localhostPath + projectName+"/images";

	    return (localhostPath + projectName);
};
/**
* 获取小星星
* */
zc.getStar = function () {
    return '<span style="color:#FF0000;">*</span>';
};
/**
* @Description  Html编辑器 插入图片控件
*/
Ext.define('Ext.zc.form.HtmlEditorImage', {
    extend: 'Ext.util.Observable',
    alias: 'widget.zc_form_HtmlEditorImage',
    langTitle: '插入图片',
    langIconCls: 'add.png',
    imageUrl : 'add.png',
    icon :'x-edit-sourceedit',
    init: function (view) {
        var scope = this;
  
        view.on('render', function () {
        	
            scope.onRender(view);
        });
    },
 
    /**
    * 添加"插入图片"按钮
    * */
    onRender: function (view) {

        var scope = this;
        view.getToolbar().add({
            iconCls: scope.icon,
            tooltip: {
                title: scope.langTitle,
                width: 60
            },
            //title:"345345345",
            handler: function () {
            	
                scope.showImgWindow(view);
            }
        });
   },
 
    /**
    * 显示"插入图片"窗体
    * */
    showImgWindow: function (view) {
        var scope = this;
        Ext.create('Ext.window.Window', {
            width: 420,
            height: 305,
            title: scope.langTitle,
            layout: 'fit',
            autoShow: true,
            modal: true,
            resizable: false,
            maximizable: false,
            constrain: true,
            plain: true,
            enableTabScroll: true,
            //bodyPadding: '1 1 1 1',
            border: false,
            items: [{
                xtype: 'tabpanel',
                enableTabScroll: true,
                bodyPadding: '1 1 1 1',
                items: [{
                    title: '上传本地图片',
                    items: [{
                        xtype: 'form',
                        layout: 'column',
                        autoScroll: true,
                        border: false,
                        defaults: {
                            columnWidth: 1,
                            labelWidth: 70,
                            labelAlign: 'right',
                            padding: '5 5 5 5',
                            allowBlank: false
                        },
                        items: [{
                            xtype: 'filefield',
                            fieldLabel: '选择文件',
                            beforeLabelTextTpl: zc.getStar(),
                            buttonText: '请选择...',
                            name: 'htmleditorImage',
                            emptyText: '请选择图片',
                            blankText: '图片不能为空',
                            listeners: {
                                change: function (view, value, eOpts) {
                                    scope.uploadImgCheck(view, value);
                                }
                            }
                        },  {
                            xtype: 'textfield',
                            fieldLabel: '图片说明',
                            name: 'content',
                            beforeLabelTextTpl: zc.getStar(),
                            allowBlank: true,
                            maxLength: 100,
                            emptyText: '简短的图片说明'
                        },{
                            xtype: 'fieldcontainer',
                           // beforeLabelTextTpl: zc.getStar(),
                            fieldLabel: '图片大小',
                            layout: 'hbox',
                            defaultType: 'numberfield',
                            defaults: {
                                flex: 1,
                                labelWidth: 20,
                                labelAlign: 'right',
                                allowBlank: true
                            },
                            items: [{
                                fieldLabel: '宽',
                                name: 'width',
                                minValue: 1
                            }, {
                                fieldLabel: '高',
                                name: 'height',
                                minValue: 1
                            }]
                        }, {
                            columnWidth: 1,
                            xtype: 'fieldset',
                            title: '上传须知',
                            layout: {
                                type: 'table',
                                columns: 1
                            },
                            collapsible: false, //是否可折叠
                            defaultType: 'label', //默认的Form表单组件
                            items: [{
                                html: '1.上传图片不超过100KB'
                            }, {
                                html: '2.为了保证图片能正常使用，我们支持以下格式的图片上传'
                            }, {
                                html: '   jpg,jpeg,png,gif'
                            }]
                        }],
                        buttons: [{
                            text: '保存',
                            action: 'btn_save',
                            icon: '',
                            handler: function (btn) {
                                scope.saveUploadImg(btn, view);
                            }
                        }, {
                            text: '取消',
                            icon: '',
                            handler: function (btn) {
                                btn.up('window').close();
                            }
                        }]
                    }]
                }/*, {
                    title: '链接网络图片',
                    items: [{
                        xtype: 'form',
                        layout: 'column',
                        autoScroll: true,
                        border: false,
                        defaults: {
                            columnWidth: 1,
                            labelWidth: 70,
                            labelAlign: 'right',
                            padding: '5 5 5 5',
                            allowBlank: false
                        },
                        items: [{
                            xtype: 'textfield',
                            fieldLabel: '图片地址',
                           //beforeLabelTextTpl: zc.getStar(),
                            name: 'htmleditorImage',
                            emptyText: '请填入支持外链的长期有效的图片URL',
                            blankText: '图片地址不能为空',
                            vtype: 'remoteUrl'
                        }, {
                            xtype: 'fieldcontainer',
                            fieldLabel: '图片大小',
                            layout: 'hbox',
                            defaultType: 'numberfield',
                            defaults: {
                                flex: 1,
                                labelWidth: 20,
                                labelAlign: 'right',
                                allowBlank: true
                            },
                            items: [{
                                fieldLabel: '宽',
                                name: 'width',
                                minValue: 1
                            }, {
                                fieldLabel: '高',
                                name: 'height',
                                minValue: 1
                            }]
                        }, {
                            xtype: 'textfield',
                            fieldLabel: '图片说明',
                            name: 'content',
                            allowBlank: true,
                            maxLength: 100,
                            emptyText: '简短的图片说明'
                        }, {
                            columnWidth: 1,
                            xtype: 'fieldset',
                            title: '上传须知',
                            layout: {
                                type: 'table',
                                columns: 1
                            },
                            collapsible: false, //是否可折叠
                            defaultType: 'label', //默认的Form表单组件
                            items: [{
                                html: '1.上传图片不超过100KB'
                            }, {
                                html: '2.为了保证图片能正常使用，我们支持以下格式的图片上传'
                            }, {
                                html: '   jpg,jpeg,png,gif'
                            }]
                        }],
                        buttons: [{
                            text: '保存',
                            action: 'btn_save',
                            icon: '/127.0.0.1/admin/manager/images/add.png',
                            handler: function (btn) {
                                scope.saveRemoteImg(btn, view);
                            }
                        }, {
                            text: '取消',
                            icon: '',
                            handler: function (btn) {
                                btn.up('window').close();
                            }
                        }]
                    }]
                }*/]
            }]
        });
    },
 
    /**
    * 上传图片验证
    **/
    uploadImgCheck: function (fileObj, fileName) {
        var scope = this;
        //图片类型验证
        if (!(scope.getImgTypeCheck(scope.getImgHZ(fileName)))) {
            Ext.MessageBox.alert('温馨提示', '上传图片类型有误');
            fileObj.reset(); //清空上传内容
            return;
        }
    },
 
    /**
    * 获取图片后缀(小写)
    * */
    getImgHZ: function (imgName) {
        //后缀
        var hz = '';
        //图片名称中最后一个.的位置
        var index = imgName.lastIndexOf('.');
        if (index != -1) {
            //后缀转成小写
            hz = imgName.substr(index + 1).toLowerCase();
        }
        return hz;
    },
 
    /**
    * 图片类型验证
    * */
    getImgTypeCheck: function (hz) {
        var typestr = 'jpg,jpeg,png,gif';
        var types = typestr.split(','); //图片类型
        for (var i = 0; i < types.length; i++) {
            if (hz == types[i]) {
                return true;
            }
        }
        return false;
    },
 
    /**
    * 上传图片
    * */
    saveUploadImg: function (btn, view) {
        var scope = this;
        var url="/admin/common/images/htmleditorUpload.do";
        var windowObj = btn.up('window'); //获取Window对象
        var formObj = btn.up('form').getForm(); //获取Form对象
        if (formObj.isValid()) { //验证Form表单
            formObj.doAction('submit', {
              //url: zc.bp() + '/',
                url: url,
                method: 'POST',
                submitEmptyText: false,
                waitMsg: '正在上传图片,请稍候...',
                timeout: 60000, // 60s
                success: function (response, options) {
                	
                	var result = options.result;
                	if (!result.success) {
                        Ext.MessageBox.alert('温馨提示', "上传失败");
                        return;
                    }
                    scope.insertImg(view, result.data);
                    windowObj.close(); //关闭窗体
                },
                failure: function (response, options) {
                	Ext.MessageBox.alert('温馨提示', "上传失败");
                }
            });
        }
    },
 
    /**
    * 保存远程的图片
    * */
    saveRemoteImg: function (btn, view) {
        var scope = this;
        var windowObj = btn.up('window'); //获取Window对象
        var formObj = btn.up('form').getForm(); //获取Form对象
        if (formObj.isValid()) {//验证Form表单
            var values = formObj.getValues(); //获取Form表单的值
            scope.insertImg(view, values);
            windowObj.close(); //关闭窗体
        }
    },
 
    /**
    * 插入图片
    * */
    insertImg: function (view, data) {
  
    	var url     ="/"+data.url;
    
        var content = data.title;
        var width   = data.width;
        var height  = data.height;
        var str = '<img src="' + url + '" border="0" ';
        if (content != undefined && content != null && content != '') {
        	str += ' title="' + content + '" ';
          }
     
        	if (width != undefined && width != null && width != 0) {
                str += ' width="' + width + '" ';
          }
   
        	if (height != undefined && height != null && height != 0) {
                str += ' height="' + height + '" ';
          }
           str+= " />";
         
        view.insertAtCursor(str);
    }
});