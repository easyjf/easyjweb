/*
 * Copyright 2006-2008 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.easyjf.web.components;

import com.easyjf.web.components.form.Form;
import com.easyjf.web.components.grid.GridPanel;
import com.easyjf.web.components.toolbar.PagingToolbar;
import com.easyjf.web.components.toolbar.ToolbarItem;

/**
 * 
 * @author 大峡
 *
 */
public abstract class CrudPanel extends Panel {
	private String baseUrl;
	public GridPanel grid;
	private PagingToolbar pagingToolbar;
	private Function linkRenderer = new Function(
			new String[] { "v" },
			"if(!v)return '';else return String.format(\"<a href='{0}' target='_blank'>{0}</a>\",v);");
	private Function refresh = new Function(
			"this.grid.store.removeAll();this.grid.store.reload();");
	private Function create=new Function("this.showWin();this.reset();");
	private Function showWin=new Function("if(!this.win){if(!this.fp){this.fp=this.createForm();}"+
		"this.win=this.createWin();this.win.on('close',function(){this.win=null;this.fp=null;},this);}"+
		"this.win.show();");
	private Function reset=new Function("if(this.win)this.fp.form.reset();");
	private Function closeWin=new Function("if(this.win)this.win.close();this.win=null;this.fp=null;");
	private Function save=new Function("var id=this.fp.form.findField('id').getValue();"+	
		   "this.fp.form.submit({"+		
				"waitMsg:'正在保存。。。',"+
	            "url:this.baseUrl+'?cmd='+(id?'update':'save'),"+
	            "success:function(){"+
	            "this.closeWin();"+
	            "this.grid.store.reload();"+          	
	            "},scope:this});");
	private Function initWin=new Function(new String[]{"width","height","title"},"var win=new Ext.Window({"+
			"width:width,height:height,buttonAlign:'center',title:title,modal:true,shadow:true,closeAction:'hide',"+
			"items:[this.fp],"+
			"buttons:[{text:'保存',handler:this.save,scope:this},"+
					  "{text:'清空',handler:this.reset,scope:this},"+
					  "{text:'取消',handler:this.closeWin,scope:this}]"+					  
			"});return win;");
	private Function createForm=new Function("return this.form.dom?this.form:new Ext.form.FormPanel(this.form);");
	private Function edit=new Function("var record=this.grid.getSelectionModel().getSelected();"+
		"if(!record){Ext.Msg.alert('提示','请先选择要编辑的行!');return;}"+
	    "var id=record.get('id'); this.showWin();this.fp.form.loadRecord(record);");	   
	private Function removeData=new Function("var record=this.grid.getSelectionModel().getSelected();"+
			"if(!record){Ext.Msg.alert('提示','请先选择要编辑的行!');return;}"+
			"var m=Ext.MessageBox.confirm('删除提示','是否真的要删除数据？',function(ret){"+
			"if(ret=='yes'){" +
			"Ext.Ajax.request({url:this.baseUrl+'?cmd=remove', params:{'id':record.get('id')},success:function(response){"+
			"var r=Ext.decode(response.responseText);if(!r.success)Ext.Msg.alert('提示信息','数据删除失败，由以下原因所致：<br/>'+(r.errors.msg?r.errors.msg:'未知原因'));"+
	        " else{Ext.Msg.alert('提示信息','成功删除数据!',function(){this.grid.store.reload();},this);}},scope:this});"+
	        "}},this);");
	public abstract Function getCreateWin();
	public abstract Form getForm();
	public CrudPanel() {
		this(null);
	}

	public CrudPanel(String id) {
		this(id, null);
	}

	public CrudPanel(String id, String title) {
		this(id, title, null);
	}

	public CrudPanel(String id, String title, String baseUrl) {
		this(id, title, null, null);
		this.baseUrl = baseUrl;
	}

	public CrudPanel(String id, String title, Integer width, Integer height) {
		this.id = id;
		this.setTitle(title);
		this.setWidth(width);
		this.setHeight(height);
	}

	protected void init() {
		this.setClosable(true);
		this.setAutoScroll(true);
		this.setLayout("fit");
		this.grid = new GridPanel("grid");
		this.grid.setLoadMask(true);
		//this.grid.setLazy(false);
		pagingToolbar = new PagingToolbar();
		pagingToolbar.setDisplayInfo(true);
		this.setBbar(pagingToolbar);
		this.add(this.grid);
		//this.registerProxy("bbar", this.pagingToolbar);
		//this.registerProxy("grid", this.grid);
		//this.addListener("render", new Function("alert(grid.el.dom);"));
	}

	public void bind(Class clz, String[] excludeColumns) {
		grid.setExcludeColumns(excludeColumns);
		grid.bind(clz, this.baseUrl + "?cmd=list");
		build();
	}

	public void setColumns(String[] columns) {
		grid.setColumns(columns);
		grid.setUrl(this.baseUrl + "?cmd=list");
		build();
	}

	public void setColumns(String[][] columns) {
		grid.setColumns(columns);
		grid.setUrl(this.baseUrl + "?cmd=list");
		build();
	}

	public void build() {
		this.grid.addTbar(new ToolbarItem.Button("添加",new OuterFunction(this,"create")));
		this.grid.addTbar(new ToolbarItem.Button("修改",new OuterFunction(this,"edit")));
		this.grid.addTbar(new ToolbarItem.Button("删除",new OuterFunction(this,"removeData")));
		this.grid.addTbar(new ToolbarItem.Button("刷新", new OuterFunction(this, "refresh")));
		pagingToolbar.setStore(this.grid.getStore());
		
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public void load() {
		this.grid.load();
	}

	public GridPanel getGrid() {
		return grid;
	}

	public PagingToolbar getPagingToolbar() {
		return pagingToolbar;
	}

	public Function getLinkRenderer() {
		return linkRenderer;
	}

	public void setLinkRenderer(Function linkRenderer) {
		this.linkRenderer = linkRenderer;
	}

	public Function getRefresh() {
		return refresh;
	}

	public void setRefresh(Function refresh) {
		this.refresh = refresh;
	}

	public Function getCreate() {
		return create;
	}

	public void setCreate(Function create) {
		this.create = create;
	}

	public Function getShowWin() {
		return showWin;
	}

	public void setShowWin(Function showWin) {
		this.showWin = showWin;
	}

	public Function getReset() {
		return reset;
	}

	public void setReset(Function reset) {
		this.reset = reset;
	}

	public Function getCloseWin() {
		return closeWin;
	}

	public void setCloseWin(Function closeWin) {
		this.closeWin = closeWin;
	}

	public Function getSave() {
		return save;
	}

	public void setSave(Function save) {
		this.save = save;
	}

	public Function getInitWin() {
		return initWin;
	}

	public void setInitWin(Function initWin) {
		this.initWin = initWin;
	}
	public Function getCreateForm() {
		return createForm;
	}
	public void setCreateForm(Function createForm) {
		this.createForm = createForm;
	}
	public Function getEdit() {
		return edit;
	}
	public void setEdit(Function edit) {
		this.edit = edit;
	}
	public Function getRemoveData() {
		return removeData;
	}
	public void setRemoveData(Function removeData) {
		this.removeData = removeData;
	}
	@Override
	public boolean isLazy() {
		return true;
	}
	
}
