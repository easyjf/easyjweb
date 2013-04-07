
DepartmentPanel=Ext.extend(EasyJF.Ext.CrudPanel,{
    id:"departmentPanel",
    title:"部门列表",
    baseUrl:"department.ejf",
    createForm:function(){
    var formPanel=new Ext.form.FormPanel({
        frame:true,
        labelWidth:70,
        labelAlign:'right',
        defaultType:'textfield',
                        defaults:{anchor:"-20"},
                        items:[{xtype:"hidden",name:"id"},
{ fieldLabel:'部门名称',name:'name'  },
{ fieldLabel:'部门编码',name:'sn'  },
{xtype:"treecombo",fieldLabel:"上级部门",name:"parent",hiddenName:"parent",tree:new   Ext.tree.TreePanel({
                            loader : new Ext.tree.TreeLoader({
                                        url : "department.ejf?cmd=getTree",
                                        nodeParameter:"parent"
                                    }),
                            root : new Ext.tree.AsyncTreeNode({
                                        text : "顶级部门",
                                        id : "root",
                                        expanded : true
                                    })})},
{ fieldLabel:'部门电话',name:'tel'  },
{xtype:"numberfield", fieldLabel:'排序',name:'sequence'  },
{ fieldLabel:'简介',xtype:"textarea",name:'intro'  }

    ]
    });
        return formPanel;
    },
    save:function(){
        var id=this.fp.form.findField("id").getValue();     
        this.fp.form.submit({
                waitMsg:'正在保存。。。',
                url:this.baseUrl+"?cmd="+(id?"update":"save"),
                method:'POST',
                success:function(){
                this.closeWin();
                this.store.reload();  
                if(this.tree)this.tree.root.reload();
                },
                scope:this
        }); 
    },
    createWin:function(){
        return this.initWin(408,280,"部门信息录入");
    },
    storeMapping:["id","name","sn","tel","intro","sequence"    ,"parent"    ],
    initComponent : function(){
    this.cm=new Ext.grid.ColumnModel([
{header: "名称", sortable:true,width: 300, dataIndex:"name"},
{header: "编号", sortable:true,width: 300, dataIndex:"sn"},
{header: "联系电话", sortable:true,width: 300, dataIndex:"tel"},
{header: "简介", sortable:true,width: 300, dataIndex:"intro"},
{header: "排序", sortable:true,width: 300, dataIndex:"sequence"}
,{header: "上级部门", sortable:true,width: 300, dataIndex:"parent",renderer:this.objectRender("name")}
        ]);
    DepartmentPanel.superclass.initComponent.call(this);
}     
});

DepartmentManagePanel = Ext.extend(Ext.Panel, {
			layout : "border",
			id : "departmentManagePanel",
			title : "部门信息管理",
            closable: true,
            border:false,
            createTreePanel:function(){
                return new Ext.tree.TreePanel({
                            xtype : "treepanel",
                            region : "west",
                            width : 180,
                            title : "部门树",
                            border : false,
                            loader : new Ext.tree.TreeLoader({
                                        url : "department.ejf?cmd=getTree",
                                        nodeParameter:"parent"
                                    }),
                            root : new Ext.tree.AsyncTreeNode({
                                        text : "顶级部门",
                                        id : "root",
                                        expanded : true
                                    }),
                            listeners:{click:function(node){
                                this.list.store.removeAll();
                                this.list.store.reload({params:{parent:node.id}});
                            },scope:this}
                        });
            },
			initComponent : function() {
				DepartmentManagePanel.superclass.initComponent.call(this);
				this.list = new DepartmentPanel({
							region : "center"
						});
                this.tree=this.createTreePanel();
                this.list.tree=this.tree;
				this.add(this.tree);
				this.add(this.list);
			}
		})


