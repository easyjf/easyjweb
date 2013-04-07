package com.easyjf.web.ajax;

import java.util.List;

public class Region {
	private Long id;

	private String name;

	private String code;

	private Region parent;

	private List<Region> children = new java.util.ArrayList<Region>();

	public Region() {

	}

	public Region(String name, String code, Region parent) {
		this.name = name;
		this.code = code;
		if (parent != null)
			parent.addChild(this);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Region getParent() {
		return parent;
	}

	public void setParent(Region parent) {
		this.parent = parent;
	}

	public List<Region> getChildren() {
		return children;
	}

	public void setChildren(List<Region> children) {
		this.children = children;
	}

	public void addChild(Region region) {
		if (!this.children.contains(region))
			this.children.add(region);
		region.setParent(this);
	}

	public void removeChild(Region region) {
		this.children.remove(region);
		region.setParent(null);
	}
}
