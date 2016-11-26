Ext.namespace('App.area');
    
Ext.define("App.area.AreaStore", {
     extend: 'Ext.data.Store',
     fields : ['id', 'parentId', 'name', 'level'],
     pageSize : 20,
     proxy : {
        type : 'ajax',
        url : '../../area/find.do',
        reader : {
            type : 'json',
            root : 'results',
            successProperty : 'success',
            totalProperty : 'total'
        },
        timeout : 180000
     }
});