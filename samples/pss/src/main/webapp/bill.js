
ProductGridList=Ext.extend(EasyJF.Ext.BaseGridList,{
 url:"product.ejf?cmd=list",
 storeMapping:["id","name","sn","salePrice","costPrice","spec","model","color","pic","intro","content","other1","other2","other3","other4","dir","brand","unit"  ],
 search:function(){
       this.store.reload({params:{searchKey:this.search_sn.getValue()}}) 
 },
 initComponent : function(){
    this.search_sn=new Ext.form.TextField({name:"sn"})
    this.tbar=["货品编号",this.search_sn,{text:"查询",handler:this.search,scope:this}];    
    this.cm=new Ext.grid.ColumnModel([
        {header: "名称", sortable:true,width: 300, dataIndex:"name" },
        {header: "编号", sortable:true,width: 300, dataIndex:"sn" },
        {header: "分类", sortable:true,width: 300, dataIndex:"dir",renderer:this.objectRender("name")},
        {header: "品牌", sortable:true,width: 300, dataIndex:"brand",renderer:this.objectRender("title")},
        {header: "单位", sortable:true,width: 300, dataIndex:"unit",renderer:this.objectRender("title")},
        {header: "销售价", sortable:true,width: 300, dataIndex:"salePrice" },
        {header: "成本价", sortable:true,width: 300, dataIndex:"costPrice" },
        {header: "规格", sortable:true,width: 300, dataIndex:"spec" },
        {header: "型号", sortable:true,width: 300, dataIndex:"model" },
        {header: "颜色", sortable:true,width: 300, dataIndex:"color" },
        {header: "简介",hidden:true, sortable:true,width: 300, dataIndex:"intro" },
        {header: "产品详情",hidden:true, sortable:true,width: 300, dataIndex:"content" },
        {header: "其它属性1",hidden:true, sortable:true,width: 300, dataIndex:"other1" },
        {header: "其它属性2",hidden:true, sortable:true,width: 300, dataIndex:"other2" },
        {header: "其它属性3",hidden:true, sortable:true,width: 300, dataIndex:"other3" },
        {header: "其它属性4",hidden:true, sortable:true,width: 300, dataIndex:"other4" }
        ]);
    ProductGridList.superclass.initComponent.call(this);
}  
});

OrderInfoPanel=Ext.extend(EasyJF.Ext.CrudMainDetailPanel,{
    id:"orderInfoPanel",
    title:"销售订单管理",
    baseUrl:"orderInfo.ejf",
    billName:"销售订单",
    typesData:[["普通",0],["其它",1]],
    detailItemKey:"product",
    detailItemName:"货品",
    editStoreMapping:["id","product",{name:"productSn",mapping:"product.sn"},"price","num","amount","remark"],
    createColumnEditor:function(){
        var service=this;
        this.productEditor=new EasyJF.Ext.ObjectSearchField({
            gridListClz:"ProductGridList",
            searchUrl:"product.ejf?cmd=list&pageSize=15",
            choiceValue:function(data){
                service.onSelectObject(data);
            }
        });
    },
    getEditGridColumn : function() {
				return new Ext.grid.ColumnModel([new Ext.grid.RowNumberer({header:"序号",width:40,name:"sequence"}),
                    {header:"Id",dataIndex:"id",width:1,hidden:true,hideable:false},
                    {
							header : "产品编号",
							width : 100,
							sortable : true,
							dataIndex : 'productSn',
                            editor:this.productEditor
						}, {
							header : "名称",
							width : 100,
							sortable : true,
                            dataIndex:"product",
                            renderer:this.objectPropertyRender("product","name"),
                            summaryType: 'count',summaryRenderer: function(v){return "合计("+v+")";}
						}, {
							header : "型号",
							width : 100,
							sortable : true,
                            dataIndex:"model",
                            renderer:this.objectPropertyRender("product","model")
						}, {
							header : "规格",
							width : 80,
							sortable : true,
                            dataIndex:"spec",
                            renderer:this.objectPropertyRender("product","spec")
						}, {
                            header : "单位",
                            width : 80,
                            sortable : true,
                            dataIndex:"unit",
                            renderer:function(v,m,r){
                                if(r.get("product")&&r.get("product").unit)return r.get("product").unit.title;
                            }
                           
                        },{
                            header : "单价",
                            width : 60,
                            sortable : true,
                            dataIndex:"price",
                            editor:new Ext.form.NumberField(),
                            renderer:this.moneyRender
                        }, {
                            header : "数量",
                            width : 60,
                            sortable : true,
                            dataIndex:"num",
                            editor:new Ext.form.NumberField(),
                            summaryType: 'sum',
                            summaryRenderer: EasyJF.Ext.Util.moneyRender
                        }, {
                            header : "金额",
                            width : 60,
                            sortable : true,
                            dataIndex:"amount",
                            renderer:this.moneyRender,
                            summaryType: 'sum',
                            summaryRenderer: EasyJF.Ext.Util.moneyRender
                        }, {
                            header : "备注",
                            width :400,
                            sortable : true,
                            dataIndex:"remark",
                            editor:new Ext.form.TextField()
                        }]);
			},
    create : function() {
		OrderInfoPanel.superclass.create.call(this);
		Ext.Ajax.request({
					url : "baseCount.ejf?cmd=getSn",
					params : {
						entityName : "OrderInfo"
					},
					success : function(response) {
                        var ret=Ext.decode(response.responseText);
                        this.fp.form.findField("sn").setValue(ret);
					},
                    scope:this
				})
	},        
    createForm:function(){
    this.editGrid=this.createEditGrid();
    var formPanel=new Ext.form.FormPanel({
        frame:true,
        labelWidth:60,
        labelAlign:'right',
        layout:"border",
        layout:"border",defaults:{anchor:"-20"},
        items:[
        {region:"north",layout:"form",autoHeight:true,items:[
        {xtype:"hidden",name:"id"},
        EasyJF.Ext.Util.columnPanelBuild(5,{ fieldLabel:'单据编号',name:'sn',readOnly:true},
       EasyJF.Ext.Util.buildCombox('types', '类别', this.typesData, 0, true), 
 {xtype:"datefield", fieldLabel:'业务时间',name:'vdate' ,format:"Y-m-d"  },this.buildRemoteCombox("client","客户","client.ejf?cmd=list",["id","name"],"name","id"),this.buildRemoteCombox("seller","业务员","employee.ejf?cmd=list",["id","trueName"],"trueName")
 ),{ xtype:"textfield",fieldLabel:'备注',name:'remark',anchor:"-20"  }
        ]},
        {xtype:"fieldset",region:"center",layout:"fit",title:"订单详情",items: this.editGrid}
    ]
    });
        return formPanel;
    },
    createWin:function(){
        return this.initWin(Ext.getBody().getViewSize().width-50,Ext.getBody().getViewSize().height-50,"订单信息录入");
    },
    storeMapping:["id","sn","types","vdate","inputTime","remark","amount","auditing","auditTime","status"    ,"client","seller","auditor"   ],
    initComponent : function(){
    this.cm=new Ext.grid.ColumnModel([
{header: "单据编号", sortable:true,width: 300, dataIndex:"sn" },
{header: "单据类别", sortable:true,width: 300, dataIndex:"types",renderer:this.typesRender(this.typesData) },
{header: "业务时间", sortable:true,width: 300, dataIndex:"vdate"  ,renderer:this.dateRender() },
{header: "录入时间", sortable:true,width: 300, dataIndex:"inputTime"  ,renderer:this.dateRender() },
{header: "备注", sortable:true,width: 300, dataIndex:"remark" },
{header: "总金额", sortable:true,width: 300, dataIndex:"amount" },
{header: "下单客户", sortable:true,width: 300, dataIndex:"client",renderer:this.objectRender("name")},
{header: "业务员", sortable:true,width: 300, dataIndex:"seller",renderer:this.objectRender("trueName")},
{header: "审核人", sortable:true,width: 300, dataIndex:"auditor",renderer:this.objectRender("trueName")},
{header: "是否审核", sortable:true,width: 300, dataIndex:"auditing" },
{header: "审核时间", sortable:true,width: 300, dataIndex:"auditTime"  ,renderer:this.dateRender() },
{header: "单据状态", sortable:true,width: 300, dataIndex:"status" }
        ]);
    OrderInfoPanel.superclass.initComponent.call(this);
}     
});


