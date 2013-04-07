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

import java.util.List;
import java.util.Map;
/**
 * 
 * @author 大峡
 *
 */
public abstract class Container extends BoxComponent {
	private String defaultType;
	private Map<String, Object> defaults = new java.util.HashMap<String, Object>();
	private List<IRichComponent> items = new java.util.ArrayList<IRichComponent>();
	private String layout;
	private Map<String, Object> layoutConfig = new java.util.HashMap<String, Object>();
	private Boolean monitorResize;
	private Boolean hideBorders;
	
	public IRichComponent getComponent(String id) {
		int index = this.findComponentById(id);
		if (index > -1)
			return this.items.get(index);
		else
			return null;
	}
	public Container add(IRichComponent c)
	{
		this.items.add(c);
		return this;
	}
	public Container add(IRichComponent...components )
	{
		if(components!=null)
		{
			for(IRichComponent c:components)
			{
				this.add(c);
			}
		}
		return this;
	}

	public Container insert(Integer index, IRichComponent c) {
		this.items.add(index, c);
		return this;
	}

	public Container remove(String id) {
		int index = this.findComponentById(id);
		if (index > -1)
			this.items.remove(index);
		return this;
	}

	protected int findComponentById(String id) {
		int index = -1;
		for (int i = 0; i < this.items.size(); i++) {
			if (id.equals(this.items.get(i).getId())) {
				index = i;
				break;
			}
		}
		return index;
	}

	public Container remove(IRichComponent c) {
		this.items.remove(c);
		return this;
	}

	public Container remove(Integer index) {
		this.items.remove(index);
		return this;
	}

	public String getDefaultType() {
		return defaultType;
	}

	public void setDefaultType(String defaultType) {
		this.defaultType = defaultType;
	}

	public Map<String, Object> getDefaults() {
		return defaults;
	}

	public void setDefaults(Map<String, Object> defaults) {
		this.defaults = defaults;
	}

	public List<IRichComponent> getItems() {
		return items;
	}

	public void setItems(List<IRichComponent> items) {
		this.items = items;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public Map<String, Object> getLayoutConfig() {
		return layoutConfig;
	}

	public void setLayoutConfig(Map<String, Object> layoutConfig) {
		this.layoutConfig = layoutConfig;
	}

	public Boolean getMonitorResize() {
		return monitorResize;
	}

	public void setMonitorResize(Boolean monitorResize) {
		this.monitorResize = monitorResize;
	}
	public Boolean getHideBorders() {
		return hideBorders;
	}
	public void setHideBorders(Boolean hideBorders) {
		this.hideBorders = hideBorders;
	}
}