DepotPanel=Ext.extend(EasyJF.Ext.CrudPanel,{
    id:"depotPanel",
    title:"仓库管理",
    baseUrl:"depot.ejf",
    typesData:[["储备库",0],["次品库",1],["零售库",2]],
    createForm:function(){
    var formPanel=new Ext.form.FormPanel({
        frame:true,
        labelWidth:60,
        labelAlign:'right',
        defaultType:'textfield',
                        defaults:{anchor:"-20"},
                        items:[{xtype:"hidden",name:"id"},
  { fieldLabel:'仓库名称',name:'name'},
  EasyJF.Ext.Util.buildCombox('types', '类别', this.typesData),
 {xtype:"numberfield", fieldLabel:'最大容量',name:'maxCapacity'  },
 {xtype:"numberfield", fieldLabel:'排序',name:'sequence'  }
    ]
    });

        return formPanel;
    },
    createWin:function(){
        return this.initWin(268,180,"仓库信息录入");
    },
    storeMapping:["id","name","types","maxCapacity","capcity","amount","sequence"       ],
    initComponent : function(){
    this.cm=new Ext.grid.ColumnModel([
{header: "名称", sortable:true,width: 300, dataIndex:"name" },
{header: "类别", sortable:true,width: 300, dataIndex:"types",renderer:this.typesRender(this.typesData)},
{header: "最大容量", sortable:true,width: 300, dataIndex:"maxCapacity" },
{header: "当前容量", sortable:true,width: 300, dataIndex:"capcity" },
{header: "金额", sortable:true,width: 300, dataIndex:"amount" },
{header: "排序", sortable:true,width: 300, dataIndex:"sequence" }
        ]);
    DepotPanel.superclass.initComponent.call(this);
}     
});
 


EmployeePanel=Ext.extend(EasyJF.Ext.CrudPanel,{
    id:"employeePanel",
    title:"员工管理",
    baseUrl:"employee.ejf",
    typesData:[["普通员工",0],["管理员",1]],
    sexData:[["男","男"],["女","女"]],
    createForm:function(){
    var formPanel=new Ext.form.FormPanel({
        frame:true,
        labelWidth:60,
        labelAlign:'right',
        defaultType:'textfield',
                        defaults:{anchor:"-20"},
                        items:[{xtype:"hidden",name:"id"},
                        EasyJF.Ext.Util.columnPanelBuild(
{fieldLabel:'用户名',name:'name'},
    {fieldLabel:'密码',name:'password',inputType:"password"},
    {fieldLabel:'姓名',name:'trueName'},
    EasyJF.Ext.Util.buildCombox("sex","性别",this.sexData,"",true),
    {xtype:"treecombo",fieldLabel:"上级部门",name:"dept",hiddenName:"dept",tree:new   Ext.tree.TreePanel({
                            loader : new Ext.tree.TreeLoader({
                                        url : "department.ejf?cmd=getTree",
                                        nodeParameter:"parent"
                                    }),
                            root : new Ext.tree.AsyncTreeNode({
                                        text : "顶级部门",
                                        id : "root",
                                        expanded : true
                                    })})},
    {fieldLabel:'Email',name:'email'},
    {fieldLabel:'电话',name:'tel'},
    {fieldLabel:'地址',name:'address'},
    {fieldLabel:'职务',name:'duty'},
    EasyJF.Ext.Util.buildCombox("types","用户类别",this.typesData,0)),
    {xtype:"textarea",fieldLabel:'备注',name:'remark',height:85}]
    });
        return formPanel;
    },
    createWin:function(){
        return this.initWin(538,300,"员工信息录入");
    },
    storeMapping:["id",
    "name","password","trueName","sex","email","remark","duty","tel","address","types","loginTimes","lastLoginIp","lastLoginTime","lastLogoutTime","dept"  ],
    initComponent : function(){
    this.cm=new Ext.grid.ColumnModel([
{header: "用户名", sortable:true,width: 300, dataIndex:"name"},
{header: "姓名", sortable:true,width: 300, dataIndex:"trueName"},
{header: "性别", sortable:true,width: 300, dataIndex:"sex"},
{header: "部门", sortable:true,width: 300, dataIndex:"dept",renderer:this.objectRender("name")},
{header: "电子邮箱", sortable:true,width: 300, dataIndex:"email"},
{header: "备注", sortable:true,width: 300, dataIndex:"remark"},
{header: "职务", sortable:true,width: 300, dataIndex:"duty"},
{header: "电话", sortable:true,width: 300, dataIndex:"tel"},
{header: "地址", sortable:true,width: 300, dataIndex:"address"},
{header: "用户类别", sortable:true,width: 300, dataIndex:"types",renderer:this.typesRender(this.typesData)},
{header: "登录次数", sortable:true,hidden:true,width: 300, dataIndex:"loginTimes"},
{header: "上次登录IP", sortable:true,hidden:true,width: 300, dataIndex:"lastLoginIp"},
{header: "上次登录时间", sortable:true,hidden:true,width: 300, dataIndex:"lastLoginTime"},
{header: "上次退出时间", sortable:true,hidden:true,width: 300, dataIndex:"lastLogoutTime"}
        ]);
    EmployeePanel.superclass.initComponent.call(this);
}     
});