PurchaseBillPanel=Ext.extend(EasyJF.Ext.CrudMainDetailPanel,{
    id:"purchaseBillPanel",
    title:"采购单管理",
    baseUrl:"purchaseBill.ejf",
    billName:"采购单",
    pageSize:20,
    typesData:[["普通",0],["其它",1]],
    
    editStoreMapping:["id","product",{name:"productSn",mapping:"product.sn"},"price","num","amount","remark"],
    //重写onSelectObject方法
    onSelectObject:function(data){
        var cell=this.editGrid.getSelectionModel().getSelectedCell();
        if(cell){
            var record=this.editGrid.store.getAt(cell[0]);
            record.set("product",data);
            record.set("productSn",data.sn);
            record.set("price",data.costPrice);
            this.autoCountData(record);
        }
        this.autoAddLastRow();
    },
    createColumnEditor:function(){
        var service=this;
        this.productEditor=new EasyJF.Ext.ObjectSearchField({
            gridListClz:"ProductGridList",
            searchUrl:"product.ejf?cmd=list&pageSize=15",
            choiceValue:function(data){
                service.onSelectObject(data);
            }
        });
    },
    getEditGridColumn : function() {
                return new Ext.grid.ColumnModel([new Ext.grid.RowNumberer({header:"序号",width:40,name:"sequence"}),
                    {header:"Id",dataIndex:"id",width:1,hidden:true,hideable:false},
                    {
                            header : "产品编号",
                            width : 100,
                            sortable : true,
                            dataIndex : 'productSn',
                            editor:this.productEditor,
                            summaryType: 'count',summaryRenderer: function(v){return "合计("+v+")";}
                        }, {
                            header : "名称",
                            width : 100,
                            sortable : true,
                            dataIndex:"product",
                            renderer:this.objectRender("name")
                        }, {
                            header : "型号",
                            width : 100,
                            sortable : true,
                            dataIndex:"model",
                            renderer:this.objectPropertyRender("product","model")
                        }, {
                            header : "规格",
                            width : 80,
                            sortable : true,
                            dataIndex:"spec",
                            renderer:this.objectPropertyRender("product","spec")
                        }, {
                            header : "单位",
                            width : 80,
                            sortable : true,
                            dataIndex:"unit",
                            renderer:function(v,m,r){
                                if(r.get("product")&&r.get("product").unit)return r.get("product").unit.title;
                            }
                        },{
                            header : "单价",
                            width : 60,
                            sortable : true,
                            dataIndex:"price",
                            editor:new Ext.form.NumberField(),
                            renderer:this.moneyRender
                        }, {
                            header : "数量",
                            width : 60,
                            sortable : true,
                            dataIndex:"num",
                            editor:new Ext.form.NumberField(),
                            summaryType: 'sum',
                            summaryRenderer: EasyJF.Ext.Util.moneyRender
                        }, {
                            header : "金额",
                            width : 60,
                            sortable : true,
                            dataIndex:"amount",
                            renderer:this.moneyRender,
                            summaryType: 'sum',
                            summaryRenderer: EasyJF.Ext.Util.moneyRender
                        }, {
                            header : "备注",
                            width :400,
                            sortable : true,
                            dataIndex:"remark",
                            editor:new Ext.form.TextField()
                        }]);
            },
    create : function() {
        PurchaseBillPanel.superclass.create.call(this);
        Ext.Ajax.request({
                    url : "baseCount.ejf?cmd=getSn",
                    params : {
                        entityName : "PurchaseBill"
                    },
                    success : function(response) {
                        var ret=Ext.decode(response.responseText);
                        this.fp.form.findField("sn").setValue(ret);
                    },
                    scope:this
                })
    },          
    createForm:function(){
    this.editGrid=this.createEditGrid();    
    var formPanel=new Ext.form.FormPanel({
        frame:true,
        labelWidth:60,
        labelAlign:'right',
        layout:"border",
        defaults:{anchor:"-20"},
        items:[{region:"north",layout:"form",autoHeight:true,items:[{xtype:"hidden",name:"id"},
EasyJF.Ext.Util.columnPanelBuild(5,{ fieldLabel:'编号',name:'sn',readOnly:true},
EasyJF.Ext.Util.buildCombox('types', '类别' , this.typesData, 0),
 {xtype:"datefield", fieldLabel:'日期',name:'vdate' ,format:"Y-m-d",value:new Date()  },
this.buildRemoteCombox("supplier","供应商","supplier.ejf?cmd=list",["id","name"],"name","id"),
this.buildRemoteCombox("buyer","采购员","employee.ejf?cmd=list",["id","trueName"],"trueName","id")),
{xtype:"textfield",fieldLabel:'备注',name:'remark',anchor:"-20"}
    ]},
    {xtype:"fieldset",title:"订单详情",region:"center",layout:"fit",items:this.editGrid}]
    });
        return formPanel;
    },
    storeMapping:["id","sn","types","vdate","inputTime","remark","amount","auditing","auditTime","status"    ,"supplier","buyer","auditor"  ],
    initComponent : function(){
    this.cm=new Ext.grid.ColumnModel([
	{header: "编号", sortable:true,width: 300, dataIndex:"sn" },
	{header: "类别", sortable:true,width: 300, dataIndex:"types",renderer:this.typesRender(this.typesData) },
	{header: "采购日期", sortable:true,width: 300, dataIndex:"vdate"  ,renderer:this.dateRender() },
	{header: "录入日期", sortable:true,width: 300, dataIndex:"inputTime"  ,renderer:this.dateRender() },
	{header: "备注", sortable:true,width: 300, dataIndex:"remark" },
	{header: "金额", sortable:true,width: 300, dataIndex:"amount" },
	{header: "是否审核", sortable:true,width: 300, dataIndex:"auditing" },
	{header: "审核时间", sortable:true,width: 300, dataIndex:"auditTime"  ,renderer:this.dateRender() },
	{header: "状态", sortable:true,width: 300, dataIndex:"status" },
	{header: "供应商", sortable:true,width: 300, dataIndex:"supplier",renderer:this.objectRender("name")},
	{header: "采购员", sortable:true,width: 300, dataIndex:"buyer",renderer:this.objectRender("trueName")},
	{header: "审核人", sortable:true,width: 300, dataIndex:"auditor",renderer:this.objectRender("trueName")}
        ]);
    PurchaseBillPanel.superclass.initComponent.call(this);
}     
});


