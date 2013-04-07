/**
 * 基于Ext的添删改查封装
 */
Ext.namespace("EasyJF.Ext");
Ext.apply(Ext.form.ComboBox.prototype,{
    setValue : function(v){
        var value=text = v;
        if(this.valueField){
            var r = this.findRecord(this.valueField, v);
            if(r){
                text = r.data[this.displayField];
            }else if(Ext.isDefined(this.valueNotFoundText)){
                text = this.valueNotFoundText;
            }
        }
        if(text&&Ext.type(text)=="object"){
            if(this.displayField)text=text[this.displayField];
            if(this.valueField)value=value[this.valueField];
        }
        this.lastSelectionText = text;
        if(this.hiddenField){
            this.hiddenField.value = Ext.value(value, '');
        }
        Ext.form.ComboBox.superclass.setValue.call(this, text);
        this.value = value;
        return this;
    }
});

EasyJF.Ext.Util={
    formatUrl:function(cmd,baseUrl){
        //return   baseUrl+"?cmd="+cmd;
        return (baseUrl||this.baseUrl)+"_"+cmd+".action"
    },
    booleanRender : function(value, p, record) {
       return value ? "<span style=\"color:green;\">是</span>" : "<span style=\"color:red;\">否</span>";
    },
    dateRender : function(format) {
        format = format || "Y-m-d G:i";
        return Ext.util.Format.dateRenderer(format);
    },
    objectRender : function(p,backgroundColor) {
        return function(v, meta) {
            if(backgroundColor)meta.attr = 'style="background-color:'+backgroundColor+';"';
            var s="";
            try{
                s=v?eval("v." + p) : ""
            }
            catch(e){                
            }
            return s;
        }
    },
    linkRender:function(v){
        if(!v)return "";
        else return String.format("<a href='{0}' target='_blank'>{0}</a>",v);
    },
    comboxRender:function(v){       
        if(v){
            return v.text || v.title || v;  
        }
    },
    typesRender:function(types){
        return function(v){         
            for(var i=0;i<types.length;i++){
                try{
                    
                if(types[i][1]===v)return types[i][0];
                }
                catch(e){alert(types)}
            }
            return "";
        }
    },  
    moneyRender:function(v){
        if(v){
            if(v.toFixed){
            if(v<0)return "<font color=red>"+v.toFixed(2)+"<font>";
            else return v.toFixed(2);
            }
            else return v;
        }
    },
 // 参考LanyoRIA Framkework
	buildCombox : function(name, fieldLabel, data, defaultValue, allowBlank) {
		return {
			xtype : "combo",
			name : name,
			hiddenName : name,
			fieldLabel : fieldLabel,
			displayField : "title",
			valueField : "value",
			store : new Ext.data.SimpleStore({
						fields : ['title', 'value'],
						data : data
					}),
			editable : false,
			value : defaultValue,
			allowBlank : allowBlank,
			mode : 'local',
			triggerAction : 'all',
			emptyText : '请选择...'
		};
	},
    // 来自LanyoRIA Framkework
    /**
	 * 构造下拉选项的方法
	 * 
	 * @param {}
	 *            name 字段名称
	 * @param {}
	 *            fieldLabel 字段标签
	 * @param {}
	 *            url 获取数据的url
	 * @param {}
	 *            fields store中的属性列表
	 * @param {}
	 *            displayField 显示的属性
	 * @param {}
	 *            valueField 保存的属性
	 * @return {}
	 */
    buildRemoteCombox:function(name,fieldLabel,url,fields,displayField,valueField){
        var config = {xtype:"combo",
                 name:name,
                 hiddenName:name,
                 displayField:displayField?displayField:"title",
                 valueField:valueField?valueField:"id",
                 lazyRender:true,
                 triggerAction:"all",
                 typeAhead: true,
                 editable:false,
                 fieldLabel:fieldLabel
                };
        var storeConfig = {
            id:"id",
            url:url,        
            root:"result",
            totalProperty:"rowCount",
            remoteSort:true,    
            baseParams:{pageSize:"-1"}, 
            pageSize:"-1",
            fields:fields
        }               
        config.store=new Ext.data.JsonStore(storeConfig);
        return config;
    },
    //来自LanyoRIA Framkework
    /**
     * 用于构建下拉树录入项
     * @param {} name 字段名称
     * @param {} fieldLabel 标签
     * @param {} url 获取树节点的url
     * @param {} displayField 显示的字段
     * @param {} valueField 保存的字段
     * @param {} nodeParameter 加载子节点时传递的参数名称
     * @param {} rootText 跟节点的名称
     * @param {} hidenRootNode 是否隐藏根节点
     * @return {}
     */
    buildRemoteTreeCombox : function(name, fieldLabel, url, displayField,
			valueField, nodeParameter,rootText,hidenRootNode) {
		return {
			xtype : "treecombo",
			fieldLabel :fieldLabel,
			name : name,
			hiddenName : name,
            valueField:valueField,
            displayField:displayField,
            rootVisible:!hidenRootNode,
			tree : new Ext.tree.TreePanel({
						loader : new Ext.tree.TreeLoader({
									url : url,
									nodeParameter : nodeParameter||"node"
								}),
						root : new Ext.tree.AsyncTreeNode({
									text : rootText||"所有",
									id : "root",
									expanded : true
								})
					})
		}
	},
    // 参考LanyoRIA Framkework
    columnPanelBuild:function(){     
        var args = Array.prototype.slice.call(arguments,0);
        var formConfig = {};
        var obj={layout:'column',anchor:"100%",defaults:{border:false},items:[],xtype:"panel",border:false,bodyBorder:false};
        var max=2;
        if(typeof args[0]=="number"){
            max=args[0];
            args.shift();
        }
        if(args[0]){
            if(args[0].FC||args[0].formConfig){
                Ext.apply(formConfig,(args[0].FC||args[0].formConfig));
                args.shift();
            }
        }
        var cs=[];
        for(var i=0;i<max;i++)
                cs[i]=Ext.apply({
                columnWidth:1/max,
                layout:'form',
                defaultType:"textfield",
                defaults:{anchor:"-20"},
                items:[]
         },formConfig);
         for(var i=0;i<args.length;){
            for(var j=0;j<max;j++,i++){
                    cs[j].items[cs[j].items.length]=(i<args.length?args[i]:{xtype:"panel",border:false});   
            }
        }
        obj.items=cs;
        return obj;
    },
    // 参考LanyoRIA Framkework
    getEditGridData:function(editGrid,prefix,key,filter){
        function getV(v){
            if(v&&v.value!==undefined)v=v.value;// 根据value属性来得到
            else if(v&& v.id!==undefined)v=v.id;// 根据id属性来得到
            if(v && typeof v=="object" && v.clearTime)v=v.format("Y-m-d");
            return v;       
        }
        var o={};
        var mc=editGrid.getColumnModel().getColumnCount();
        for(var i=0;i<mc;i++){
            var n=editGrid.getColumnModel().getDataIndex(i);
            if(n)o[(prefix?prefix:"")+n]=[];
        }  
        var store=editGrid.store;
        var j=0;
        var numbererField=editGrid.getColumnModel().getColumnById("numberer")?editGrid.getColumnModel().getColumnById("numberer").dataIndex:"";
        for(var i=0;i<store.getCount();i++){
            if(key){// 如果指定了必填项，则要作必填项判断
                var v=store.getAt(i).get(key);
                v=getV(v);
                if(!v)continue;// 如果必填项没有值，则退出
                if(filter && !filter(store.getAt(i))) continue;
                if(typeof v=="object" && !String(v))continue;// 对Object类型进行处理
            }
            for(var n in o){                            
                var f=prefix?n.replace(prefix,""):n;
                if(f==numbererField)o[n][j]=j;// 处理自动排序字段
                else {
                    var v=store.getAt(i).get(f);                
                    v=getV(v);      
                    o[n][j]=(v!==null?v:"");
                }
            }
            j++;
        }       
        return j>0?o:{};
    },
    addGridRow:function(grid,obj,appendTo){
        var row=appendTo;
        if(row==undefined){
        var selModel=grid.getSelectionModel();
        var record =selModel.getSelectedCell?selModel.getSelectedCell():null;   
        row=grid.store.getCount()-1;    
        if(record){
            row=record[0];
        }
        }
        var create= grid.store.recordType;
        var obj=new create(Ext.apply({},obj||{}));   
        if(grid.stopEditing)grid.stopEditing();
        grid.store.insert(row+1,obj);
       
        grid.getView().refresh();
    },
    removeGridRow:function(grid,callback){   
        var record = grid.getSelectionModel().getSelectedCell();    
        if(record){
        var store=grid.store;
        Ext.MessageBox.confirm("请确认","确定要删除吗？",function(ret){
            if(ret=="yes"){
                var r=store.getAt(record[0]);
                if(callback) callback(r);
                store.remove(r);
                grid.getView().refresh();
                if(store.getCount()>0)grid.getSelectionModel().select(record[0]-1<0?0:record[0]-1,record[1]);
            }});
        }
        else Ext.MessageBox.alert("提示","请选择要删除的记录!");
    },
    getExportForm:function(){
            var exportForm=Ext.getCmp("global_export_form");
            if(!exportForm){
                exportForm=new Ext.form.FormPanel({fileUpload:true,hidden:true,items:{}});
                var fe=document.createElement("div");
                document.body.appendChild(fe);
                exportForm.render(fe);
            }
            return exportForm;
    }
}

