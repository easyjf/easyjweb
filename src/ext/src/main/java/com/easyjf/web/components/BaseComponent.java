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

import java.util.HashMap;
import java.util.Map;
/**
 * 
 * @author 大峡
 *
 */
public abstract class BaseComponent implements IRichComponent, Cloneable {
    private static final long serialVersionUID = -106503460926L;
    private boolean init=false;//默认时都不显示
    protected String id;
    protected String xtype;
    private boolean lazy=true;//默认时都是使用延迟加载
    protected Map<String,Object> properties = new HashMap<String, Object>();
    protected Map<String,Function> listeners=new java.util.HashMap<String,Function>();
    protected Function callback;
	private boolean global;
	private String varName;
	private String initProperty;
	private Map<String,BaseComponent> reloadComponents=new java.util.HashMap<String, BaseComponent>();
    public Object get(String name) {
        return get(name, null);
    }
    
    @SuppressWarnings("unchecked")
    public <T> T get(String name, T def) {
        T result = (T)properties.get(name);
        return result;
    }

    public void set(String name, Object value) {
        if (value == null) {
            properties.remove(name);
        } else {
            properties.put(name, value);
        }
    }

    public boolean isSet(String name) {
        return properties.containsKey(name);
    }

    public void reset() {
        properties.clear();
    }
    
   /**
    * 属性合并　
    * @param other
    */
    public void merge(BaseComponent other) {        
        for (Map.Entry<String, Object> entry : properties.entrySet()) {
            other.set(entry.getKey(), entry.getValue());
        }
    }
 
    /**
	 * 可以替换父类中指定属性的名称
	 */
    protected String getReplacedName(String name) {
        return name;
    }
  
    public BaseComponent clone() {
        try {
            BaseComponent ec = (BaseComponent)super.clone();
            ec.properties = new HashMap<String, Object>();
            ec.properties.putAll(this.properties);
            return ec;
        } catch (CloneNotSupportedException ex) {
            throw new InternalError();
        }
    }

	public Map<String, Object> readProperties() {
		return this.properties;
	}

	public boolean isInit() {
		return init;
	}

	public void setInit(boolean init) {
		this.init = init;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getXtype() {
		return xtype;
	}

	public void setXtype(String xtype) {
		this.xtype = xtype;
	}

	public void addListener(String eventName,Function function)
	{
		java.util.Map<String,Function> map=new java.util.HashMap<String,Function>();
		map.put(eventName, function);
		this.listeners.put(eventName, function);
	}

	public boolean isLazy() {
		return lazy;
	}

	public void setLazy(boolean lazy) {
		this.lazy = lazy;
	}

	public Function getCallback() {
		return callback;
	}

	public void setCallback(Function callback) {
		this.callback = callback;
	}

	public Map<String, Function> getListeners() {
		return listeners;
	}

	public void setListeners(Map<String, Function> listeners) {
		this.listeners = listeners;
	}
	public boolean isGlobal() {
		return global;
	}

	public void setGlobal(boolean global) {
		this.global = global;
	}

	public String getVarName() {
		return varName;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}

	public String getInitProperty() {
		return initProperty;
	}

	public void setInitProperty(String initProperty) {
		this.initProperty = initProperty;
	}
	public void reloadComponent(String name,BaseComponent component)
	{
		this.reloadComponents.put(name,component);
	}
	
	public Map<String,BaseComponent> reloadComponents()
	{
		return this.reloadComponents;
	}
}