StockIncomePanel=Ext.extend(EasyJF.Ext.CrudMainDetailPanel,{
    id:"stockIncomePanel",
    title:"入库单管理",
    baseUrl:"stockIncome.ejf",
    billName:"入库单",
    onSelectObject:function(data){
        var cell=this.editGrid.getSelectionModel().getSelectedCell();
        if(cell){
            var record=this.editGrid.store.getAt(cell[0]);
            record.set((this.detailItemKey||"product"),data);
            record.set("productSn",data.sn);
            record.set("price",data.costPrice);
            this.autoCountData(record);
        }
        this.autoAddLastRow();
    },
    editStoreMapping:["id","product",{name:"productSn",mapping:"product.sn"},"price","num","amount","remark"],
    //以下方法子类必须重新定义，定义表单输入项，这里作为参考
    createColumnEditor:function(){
        //下面的代码仅提供给子类中参考
        var service=this;
        this.productEditor=new EasyJF.Ext.ObjectSearchField({
            gridListClz:"ProductGridList",
            searchUrl:"product.ejf?cmd=list&pageSize=15",
            choiceValue:function(data){
                service.onSelectObject(data);
            }
        });
    },
    audit:function(){
        var record=this.grid.getSelectionModel().getSelected();
        if(!record){
            Ext.Msg.alert("提示","请先选择一条记录！");
            return;
        }
        if(record.get("auditing")){
             Ext.Msg.alert("提示","该单据已经审核，不能重复审核！");
            return;
        }
        Ext.Ajax.request({
					url : this.baseUrl + "?cmd=audit",
                    params:{id:record.get("id")},
					success : function(response) {
                        var ret=Ext.decode(response.responseText);
                        if(ret.success){
                             Ext.Msg.alert("提示","操作成功!",function(){
                              this.store.reload();
                             },this);
                            
                        }
                        else {
                            Ext.Msg.alert("失败","审核失败，失败原因："+ret.errors.msg); 
                        }
					},
                    scope:this
				});
    },
    getEditGridColumn : function() {
                //下面的代码仅提供给子类中参考
                return new Ext.grid.ColumnModel([new Ext.grid.RowNumberer({header:"序号",width:40,name:"sequence"}),
                    {header:"Id",dataIndex:"id",width:1,hidden:true,hideable:false},
                    {
                            header : "产品编号",
                            width : 100,
                            sortable : true,
                            dataIndex : 'productSn',
                            editor:this.productEditor
                        }, {
                            header : "名称",
                            width : 100,
                            sortable : true,
                            dataIndex:"product",
                            renderer:this.objectRender("name"),
                            summaryType: 'count',summaryRenderer: function(v){return "合计("+v+")";}
                        }, {
                            header : "型号",
                            width : 100,
                            sortable : true,
                            dataIndex:"model",
                            renderer:this.objectPropertyRender("product","model")
                        }, {
                            header : "规格",
                            width : 80,
                            sortable : true,
                            dataIndex:"spec",
                            renderer:this.objectPropertyRender("product","spec")
                        }, {
                            header : "单位",
                            width : 80,
                            sortable : true,
                            dataIndex:"unit",
                            renderer:function(v,m,r){
                                if(r.get("product")&&r.get("product").unit)return r.get("product").unit.title;
                            }
                        },{
                            header : "单价",
                            width : 60,
                            sortable : true,
                            dataIndex:"price",
                            editor:new Ext.form.NumberField(),
                            renderer:this.moneyRender
                        }, {
                            header : "数量",
                            width : 60,
                            sortable : true,
                            dataIndex:"num",
                            editor:new Ext.form.NumberField(),
                            summaryType: 'sum',
                            summaryRenderer: EasyJF.Ext.Util.moneyRender
                        }, {
                            header : "金额",
                            width : 60,
                            sortable : true,
                            dataIndex:"amount",
                            renderer:this.moneyRender,
                            summaryType: 'sum',
                            summaryRenderer: EasyJF.Ext.Util.moneyRender
                        }, {
                            header : "备注",
                            width :400,
                            sortable : true,
                            dataIndex:"remark",
                            editor:new Ext.form.TextField()
                        }]);
            },
    create : function() {
        StockIncomePanel.superclass.create.call(this);
        Ext.Ajax.request({
                    url : "baseCount.ejf?cmd=getSn",
                    params : {
                        entityName : "StockIncome"
                    },
                    success : function(response) {
                        var ret=Ext.decode(response.responseText);
                        this.fp.form.findField("sn").setValue(ret);
                    },
                    scope:this
                })
    },         
    createForm:function(){
    this.editGrid=this.createEditGrid();  
    var formPanel=new Ext.form.FormPanel({
        frame:true,
        labelWidth:60,
        labelAlign:'right',
        layout:"border",
                        defaults:{anchor:"-20"},
                        items:[{region:"north",autoHeight:true,layout:"form",items:[{xtype:"hidden",name:"id"},
{xtype:"hidden", fieldLabel:'类别',name:'types',value:(this.baseQueryParameter?this.baseQueryParameter.types||0:0)},
EasyJF.Ext.Util.columnPanelBuild(5,{ fieldLabel:'编号',name:'sn',allowBlank:false,readOnly:true},
{xtype:"datefield", fieldLabel:'日期',name:'vdate' ,format:"Y-m-d",value:new Date()},
Ext.apply({},{allowBlank:false},this.buildRemoteCombox("depot","仓库","depot.ejf?cmd=list",["id","name"],"name","id")),
this.buildRemoteCombox("keeper","库管员","employee.ejf?cmd=list",["id","trueName"],"trueName","id"),
this.buildRemoteCombox("supplier","供应商","supplier.ejf?cmd=list",["id","name"],"name","id")
),
{ xtype:"textfield",fieldLabel:'备注',name:'remark',anchor:"-20"  }
    ]},
    {xtype:"fieldset",region:"center",layout:"fit",items:this.editGrid}]
    });

        return formPanel;
    },
    storeMapping:["id","sn","types","vdate","inputTime","remark","amount","auditing","auditTime","status","keeper","supplier","depot","auditor" ],
    initComponent : function(){
    this.cm=new Ext.grid.ColumnModel([
{header: "编号", sortable:true,width: 300, dataIndex:"sn" },
{header: "类别", sortable:true,hidden:true,width: 300, dataIndex:"types" },
{header: "日期", sortable:true,width: 300, dataIndex:"vdate"  ,renderer:this.dateRender() },
{header: "录入日期", sortable:true,width: 300, dataIndex:"inputTime"  ,renderer:this.dateRender() },
{header: "备注", sortable:true,width: 300, dataIndex:"remark" },
{header: "金额", sortable:true,width: 300, dataIndex:"amount" },
{header: "库管员", sortable:true,width: 300, dataIndex:"keeper",renderer:this.objectRender("trueName")},
{header: "供应商", sortable:true,width: 300, dataIndex:"supplier",renderer:this.objectRender("name")},
{header: "仓库", sortable:true,width: 300, dataIndex:"depot",renderer:this.objectRender("name")},
{header: "审核人", sortable:true,width: 300, dataIndex:"auditor",renderer:this.objectRender("trueName")},
{header: "是否审核", sortable:true,width: 300, dataIndex:"auditing",renderer:this.booleanRender },
{header: "审核时间", sortable:true,hidden:true,width: 300, dataIndex:"auditTime"  ,renderer:this.dateRender() },
{header: "状态", sortable:true,hidden:true,width: 300, dataIndex:"status" }

        ]);
    this.gridButtons=["-",{text:"打印",cls : "x-btn-text-icon",icon : "images/icons/printer.png",handler:this.printData,scope:this},{text:"审核",cls : "x-btn-text-icon",icon : "images/icons/audit.png",handler:this.audit,scope:this}];     
    StockIncomePanel.superclass.initComponent.call(this);
}     
});