ProductDirPanel=Ext.extend(EasyJF.Ext.CrudTreePanel,{
    id:"productDirPanel",
    title:"产品分类管理",
    baseUrl:"productDir.ejf",
    treeTitle:"分类树",
    rootTitle:"全部分类",
    createForm:function(){
    var formPanel=new Ext.form.FormPanel({
        frame:true,
        labelWidth:60,
        labelAlign:'right',
        defaultType:'textfield',
                        defaults:{anchor:"-20"},
                        items:[{xtype:"hidden",name:"id"},
    {fieldLabel:'编号',name:'sn',allowBlank:false},
    {fieldLabel:'名称',name:'name',allowBlank:false},
    EasyJF.Ext.Util.buildRemoteTreeCombox("parent", "父目录", "productDir.ejf?cmd=getTree", "name","id", "parent","所有分类"),
    {fieldLabel:'排序',name:'sequence'},
    {xtype:"textarea",fieldLabel:'简介',name:'intro'}
     ]
    });

        return formPanel;
    },
    createWin:function(){
        return this.initWin(358,255,"分类信息录入");
    },
    storeMapping:["id",
    "sn","name","intro","dirPath","sequence"    ],
    initComponent : function(){
    this.cm=new Ext.grid.ColumnModel([
{header: "编号", sortable:true,width: 300, dataIndex:"sn"},
{header: "名称", sortable:true,width: 300, dataIndex:"name"},
{header: "父级", sortable:true,width: 300, dataIndex:"parent",renderer:this.objectRender("name")},
{header: "排序", sortable:true,width: 300, dataIndex:"sequence"},
{header: "简介", sortable:true,width: 300, dataIndex:"intro"}
        ]);
    ProductDirPanel.superclass.initComponent.call(this);
}     
});


