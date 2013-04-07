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
package com.easyjf.web.components.form;

import com.easyjf.web.components.tree.TreePanel;


/**
 * 
 * @author 大峡
 *
 */
public class TreeComboField extends TextField {
	private String hiddenName;
	private TreePanel tree;
	public TreeComboField() {
		this(null, null);
	}

	public TreeComboField(String name, String label) {
		this(name, label, null);
	}

	public TreeComboField(String name, String label, TreePanel tree) {
		this(name, label, tree, null);
	}

	public TreeComboField(String name, String label, TreePanel tree, Integer width) {
		super(name, label, null, width);
		this.tree=tree;		
		this.hiddenName=name;
		this.init();
	}

	protected void init() {
		this.setXtype("treecombo");		
	}

	@Override
	public String clz() {
		return "EasyJF.Ext.TreeComboField";
	}

	public String getHiddenName() {
		return hiddenName;
	}

	public void setHiddenName(String hiddenName) {
		this.hiddenName = hiddenName;
	}

	public TreePanel getTree() {
		return tree;
	}

	public void setTree(TreePanel tree) {
		this.tree = tree;
	}

}