/**
 * 通用查询控件
 * @class Ext.form.SearchField
 * @extends Ext.form.TwinTriggerField
 */
Ext.form.SearchField = Ext.extend(Ext.form.TwinTriggerField, {
    onSearch:Ext.emptyFn,
    initComponent : function(){
        Ext.form.SearchField.superclass.initComponent.call(this);
        this.addEvents(
            'onTrigger1Click',
            'onTrigger2Click',
            'search'
        );
        this.on('specialkey', function(f, e){
            if(e.getKey() == e.ENTER && !(e.shiftKey||e.ctrlKey||e.altKey)){
                this.onSearch(this,this.getValue(),e);
            }
        }, this);
        this.on("search",this.onSearch,this);
    },
    validationEvent:false,
    validateOnBlur:false,
    trigger1Class:'x-form-clear-trigger',
    trigger2Class:'x-form-search-trigger',
    width:180,
    hasSearch : false,
    paramName : 'query',
    onTrigger1Click : function(e){
        this.fireEvent("onTrigger1Click",this,this.getValue(),e);
    },
    onTrigger2Click : function(e){
       this.fireEvent("search",this,this.getValue(),e);
    }
});
/**
 * 用于CRUDPanel上的查询控件
 * @class EasyJF.Ext.CrudSearchField
 * @extends Ext.form.TwinTriggerField
 */
EasyJF.Ext.CrudSearchField = Ext.extend(Ext.form.TwinTriggerField, {
            initComponent : function() {
                if (!this.store.baseParams) {
                    this.store.baseParams = {};
                }
                EasyJF.Ext.CrudSearchField.superclass.initComponent.call(this);
                this.addEvents('beforeClick');
                this.on('specialkey', function(f, e) {
                            if (e.getKey() == e.ENTER) {
                                this.onTrigger2Click();
                            }
                        }, this);
            },
            validationEvent : false,
            validateOnBlur : false,
            emptyText : '内容关键字......',
            trigger1Class : 'x-form-clear-trigger',
            trigger2Class : 'x-form-search-trigger',
            hideTrigger1 : true,
            width : 180,
            hasSearch : false,
            paramName : 'searchKey',
            reset:function(){
                this.el.dom.value = '';
                this.triggers[0].hide();
                this.hasSearch = false;
                EasyJF.Ext.CrudSearchField.superclass.reset.call(this);
            },
            onTrigger1Click : function() {
                if(this.fireEvent('beforeClick',this)!==false){
                    if (this.hasSearch) {
                        this.store.baseParams={};
                        this.store.baseParams[this.paramName] = '';
                        this.store.removeAll();
                        this.store.reload();
                        this.el.dom.value = '';
                        this.triggers[0].hide();
                        this.hasSearch = false;
                        this.focus();
                    }
                }
            },
            onTrigger2Click : function() {
                if(this.fireEvent('beforeClick',this)!==false){
                    var v = this.getRawValue();
                    if (v.length < 1)return this.onTrigger1Click();
                    this.store.removeAll();
                    this.store.baseParams = {};
                    this.store.baseParams[this.paramName] = v;
                    var o = {start : 0,searchType:'simple'};
                    this.store.reload({
                        params : o,
                        callback:function(rs){
                            if(!rs || rs.length<1){
                                Ext.Msg.alert("提示","没有找到符合条件的记录！");
                            }
                        }
                    });
                    this.hasSearch = true;
                    this.triggers[0].show();
                    this.focus();
                }
            }
        });