/**
 * 期初入库单
 * @class StockOutcomeInitPanel
 * @extends StockIncomePanel
 */
StockIncomeInitPanel=Ext.extend(StockIncomePanel,{
    id:"stockIncomeInitPanel",
    title:"期初入库单管理",
    billName:"期初入库单",
    baseQueryParameter:{types:0}
});
/**
 * 采购入库单
 * @class StockOutcomePruchasePanel
 * @extends StockIncomePanel
 */
StockIncomePruchasePanel=Ext.extend(StockIncomePanel,{
    id:"stockIncomePruchasePanel",
    title:"采购入库单管理",
    billName:"采购入库单",
    baseQueryParameter:{types:1}
});
/**
 * 销售出库单
 * @class StockOutcomeOhterPanel
 * @extends StockIncomePanel
 */
StockIncomeOhterPanel=Ext.extend(StockIncomePanel,{
    id:"stockIncomeOhterPanel",
    title:"其它入库单管理",
    billName:"其它入库单",
    baseQueryParameter:{types:2}
});


StockOutcomePanel=Ext.extend(EasyJF.Ext.CrudMainDetailPanel,{
    id:"stockOutcomePanel",
    title:"出库单管理",
    baseUrl:"stockOutcome.ejf",
    billName:"出库单",
    useSalePrice:false,
    autoCountData:function(record){
        var num=record.get("num")||0,price=record.get("price")||0,salePrice=record.get("salePrice")||0;
        record.set("amount",num*price);
        if(this.useSalePrice)record.set("saleAmount",num*salePrice);
    },
    onSelectObject:function(data){
        var cell=this.editGrid.getSelectionModel().getSelectedCell();
        if(cell){
            var record=this.editGrid.store.getAt(cell[0]);
            record.set((this.detailItemKey||"product"),data);
            record.set("productSn",data.sn);
            record.set("price",data.costPrice);
            if(this.useSalePrice)record.set("salePrice",data.salePrice);
            this.autoCountData(record);
        }
        this.autoAddLastRow();
    },
    editStoreMapping:["id","product",{name:"productSn",mapping:"product.sn"},"price","num","amount","salePrice","saleAmount","remark"],
    createColumnEditor:function(){
        var service=this;
        this.productEditor=new EasyJF.Ext.ObjectSearchField({
            gridListClz:"ProductGridList",
            searchUrl:"product.ejf?cmd=list&pageSize=15",
            choiceValue:function(data){
                service.onSelectObject(data);
            }
        });
    },
    audit:function(){
        var record=this.grid.getSelectionModel().getSelected();
        if(!record){
            Ext.Msg.alert("提示","请先选择一条记录！");
            return;
        }
        if(record.get("auditing")){
             Ext.Msg.alert("提示","该单据已经审核，不能重复审核！");
            return;
        }
        Ext.Ajax.request({
                    url : this.baseUrl + "?cmd=audit",
                    params:{id:record.get("id")},
                    success : function(response) {
                        var ret=Ext.decode(response.responseText);
                        if(ret.success){
                             Ext.Msg.alert("提示","操作成功!",function(){
                              this.store.reload();
                             },this);
                            
                        }
                        else {
                            Ext.Msg.alert("失败","审核失败，失败原因："+ret.errors.msg); 
                        }
                    },
                    scope:this
                });
    },
    getEditGridColumn : function() {
                return new Ext.grid.ColumnModel([new Ext.grid.RowNumberer({header:"序号",width:40,name:"sequence"}),
                    {header:"Id",dataIndex:"id",width:1,hidden:true,hideable:false},
                    {
                            header : "产品编号",
                            width : 100,
                            sortable : true,
                            dataIndex : 'productSn',
                            editor:this.productEditor
                        }, {
                            header : "名称",
                            width : 100,
                            sortable : true,
                            dataIndex:"product",
                            renderer:this.objectRender("name"),
                            summaryType: 'count',summaryRenderer: function(v){return "合计("+v+")";}
                        }, {
                            header : "型号",
                            width : 100,
                            sortable : true,
                            dataIndex:"model",
                            renderer:this.objectPropertyRender("product","model")
                        }, {
                            header : "规格",
                            width : 80,
                            sortable : true,
                            dataIndex:"spec",
                            renderer:this.objectPropertyRender("product","spec")
                        }, {
                            header : "单位",
                            width : 80,
                            sortable : true,
                            dataIndex:"unit",
                            renderer:function(v,m,r){
                                if(r.get("product")&&r.get("product").unit)return r.get("product").unit.title;
                            }
                        },{
                            header : "销售单价",
                            width : 60,
                            sortable : true,
                            dataIndex:"salePrice",
                            hidden:!this.useSalePrice,
                            hideable:this.useSalePrice,
                            editor:new Ext.form.NumberField(),
                            renderer:this.moneyRender
                        },{
                            header : "单价",
                            width : 60,
                            sortable : true,
                            dataIndex:"price",
                            renderer:this.moneyRender
                        }, {
                            header : "数量",
                            width : 60,
                            sortable : true,
                            dataIndex:"num",
                            editor:new Ext.form.NumberField(),
                            summaryType: 'sum',
                            summaryRenderer: EasyJF.Ext.Util.moneyRender
                        }, {
                            header : "销售金额",
                            width : 60,
                            sortable : true,
                            dataIndex:"saleAmount",
                             hidden:!this.useSalePrice,
                            hideable:this.useSalePrice,
                            renderer:this.moneyRender,
                            summaryType: 'sum',
                            summaryRenderer: EasyJF.Ext.Util.moneyRender
                        }, {
                            header : "金额",
                            width : 60,
                            sortable : true,
                            dataIndex:"amount",
                            renderer:this.moneyRender,
                            summaryType: 'sum',
                            summaryRenderer: EasyJF.Ext.Util.moneyRender
                        }, {
                            header : "备注",
                            width :400,
                            sortable : true,
                            dataIndex:"remark",
                            editor:new Ext.form.TextField()
                        }]);
            },
    create : function() {
        StockOutcomePanel.superclass.create.call(this);
        Ext.Ajax.request({
                    url : "baseCount.ejf?cmd=getSn",
                    params : {
                        entityName : "StockOutcome"
                    },
                    success : function(response) {
                        var ret=Ext.decode(response.responseText);
                        this.fp.form.findField("sn").setValue(ret);
                    },
                    scope:this
                })
    },        
    createForm:function(){
    this.createEditGrid(); 
    var formPanel=new Ext.form.FormPanel({
        frame:true,
        labelWidth:60,
        labelAlign:'right',
        layout:"border",
                        defaults:{anchor:"-20"},
       items:[{region:"north",autoHeight:true,layout:"form",items:[{xtype:"hidden",name:"id"},
       {xtype:"hidden", fieldLabel:'类别',name:'types',value:(this.baseQueryParameter?this.baseQueryParameter.types||0:0)  },
EasyJF.Ext.Util.columnPanelBuild(5,       
{ fieldLabel:'编号',name:'sn',allowBlank:false,readOnly:true},
{xtype:"datefield", fieldLabel:'日期',name:'vdate' ,format:"Y-m-d",value:new Date()  },
Ext.apply({},{allowBlank:false},this.buildRemoteCombox("depot","仓库","depot.ejf?cmd=list",["id","name"],"name","id")),
this.buildRemoteCombox("client","客户","client.ejf?cmd=list",["id"]),
this.buildRemoteCombox("keeper","库管员","employee.ejf?cmd=list",["id"])),
{ fieldLabel:'备注',name:'remark',anchor:"-20"  }]},
{xtype:"fieldset",region:"center",layout:"fit",items:this.editGrid}
    ]
    });
        return formPanel;
    },
    storeMapping:["id","sn","types","vdate","remark","amount","saleAmount","auditing","auditTime","status","client","depot","keeper","auditor"   ],
    initComponent : function(){
    this.cm=new Ext.grid.ColumnModel([
{header: "编号", sortable:true,width: 300, dataIndex:"sn" },
{header: "类别", sortable:true,hidden:true,width: 300, dataIndex:"types" },
{header: "日期", sortable:true,width: 300, dataIndex:"vdate"  ,renderer:this.dateRender() },
{header: "仓库", sortable:true,width: 300, dataIndex:"depot",renderer:this.objectRender("name")},
{header: "客户", sortable:true,width: 300, dataIndex:"client",renderer:this.objectRender("name")},
{header: "库管员", sortable:true,width: 300, dataIndex:"keeper",renderer:this.objectRender("trueName")},
{header: "销售金额", sortable:true,hidden:true,width: 300, dataIndex:"saleAmount" },
{header: "金额", sortable:true,width: 300, dataIndex:"amount" },
{header: "审核", sortable:true,width: 300, dataIndex:"auditing",renderer:this.booleanRender },
{header: "审核人", sortable:true,width: 300, dataIndex:"auditor",renderer:this.objectRender("trueName")},
{header: "审核时间", sortable:true,hidden:true,width: 300, dataIndex:"auditTime"  ,renderer:this.dateRender() },
{header: "备注", sortable:true,width: 300, dataIndex:"remark" },
{header: "状态", sortable:true,hidden:true,width: 300, dataIndex:"status" }

        ]);
    this.gridButtons=["-",{text:"打印",cls : "x-btn-text-icon",icon : "images/icons/printer.png",handler:this.printData,scope:this},{text:"审核",cls : "x-btn-text-icon",icon : "images/icons/audit.png",handler:this.audit,scope:this}];    
    StockOutcomePanel.superclass.initComponent.call(this);
}     
});

