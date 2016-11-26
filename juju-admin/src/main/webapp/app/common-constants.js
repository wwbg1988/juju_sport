Ext.namespace('App.store.common');

App.store.common.jobStore = Ext.create('Ext.data.Store', {
        fields : ['key', 'value'],
       	pageSize : 20,
        proxy : {
            type : 'ajax',
            url : '../../common/constants/jobCategory.do',
            reader : {
                type : 'json',
                root : 'results',
                successProperty : 'success',
                totalProperty : 'total'
            },
            timeout : 180000
        }
    });

Ext.define("App.store.common.UserStatStore", {
    extend: 'Ext.data.Store',
    fields : ['key', 'value'],
    pageSize : 20,
    proxy : {
       type : 'ajax',
       url : '../../common/constants/userStat.do',
       reader : {
           type : 'json',
           root : 'results',
           successProperty : 'success',
           totalProperty : 'total'
       },
       timeout : 180000
    }
});

Ext.define("App.store.common.SportTypeStore", {
    extend: 'Ext.data.Store',
    fields : ['key', 'value'],
    pageSize : 20,
    proxy : {
       type : 'ajax',
       url : '../../common/constants/sportType.do',
       reader : {
           type : 'json',
           root : 'results',
           successProperty : 'success',
           totalProperty : 'total'
       },
       timeout : 180000
    }
});

Ext.define("App.store.common.InfoTypeStore", {
    extend: 'Ext.data.Store',
    fields : ['key', 'value'],
    pageSize : 20,
    proxy : {
       type : 'ajax',
       url : '../../common/constants/infoType.do',
       reader : {
           type : 'json',
           root : 'results',
           successProperty : 'success',
           totalProperty : 'total'
       },
       timeout : 180000
    }
});

Ext.define("App.store.common.WarTypeStore", {
    extend: 'Ext.data.Store',
    fields : ['key', 'value'],
    pageSize : 20,
    proxy : {
       type : 'ajax',
       url : '../../common/constants/warType.do',
       reader : {
           type : 'json',
           root : 'results',
           successProperty : 'success',
           totalProperty : 'total'
       },
       timeout : 180000
    }
});

Ext.define("App.store.common.TeamPositionStore", {
    extend: 'Ext.data.Store',
    fields : ['key', 'value'],
    pageSize : 20,
    proxy : {
       type : 'ajax',
       url : '../../common/constants/teamPosition.do',
       reader : {
           type : 'json',
           root : 'results',
           successProperty : 'success',
           totalProperty : 'total'
       },
       timeout : 180000
    }
});