EasyJF.Ext.CrudPanel=Ext.extend(Ext.Panel,{
	closable: true,
  	autoScroll:true,
  	layout:"fit",
    pageSize:10,
    showSearchField:true,
  	gridViewConfig:{}, 
    showExportData:false,
    showImportData:false,
    entityIdName:"id",
    refresh:function(){
    	this.store.removeAll();
   		this.store.reload();
    },    
    initWin:function(width,height,title){
    	var win=new Ext.Window({
			width:width,
			height:height,
			buttonAlign:"center",
			title:title,
			modal:true,
			shadow:true,
			closeAction:"hide",
            layout:"fit",
			items:[this.fp],
			buttons:[{text:"保存",
					  handler:this.save,
                      cls : "x-btn-text-icon",
                icon : "images/icons/save.png",
					  scope:this},
					  {text:"重置",
					   handler:this.reset,
                       cls : "x-btn-text-icon",
                icon : "images/icons/reset.png",
					   scope:this},
					  {text:"取消",
					   handler:this.closeWin,
                       cls : "x-btn-text-icon",
                icon : "images/icons/cancel.png",
					   scope:this}
					   	]					  
		});
		return win;
    },
    showWin:function(){	
		if(!this.win){
			if(!this.fp){
				this.fp=this.createForm();
			}
		this.win=this.createWin();
		this.win.on("close",function(){this.win=null;this.fp=null;},this);
		}
		this.win.show();
	},
	create:function(){
		this.showWin();
		this.reset();
	},
	save:function(){
		var idField=this.fp.form.findField(this.entityIdName);
        if(!idField){
            Ext.Msg.alert("系统错误","请在表单中设置名称为"+this.entityIdName+"的ID项!");
            return;
        }
        var id=idField.getValue();		
        var url = this.baseUrl;
        if (this.fp.form.fileUpload) {
                var cmd = this.fp.form.findField("cmd");
                if (cmd == null) {
                    cmd = new Ext.form.Hidden({
                        name : "cmd"
                    });
                    this.fp.add(cmd);
                    this.fp.doLayout();
                }
                cmd.setValue((id ? "update" : "save"));
            } else {
                url =this.formatUrl(id ? "update" : "save");
            }
		this.fp.form.submit({
				waitMsg:'正在保存。。。',
	            url:url,
	            method:'POST',
	            success:function(){
	           	this.closeWin();
	           	this.store.reload();          	
	            },
	            scope:this
		});	
	},
	reset : function() {
		if (this.win)
			this.fp.form.reset();
	},
	closeWin:function(){
		if(this.win)this.win.close();
		this.win=null;
	},
	edit:function(){
		var record=this.grid.getSelectionModel().getSelected();
		if(!record){
			Ext.Msg.alert("提示","请先选择要编辑的行!");
			return;
		}
	    var id=record.get(this.entityIdName);
	    this.showWin();
	    this.fp.form.loadRecord(record); 
        this.fp.form.items.each(function(f){
            f.originalValue=f.getValue();
        });
	},	
	removeData:function(){
			var record=this.grid.getSelectionModel().getSelected();
			if(!record){
				Ext.Msg.alert("提示","请先选择要编辑的行!");
				return;
			}
			var m=Ext.MessageBox.confirm("删除提示","是否真的要删除数据？",function(ret){
			if(ret=="yes"){	
              var params={};
              params[this.entityIdName]=record.get(this.entityIdName);
			  Ext.Ajax.request({
	            url:this.formatUrl('remove'),
	            params:params,
	            method:'POST',
	            success:function(response){
	            Ext.Msg.alert("提示信息","成功删除数据!",function(){
                this.store.removeAll();
	            this.store.reload();	
	            },this);
	            },
	            scope:this
			  });
			}},this);
	},
    /**
     * 批量导入数据
     */
    importDatas:function(){
            if(!EasyJF.Ext.ImportPanel){
                EasyJF.Ext.ImportPanel=new Ext.form.FormPanel({
                        id:"crudExportPanel",
                        fileUpload:true,
                        items:[{xtype:"fieldset",title:"选择数据文件",autoHeight:true,items:{xtype:"textfield",hideLabel:true,inputType:"file",name:"file",anchor:"100%"}},
                        {xtype:"fieldset",title:"导入说明",html:"",height:60}
                    ]});
            }
            var win=new Ext.Window({
                width:300,
                height:210,
                title:"导入数据",
                modal:true,
                layout:"fit",
                frame:true,
                items:EasyJF.Ext.ImportPanel,
                buttons:[{text:"确定",handler:function(){
                var form=EasyJF.Ext.ImportPanel.form;           
                if(form.findField("file").getValue().length<2){
                    Ext.Msg.alert("提示","你没有选择要导入的文件！");
                    return ;
                }
                EasyJF.Ext.ImportPanel.form.submit({
                    url:this.baseUrl,
                    params:{cmd:"import"},
                    waitMsg:"请稍候，正在导入数据",
                    success:function(){
                        Ext.Msg.alert("提示","数据导入成功!",function(){form.findField("file").reset();win.close();this.store.reload();},this)
                    },
                    failure:function(){
                        this.alert("数据导入出错，请检测所选择的文件格式是否正确?","提示信息");
                    },
                    scope:this
                })
                },scope:this},{text:"取消",handler:function(){
                win.close();
                },scope:this}]
            });
            win.show();
        },
     /**
         * 批量导出数据
         */
      exportDatas : function() {
            var params = this.store.baseParams;
            Ext.apply(params, {
                        searchKey : this.searchField?this.searchField.getValue():""
                    });
            var exportForm=EasyJF.Ext.Util.getExportForm();
            exportForm.form.submit({
                    url:this.baseUrl,
                    params:Ext.apply({cmd:"export"},this.store.baseParams)
            });
        },
    initComponent : function(){   
       this.store=new Ext.data.JsonStore({
		id:"id",
       	url: this.formatUrl('list'),
       	root:"result",
  		totalProperty:"rowCount",
  		remoteSort:true,  		
  		fields:this.storeMapping});
  		this.store.baseParams=Ext.apply({},{limit:this.pageSize||""},{});
        if(this.baseQueryParameter){
            this.store.on('beforeload',function(store,options){
                Ext.apply(store.baseParams,this.baseQueryParameter);
            },this);
        }
      	this.store.paramNames.sort="orderBy";
	 	this.store.paramNames.dir="orderType";	  
      	this.cm.defaultSortable=true;   	  	
        EasyJF.Ext.CrudPanel.superclass.initComponent.call(this);
        var viewConfig=Ext.apply({forceFit:true},this.gridViewConfig);  
        this.searchField=new EasyJF.Ext.CrudSearchField({hidden:!this.showSearchField,width:this.searchFieldWidth||100,store:this.store});
        var tbar=[
             {    
                text: '添加',  
                handler: this.create,
                cls : "x-btn-text-icon",
                icon : "images/icons/add.png",
                scope:this
            },
            {    
                text: '修改', 
                handler: this.edit,
                cls : "x-btn-text-icon",
                icon : "images/icons/edit.png",
                scope:this
            },
            {    
                text: '删除', 
                handler: this.removeData,
                cls : "x-btn-text-icon",
                icon : "images/icons/delete.png",
                scope:this
            },
             {    
                text: '刷新',  
                handler: this.refresh,
                cls : "x-btn-text-icon",
                icon : "images/icons/f5.png",
                scope:this
            },
            {    
                text: '导出',  
                handler: this.exportDatas,
                cls : "x-btn-text-icon",
                icon : "images/icons/excel.png",
                hidden:!this.showExportData,
                scope:this
            },
            {    
                text: '导入',  
                handler: this.importDatas,
                cls : "x-btn-text-icon",
                icon : "images/icons/import.png",
                hidden:!this.showImportData,
                scope:this
            }];
            
        if(this.gridButtons)tbar=tbar.concat(this.gridButtons);
        tbar=tbar.concat(["->",'查询: ',this.searchField]);
        this.grid=new Ext.grid.GridPanel({
        store: this.store,
        cm: this.cm,
        trackMouseOver:false,    
        loadMask: true,
        region:"center",
        viewConfig:viewConfig,
        tbar:tbar,
        bbar: new Ext.PagingToolbar({
            pageSize: this.pageSize,
            store: this.store,
            displayInfo: true,
            displayMsg: '记录 {0} - {1} of {2}',
            emptyMsg: "没有数据记录"
        })
   		});   		   		
   		this.grid.on("celldblclick",this.edit,this);       
   		this.add(this.grid);
   		this.store.load();
        }
});
Ext.apply(EasyJF.Ext.CrudPanel.prototype,EasyJF.Ext.Util);

