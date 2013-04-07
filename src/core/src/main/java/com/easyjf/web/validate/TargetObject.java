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
package com.easyjf.web.validate;

import java.util.List;
import java.util.Map;

/**
 * 验证目标对象的封装
 * 
 * @author 大峡
 * 
 */
public class TargetObject {

	/**
	 * 验证的目标对象，对于property或field两种类型的验证来说，指的是Domain或Command对象
	 */
	private Object target;

	/**
	 * 验证类型
	 */
	private ValidateType type = ValidateType.Field;

	/**
	 * 多国语言标识键
	 */
	private String key;

	/**
	 * 显示名称
	 */
	private String displayName;

	/**
	 * 字段或属性名称
	 */
	private String fieldName;

	/**
	 * 错误提示信息
	 */
	private String defaultMessage;

	/**
	 * 是否必填字段
	 */
	private boolean requried;

	/**
	 * 配置信息的值集合
	 */
	private Map values = new java.util.HashMap();

	/**
	 * 验证器
	 */
	private List<Validator> validators = new java.util.ArrayList<Validator>();

	private ValidatorManager manager;

	public TargetObject() {

	}

	public ValidatorManager getManager() {
		return manager;
	}

	public void setManager(ValidatorManager manager) {
		this.manager = manager;
	}

	public TargetObject(ValidatorManager manager) {
		this(manager, null);
	}

	public TargetObject(ValidatorManager manager, Object target) {
		this.target = target;
		this.manager = manager;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public void setDefaultMessage(String defaultMessage) {
		this.defaultMessage = defaultMessage;
	}

	public ValidateType getType() {
		return type;
	}

	public void setType(ValidateType type) {
		this.type = type;
	}

	public List<Validator> getValidators() {
		return validators;
	}

	public void setValidators(List<Validator> validators) {
		this.validators = validators;
	}

	public void addValidator(Validator validator) {
		this.validators.add(validator);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDefaultMessage() {
		return defaultMessage;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public boolean isRequried() {
		return requried;
	}

	public void setRequried(boolean requried) {
		this.requried = requried;
	}

	public Map getValues() {
		return values;
	}

	public void setValues(String value) {
		this.values.putAll(parseValue(value));
	}

	public Map parseValue(String value) {
		Map map = new java.util.HashMap();
		if (value != null && !"".equals(value.trim())) {
			// value=value.replaceAll("\\\\;", "[分号]");
			String[] s = value.replaceAll("\\\\;", "[分号]").split(";");
			for (int i = 0; i < s.length; i++) {
				String[] vs = s[i].split(":");
				if (vs.length > 1)
					this.addValue(vs[0], vs[1].replaceAll("\\[分号\\]", ";"));
				else
					this.addValue(vs[0], true);
			}
		}
		return map;
	}

	public void addValue(String name, Object value) {
		this.values.put(name.toLowerCase(), value);
	}

	/**
	 * 该方法能够得通过以逗号分隔的一串属性名称中，分别通过尝试各个属性值取得配置参数
	 * 
	 * @param name
	 * @return 若存在指定名称的参数值，则返回该值，否则返回null
	 */
	public Object getValue(String name) {

		Object ret = null;
		if (name != null) {
			String[] names = name.split(",");
			for (int i = 0; i < names.length; i++) {
				ret = this.values.get(names[i].toLowerCase());
				if (ret != null)
					break;
			}
		}
		return ret;

	}

	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}

	public void doValidate(Object value) {
		for (Validator v : this.validators) {
			v.validate(this, value, manager.getErrors());
		}
	}

	public Errors getErrors() {
		return  manager.getErrors();		
	}

}
