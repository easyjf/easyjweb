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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.easyjf.beans.BeanUtils;
import com.easyjf.util.I18n;
import com.easyjf.util.TagUtil;

/**
 * EasyJWeb内置的验证器 所有内置的验证器以内部类的方式提供，并在EasyJWeb启动的过程中自动加载。
 * 
 * @author 大峡,stef_wu
 * 
 */
public class Validators {
	/**
	 * 必填验证器，要求验证目标包含非null的值，该验证器预定义的名称required 必填验证器不需要包含任何参数
	 * 
	 * @author 大峡
	 * 
	 */
	public class RequiredValidator extends AbstractValidator {

		public RequiredValidator() {

		}

		public void validate(TargetObject obj, Object value, Errors errors) {
			String msg = (String) obj.getValue("msg");
			if (value == null)
				addError(obj, value, errors, msg);
			else if (value instanceof String && "".equals(value)) {
				addError(obj, value, errors, msg);
			}
		}

		public String getDefaultMessage() {
			return I18n.getMessage("validator.required");
		}
	}

	/**
	 * 正则表达式匹配验证器，要求指定值匹配特定的正则表达式，该验证器预定义的名称regex
	 * 在该验证器，参数expression值用来设定需要匹配的正则表达式，trim用来在匹配之前是否需要先进行去除空格的字符串处理操作，sensitive用来表示是否忽略大小写
	 * 使用方法
	 * 
	 * <pre>
	 * @Validator(name = &quot;regex&quot;, value = &quot;expression:/ddd/;trim&quot;)
	 * private String name;
	 * </pre>
	 * 
	 * @author 大峡
	 * 
	 */
	public class RegexpValidator extends AbstractValidator {

		Validators v = new Validators();

		private final RequiredValidator rv = v.new RequiredValidator();

		public String getDefaultMessage() {
			return I18n.getMessage("validator.regexp");			
		}

		public void validate(TargetObject obj, Object value, Errors errors) {
			if (obj.isRequried()) {
				int i = errors.getErrorCount();
				rv.validate(obj, value, errors);
				if (errors.getErrorCount() > i)
					return;
			}
			boolean isCaseSensitive = obj.getValue("sensitive") != null ? true
					: false;
			String expression = (String) obj.getValue("expression");
			boolean trim = obj.getValue("trim") != null ? true : false;
			if (value == null || expression == null) {
				return;
			}
			// 必须是数字
			if (!(value instanceof String)) {
				return;
			}
			// 处理空字符串
			String compare = (String) value;
			if (trim)
				compare = compare.trim();
			// 长度为０的字符串直接退出
			if (compare.length() == 0) {
				return;
			}

			// 把检验的值与验证对你中的expression进行匹配
			Pattern pattern;
			if (isCaseSensitive) {
				pattern = Pattern.compile(expression);
			} else {
				pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
			}
			Matcher matcher = pattern.matcher(compare);
			if (!matcher.matches()) {
				addError(obj, value, errors, (String) obj.getValue("msg"));
			}
		}

	}

	/**
	 * 字符串数据验证器，主要用来针对字符串进行验证，该验证器预定义的名称为string
	 * 该验证器可以设置以下的参数信息，min用来定义字符串的最小长度,min_msg定义当小于指定字符时的提示信息，max_msg用来定义当大于max字符时的提示信息，max用来定义字符串的最大长度，required表示必填，trim表示在验证前是否先去除左右空格，blank用来表示是否强制处理空格
	 * 
	 * <pre>
	 * @Validator(name = &quot;string&quot;, value = &quot;trim;blank;min:10;max:20&quot;)
	 * private String name;
	 * </pre>
	 * 
	 * @author 大峡
	 * 
	 */
	public class StringValidator extends AbstractValidator {

		Validators v = new Validators();

		private final RequiredValidator rv = v.new RequiredValidator();

		public String getDefaultMessage() {
			return I18n.getMessage("validator.string");
		}

		public void validate(TargetObject obj, Object value, Errors errors) {
			if (obj.isRequried()) {
				int i = errors.getErrorCount();
				rv.validate(obj, value, errors);
				if (errors.getErrorCount() > i)
					return;
			}
			if (value == null)
				return;
			boolean blank = obj.getValue("blank") != null ? true : false;
			boolean trim = obj.getValue("trim") != null ? true : false;
			String v = (String) value;
			if (trim) {
				if (v != null)
					v = v.trim();
			}
			if (blank && v.toString().trim().length() == 0) {
				super.addError(obj, value, errors, rv.getDefaultMessage());
			}
			int length = v.length();
			Integer min = (Integer) BeanUtils.convertType(obj.getValue("min"),
					Integer.class);
			Integer max = (Integer) BeanUtils.convertType(obj.getValue("max"),
					Integer.class);
			if (min != null && length < min) {
				String minMsg = (String) obj.getValue("minMsg,min_msg");
				this.addError(obj, value, errors, minMsg != null ? minMsg
						: (String) obj.getValue("msg"));
			}
			if (max != null && length > max) {
				String maxMsg = (String) obj.getValue("maxMsg,max_msg");
				this.addError(obj, value, errors, maxMsg != null ? maxMsg
						: (String) obj.getValue("msg"));
			}
		}

	}