ProductPanel=Ext.extend(EasyJF.Ext.CrudPanel,{
    id:"productPanel",
    title:"货品管理",
    baseUrl:"product.ejf",
    showExportData:true,
    showImportData:true,
    createForm:function(){
    var formPanel=new Ext.form.FormPanel({
        frame:true,
        labelWidth:60,
        labelAlign:'right',
        fileUpload:true,
        defaultType:'textfield',
                        defaults:{anchor:"-20"},
                        items:[{xtype:"hidden",name:"id"},
EasyJF.Ext.Util.columnPanelBuild(
{ fieldLabel:'名称',name:'name',allowBlank:false},
{ fieldLabel:'编号',name:'sn',allowBlank:false},
EasyJF.Ext.Util.buildRemoteTreeCombox("dir", "分类", "productDir.ejf?cmd=getTree", "name","id", "parent","所有分类"),
this.buildRemoteCombox("brand","品牌","systemDictionaryDetail.ejf?cmd=list&parentSn=ProductBrand",["id","title"],"title"),
this.buildRemoteCombox("unit","单位","systemDictionaryDetail.ejf?cmd=list&parentSn=ProductUnit",["id","title"],"title"),
 {xtype:"numberfield", fieldLabel:'销售价',name:'salePrice'  },
 {xtype:"numberfield", fieldLabel:'成本价',name:'costPrice'  },
{ fieldLabel:'规格',name:'spec'  },
{ fieldLabel:'型号',name:'model'  },
{ fieldLabel:'颜色',name:'color'  },
{ fieldLabel:'其它1',name:'other1'  },
{ fieldLabel:'其它2',name:'other2'  },
{ fieldLabel:'其它3',name:'other3'  },
{ fieldLabel:'其它4',name:'other4'  },
{ fieldLabel:'图片',xtype:"labelfield",name:'pic',renderer:function(v){return v?("<a href='"+v+"' target='_blank'>"+v+"</a>"):"";}},
{ fieldLabel:'上传',name:'picFile',inputType:"file"  }),
{xtype:"textarea",fieldLabel:'简介',name:'intro',height:70 },
{xtype:"textarea",fieldLabel:'产品详情',name:'content',height:130 }
    ]
    });

        return formPanel;
    },
    createWin:function(){
        return this.initWin(638,500,"产品信息录入");
    },
    storeMapping:["id","name","sn","salePrice","costPrice","spec","model","color","pic","intro","content","other1","other2","other3","other4"    ,"dir","brand","unit"  ],
    initComponent : function(){
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
{header: "图片",hidden:true, sortable:true,width: 300, dataIndex:"pic" },
{header: "简介",hidden:true, sortable:true,width: 300, dataIndex:"intro" },
{header: "产品详情",hidden:true, sortable:true,width: 300, dataIndex:"content" },
{header: "其它属性1",hidden:true, sortable:true,width: 300, dataIndex:"other1" },
{header: "其它属性2",hidden:true, sortable:true,width: 300, dataIndex:"other2" },
{header: "其它属性3",hidden:true, sortable:true,width: 300, dataIndex:"other3" },
{header: "其它属性4",hidden:true, sortable:true,width: 300, dataIndex:"other4" }
        ]);
    ProductPanel.superclass.initComponent.call(this);
}     
});


SystemDictionaryPanel=Ext.extend(EasyJF.Ext.CrudPanel,{
    id:"systemdictionaryPanel",
    title:"字典列表",
    baseUrl:"systemDictionary.ejf",
    createForm:function(){
    var formPanel=new Ext.form.FormPanel({
        frame:true,
        labelWidth:60,
        labelAlign:'right',
        defaultType:'textfield',
                        defaults:{anchor:"-20"},
                        items:[{xtype:"hidden",name:"id"},
{fieldLabel:'编号',name:'sn'},
    {fieldLabel:'名称',name:'title'},
    {xtype:"textarea",fieldLabel:'简介',name:'intro',height:50}
    ]
    });
        return formPanel;
    },
    createWin:function(){
        return this.initWin(338,180,"字典信息录入");
    },
    storeMapping:["id","sn","title","intro"    ],
    initComponent : function(){
    this.cm=new Ext.grid.ColumnModel([
{header: "编号", sortable:true,width: 300, dataIndex:"sn"},
{header: "名称", sortable:true,width: 300, dataIndex:"title"},
{header: "简介", sortable:true,width: 300, dataIndex:"intro"}
        ]);
    SystemDictionaryPanel.superclass.initComponent.call(this);
}     
});

SystemDictionaryDetailPanel=Ext.extend(EasyJF.Ext.CrudPanel,{
    id:"systemDictionaryDetailPanel",
    title:"字典值管理",
    baseUrl:"systemDictionaryDetail.ejf",
    createForm:function(){
    var formPanel=new Ext.form.FormPanel({
        frame:true,
        labelWidth:60,
        labelAlign:'right',
        defaultType:'textfield',
                        defaults:{anchor:"-20"},
                        items:[{xtype:"hidden",name:"id"},
    {fieldLabel:'名称',name:'title'},
    {fieldLabel:'值',name:'tvalue'},
    EasyJF.Ext.Util.buildRemoteCombox("parent","所属字典","systemDictionary.ejf?cmd=list",["id","title"],"title","id"),
    {fieldLabel:'排序',name:'sequence'},
    {xtype:"textarea",fieldLabel:'说明',name:'intro'}
                        ]
    });

        return formPanel;
    },
    createWin:function(){
        return this.initWin(338,250,"字典值管理");
    },
    storeMapping:["id","title","tvalue","sequence","intro","parent" ],
    initComponent : function(){
    this.cm=new Ext.grid.ColumnModel([
{header: "名称", sortable:true,width: 100, dataIndex:"title"},
{header: "值", sortable:true,width: 100, dataIndex:"tvalue"},
{header: "所属字典", sortable:true,width: 100, dataIndex:"parent",renderer:this.objectRender("title")},
{header: "排序", sortable:true,width: 100, dataIndex:"sequence"},
{header: "简介", sortable:true,width: 300, dataIndex:"intro"}
        ]);
    SystemDictionaryDetailPanel.superclass.initComponent.call(this);
}     
});