StockOutcomeOrdersPanel=Ext.extend(StockOutcomePanel,{
    id:"stockOutcomeOrdersPanel",
    title:"销售出库单管理",
    useSalePrice:true,
    billName:"销售出库单",
    baseQueryParameter:{types:0}
});
StockOutcomeProducePanel=Ext.extend(StockOutcomePanel,{
    id:"stockOutcomeProducePanel",
    title:"生产领料单管理",
    billName:"生产领料单",
    baseQueryParameter:{types:1}
});
StockOutcomeOtherPanel=Ext.extend(StockOutcomePanel,{
    id:"stockOutcomeOtherPanel",
    title:"其它出库单管理",
    billName:"其它出库单",
    baseQueryParameter:{types:2}
});


ProductStockPanel=Ext.extend(EasyJF.Ext.BaseGridList,{
    id:"productStockPanel",
    title:"即时库存",
    url:"productStock.ejf?cmd=list",
    storeMapping:["id","storeNum","price","amount","incomeDate","outcomeDate","warning","topNum","bottomNum"    ,"product","depot"    ],
    loadData:true,
    search:function(){
        this.store.reload();
    },
    initComponent : function(){
    this.search_depot=new  Ext.form.ComboBox(EasyJF.Ext.Util.buildRemoteCombox("depot","仓库","depot.ejf?cmd=list",["id","name"],"name","id"));
    this.search_sn=new Ext.form.TextField({name:"sn"})
    this.tbar=["仓库:",this.search_depot,"货品编号",this.search_sn,{text:"查询",handler:this.search,scope:this,cls : "x-btn-text-icon",icon : "images/icons/search.png"}];    
    this.cm=new Ext.grid.ColumnModel([
{header: "仓库", sortable:true,width: 300, dataIndex:"depot",renderer:this.objectRender("name")},      
{header: "货品名称", sortable:true,width: 300, dataIndex:"product",renderer:this.objectRender("name")},
{header: "货品编号", sortable:true,width: 300, dataIndex:"product",renderer:this.objectRender("sn")},
{header: "库存量", sortable:true,width: 300, dataIndex:"storeNum" },
{header: "单价", sortable:true,width: 300, dataIndex:"price" },
{header: "金额", sortable:true,width: 300, dataIndex:"amount" },
{header: "最近入库", sortable:true,width: 300, dataIndex:"incomeDate"  ,renderer:this.dateRender() },
{header: "最近出库", sortable:true,width: 300, dataIndex:"outcomeDate"  ,renderer:this.dateRender() },
{header: "预警", sortable:true,width: 300, dataIndex:"warning" },
{header: "最高库存", sortable:true,width: 300, dataIndex:"topNum" },
{header: "最底库存", sortable:true,width: 300, dataIndex:"bottomNum" }
        ]);
    ProductStockPanel.superclass.initComponent.call(this);
}     
});