	/**
	 * URL格式字符串验证器，在系统中预定义的名称为url 用来要求目标值必须为正确的url格式字符。如http://或https://等开头
	 * 
	 * @author 大峡
	 * 
	 */
	public class URLValidator extends AbstractValidator {

		Validators v = new Validators();

		private final StringValidator sv = v.new StringValidator();

		public String getDefaultMessage() {
			return I18n.getMessage("validator.url");
		}

		public void validate(TargetObject obj, Object value, Errors errors) {
			if (obj.isRequried()) {
				int i = errors.getErrorCount();
				sv.validate(obj, value, errors);
				if (errors.getErrorCount() > i)
					return;
			}
			if (value == null || value.toString().length() == 0) {
				return;
			}

			if (!(value.getClass().equals(String.class))
					|| !TagUtil.verifyUrl((String) value)) {
				addError(obj, value, errors, (String) obj.getValue("msg"));
			}
		}
	}

	/**
	 * Email格式字符串验证器，在系统中预定义的名称为email 用来要求目标值必须为正确的email格式字符。如easyjf@163.com
	 * 
	 * @author 大峡
	 * 
	 */
	public class EmailValidator extends AbstractValidator {

		Validators v = new Validators();

		private final RegexpValidator rv = v.new RegexpValidator();

		/**
		 * 邮件地址的与此同时表达式
		 */
		public static final String emailAddressPattern = "\\b(^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$)\\b";

		public String getDefaultMessage() {
			return I18n.getMessage("validator.email");
		}

		public void validate(TargetObject obj, Object value, Errors errors) {
			if (obj.isRequried()) {
				int i = errors.getErrorCount();
				rv.validate(obj, value, errors);
				if (errors.getErrorCount() > i)
					return;
			}
			if (value == null || value.toString().length() == 0) {
				return;
			}			
			obj.addValue("expression", emailAddressPattern);
			rv.validate(obj, value, errors);
		}
	}

	/**
	 * 范围数据验证器，在系统中预定义的名称为range
	 * 该验证器用来要求目标值必须在特定范围之类，可以用于任何实现了Comparable接口的类，也包括Native类。
	 * 该验证器的主要参数为min表示目标值不能小于该值，max表示目标值不能大于该值。
	 * 
	 * <pre>
	 *  @Validator(name=&quot;range&quot;;value=&quot;min:1981-01-01;max:2008-01-01&quot;)
	 * private Date bornDate;
	 *  @Validator(name=&quot;range&quot;;value=&quot;min:10;max:300&quot;)
	 * private Integer height;
	 * </pre>
	 * 
	 * @author 大峡
	 * 
	 */
	public class RangeValidator extends RequiredValidator {

		Validators v = new Validators();

		private final RequiredValidator rv = v.new RequiredValidator();

		public String getDefaultMessage() {
			return I18n.getMessage("validator.range");
		}

		public void validate(TargetObject obj, Object value, Errors errors) {
			if (obj.isRequried()) {
				int i = errors.getErrorCount();
				rv.validate(obj, value, errors);
				if (errors.getErrorCount() > i)
					return;
			}
			if (value == null || value.toString().length() == 0) {
				return;
			}
			Comparable v = (Comparable) value;
			Comparable minComparatorValue = null;
			if (obj.getValue("min") != null) {
				minComparatorValue = (Comparable) BeanUtils.convertType(obj
						.getValue("min"), value.getClass());
			}
			Comparable maxComparatorValue = null;
			if (obj.getValue("max") != null) {
				maxComparatorValue = (Comparable) BeanUtils.convertType(obj
						.getValue("max"), value.getClass());
			}
			// 检查数据是否小于指定的数据
			if ((minComparatorValue != null)
					&& (v.compareTo(minComparatorValue) < 0)) {
				String minMsg = (String) obj.getValue("minMsg,min_msg");
				this.addError(obj, value, errors, minMsg != null ? minMsg
						: (String) obj.getValue("msg"));
			}
			// 指定数据是否大于指定的数据
			if ((maxComparatorValue != null)
					&& (v.compareTo(maxComparatorValue) > 0)) {
				String maxMsg = (String) obj.getValue("maxMsg,max_msg");
				this.addError(obj, value, errors, maxMsg != null ? maxMsg
						: (String) obj.getValue("msg"));
			}

		}
	}
}