EasyJF.Ext.CrudTreePanel=Ext.extend(EasyJF.Ext.CrudPanel,{
    nodeParameter:"parent",
    treeTitle:"树",
    treeRegion:"west",
    treeWidth:180,
    rootTitle:"全部",
    layout:"border",
    save:function(){
        var id=this.fp.form.findField(this.entityIdName).getValue();     
        this.fp.form.submit({
                waitMsg:'正在保存。。。',
                url:this.formatUrl(id?"update":"save"),
                method:'POST',
                success:function(){
                this.closeWin();
                this.store.reload();  
                if(this.tree)this.tree.root.reload();
                },
                scope:this
        }); 
    },
    initComponent:function(){
        EasyJF.Ext.CrudTreePanel.superclass.initComponent.call(this);
        this.tree=new Ext.tree.TreePanel({
                            region : this.treeRegion,
                            width : this.treeWidth,
                            title : this.treeTitle,
                            border : false,
                            split:true,
                            loader : new Ext.tree.TreeLoader({
                                        url : this.formatUrl("getTree"),
                                        nodeParameter:this.nodeParameter
                                    }),
                            root : new Ext.tree.AsyncTreeNode({
                                        text : this.rootTitle,
                                        id : "root",
                                        expanded : true
                                    }),
                            listeners:{click:function(node){
                                this.store.removeAll();
                                var ps={};
                                ps[this.nodeParameter]=(node.id=="root"?"":node.id);
                                this.store.reload({params:ps});
                            },scope:this}
                        });
      this.add(this.tree);
    }
})
    

EasyJF.Ext.BaseGridList = Ext.extend(Ext.Panel, {
            layout : "fit",
            loadData : false,
            pageSize : 10,
            closable : true,
            autoScroll : true,
            pagingToolbar : true,           
            gridForceFit : true,// 强制表格自动适应
            gridViewConfig : {},
            gridConfig : {},// 表格的自定义配置
            storeConfig:{},
            storeType:Ext.data.JsonStore,
            refresh : function() {
                this.store.removeAll();
                this.store.reload({callback:function(rs){
                    if(rs && rs.length<1){Ext.Msg.alert("提示","没有符合条件的数据！");EasyJF.Ext.Util.autoCloseMsg.defer(2000);}
                    }
                });
            },
            showContextMenu : function(g, i, e) {
                var evn = e ? e : g;
                evn.preventDefault();
                if (i) {
                    this.grid.getSelectionModel().selectRow(i, false);
                }
                this.menu.showAt(evn.getPoint());
            },  
            doOperate:function(grid,rowIndex,columnIndex,e){
                var tag = e.getTarget("A", 3);
                if (tag) {
                    var id = tag.getAttribute("theid");
                    var cmd = tag.getAttribute("op");
                    var cf=tag.getAttribute("cf");
                    if (id && cmd && this.operate)
                        this.operate(cmd, id, cf, grid, rowIndex, columnIndex, e);
                }
            },
            initComponent : function() {
                EasyJF.Ext.BaseGridList.superclass.initComponent.call(this);
                if(!this.store){
                    this.store = new this.storeType(Ext.apply({
                        id : "id",
                        url : this.url,
                        root : "result",
                        totalProperty : "rowCount",
                        remoteSort : true,
                        fields : this.storeMapping
                    },this.storeConfig));
                }
                if(this.baseQueryParameter&&Ext.objPcount(this.baseQueryParameter)){
                        this.store.on('beforeload',function(store,options){
                            Ext.apply(store.baseParams,this.baseQueryParameter);
                        },this);
                    }
                this.store.paramNames.sort = "orderBy";
                this.store.paramNames.dir = "orderType";
                this.cm.defaultSortable = true;             
                var viewConfig = Ext.apply({
                            forceFit : this.gridForceFit
                        }, this.gridViewConfig);
                var gridConfig=Ext.apply({},{                   
                    store : this.store,
                    cm : this.cm,
                    trackMouseOver : false,
                    loadMask : true,
                    viewConfig : viewConfig,
                    bbar : this.pagingToolbar ? new Ext.PagingToolbar({
                        pageSize: this.pageSize,
			            store: this.store,
			            displayInfo: true,
			            displayMsg: '记录 {0} - {1} of {2}',
			            emptyMsg: "没有数据记录"
                    }) : null
                });
               
                Ext.apply(gridConfig,this.gridConfig);
               
                if(this.gridTbar)gridConfig.tbar=this.gridTbar;
                if(this.gridBorder===false||this.gridBorder)gridConfig.border=this.gridBorder;
                this.grid=new Ext.grid.GridPanel(gridConfig);
                this.add(this.grid);
                if (this.loadData)
                    this.store.load();
            }
        });
Ext.apply(EasyJF.Ext.BaseGridList.prototype,EasyJF.Ext.Util);        


//下面的代码参考自LanyoRIA Framework,主要用于处理对象选择
EasyJF.Ext.ObjectSelectWin=Ext.extend(Ext.Window,{
    width:700,
    height:500,
    layout:"fit",
    closeAction:"hide",
    buttonAlign:"center",
    choiceObject:function(){
        var sel=this.list.grid.getSelectionModel();
        var record=sel.getSelected();
        if(!record){
            Ext.Msg.alert("提示","请选择一条记录!");
            return;
        }
        this.fireEvent("choice",record.data,this);
        this.hide();
    },
    initComponent:function(){
         this.buttons = [{
                            text : "确定",
                            handler : this.choiceObject,
                            scope : this
                        }, {
                            text : "取消",
                            handler : function() {
                                this.hide();
                            },
                            scope : this
                        }];
        EasyJF.Ext.ObjectSelectWin.superclass.initComponent.call(this);                
        if(!this.gridListClz){
            Ext.Msg.alert("error","使用ObjectSelectWin必须设置gridListClz属性!");
            return;
        }
        else {
        this.list=eval("new "+this.gridListClz);
        this.add(this.list);
        this.list.grid.on("rowdblclick",this.choiceObject,this);
        }
        this.addEvents("choice");
    }
})