BaseReportChartPanel=Ext.extend(EasyJF.Ext.BaseGridList,{
    reset:function(){
      this.getTopToolbar().items.each(function(f){
        if(f.reset)f.reset();
      })  
    },
    showColumnChart:function(){
          var win=new Ext.Window({
                layout:"fit",
                title:"统计结果分析",
                width:Ext.getBody().getViewSize().width-100,
                height:Ext.getBody().getViewSize().height-50,
                closeAction:"hide",
                items:{
            store: this.store,
            xtype: 'columnchart',
            xField:"title",
            yAxis: new Ext.chart.NumericAxis({
                displayName: '金额',
                labelRenderer : Ext.util.Format.numberRenderer('0,0')
            }),
            series: [{
                type: 'column',
                displayName: '金额',
                yField: 'amount',
                style: {
                    image:'bar.gif',
                    mode: 'stretch',
                    color:0x99BBE8
                }
            }]

        }})
        win.show();
    },
    showPieChart:function(){
          var win=new Ext.Window({
                layout:"fit",
                title:"统计结果分析",
                width:Ext.getBody().getViewSize().width-300,
                height:Ext.getBody().getViewSize().height-50,
                closeAction:"hide",
                items:{
            store: this.store,
            xtype: 'piechart',
            dataField: 'amount',
            categoryField: 'title',
            extraStyle : {
                            legend : {
                                display : 'bottom'
                            }
                        }

        }})
        win.show();
    },
    showLineChart:function(){
          var win=new Ext.Window({
                layout:"fit",
                title:"统计结果分析",
                width:Ext.getBody().getViewSize().width-100,
                height:Ext.getBody().getViewSize().height-50,
                closeAction:"hide",
                items:{
            xtype: 'linechart',
            store: this.store,
            xField: 'title',
            yField: 'amount',
            yAxis: new Ext.chart.NumericAxis({
                displayName: '金额',
                labelRenderer : Ext.util.Format.numberRenderer('0,0')
            }),
            tipRenderer : function(chart, record){
                return record.data.title+"的金额:"+Ext.util.Format.number(record.data.amount, '0,0');
            }
        }})
        win.show();
    }
})

PurchaseReportPanel=Ext.extend(BaseReportChartPanel,{
    id:"purchaseReportPanel",
    title:"采购报表",
    url:"purchaseBill.ejf?cmd=statistics",
    storeMapping:["id","title","value","num","amount"],
    searchResult:function(groupBy){
        return function(){
        var obj={supplier:this.search_supplier.getValue(),
        sn:this.search_sn.getValue(),
        name:this.search_name.getValue(),
        dir:this.search_dir.getValue(),
        vdate1:this.search_vdate1.getValue()?this.search_vdate1.getValue().format("Y-m-d"):"",
        vdate2:this.search_vdate2.getValue()?this.search_vdate2.getValue().format("Y-m-d"):"",
        buyer:this.search_buyer.getValue(),
        groupBy:groupBy};
        this.store.reload({params:obj});
        }
    },
    initComponent : function(){
    this.search_supplier=new  Ext.form.ComboBox(Ext.apply({},{width:50},this.buildRemoteCombox("supplier","供应商","supplier.ejf?cmd=list",["id","name"],"name","id")));
    this.search_sn=new Ext.form.TextField({name:"sn",width:60});
    this.search_name=new Ext.form.TextField({name:"name",width:60});
    this.search_dir=new EasyJF.Ext.TreeComboField(Ext.apply({},{width:50},EasyJF.Ext.Util.buildRemoteTreeCombox("dir", "分类", "productDir.ejf?cmd=getTree", "name","id", "parent","所有分类")));
    this.search_vdate1=new Ext.form.DateField({name:"vdate1",format:"Y-m-d"});
    this.search_vdate2=new Ext.form.DateField({name:"vdate2",format:"Y-m-d"});
    this.search_buyer=new Ext.form.ComboBox(Ext.apply({},{width:80},this.buildRemoteCombox("buyer","采购员","employee.ejf?cmd=list",["id","name","trueName"],"trueName","id")));
    this.tbar=["货品类别",this.search_dir,"货品编号",this.search_sn,"货品名称",this.search_name,"日期",this.search_vdate1,"到",this.search_vdate2,"供应商",this.search_supplier,"采购员",this.search_buyer,
    "-",
    new Ext.SplitButton({
    text: '查看结果',
    handler: this.searchResult("p.dir_id"),
    scope:this,
    cls : "x-btn-text-icon",icon : "images/icons/search.png",
    menu: new Ext.menu.Menu({
        items: [
            {text: '按分类分组', handler:  this.searchResult("p.dir_id"),scope:this},
            {text: '按产品统计', handler: this.searchResult("p.id"),scope:this},
            {text: '按采购员分组', handler: this.searchResult("bill.buyer_id"),scope:this},
            {text: '按日期分组', handler: this.searchResult("day"),scope:this},
            {text: '按月份分组', handler: this.searchResult("month"),scope:this},
            {text: '按供应商分组', handler: this.searchResult("bill.supplier_id"),scope:this}
        ]
    })}),new Ext.SplitButton({
    text: '图表分析',
    handler: this.showColumnChart,
    scope:this,
    cls : "x-btn-text-icon",icon : "images/icons/chart.png",
    menu: new Ext.menu.Menu({
        items: [
            {text: '柱状图', handler: this.showColumnChart,scope:this},
            {text: '饼状图', handler:  this.showPieChart,scope:this},
            {text: '曲线图', handler:  this.showLineChart,scope:this}
        ]
    })}),"-",{text:"重置",handler:this.reset,scope:this,cls : "x-btn-text-icon",icon : "images/icons/reset.png"}];    
    this.cm=new Ext.grid.ColumnModel([
{header: "项目名称", sortable:true,width: 300, dataIndex:"title"},      
{header: "数量", sortable:true,width: 300, dataIndex:"num"},
{header: "金额", sortable:true,width: 300, dataIndex:"amount"}
        ]);
    PurchaseReportPanel.superclass.initComponent.call(this);
}     
});


