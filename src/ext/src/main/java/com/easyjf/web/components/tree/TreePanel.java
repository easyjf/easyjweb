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
package com.easyjf.web.components.tree;

import com.easyjf.web.components.Panel;
/**
 * 
 * @author 大峡
 *
 */
public class TreePanel extends Panel {
	private TreeNode root;
	private TreeLoader loader;
	private Boolean rootVisible;
	private Boolean containerScroll;
	private Boolean lines;
	private Boolean singleExpand;
	public TreePanel() {
		this(null);
	}

	public TreePanel(String id) {
		this(id, (Integer) null);
	}
	public TreePanel(String id,String title) {
		this(id, title,null,null,null);
	}
	public TreePanel(String id, TreeNode root) {
		this(id, root, null);
	}

	public TreePanel(String id,String title ,Integer width) {
		this(id, title,null,width, null);
	}
	public TreePanel(String id, Integer width) {
		this(id, width, null);
	}
	public TreePanel(String id, Integer width, Integer height) {
		this(id, null,null, width, height);
	}

	public TreePanel(String id,String title,TreeNode root, Integer width) {
		this(id, title,root, width, null);
	}
	public TreePanel(String id,TreeNode root, Integer width) {
		this(id, null,root, width, null);
	}

	public TreePanel(String id, String title,TreeNode root, Integer width, Integer height) {
		this.setId(id);
		this.setTitle(title);
		this.root = root;
		this.setWidth(width);
		this.setHeight(height);
		this.init();
	}

	protected void init() {
		super.init();
		this.setXtype("treepanel");
	}

	@Override
	public String clz() {
		return "Ext.tree.TreePanel";
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public TreeLoader getLoader() {
		return loader;
	}

	public void setLoader(TreeLoader loader) {
		this.loader = loader;
	}

	public Boolean getRootVisible() {
		return rootVisible;
	}
	public void setRootVisible(Boolean rootVisible) {
		this.rootVisible = rootVisible;
	}

	public Boolean getContainerScroll() {
		return containerScroll;
	}

	public void setContainerScroll(Boolean containerScroll) {
		this.containerScroll = containerScroll;
	}

	public Boolean getLines() {
		return lines;
	}

	public void setLines(Boolean lines) {
		this.lines = lines;
	}

	public Boolean getSingleExpand() {
		return singleExpand;
	}

	public void setSingleExpand(Boolean singleExpand) {
		this.singleExpand = singleExpand;
	}	
}