EasyJF.Ext.ObjectSearchField = Ext.extend(Ext.form.SearchField,{
    hideTrigger1:true,
   // searchUrl:"product.ejf?cmd=list&pageSize=15",
    paramName:'searchKey',
    choiceValue : Ext.emptyFn(),
    blank:Ext.emptyFn,
    noneProductExistMsg:"该编号没有货品对应",
    beforeDestroy:function(){
        EasyJF.Ext.ObjectSearchField.superclass.beforeDestroy.call(this);
        if(this.searchWin){
            this.searchWin.destroy();
            delete this.searcheWin; 
        }
    },
    selectProvider : function(data, win) {
        if (this.choiceValue)
            this.choiceValue(data,win);
    },
    getSearchWin:function(){
        if(!this.searchWin){
            if(!this.gridListClz){
                Ext.Msg.alert("提示","使用ObjectSearchField类必须设置gridListClz属性，否则将不能正常工作!");
            }
            this.searchWin = new EasyJF.Ext.ObjectSelectWin({gridListClz:this.gridListClz});
            this.searchWin.on("choice",this.selectProvider,this) ;
        }
        return this.searchWin;
    },
    getSearchGrid:function(){
        return this.getSearchWin().list.grid;
    },
    bindData:function(data,params){
        if(Ext.isArray(data)){
            data = {
                result:data,
                rowCount:data.length
            }
        };
        var searchWin = this.getSearchWin();
        var grid = this.getSearchGrid();
        grid.getStore().removeAll();
        searchWin.show();
        if(data){
            grid.getStore().loadData(data);
        }else{
            grid.getStore().load();
        }
        (function(){
             grid.getSelectionModel().clearSelections();
             grid.getView().focusRow(0);
        }).defer(100,this);
        grid.getStore().baseParams = params;
    },
    getParams:function(){
        var p={};
        p[this.paramName||"searchKey"]=this.getValue();
        return p;
    },
    onSearch:function(field,value,e){
        if(!this.searchUrl){
            Ext.Msg.alert("提示","必须设置searchUrl属性来查询产品信息!");
            return;
        }
        if(this.isAjaxLoad)return false;
        var params = this.getParams(field);//获得查询条件
        if(params===false)return false;
        this.isAjaxLoad=true;
        Ext.Ajax.request({url:this.searchUrl,params:params,success:function(response){
            var obj=Ext.decode(response.responseText);
            if(Ext.isArray(obj)){
                obj = {
                    rowCount:obj.length,
                    result:obj
                }
            }
            if(obj && obj.rowCount==1){
                  this.selectProvider(obj.result[0]);
             }else if(obj && obj.rowCount >1){
                  this.bindData(obj,params);
            }else{
               Ext.Msg.alert("提示信息","所要查询的产品不存在",this.blank.createDelegate(this,[this]));
            }
             this.isAjaxLoad=false;
        },scope:this});
    }
});
/**
 * 产品单据基础类，可以用轻松实现各种产品性质单据,适合用于主从表的Crud应用
 * @class EasyJF.Ext.BaseProductBillPanel
 * @extends EasyJF.Ext.CrudPanel
 */
EasyJF.Ext.CrudMainDetailPanel=Ext.extend(EasyJF.Ext.CrudPanel,{
    //子类可以替换该方法
    autoCountData:function(record){
        var num=record.get("num")||0,price=record.get("price")||0;
        record.set("amount",num*price);
    },
    onAfterEdit:function(e){
        if(e.field=="num"||e.field=="price"){
            this.autoCountData(e.record);
        }
    },
    autoAddLastRow:function(){
        if(this.editGrid.store.getCount()<1 || (this.editGrid.store.getAt(this.editGrid.store.getCount()-1).get("product") && this.editGrid.store.getAt(this.editGrid.store.getCount()-1).get("product").id)){
           this.addRow();
        }
    },
    loadItems:function(record,grid){
        if(!grid)grid=this.editGrid;
        grid.store.removeAll();
        if(!record.get("items")){
           //远程加载
            Ext.Msg.wait("请稍候...","正在加载订单详情");
            Ext.Ajax.request({
                        url : this.baseUrl + "?cmd=view",
                        params : {
                            id : record.get("id")
                        },
                        success:function(response){
                            var ret=Ext.decode(response.responseText);
                            if(ret.items){
                            record.set("items",ret.items);
                            grid.store.loadData({result:record.get("items")});
                            }
                            Ext.Msg.hide();
                        }
                    });
        }
        else {
           grid.store.loadData({result:record.get("items")}); 
        }
    },
    createEditGrid : function() {
        this.createColumnEditor();
        var colM=this.getEditGridColumn();
        this.editGrid=new Ext.grid.EditorGridPanel({
                            store : new Ext.data.JsonStore({
                                        root : "result",
                                        totalProperty : "rowCount",
                                        fields : this.editStoreMapping
                                    }),
                            cm : colM,
                            clicksToEdit:1,
                            trackMouseOver : false,
                            loadMask : true,
                            autoExpandColumn:colM.getColumnCount()-1,
                            plugins:[new Ext.ux.grid.GridSummary()],
                            bbar:[{text:"添加"+(this.detailItemName||"货品"),handler:this.addRow,scope:this},{text:"删除"+(this.detailItemName||"货品"),handler:this.removeRow,scope:this}],
                            listeners:{
                                afteredit:function(e){
                                    this.onAfterEdit(e);
                                },
                                scope:this
                            }
                        });
            return this.editGrid;
            },
    addRow:function(){
       EasyJF.Ext.Util.addGridRow(this.editGrid,{product:{sn:null}});
    },
    removeRow:function(){
       EasyJF.Ext.Util.removeGridRow(this.editGrid);
    },
    edit:function(){
        EasyJF.Ext.CrudMainDetailPanel.superclass.edit.call(this);
        var record=this.grid.getSelectionModel().getSelected();
        if(record){
            this.loadItems(record);
        }
    },
    create:function(){
        EasyJF.Ext.CrudMainDetailPanel.superclass.create.call(this);
        this.editGrid.store.removeAll();
        this.autoAddLastRow();
    },
    save:function(){
        var o=EasyJF.Ext.Util.getEditGridData(this.editGrid,"item_",this.detailItemKey||"product");
        this.fp.form.baseParams=o;
        EasyJF.Ext.CrudMainDetailPanel.superclass.save.call(this);
    },
    createWin:function(){
        return this.initWin(Ext.getBody().getViewSize().width-50,Ext.getBody().getViewSize().height-50,this.billName||"订单信息录入");
    },
    objectPropertyRender:function(objName,propertyName){
      return function(v,m,r){
        var p=r.get(objName);
        if(p&&p.id){
            return p[propertyName];
        }
      }
    },
    //以下两个方法子类可以根据情况重新定义
    //当选中一个对象时执行，默认为对产品信息进行处理
    onSelectObject:function(data){
        var cell=this.editGrid.getSelectionModel().getSelectedCell();
        if(cell){
            var record=this.editGrid.store.getAt(cell[0]);
            record.set((this.detailItemKey||"product"),data);
            record.set("productSn",data.sn);
            record.set("price",data.salePrice);
            this.autoCountData(record);
        }
        this.autoAddLastRow();
    },
    editStoreMapping:["id","product",{name:"productSn",mapping:"product.sn"},"price","num","amount","remark"],
    //以下方法子类必须重新定义，定义表单输入项，这里作为参考
    createColumnEditor:function(){
        Ext.Msg.alert("提示","子类必须自定义createColumnEditor方法，可参考core.js中的定义!");
        //下面的代码仅提供给子类中参考
        /*var service=this;
        this.productEditor=new EasyJF.Ext.ObjectSearchField({
            gridListClz:"ProductGridList",
            searchUrl:"product.ejf?cmd=list&pageSize=15",
            choiceValue:function(data){
                service.onSelectObject(data);
            }
        });*/
    },
    getEditGridColumn : function() {
                Ext.Msg.alert("提示","子类必须自定义getEditGridColumn方法，可参考core.js中的定义!");
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
            }
});