OrderInfoReportPanel=Ext.extend(BaseReportChartPanel,{
    id:"orderInfoReportPanel",
    title:"销售报表",
    url:"orderInfo.ejf?cmd=statistics",
    storeMapping:["id","title","value","num","amount"],
    searchResult:function(groupBy){
        return function(){
        var obj={client:this.search_client.getValue(),
        sn:this.search_sn.getValue(),
        name:this.search_name.getValue(),
        dir:this.search_dir.getValue(),
        vdate1:this.search_vdate1.getValue()?this.search_vdate1.getValue().format("Y-m-d"):"",
        vdate2:this.search_vdate2.getValue()?this.search_vdate2.getValue().format("Y-m-d"):"",
        seller:this.search_seller.getValue(),
        groupBy:groupBy};
        this.store.reload({params:obj});
        }
    },
    initComponent : function(){
    this.search_client=new  Ext.form.ComboBox(Ext.apply({},{width:100},this.buildRemoteCombox("client","客户","client.ejf?cmd=list",["id","name"],"name","id")));
    this.search_sn=new Ext.form.TextField({name:"sn",width:60});
    this.search_name=new Ext.form.TextField({name:"name",width:60});
    this.search_dir=new EasyJF.Ext.TreeComboField(Ext.apply({},{width:100},EasyJF.Ext.Util.buildRemoteTreeCombox("dir", "分类", "productDir.ejf?cmd=getTree", "name","id", "parent","所有分类")));
    this.search_vdate1=new Ext.form.DateField({name:"vdate1",format:"Y-m-d"});
    this.search_vdate2=new Ext.form.DateField({name:"vdate2",format:"Y-m-d"});
    this.search_seller=new Ext.form.ComboBox(Ext.apply({},{width:80},this.buildRemoteCombox("seller","业务员","employee.ejf?cmd=list",["id","name","trueName"],"trueName","id")));
    this.tbar=["货品类别",this.search_dir,"货品编号",this.search_sn,"货品名称",this.search_name,"日期",this.search_vdate1,"到",this.search_vdate2,"客户",this.search_client,"业务员",this.search_seller,
    "-",
    new Ext.SplitButton({
    text: '查看结果',
    handler: this.searchResult("p.dir_id"),
    scope:this,
    cls : "x-btn-text-icon",icon : "images/icons/search.png",
    menu: new Ext.menu.Menu({
        items: [
            {text: '按分类分组', handler:  this.searchResult("p.dir_id"),scope:this},
            {text: '按产品统计', handler: this.searchResult("p.id"),scope:this},
            {text: '按业务员分组', handler: this.searchResult("bill.seller_id"),scope:this},
            {text: '按日期分组', handler: this.searchResult("day"),scope:this},
            {text: '按月份分组', handler: this.searchResult("month"),scope:this},
            {text: '按供客户分组', handler: this.searchResult("bill.client_id"),scope:this}
        ]
    })}),new Ext.SplitButton({
    text: '图表分析',
    handler: this.showColumnChart,
    scope:this,
    cls : "x-btn-text-icon",icon : "images/icons/chart.png",
    menu: new Ext.menu.Menu({
        items: [
            {text: '柱状图', handler: this.showColumnChart,scope:this},
            {text: '饼状图', handler:  this.showPieChart,scope:this},
            {text: '曲线图', handler:  this.showLineChart,scope:this}
        ]
    })}),"-",{text:"重置",handler:this.reset,scope:this,cls : "x-btn-text-icon",icon : "images/icons/reset.png"}];    
    this.cm=new Ext.grid.ColumnModel([
{header: "项目名称", sortable:true,width: 300, dataIndex:"title"},      
{header: "数量", sortable:true,width: 300, dataIndex:"num"},
{header: "金额", sortable:true,width: 300, dataIndex:"amount"}
        ]);
    OrderInfoReportPanel.superclass.initComponent.call(this);
}     
});