SystemDictionaryManagePanel = Ext.extend(Ext.Panel, {
			border : false,
            closable: true,
			layout : "border",
			id : "systemDictionaryManagePanel",
			title : "数据字典管理",
			initComponent : function() {
				SystemDictionaryManagePanel.superclass.initComponent.call(this);
				this.parent = new SystemDictionaryPanel({
							border : false,
							region : "west",
							width : "50%"
						});
				this.detail = new SystemDictionaryDetailPanel({
							border : false,
							region : "center"
						});
				this.add(this.parent);
				this.add(this.detail);
				this.parent.grid.on("rowclick", function(grid, rowIndex) {
							var record = grid.store.getAt(rowIndex);
                            this.detail.store.removeAll();
							this.detail.store.reload({
										params : {
											parent : record.get("id")
										}
									});
						}, this);
			}
		})



ClientPanel=Ext.extend(EasyJF.Ext.CrudPanel,{
    id:"clientPanel",
    title:"客户管理",
    baseUrl:"client.ejf",
    statusData:[["普通",0],["热点",1],["签约",2],["丢单",-1]],
    createForm:function(){
    var formPanel=new Ext.form.FormPanel({
        frame:true,
        labelWidth:70,
        labelAlign:'right',
        defaultType:'textfield',
                        defaults:{anchor:"-20"},
                        items:[{xtype:"hidden",name:"id"},
                        EasyJF.Ext.Util.columnPanelBuild(
{ fieldLabel:'编号',name:'sn',allowBlank:false},
{ fieldLabel:'名称',name:'name',allowBlank:false},
{ fieldLabel:'简写',name:'shortName'  },
this.buildRemoteCombox("types","类别","systemDictionaryDetail.ejf?cmd=list&parentSn=ClientTypes",["id","title"],"title","id"),
this.buildRemoteCombox("source","来源","systemDictionaryDetail.ejf?cmd=list&parentSn=ClientSource",["id","title"],"title","id"),
this.buildRemoteCombox("trade","行业","systemDictionaryDetail.ejf?cmd=list&parentSn=Trade",["id","title"],"title","id"),
this.buildRemoteCombox("seller","业务员","employee.ejf?cmd=list",["id","name","trueName"],"trueName","id"),
{ fieldLabel:'邮编',name:'zip'  },
{ fieldLabel:'传真',name:'fax'  },
{ fieldLabel:'电话',name:'tel'  },
{ fieldLabel:'地址',name:'address'  },
{ fieldLabel:'联系人',name:'linkMan'  },
{ fieldLabel:'邮箱',name:'email'  },
{ fieldLabel:'主页',name:'homePage'  },
EasyJF.Ext.Util.buildCombox('status', '状态', this.statusData, 0)
),
{xtype:"textarea", fieldLabel:'简介',name:'intro',height:110  }
    ]
    });

        return formPanel;
    },
    createWin:function(){
        return this.initWin(668,400,"客户信息录入");
    },
    storeMapping:["id","sn","name","shortName","zip","fax","tel","address","linkMan","email","homePage","inputTime","intro","status"    ,"types","source","trade","seller","inputUser"  ],
    initComponent : function(){
    this.cm=new Ext.grid.ColumnModel([
{header: "编号", sortable:true,width: 300, dataIndex:"sn" },
{header: "名称", sortable:true,width: 300, dataIndex:"name" },
{header: "简写", sortable:true,width: 300, dataIndex:"shortName" },
{header: "邮编", sortable:true,width: 300, dataIndex:"zip" },
{header: "传真", sortable:true,width: 300, dataIndex:"fax" },
{header: "电话", sortable:true,width: 300, dataIndex:"tel" },
{header: "地址", sortable:true,width: 300, dataIndex:"address" },
{header: "类别", sortable:true,width: 300, dataIndex:"types",renderer:this.objectRender("title")},
{header: "来源", sortable:true,width: 300, dataIndex:"source",renderer:this.objectRender("title")},
{header: "行业", sortable:true,width: 300, dataIndex:"trade",renderer:this.objectRender("title")},
{header: "业务员", sortable:true,width: 300, dataIndex:"seller",renderer:this.objectRender("trueName")},
{header: "录入人", sortable:true,hidden:true,width: 300, dataIndex:"inputUser",renderer:this.objectRender("trueName")},
{header: "联系人", sortable:true,hidden:true,width: 300, dataIndex:"linkMan" },
{header: "电子邮件", sortable:true,hidden:true,width: 300, dataIndex:"email" },
{header: "主页", sortable:true,hidden:true,width: 300, dataIndex:"homePage" },
{header: "录入时间", sortable:true,hidden:true,width: 300, dataIndex:"inputTime"  ,renderer:this.dateRender() },
{header: "简介", sortable:true,hidden:true,width: 300, dataIndex:"intro" },
{header: "状态", sortable:true,width: 300, dataIndex:"status",renderer:this.typesRender(this.statusData)}

        ]);
    ClientPanel.superclass.initComponent.call(this);
}     
});