EasyJF.Ext.LabelField = Ext.extend(Ext.form.Field, {
    defaultAutoCreate: {tag: 'div'},
    fieldClass:"x-form-item-label",
    style:"padding-top:3px",
    onRender : function(ct, position){
      EasyJF.Ext.LabelField.superclass.onRender.call(this, ct, position);
      this.el.dom.name=this.initialConfig.name;
      this.el.dom.value='';      
    },  
    setValue : function(v){
       EasyJF.Ext.LabelField.superclass.setValue.call(this,v);  
       var t=v;    
       if(this.renderer)t=this.renderer(v);
       if(typeof t == window.undefined)t= '';
       this.el.update(t);
    },
    markInvalid : Ext.emptyFn,
    clearInvalid : Ext.emptyFn
});
Ext.reg('labelfield', EasyJF.Ext.LabelField);
/**
 * 树状下拉框
 */
EasyJF.Ext.TreeComboField = Ext.extend(Ext.form.TriggerField, {
            valueField : "id",
            displayField : "name",
            minListWidth : 70,
            haveShow : false,
            editable : false,
            returnObject : false,
            leafOnly : false,
            clicksFinishEdit : 1,
            readOnly:false,
            hiddenNodes:[],
            initEvents : function(){
            EasyJF.Ext.TreeComboField.superclass.initEvents.call(this);
                this.keyNav = new Ext.KeyNav(this.el, {
                  "up" : function(e){
                    this.inKeyMode = true;
                    this.selectPrevious();
                },
    
                "down" : function(e){
                    if(!this.isExpanded()){
                        this.onTriggerClick();
                    }else{
                        this.inKeyMode = true;
                        this.selectNext();
                    }
                },
                "enter" : function(e){
                    var sm = this.tree.getSelectionModel();
                    if(sm.getSelectedNode()){
                        var node = sm.getSelectedNode();
                        this.choice(node);
                        sm.clearSelections();
                        return ;
                    }
                },
                "esc" : function(e){
                    this.collapse();
                },
            scope : this
        });
        this.queryDelay = Math.max(this.queryDelay || 10,
                this.mode == 'local' ? 10 : 250);
        this.dqTask = new Ext.util.DelayedTask(this.initQuery, this);
        if(this.typeAhead){
            this.taTask = new Ext.util.DelayedTask(this.onTypeAhead, this);
        }
        if(this.editable !== false){
            this.el.on("keyup", this.onKeyUp, this);
        }
        if(this.forceSelection){
            this.on('blur', this.doForce, this);
        }
        },
        selectPrevious:function(){
                var sm = this.tree.getSelectionModel();
                if(!sm.selectPrevious()){
                    var root = this.tree.getRootNode();
                    sm.select(root);
                    this.el.focus();
                }else{
                    this.el.focus();
                }
            },
            selectNext : function(){
                var sm = this.tree.getSelectionModel();
                if(!sm.selectNext()){
                    var root = this.tree.getRootNode();
                    sm.select(root);
                    this.el.focus();
                }else{
                    this.el.focus();
                }
            },
            onTriggerClick : function() {
                if(this.readOnly || this.disabled){
                    return false;
                }else if (!this.tree.rendered || !this.list) {
                    this.treeId = Ext.id();
                    this.list = new Ext.Layer({
                        id : this.treeId,
                        cls : "x-combo-list",
                        constrain:false
                    });                 
                    if(!this.innerDom)this.innerDom=Ext.getBody().dom;  
                    if(this.tree.rendered){
                        this.list.appendChild(this.tree.el);
                    }
                    else {                                                  
                        this.tree.render(this.treeId);
                        var lw = this.listWidth || Math.max(this.wrap.getWidth(), this.minListWidth);                                   
                        this.tree.setWidth(lw);
                        this.tree.on("expandnode",this.restrictHeight,this);
                        this.tree.on("collapsenode",this.restrictHeight,this);
                    }
                }
                else this.restrictHeight();
                this.expand();
            },
            restrictHeight : function(){
                // this.list.dom.style.height = '';
                if(!this.list)return;
                var inner = this.innerDom;                                  
                var h=inner.clientHeight-this.wrap.getBottom();                 
                if(this.tree.el.dom.offsetHeight>=h){
                    this.tree.setHeight(h);
                }
                else {
                    this.tree.setHeight("auto");
                }
               // this.list.alignTo(this.getEl(), "tl-bl?");
            },
           
            filterTree:function(e){
                if(!this.isExpanded())this.expand();
                var text = e.target.value;
                Ext.each(this.hiddenNodes, function(n){
                    n.ui.show();
                });
                if(!text){
                    this.filter.clear();
                    return;
                }
                this.tree.expandAll();
                this.restrictHeight();
                this.filter.filterBy(function(n){
                    return (!n.attributes.leaf || n.text.indexOf(text)>=0);
                });
                
                // hide empty packages that weren't filtered
                this.hiddenNodes = [];
                this.tree.root.cascade(function(n){
                    if(!n.attributes.leaf && n.ui.ctNode.offsetHeight < 3){
                        n.ui.hide();
                        this.hiddenNodes.push(n);
                    }
                },this);
            },
            expand :function(){
                if(this.list){
                    Ext.getDoc().on('mousedown', this.hideIf, this);
                    this.list.show();
                    this.list.alignTo(this.getEl(), "tl-bl?");
                }
                else {
                    this.onTriggerClick();
                }
            },
            collapse : function(){
                 if(this.list){
                 this.list.hide();
                 Ext.getDoc().un('mousedown', this.hideIf, this);
                 }
            },
            onEnable: function(){
                EasyJF.Ext.TreeComboField.superclass.onEnable.apply(this, arguments);
                if(this.hiddenField){
                    this.hiddenField.disabled = false;
                }
            },
            onDisable: function(){
                EasyJF.Ext.TreeComboField.superclass.onDisable.apply(this, arguments);
                if(this.hiddenField){
                    this.hiddenField.disabled = true;
                }
                Ext.getDoc().un('mousedown',this.hideIf,this);
            },
            hideIf:function(e){
                if(!e.within(this.wrap) && !e.within(this.list)){
                   this.collapse();
                }
            },
            initComponent : function() {
                EasyJF.Ext.TreeComboField.superclass.initComponent.call(this);
                this.addEvents('beforeSetValue');
                this.filter = new Ext.tree.TreeFilter(this.tree, {
                    clearBlank: true,
                    autoClear: true
                });
            },
            onRender : function(ct, position) {
                EasyJF.Ext.TreeComboField.superclass.onRender.call(this, ct,
                        position);
                if (this.clicksFinishEdit > 1)
                    this.tree.on("dblclick", this.choice, this);
                else
                    this.tree.on("click", this.choice, this);
                if (this.hiddenName) {
                    this.hiddenField = this.el.insertSibling({
                                tag : 'input',
                                type : 'hidden',
                                name : this.hiddenName,
                                id : (this.hiddenId || this.hiddenName)
                            }, 'before', true);
                    this.hiddenField.value = this.hiddenValue !== undefined
                            ? this.hiddenValue
                            : this.value !== undefined ? this.value : '';
                    this.el.dom.removeAttribute('name');
                }
                if (!this.editable) {
                    this.editable = true;
                    this.setEditable(false);
                }
                else {
                    this.el.on('keydown', this.filterTree, this, {buffer: 350});
                }
            },
            getValue : function(returnObject) {
                if ((returnObject===true) || this.returnObject)
                    return typeof this.value != 'undefined' ? {
                        value : this.value,
                        text : this.text,
                        toString : function() {
                            return this.text;
                        }
                    } : "";
                return typeof this.value != 'undefined' ? this.value : '';
            },
            clearValue : function() {
                if (this.hiddenField) {
                    this.hiddenField.value = '';
                }
                this.setRawValue('');
                this.lastSelectionText = '';
                this.applyEmptyText();
                this.value="";
            },
validate : function(){
                if(this.disabled || this.validateValue(this.processValue(this.getValue()))){
                    this.clearInvalid();
                    return true;
                }
                return false;
            },
            isValid : function(preventMark){
                if(this.disabled){
                    return true;
                }
                var restore = this.preventMark;
                this.preventMark = preventMark === true;
                var v = this.validateValue(this.processValue(this.getValue()));
                this.preventMark = restore;
                return v;
            },
             /**
                 * Validates a value according to the field's validation rules
                 * and marks the field as invalid if the validation fails
                 * 
                 * @param {Mixed}
                 *            value The value to validate
                 * @return {Boolean} True if the value is valid, else false
                 */
            validateValue : function(value){
                if(value.length < 1 || value === null){ // if it's blank
                     if(this.allowBlank){
                         this.clearInvalid();
                         return true;
                     }else{
                         this.markInvalid(this.blankText);
                         return false;
                     }
                }
                if(value.length < this.minLength){
                    this.markInvalid(String.format(this.minLengthText, this.minLength));
                    return false;
                }
                if(value.length > this.maxLength){
                    this.markInvalid(String.format(this.maxLengthText, this.maxLength));
                    return false;
                }
                if(this.vtype){
                    var vt = Ext.form.VTypes;
                    if(!vt[this.vtype](value, this)){
                        this.markInvalid(this.vtypeText || vt[this.vtype +'Text']);
                        return false;
                    }
                }
                if(typeof this.validator == "function"){
                    var msg = this.validator(value);
                    if(msg !== true){
                        this.markInvalid(msg);
                        return false;
                    }
                }
                if(this.regex && !this.regex.test(value)){
                    this.markInvalid(this.regexText);
                    return false;
                }
                return true;
            },
            readPropertyValue : function(obj, p) {
                var v = null;
                for (var o in obj) {
                    if (o == p)return true;
                        // v = obj[o];
                }
                return v;
            },
            setValue : function(obj) {
                if (!obj) {
                    this.clearValue();
                    return;
                }
                if(this.fireEvent('beforeSetValue',this,obj)===false){return;}
                var v = obj;
                var text = v;
                var value = this.valueField || this.displayField;
                if (typeof v == "object" && this.readPropertyValue(obj, value)) {
                    text = obj[this.displayField || this.valueField];
                    v = obj[value];
                }
                
                var node = this.tree.getNodeById(v);
                if (node) {
                    text = node.text;
                } else if (this.valueNotFoundText !== undefined) {
                    text = this.valueNotFoundText;
                }
                this.lastSelectionText = text;
                if (this.hiddenField) {
                    this.hiddenField.value = v;
                }
                EasyJF.Ext.TreeComboField.superclass.setValue.call(this, text);
                this.value = v;
                this.text = text;
            },
            setEditable : function(value) {
                if (value == this.editable) {
                    return;
                }
                this.editable = value;
                if (!value) {
                    this.el.dom.setAttribute('readOnly', true);
                    this.el.on('mousedown', this.onTriggerClick, this);
                    this.el.addClass('x-combo-noedit');
                } else {
                    this.el.dom.setAttribute('readOnly', false);
                    this.el.un('mousedown', this.onTriggerClick, this);
                    this.el.removeClass('x-combo-noedit');
                }
            },
            choice : function(node, eventObject) {
                if((!this.leafOnly || node.isLeaf())){
                    if (node.id != "root"){
                        this.setValue(node.id);
                    }
                    else {
                        this.clearValue();
                        this.el.dom.value=node.text;
                    }
                    this.fireEvent('select', this, node);
                    this.collapse();
                    this.fireEvent('collapse', this);
                }else{
                    if (node.id == "root"){
                        this.clearValue();
                        this.el.dom.value=node.text;
                        this.fireEvent('select', this, node);
                        this.collapse();
                        this.fireEvent('collapse', this);
                    }
                }
            },
            validateBlur : function() {
                return !this.list || !this.list.isVisible();
            },
            isExpanded : function(){
                return this.list && this.list.isVisible();
            },
            canFocus:function(){
                return !this.disabled ;
            },
            onDestroy : function() {
                if (this.tree.rendered && this.list) {
                    this.list.hide();                   
                     this.list.destroy();
                    delete this.list;                                   
                }
                EasyJF.Ext.TreeComboField.superclass.onDestroy.call(this);
            }
        });
