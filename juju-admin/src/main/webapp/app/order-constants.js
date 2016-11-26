Ext.namespace('App.store.order');

/*App.store.order.orderTypeStore = Ext.create('Ext.data.Store', {
        fields : ['key', 'value'],
       	pageSize : 20,
        proxy : {
            type : 'ajax',
            url : '../../order/constants/orderType.do',
            reader : {
                type : 'json',
                root : 'results',
                successProperty : 'success',
                totalProperty : 'total'
            },
            timeout : 180000
        }
    });*/
    
/*App.store.order.orderPaymentStatusStore = Ext.create('Ext.data.Store', {
        fields : ['key', 'value'],
       	pageSize : 20,
        proxy : {
            type : 'ajax',
            url : '../../order/constants/orderInternalType.do',
            reader : {
                type : 'json',
                root : 'results',
                successProperty : 'success',
                totalProperty : 'total'
            },
            timeout : 180000
        }
    });*/

App.store.order.orderPaymentStatusStore = Ext.create('Ext.data.Store', {
        fields : ['key', 'value'],
       	pageSize : 20,
        proxy : {
            type : 'ajax',
            url : '../../order/constants/orderPaymentStatus.do',
            reader : {
                type : 'json',
                root : 'results',
                successProperty : 'success',
                totalProperty : 'total'
            },
            timeout : 180000
        }
    });    