SupplierPanel=Ext.extend(EasyJF.Ext.CrudPanel,{
    id:"supplierPanel",
    title:"供应商管理",
    baseUrl:"supplier.ejf",
    statusData:[["普通",0],["热点",1],["签约",2],["丢单",-1]],
    createForm:function(){
    var formPanel=new Ext.form.FormPanel({
        frame:true,
        labelWidth:60,
        labelAlign:'right',
        defaultType:'textfield',
        defaults:{anchor:"-20"},
        items:[{xtype:"hidden",name:"id"},
         EasyJF.Ext.Util.columnPanelBuild(
         { fieldLabel:'编号',name:'sn',allowBlank:false},
{ fieldLabel:'名称',name:'name',allowBlank:false},
{ fieldLabel:'简写',name:'shortName'  },
this.buildRemoteCombox("types","类别","systemDictionaryDetail.ejf?cmd=list&parentSn=ClientTypes",["id","title"],"title","id"),
this.buildRemoteCombox("source","来源","systemDictionaryDetail.ejf?cmd=list&parentSn=ClientSource",["id","title"],"title","id"),
this.buildRemoteCombox("trade","行业","systemDictionaryDetail.ejf?cmd=list&parentSn=Trade",["id","title"],"title","id"),
this.buildRemoteCombox("seller","业务员","employee.ejf?cmd=list",["id","name","trueName"],"trueName","id"),
{ fieldLabel:'邮编',name:'zip'  },
{ fieldLabel:'传真',name:'fax'  },
{ fieldLabel:'电话',name:'tel'  },
{ fieldLabel:'地址',name:'address'  },
{ fieldLabel:'联系人',name:'linkMan'  },
{ fieldLabel:'邮箱',name:'email'  },
{ fieldLabel:'主页',name:'homePage'  },
 {xtype:"numberfield", fieldLabel:'质保金',name:'assureAmount'  },
 EasyJF.Ext.Util.buildCombox('status', '状态', this.statusData, 0)),
{ xtype:"textarea",fieldLabel:'简介',name:'intro',height:110}
    ]
    });

        return formPanel;
    },
    createWin:function(){
        return this.initWin(668,400,"供应商信息录入");
    },
    storeMapping:["id","assureAmount","sn","name","shortName","zip","fax","tel","address","linkMan","email","homePage","inputTime","intro","status"    ,"types","source","trade","seller","inputUser"   ],
    initComponent : function(){
    this.cm=new Ext.grid.ColumnModel([
{header: "编号", sortable:true,width: 300, dataIndex:"sn" },
{header: "名称", sortable:true,width: 300, dataIndex:"name" },
{header: "简写", sortable:true,width: 300, dataIndex:"shortName" },
{header: "邮编", sortable:true,width: 300, dataIndex:"zip" },
{header: "传真", sortable:true,width: 300, dataIndex:"fax" },
{header: "电话", sortable:true,width: 300, dataIndex:"tel" },
{header: "地址", sortable:true,width: 300, dataIndex:"address" },
{header: "类别", sortable:true,width: 300, dataIndex:"types",renderer:this.objectRender("title")},
{header: "来源", sortable:true,width: 300, dataIndex:"source",renderer:this.objectRender("title")},
{header: "行业", sortable:true,width: 300, dataIndex:"trade",renderer:this.objectRender("title")},
{header: "业务员", sortable:true,width: 300, dataIndex:"seller",renderer:this.objectRender("trueName")},
{header: "质保金", sortable:true,width: 300, dataIndex:"assureAmount" },
{header: "录入人", sortable:true,hidden:true,width: 300, dataIndex:"inputUser",renderer:this.objectRender("trueName")},
{header: "联系人", sortable:true,hidden:true,width: 300, dataIndex:"linkMan" },
{header: "电子邮件", sortable:true,hidden:true,width: 300, dataIndex:"email" },
{header: "主页", sortable:true,hidden:true,width: 300, dataIndex:"homePage" },
{header: "录入时间", sortable:true,hidden:true,width: 300, dataIndex:"inputTime"  ,renderer:this.dateRender() },
{header: "简介", sortable:true,hidden:true,width: 300, dataIndex:"intro" },
{header: "状态", sortable:true,width: 300, dataIndex:"status",renderer:this.typesRender(this.statusData)}
        ]);
    SupplierPanel.superclass.initComponent.call(this);
}     
});