Ext.reg('treecombo', EasyJF.Ext.TreeComboField);


//来自Ext社区的表格求和插件

Ext.ns('Ext.ux.grid');
Ext.ux.grid.GridSummary = function(config) {
        Ext.apply(this, config);
};

Ext.extend(Ext.ux.grid.GridSummary, Ext.util.Observable, {
    init : function(grid) {
        this.grid = grid;
        this.cm = grid.getColumnModel();
        this.view = grid.getView();

        var v = this.view;

        // override GridView's onLayout() method
        v.onLayout = this.onLayout;

        v.afterMethod('render', this.refreshSummary, this);
        v.afterMethod('refresh', this.refreshSummary, this);
        v.afterMethod('syncScroll', this.syncSummaryScroll, this);
        v.afterMethod('onColumnWidthUpdated', this.doWidth, this);
        v.afterMethod('onAllColumnWidthsUpdated', this.doAllWidths, this);
        v.afterMethod('onColumnHiddenUpdated', this.doHidden, this);

        // update summary row on store's add/remove/clear/update events
        grid.store.on({
            add: this.refreshSummary,
            remove: this.refreshSummary,
            clear: this.refreshSummary,
            update: this.refreshSummary,
            scope: this
        });

        if (!this.rowTpl) {
            this.rowTpl = new Ext.Template(
                '<div class="x-grid3-summary-row x-grid3-gridsummary-row-offset">',
                    '<table class="x-grid3-summary-table" border="0" cellspacing="0" cellpadding="0" style="{tstyle}">',
                        '<tbody><tr>{cells}</tr><tr style="display:{totalDisplay}">{tcells}</tr></tbody>',
                    '</table>',
                '</div>'
            );
            this.rowTpl.disableFormats = true;
        }
        this.rowTpl.compile();

        if (!this.cellTpl) {
            this.cellTpl = new Ext.Template(
                '<td class="x-grid3-col x-grid3-cell x-grid3-td-{id} {css}" style="{style}">',
                    '<div class="x-grid3-cell-inner x-grid3-col-{id}" unselectable="on" {attr}>{value}</div>',
                "</td>"
            );
            this.cellTpl.disableFormats = true;
        }
        this.cellTpl.compile();
    },

    calculate : function(rs, cm,isTotal) {
        var data = {}, cfg = cm.config;
        for (var i = 0, len = cfg.length; i < len; i++) { // loop through all
                                                            // columns in
                                                            // ColumnModel
            var cf = cfg[i], // get column's configuration
                cname = cf.dataIndex; // get column dataIndex

            // initialise grid summary row data for
            // the current column being worked on
            data[cname+(isTotal?"_total":"")] = 0;

            if (cf.summaryType) {
                for (var j = 0, jlen = rs.length; j < jlen; j++) {
                    var r = rs[j]; // get a single Record
                    if(isTotal){
                        data[cname+"_total"] = Ext.ux.grid.GridSummary.Calculations[cf.summaryType](r[cname], r, cname+"_total", data, j);
                    }
                    else {
                        data[cname] = Ext.ux.grid.GridSummary.Calculations[cf.summaryType](r.get(cname), r, cname, data, j);
                    }
                }
            }
        }

        return data;
    },

    onLayout : function(vw, vh) {
        if (Ext.type(vh) != 'number') { // handles grid's height:'auto' config
            return;
        }
        // note: this method is scoped to the GridView
        if (!this.grid.getGridEl().hasClass('x-grid-hide-gridsummary')) {
            // readjust gridview's height only if grid summary row is visible
            this.scroller.setHeight(vh - this.summary.getHeight());
        }
    },

    syncSummaryScroll : function() {
        var mb = this.view.scroller.dom;

        this.view.summaryWrap.dom.scrollLeft = mb.scrollLeft;
        this.view.summaryWrap.dom.scrollLeft = mb.scrollLeft; 
    },

    doWidth : function(col, w, tw) {
        var s = this.view.summary.dom;

        s.firstChild.style.width = tw;
        s.firstChild.rows[0].childNodes[col].style.width = w;
    },

    doAllWidths : function(ws, tw) {
        var s = this.view.summary.dom, wlen = ws.length;

        s.firstChild.style.width = tw;

        var cells = s.firstChild.rows[0].childNodes;

        for (var j = 0; j < wlen; j++) {
            cells[j].style.width = ws[j];
        }
    },

    doHidden : function(col, hidden, tw) {
        var s = this.view.summary.dom,
            display = hidden ? 'none' : '';

        s.firstChild.style.width = tw;
        s.firstChild.rows[0].childNodes[col].style.display = display;
    },

    renderSummary : function(o, cs, cm) {
        cs = cs || this.view.getColumnData();
        var cfg = cm.config, buf = [],tbuf=[], last = cs.length - 1;
        for (var i = 0, len = cs.length; i < len; i++) {
            var c = cs[i], cf = cfg[i], p = {};

            p.id = c.id;
            p.style = c.style;
            p.css = i == 0 ? 'x-grid3-cell-first ' : (i == last ? 'x-grid3-cell-last ' : '');
            var tp=Ext.apply({},p);
          
            if (cf.summaryType || cf.summaryRenderer) {             
                p.value = (cf.summaryRenderer || c.renderer)(o.data[c.name], p, o);
                tp.value=(cf.summaryTotalRenderer||cf.summaryRenderer || c.renderer)(o.data[c.name+"_total"], p, o);
            } else {
                p.value = '';
                tp.value='';
            }
              
            if (p.value == undefined || p.value === "") p.value = "&#160;";
            if (tp.value == undefined || tp.value === "") tp.value = "&#160;";
            buf[buf.length] = this.cellTpl.apply(p);
            tbuf[tbuf.length]=this.cellTpl.apply(tp);
            
        }

        return this.rowTpl.apply({
            tstyle: 'width:' + this.view.getTotalWidth() + ';',
            cells: buf.join(''),
            tcells:tbuf.join(''),
            totalDisplay:this.grid.store.refreshCache?"display":"none"
        });
    },

    refreshSummary : function() {
        var g = this.grid, ds = g.store,
            cs = this.view.getColumnData(),
            cm = this.cm,
            rs = ds.getRange();
            var data = this.calculate(rs, cm);
            if(ds.refreshCache){
                var total=this.calculate(ds.proxy.getData().getRange(), cm,true);
                Ext.apply(data,total);
            }       
            var buf = this.renderSummary({data: data}, cs, cm);
            
        if (!this.view.summaryWrap) {        
            this.view.summaryWrap = Ext.DomHelper.insertAfter(this.view.scroller, {
                tag: 'div',
                cls: 'x-grid3-gridsummary-row-inner'
            }, true);           
        }
        this.view.summary = this.view.summaryWrap.update(buf).first();
    },

    toggleSummary : function(visible) { // true to display summary row
        var el = this.grid.getGridEl();

        if (el) {
            if (visible === undefined) {
                visible = el.hasClass('x-grid-hide-gridsummary');
            }
            el[visible ? 'removeClass' : 'addClass']('x-grid-hide-gridsummary');

            this.view.layout(); // readjust gridview height
        }
    },

    getSummaryNode : function() {
        return this.view.summary
    }
});
Ext.reg('gridsummary', Ext.ux.grid.GridSummary);

/*
 * all Calculation methods are called on each Record in the Store with the
 * following 5 parameters:
 * 
 * v - cell value record - reference to the current Record colName - column name
 * (i.e. the ColumnModel's dataIndex) data - the cumulative data for the current
 * column + summaryType up to the current Record rowIdx - current row index
 */
Ext.ux.grid.GridSummary.Calculations = {
    sum : function(v, record, colName, data, rowIdx) {
        return data[colName] + Ext.num(v, 0);
    },

    count : function(v, record, colName, data, rowIdx) {
        return rowIdx + 1;
    },

    max : function(v, record, colName, data, rowIdx) {
        return Math.max(Ext.num(v, 0), data[colName]);
    },

    min : function(v, record, colName, data, rowIdx) {
        return Math.min(Ext.num(v, 0), data[colName]);
    },

    average : function(v, record, colName, data, rowIdx) {
        var t = data[colName] + Ext.num(v, 0), count = record.store.getCount();
        try{
        var result= rowIdx == count - 1 ? (t / count) : t;
         return result;
        }
        catch(e){
            alert(e);
        }
       
        
    },
    last:function(v, record, colName, data, rowIdx) {
        return v;
    }
}