StockDetailTotalReportPanel=Ext.extend(BaseReportChartPanel,{
    id:"stockDetailTotalReportPanel",
    title:"进销存汇总表",
    url:"stockDetailAccount.ejf?cmd=statisticsTotal",
    storeMapping:["id","title","value","initNum","initAmount","inNum","inAmount","outNum","outAmount","num","amount"],
    searchResult:function(){
        var obj={
        vdate1:this.search_vdate1.getValue()?this.search_vdate1.getValue().format("Y-m-d"):"",
        vdate2:this.search_vdate2.getValue()?this.search_vdate2.getValue().format("Y-m-d"):""};
        this.store.reload({params:obj});
    },
    numRender:function(v){
      return v||"";  
    },
    initComponent : function(){
    this.search_dir=new EasyJF.Ext.TreeComboField(Ext.apply({},{width:100},EasyJF.Ext.Util.buildRemoteTreeCombox("dir", "分类", "productDir.ejf?cmd=getTree", "name","id", "parent","所有分类")));
    this.search_vdate1=new Ext.form.DateField({name:"vdate1",format:"Y-m-d"});
    this.search_vdate2=new Ext.form.DateField({name:"vdate2",format:"Y-m-d"});
    this.tbar=["日期",this.search_vdate1,"到",this.search_vdate2,
    "-",{
    text: '查看结果',
    handler: this.searchResult,
    scope:this,
    cls : "x-btn-text-icon",icon : "images/icons/search.png"},new Ext.SplitButton({
    text: '图表分析',
    handler: this.showColumnChart,
    scope:this,
    cls : "x-btn-text-icon",icon : "images/icons/chart.png",
    menu: new Ext.menu.Menu({
        items: [
            {text: '柱状图', handler: this.showColumnChart,scope:this},
            {text: '饼状图', handler:  this.showPieChart,scope:this},
            {text: '曲线图', handler:  this.showLineChart,scope:this}
        ]
    })}),"-",{text:"重置",handler:this.reset,scope:this,cls : "x-btn-text-icon",icon : "images/icons/reset.png"}];    
    this.cm=new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer({header:"序号",width:40}),    
		{header: "项目名称", sortable:true,width: 300, dataIndex:"title"},      
		{header: "数量", sortable:true,width: 300, dataIndex:"initNum",renderer:this.numRender},
		{header: "金额", sortable:true,width: 300, dataIndex:"initAmount",renderer:this.moneyRender},
		{header: "数量", sortable:true,width: 300, dataIndex:"inNum",renderer:this.numRender},
		{header: "金额", sortable:true,width: 300, dataIndex:"inAmount",renderer:this.moneyRender},
		{header: "数量", sortable:true,width: 300, dataIndex:"outNum",renderer:this.numRender},
		{header: "金额", sortable:true,width: 300, dataIndex:"outAmount",renderer:this.moneyRender},
		{header: "数量", sortable:true,width: 300, dataIndex:"num",renderer:this.numRender},
		{header: "金额", sortable:true,width: 300, dataIndex:"amount",renderer:this.moneyRender}
        ]);
    this.gridConfig={plugins:[new Ext.ux.grid.ColumnHeaderGroup({
        rows: [[
               {},{},
               {header: '期初', colspan: 2, align: 'center'},
               {header: '入库', colspan: 2, align: 'center'},
               {header: '出库', colspan: 2, align: 'center'},
               {header: '结余', colspan: 2, align: 'center'}
              ]],
             hierarchicalColMenu: true
    })
]};    
    StockDetailTotalReportPanel.superclass.initComponent.call(this);
}     
});


StockDetailAccountReportPanel=Ext.extend(BaseReportChartPanel,{
    id:"stockDetailAccountReportPanel",
    title:"进销存明细帐",
    url:"stockDetailAccount.ejf?cmd=statistics",
    storeMapping:["id","product","depot","initNum","initAmount","inNum","inAmount","outNum","outAmount","num","amount"],
    searchResult:function(){
        var obj={
        sn:this.search_sn.getValue(),
        name:this.search_name.getValue(),
        dir:this.search_dir.getValue(),
        depot:this.search_depot.getValue(),
        vdate1:this.search_vdate1.getValue()?this.search_vdate1.getValue().format("Y-m-d"):"",
        vdate2:this.search_vdate2.getValue()?this.search_vdate2.getValue().format("Y-m-d"):""};
        this.store.reload({params:obj});
    },
    numRender:function(v){
      return v||"";  
    },
    initComponent : function(){
    this.search_sn=new Ext.form.TextField({name:"sn",width:60});
    this.search_name=new Ext.form.TextField({name:"name",width:60});
    this.search_dir=new EasyJF.Ext.TreeComboField(Ext.apply({},{width:100},EasyJF.Ext.Util.buildRemoteTreeCombox("dir", "分类", "productDir.ejf?cmd=getTree", "name","id", "parent","所有分类")));
    this.search_vdate1=new Ext.form.DateField({name:"vdate1",format:"Y-m-d"});
    this.search_vdate2=new Ext.form.DateField({name:"vdate2",format:"Y-m-d"});
    this.search_depot=new Ext.form.ComboBox(Ext.apply({},{width:80},this.buildRemoteCombox("depot","仓库","depot.ejf?cmd=list",["id","name"],"name","id",false)));
    this.tbar=["仓库",this.search_depot,"分类",this.search_dir,"货品编号",this.search_sn,"货品名称",this.search_name,"日期",this.search_vdate1,"到",this.search_vdate2,
    "-",{
    text: '查看结果',
    handler: this.searchResult,
    scope:this,
    cls : "x-btn-text-icon",icon : "images/icons/search.png"},new Ext.SplitButton({
    text: '图表分析',
    handler: this.showColumnChart,
    scope:this,
    cls : "x-btn-text-icon",icon : "images/icons/chart.png",
    menu: new Ext.menu.Menu({
        items: [
            {text: '柱状图', handler: this.showColumnChart,scope:this},
            {text: '饼状图', handler:  this.showPieChart,scope:this},
            {text: '曲线图', handler:  this.showLineChart,scope:this}
        ]
    })}),"-",{text:"重置",handler:this.reset,scope:this,cls : "x-btn-text-icon",icon : "images/icons/reset.png"}];    
    this.cm=new Ext.grid.ColumnModel([
        new Ext.grid.RowNumberer({header:"序号",width:40}),    
        {header: "仓库", sortable:true,width: 300, dataIndex:"depot",renderer:this.objectRender("name")},  
        {header: "产品名称", sortable:true,width: 300, dataIndex:"product",renderer:this.objectRender("name")}, 
        {header: "产品编号", sortable:true,width: 300, dataIndex:"product",renderer:this.objectRender("sn")}, 
        {header: "数量", sortable:true,width: 300, dataIndex:"initNum",renderer:this.numRender},
        {header: "金额", sortable:true,width: 300, dataIndex:"initAmount",renderer:this.moneyRender},
        {header: "数量", sortable:true,width: 300, dataIndex:"inNum",renderer:this.numRender},
        {header: "金额", sortable:true,width: 300, dataIndex:"inAmount",renderer:this.moneyRender},
        {header: "数量", sortable:true,width: 300, dataIndex:"outNum",renderer:this.numRender},
        {header: "金额", sortable:true,width: 300, dataIndex:"outAmount",renderer:this.moneyRender},
        {header: "数量", sortable:true,width: 300, dataIndex:"num",renderer:this.numRender},
        {header: "金额", sortable:true,width: 300, dataIndex:"amount",renderer:this.moneyRender}
        ]);
    this.gridConfig={plugins:[new Ext.ux.grid.ColumnHeaderGroup({
        rows: [[
               {},{},{},{},
               {header: '期初', colspan: 2, align: 'center'},
               {header: '入库', colspan: 2, align: 'center'},
               {header: '出库', colspan: 2, align: 'center'},
               {header: '结余', colspan: 2, align: 'center'}
              ]],
             hierarchicalColMenu: true
    })
]};    
    StockDetailAccountReportPanel.superclass.initComponent.call(this);
}     
});