ChangePasswordWindow = Ext.extend(Ext.Window, {
    width : 240,
    height : 160,
    title : "修改密码",
    buttonAlign : "center",
    layout : "fit",
    modal:true,
    save : function() {
        if(this.fp.form.findField("password").getValue()!=this.fp.form.findField("password1").getValue()){
            Ext.Msg.alert("提示","两次输入的新密码必须相同!");
            return;
        }
        this.fp.getForm().submit({
                    url : "employee.ejf?cmd=changePassword",
                    waitMsg : "正在保存",
                    waitTitle : "请稍候",
                    success : function(form, action) {
                        //登录成功，路转到主页
                        location.href='login.html';
                    },
                    failure : function(form, action) {
                        if (action.failureType == Ext.form.Action.SERVER_INVALID) {
                            Ext.Msg.alert("登录失败", action.result.errors?action.result.errors.msg:"未知原因");
                        }
                        form.findField("oldPassword").focus();
                    }
                });
    },
    reset : function() {
        this.close();
    },
    createFormPanel : function() {
        return new Ext.form.FormPanel({
                    labelWidth :70,
                    frame : true,
                    labelAlign : "right",
                    items : [{
                                xtype : "textfield",
                                name : "oldPassword",
                                inputType : "password",
                                fieldLabel : "旧密码",
                                allowBlank : false
                            }, {
                                xtype : "textfield",
                                name : "password",
                                inputType : "password",
                                fieldLabel : "新密码",
                                allowBlank : false
                            }, {
                                xtype : "textfield",
                                name : "password1",
                                inputType : "password",
                                fieldLabel : "确认新密码",
                                allowBlank : false
                            }]
                })
    },
    initComponent : function() {
        this.buttons = [{
                    text : "确定",
                    handler : this.save,
                    scope : this
                }, {
                    text : "取消",
                    handler : this.reset,
                    scope : this
                }];
        ChangePasswordWindow.superclass.initComponent.call(this);
        this.fp = this.createFormPanel();
        this.add(this.fp);
    }
})