Ext.QuickTips.init();
Ext.chart.Chart.CHART_URL="plugins/extjs/ext-3.2/resources/charts.swf";
function menuclick(node){
   var main=Ext.getCmp("main");
   if(node.attributes.clz){
    if(node.attributes.clz=="ChangePasswordWindow"){
        var win=new ChangePasswordWindow();
        win.show();
        return;
    }
   //var panel=new ClientPanel();
   var panel=eval("new "+node.attributes.clz);
   if(node.attributes.addObject){
    panel.create();
   }
   else {
   main.add(panel);
   main.setActiveTab(panel);
   }
   }
}
function logout(){
    
}
Ext.onReady(function() {
	new Ext.Viewport({
				layout : "border",
				items : [{
							region : "north",
							height : 50,
                            border:false,
							//html : "Logo部分",
                            el:"header"
						}, {
                            id:"main",
							xtype : "tabpanel",
							region : "center"
						}, {
							region : "west",
							width : 150,
                            border:false,
							title : "应用程序功能项",
                            layout:"accordion",
                            split:true,
                            defaults : {
								xtype : "treepanel",
								rootVisible : false,
                                listeners:{
                                click:menuclick
                                }
							},
							items : [{
                               
										title : "采购管理",
										root : {
											nodeType : 'async',
											loader : new Ext.tree.TreeLoader(),
											expanded : true,
											children : [{
														text : "新建采购单",
                                                        clz:"PurchaseBillPanel",
                                                        addObject:true,
														leaf : true
													}, {
														text : "采购单管理",
                                                        clz:"PurchaseBillPanel",
														leaf : true
													}, {
														text : "库存查询",
                                                        clz:"ProductStockPanel",
														leaf : true
													}]
										}
									}, {
                                        title : "销售管理",
                                        root : {
                                            nodeType : 'async',
                                            loader : new Ext.tree.TreeLoader(),
                                            expanded : true,
                                            children : [{
                                                        text : "新建销售订单",
                                                        clz:"OrderInfoPanel",
                                                         addObject:true,
                                                        leaf : true
                                                    }, {
                                                        text : "销售订单管理",
                                                        clz:"OrderInfoPanel",
                                                        leaf : true
                                                    }, {
                                                        text : "库存查询",
                                                        clz:"ProductStockPanel",
                                                        leaf : true
                                                    }]
                                        }
                                    }, {
                                        title : "库存管理",
                                        root : {
                                            nodeType : 'async',
                                            loader : new Ext.tree.TreeLoader(),
                                            expanded : true,
                                            children : [{
                                                        text : "即时库存",
                                                        clz:"ProductStockPanel",
                                                        leaf : true
                                                    },{
                                                        text : "入库管理",
                                                        children:[{
                                                        text : "期初入库",
                                                        clz:"StockIncomeInitPanel",
                                                        leaf : true
                                                    },{
                                                        text : "采购入库",
                                                        clz:"StockIncomePruchasePanel",
                                                        leaf : true
                                                    },{
                                                        text : "其它入库",
                                                        clz:"StockIncomeOhterPanel",
                                                        leaf : true
                                                    }]
                                                    }, {
                                                        text : "出库管理",
                                                        children:[{
                                                        text : "销售出库",
                                                        clz:"StockOutcomeOrdersPanel",
                                                        leaf : true
                                                    },{
                                                        text : "生产领料",
                                                        clz:"StockOutcomeProducePanel",
                                                        leaf : true
                                                    },{
                                                        text : "其它出库",
                                                        clz:"StockOutcomeOtherPanel",
                                                        leaf : true
                                                    }]
                                                    }]
                                        }
                                    }, {
                                        title : "报表中心",
                                        root : {
                                            nodeType : 'async',
                                            loader : new Ext.tree.TreeLoader(),
                                            expanded : true,
                                            children : [{
                                                        text : "采购报表",
                                                        clz:"PurchaseReportPanel",
                                                        leaf : true
                                                    }, {
                                                        text : "销售报表",
                                                        clz:"OrderInfoReportPanel",
                                                        leaf : true
                                                    }, {
                                                        text : "进销存汇总表",
                                                        clz:"StockDetailTotalReportPanel",
                                                        leaf : true
                                                    }, {
                                                        text : "进销存明细帐",
                                                        clz:"StockDetailAccountReportPanel",
                                                        leaf : true
                                                    }]
                                        }
                                    }, {
                                        title : "基础数据维护",
                                        root : {
                                            nodeType : 'async',
                                            loader : new Ext.tree.TreeLoader(),
                                            expanded : true,
                                            children : [{
                                                        text : "货品管理",
                                                        clz:"ProductPanel",
                                                        leaf : true
                                                    }, {
                                                        text : "货品分类设置",
                                                        clz:"ProductDirPanel",
                                                        leaf : true
                                                    }, {
                                                        text : "仓库设置",
                                                        clz:"DepotPanel",
                                                        leaf : true
                                                    }, {
                                                        text : "客户管理",
                                                        clz:"ClientPanel",
                                                        leaf : true
                                                    }, {
                                                        text : "供应商管理",
                                                        clz:"SupplierPanel",
                                                        leaf : true
                                                    }]
                                        }
                                    }, {
                                        title : "系统管理",
                                        root : {
                                            nodeType : 'async',
                                            loader : new Ext.tree.TreeLoader(),
                                            expanded : true,
                                            children : [{
                                                        text : "部门设置",
                                                        clz:"DepartmentManagePanel",
                                                        leaf : true
                                                    }, {
                                                        text : "员工管理",
                                                        clz:"EmployeePanel",
                                                        leaf : true
                                                    }, {
                                                        text : "数据字典",
                                                        clz:"SystemDictionaryManagePanel",
                                                        leaf : true
                                                    }, {
                                                        text : "修改密码",
                                                        clz:"ChangePasswordWindow",
                                                        leaf : true
                                                    }, {
                                                        text : "退出系统",
                                                        clz:"Logout",
                                                        leaf : true
                                                    }]
                                        }
                                    }]
						}]
			});
	});