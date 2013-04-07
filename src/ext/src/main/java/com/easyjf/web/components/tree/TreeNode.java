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

import java.util.List;
import java.util.Map;

import com.easyjf.web.components.BaseComponent;
import com.easyjf.web.components.Function;

/**
 * 
 * @author 大峡
 * 
 */
public class TreeNode extends BaseComponent {
	private String id;
	private String text;
	private TreeLoader loader;
	private boolean expanded;
	private List<TreeNode> childNodes = new java.util.ArrayList<TreeNode>();
	private TreeNode parentNode;
	private Function handler;
	private Boolean leaf;
	private Boolean checked;
	private Boolean allowChildren;
	private Boolean disabled;
	private Boolean expandable;
	private String href;
	private String hrefTarget;
	private Boolean singleClickExpand;
	private Map<String, Object> attributes = new java.util.HashMap<String, Object>();

	public TreeNode() {
		this(null, null);
	}

	public TreeNode(String id, String text) {
		this(id, text, null);
	}

	public TreeNode(String id, String text, TreeNode parent) {
		this.id = id;
		this.text = text;
		this.parentNode = parent;
		this.setLazy(false);
		Function callback = new Function(
				new String[] { "node" },
				"if(node.childNodes){for(var i=0;i<node.childNodes.length;i++){this.appendChild(node.childNodes[i]);}}");
		// this.addListener("","");
		this.setCallback(callback);
	}

	public String clz() {
		return (getLeaf() != null && !getLeaf() && this.childNodes.isEmpty() ? "Ext.tree.AsyncTreeNode"
				: "Ext.tree.TreeNode");
	}

	public TreeNode(ITreeNode n) {
		this.id = n.getId();
		this.text = n.getText();
	}

	public TreeNode add(TreeNode node) {
		node.setParentNode(this);
		this.childNodes.add(node);
		return this;
	}

	public TreeNode add(TreeNode... nodes) {
		if (nodes != null) {
			for (TreeNode node : nodes) {
				this.add(node);
			}
		}
		return this;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Boolean getLeaf() {
		Boolean ret = this.leaf != null ? this.leaf
				&& this.childNodes.isEmpty() : this.childNodes == null
				|| this.childNodes.isEmpty();
		return ret;
	}

	public TreeLoader getLoader() {
		return loader;
	}

	public void setLoader(TreeLoader loader) {
		this.loader = loader;
	}

	public List<TreeNode> getChildNodes() {
		return childNodes;
	}

	public void setChildNodes(List<TreeNode> childNodes) {
		this.childNodes = childNodes;
	}

	public TreeNode getParentNode() {
		return parentNode;
	}

	public void setParentNode(TreeNode parentNode) {
		this.parentNode = parentNode;
	}

	public Function getHandler() {
		return handler;
	}

	public void setHandler(Function handler) {
		this.handler = handler;
	}

	public Map<String, Object> getAttributes() {
		this.attributes.put("id", id);
		this.attributes.put("text", text);
		this.attributes.put("leaf", this.getLeaf());
		this.attributes.put("expanded", this.expanded);
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Boolean getAllowChildren() {
		return allowChildren;
	}

	public void setAllowChildren(Boolean allowChildren) {
		this.allowChildren = allowChildren;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public Boolean getExpandable() {
		return expandable;
	}

	public void setExpandable(Boolean expandable) {
		this.expandable = expandable;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getHrefTarget() {
		return hrefTarget;
	}

	public void setHrefTarget(String hrefTarget) {
		this.hrefTarget = hrefTarget;
	}

	public Boolean getSingleClickExpand() {
		return singleClickExpand;
	}

	public void setSingleClickExpand(Boolean singleClickExpand) {
		this.singleClickExpand = singleClickExpand;
	}
}
