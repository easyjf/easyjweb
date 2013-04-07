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

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.easyjf.container.annonation.Field;
import com.easyjf.container.annonation.FormPO;
import com.easyjf.util.I18n;
import com.easyjf.util.StringUtils;
import com.easyjf.web.core.FrameworkEngine;
import com.easyjf.web.exception.FrameworkException;

/**
 * 系统验证器管理器，负责管理系统中的注册的验证器，验证对象，以及错误对象等。其它对象通过调用该对象可以直接使用验证功能。
 * 
 * @author 大峡
 * 
 */
public class ValidatorManager {

	/**
	 * 用来存放系统中的注册的验证器类，默认情况会自动使用类名作为验证器，系统会自动搜索系统中的所有验证器，并注册到该Map中。
	 */
	private java.util.Map<String, Validator> validators = new java.util.HashMap<String, Validator>();

	/**
	 * 用来存放验证器的类型，特别是那种需要每次创建，用户自定义的验证器。
	 */
	private java.util.Map<String, Class> validatorsType = new java.util.HashMap<String, Class>();

	/**
	 * 缓存验证目标对象
	 */
	private java.util.Map<Object, TargetObject> targetObjects = new java.util.HashMap<Object, TargetObject>();

	/**
	 * 缓存记录验证类型，如对于Person对象，name为person，值为com.easyjweb.demo.domain.Person
	 */
	private java.util.Map<String, Class> targetTypes = new java.util.HashMap<String, Class>();

	private static final Logger logger = Logger
			.getLogger(ValidatorManager.class);

	/**
	 * 
	 */
	private static java.lang.ThreadLocal<Map> errorsWrapper;

	/**
	 * 存放验证错误结果
	 */
	private java.lang.ThreadLocal<Errors> errors;

	/**
	 * 重新初始化验证器管理器，自动扫描系统中的验证器，并注册到validators中，同时将清除已管理的各种验证器对象。
	 * 
	 */
	public void reload() {
		validatorsType.clear();
		validators.clear();
		targetObjects.clear();
		if (errors != null)
			errors = null;
		if (errorsWrapper != null)
			errorsWrapper = null;
		init();
	}

	/**
	 * 初始化系统中验证器
	 * 
	 */
	protected void init() {
		Validators vs = new Validators();
		this.registerValidator("required", vs.new RequiredValidator());
		this.registerValidator("email", vs.new EmailValidator());
		this.registerValidator("url", vs.new URLValidator());
		this.registerValidator("string", vs.new StringValidator());
		this.registerValidator("range", vs.new RangeValidator());
		this.registerValidator("regex", vs.new RegexpValidator());
	}

	/**
	 * 从验证管理器中返回验证器
	 * 
	 * @param name
	 *            验证器的逻辑名称，如string、required、range等
	 * @return 获取系统中注册的指定名称的验证器
	 */
	public Validator getValidator(String name) {
		if (!StringUtils.hasLength(name))
			return null;
		String key = name.toLowerCase();
		Validator v = validators.get(key);
		/**
		 * 此处让资源管理器进一步到容器中进行查找，造成ValidatorManager与FrameworkEngine严重耦合
		 */
		if (FrameworkEngine.getContainer() != null) {
			Object tv = FrameworkEngine.getContainer().getBean(name);
			if (tv != null && tv instanceof Validator) {
				this.registerValidator(name, (Validator) tv);
				v = (Validator) tv;
			}
		}
		if (v == null)
			throw new FrameworkException(I18n.getLocaleMessage("core.web.in.system.No.registration.called") + name + I18n.getLocaleMessage("core.web.Verification.check"));
		return v;
	}

	/**
	 * 根据名称从验证器类别列表中创建验证器类,这个方法很少使用。
	 * 
	 * @param name
	 *            验证器的类型
	 * @return 获取系统注意的指定类型的验证器
	 */
	public Validator getValidatorByType(String name) {
		if (!StringUtils.hasLength(name))
			return null;
		String key = name.toLowerCase();
		Class vclz = validatorsType.get(key);
		Validator v = null;
		if (vclz != null) {
			try {
				vclz.newInstance();
			} catch (Exception e) {
				throw new FrameworkException(I18n.getLocaleMessage("core.web.Unable.to.initialize.type") + vclz
						+ I18n.getLocaleMessage("core.web.Validator.please.confirm.that.the.test.for.whether.the.default.constructor.function"), e);
			}
		} else
			throw new FrameworkException("系统中没有注册名为" + name + "的验证器，请检查！");
		return v;

	}

	/**
	 * 读取验证结果错误对象
	 * 
	 * @return 返回验证错误或异常结果
	 */
	public Errors getErrors() {
		if (errors == null || errors.get() == null) {
			errors = new java.lang.ThreadLocal<Errors>();
			Errors errs = new Errors();
			errors.set(errs);
		}
		return errors == null || errors.get() == null? new Errors():errors.get();
	}

	public void cleanErrors() {
		errors = null;
	}

	/**
	 * 返回系统中的所有验证器
	 * 
	 * @return 系统中的所有验证器类型
	 */
	public java.util.Collection<Class> getValidatorsType() {
		return validatorsType.values();
	}

	/**
	 * 注册验证器
	 * 
	 * @param name
	 *            验证器名称
	 * @param validator
	 *            验证器
	 */
	public void registerValidator(String name, Validator validator) {
		if (!StringUtils.hasLength(name))
			return;
		String key = name.toLowerCase();
		validators.put(key, validator);
	}

	/**
	 * 注册验证器类
	 * 
	 * @param name
	 * @param clz
	 */
	public void registerValidator(String name, Class clz) {
		if (!StringUtils.hasLength(name))
			return;
		String key = name.toLowerCase();
		if (Validator.class.isAssignableFrom(clz))
			validatorsType.put(key, clz);
	}

	/**
	 * 返回系统中注册的所有验证对象
	 * 
	 * @return 得到目标验证对象的
	 */
	public Collection<TargetObject> getTargetTypes() {
		return targetObjects.values();
	}

	/**
	 * 得到某一种类型的验证对象
	 * 
	 * @param type
	 * @return 目标验证对象集合
	 */
	public java.util.Collection<TargetObject> getValidateObjects(
			ValidateType type) {
		return targetObjects.values();
	}

	/**
	 * 往系统中添加验证对象
	 * 
	 * @param object
	 */
	public void addValidateObject(TargetObject object) {
		targetObjects.put(object.getTarget(), object);
	}

	/**
	 * 返回系统中的验证对象
	 * 
	 * @param obj
	 * @return 得到obj对应的验证目标对象
	 */
	public TargetObject getValidateObject(Object obj) {
		return targetObjects.get(obj);
	}

	/**
	 * 创建可以包含多个验证结果的验证器对象
	 * 
	 * @param obj
	 *            验证主体对象
	 * @return 验证主体对象的验证结果
	 */
	public Errors createValidateMulitErrors(Object obj) {
		if (errorsWrapper == null) {
			errorsWrapper = new java.lang.ThreadLocal<Map>();
			errorsWrapper.set(new java.util.HashMap());
		}
		Errors errs = (Errors) errorsWrapper.get().get(obj);
		if (errs == null) {
			errs = new Errors();
			errorsWrapper.get().put(obj, errs);
		}
		return errs;
	}

	/**
	 * 检测一个属性是否需要进行验证，若检测到属性，则根据ceateInCache参数把验证对象加自动加入到系统缓存中
	 * 
	 * @param property
	 *            要检测的属性
	 * @return 若存在指定属性的验证对象信息，则返回该对象，否则返回null
	 */
	public TargetObject findValidateObject(
			java.beans.PropertyDescriptor property) {
		TargetObject validateObject = null;
		// java.util.List<com.easyjf.web.tools.annotations.Validator>
		// vds = new
		// java.util.ArrayList<com.easyjf.web.tools.annotations.Validator>();
		java.util.List ret = findValidators1(property);
		java.util.Iterator it = ret.iterator();
		while (it.hasNext()) {
			Map map = (Map) it.next();
			java.util.Iterator vsi = map.entrySet().iterator();
			while (vsi.hasNext()) {
				Map.Entry en = (Map.Entry) vsi.next();
				java.util.List<com.easyjf.container.annonation.Validator> vds = (List) en
						.getValue();
				if (vds.size() > 0) {
					for (int i = 0; i < vds.size(); i++) {
						if (validateObject == null)
							validateObject = this.addValidateObject(vds.get(i),
									property);
						else
							updateValidateObject(validateObject, vds.get(i));
					}
					Object key = en.getKey();
					if (key instanceof Field) {
						Field f = (Field) key;
						if (!StringUtils.hasLength(validateObject
								.getDisplayName()))
							validateObject.setDisplayName(f.name());
						if (validateObject.getKey() == null)
							validateObject.setKey(f.key());
						if (!StringUtils.hasLength(validateObject
								.getFieldName()))
							validateObject.setFieldName(f.fieldName());
					} else if (key instanceof FormPO) {
						if (validateObject.getFieldName() == null
								|| validateObject.getFieldName().indexOf(',') > 0)
							validateObject.setFieldName(property.getName());
					}
					if (!StringUtils.hasLength(validateObject.getFieldName()))
						validateObject.setFieldName(property.getName());
				}
			}
		}
		if (validateObject != null && validateObject.getFieldName() == null)
			validateObject.setFieldName(property.getName());
		return validateObject;
	}

	/**
	 * 通过Validator，Field及FormPO标签中的validators标签中的标签来判断验证对象配置。该方法将返回一个结构奇怪的数据结构，也即List<Map<Annonation,List<Validator>>>。即把针对字段的Field、Validator标签；针对方法的Field、Validator标签以；针对AFormPO的标签等分别放在存放在返回列表中。
	 * 
	 * @param property
	 *            属性名称
	 * @return List<Map<Annonation,List<Validator>>>
	 */
	private java.util.List findValidators1(
			java.beans.PropertyDescriptor property) {
		java.util.List ret = new java.util.ArrayList();

		java.lang.reflect.Method method = property.getWriteMethod();// 只有setter才需要进入验证
		if (method != null) {

			// 首先查询property对应的Field属性中的Field配置
			try {
				java.lang.reflect.Field f = method.getDeclaringClass()
						.getDeclaredField(property.getName());
				java.util.List<com.easyjf.container.annonation.Validator> vds = new java.util.ArrayList<com.easyjf.container.annonation.Validator>();
				java.util.Map<Object, List<com.easyjf.container.annonation.Validator>> map = new java.util.HashMap<Object, List<com.easyjf.container.annonation.Validator>>();
				// 首先通过Validator标签来判断验证对象
				com.easyjf.container.annonation.Validator vc = method
						.getAnnotation(com.easyjf.container.annonation.Validator.class);
				if (vc != null) {
					vds.add(vc);
				}
				Field field = f.getAnnotation(Field.class);
				if (field != null)
					vds.addAll(findValidators(field));
				map.put(field, vds);
				ret.add(map);
			} catch (Exception e) {				
				// 没找到在field上的验证标签定义
			}			
			{
				java.util.List<com.easyjf.container.annonation.Validator> vds = new java.util.ArrayList<com.easyjf.container.annonation.Validator>();
				java.util.Map<Object, List<com.easyjf.container.annonation.Validator>> map = new java.util.HashMap<Object, List<com.easyjf.container.annonation.Validator>>();

				// 首先通过Validator标签来判断验证对象
				com.easyjf.container.annonation.Validator vc = method
						.getAnnotation(com.easyjf.container.annonation.Validator.class);
				// TargetObject validateObject = null;
				if (vc != null) {
					vds.add(vc);
				}
				/**
				 * 通过Field标签中的validator及validators来判断验证对象配置
				 */
				Field field = method.getAnnotation(Field.class);
				if (field != null)
					vds.addAll(findValidators(field));

				map.put(field, vds);
				ret.add(map);

			}
			// 通过FormPO中的validators属性来检测配置对象
			java.util.List<com.easyjf.container.annonation.Validator> vds = new java.util.ArrayList<com.easyjf.container.annonation.Validator>();
			java.util.Map<Object, List<com.easyjf.container.annonation.Validator>> map = new java.util.HashMap<Object, List<com.easyjf.container.annonation.Validator>>();
			Class clz= method.getDeclaringClass();
			FormPO tempPO=null;			
			while(clz!=Object.class){
			FormPO po =(FormPO)clz.getAnnotation(FormPO.class);
			if(po!=null && tempPO==null)tempPO=po;			
			if (po != null && po.validators() != null) {
				vds.addAll(findValidators(po, property.getName()));
			}			
			clz=clz.getSuperclass();
			}			
			if(tempPO!=null)
			map.put(tempPO, vds);
			ret.add(map);
		}
		return ret;
	}

	/**
	 * 根据属性查询验证器列表
	 * 
	 * @param property
	 *            属性名称
	 * @return 查询结果
	 */
	public java.util.List<com.easyjf.container.annonation.Validator> findValidators(
			java.beans.PropertyDescriptor property) {
		java.util.List<com.easyjf.container.annonation.Validator> vds = new java.util.ArrayList<com.easyjf.container.annonation.Validator>();
		java.util.List ret = findValidators1(property);
		java.util.Iterator it = ret.iterator();
		while (it.hasNext()) {
			Map map = (Map) it.next();
			java.util.Iterator vsi = map.entrySet().iterator();
			while (vsi.hasNext()) {
				Map.Entry en = (Map.Entry) vsi.next();
				vds.addAll((List) en.getValue());
			}
		}

		return vds;
	}

	/**
	 * 解析Field这个标签，返回验证器对象
	 * 
	 * @param field
	 * @return 查询结果
	 */
	protected java.util.List<com.easyjf.container.annonation.Validator> findValidators(
			Field field) {
		java.util.List<com.easyjf.container.annonation.Validator> vds = new java.util.ArrayList<com.easyjf.container.annonation.Validator>();
		if (field != null) {
			if (!"NULL".equalsIgnoreCase(field.validator().name())) {
				com.easyjf.container.annonation.Validator vconfig = field
						.validator();
				vds.add(vconfig);

			}
			if (field.validators() != null) {
				for (int i = 0; i < field.validators().length; i++) {
					com.easyjf.container.annonation.Validator vtor = field
							.validators()[i];
					vds.add(vtor);
				}
			}
		}
		return vds;
	}

	/**
	 * 从标签FormPO中，返回
	 * 
	 * @param formPo
	 * @param name
	 * @return 查询结果
	 */
	public java.util.List<com.easyjf.container.annonation.Validator> findValidators(
			FormPO formPo, String name) {
		java.util.List<com.easyjf.container.annonation.Validator> vds = new java.util.ArrayList<com.easyjf.container.annonation.Validator>();
		if (formPo != null && formPo.validators() != null) {
			for (int i = 0; i < formPo.validators().length; i++) {
				String fs = formPo.validators()[i].field();
				fs = fs + ",";
				if (fs.toLowerCase().indexOf(name.toLowerCase() + ",") >= 0)
					vds.add(formPo.validators()[i]);
			}
		}
		return vds;

	}

	/**
	 * 更新验证目标对象中的内容
	 * 
	 * @param targetObject
	 * @param vc
	 */
	private void updateValidateObject(TargetObject targetObject,
			com.easyjf.container.annonation.Validator vc) {
		targetObject.setType(vc.type());
		if (!"".equals(vc.msg()))
			targetObject.setDefaultMessage(vc.msg());
		if (!"".equals(vc.displayName()))
			targetObject.setDisplayName(vc.displayName());
		if (!"".equals(vc.field()))
			targetObject.setFieldName(vc.field());
		if (!"".equals(vc.key()))
			targetObject.setKey(vc.key());
		if (vc.required())
			targetObject.setRequried(vc.required());
		if (!"".equals(vc.value()))
			targetObject.setValues(vc.value());
		Validator vtor = this.getValidator(vc.name());
		targetObject.addValidator(vtor);
	}

	/**
	 * 根据验证对象配置信息创建验证对象
	 * 
	 * @param vc
	 *            基于annotations的配置信息
	 * @param target
	 *            属性所属的目标对象
	 * @return 新创建或者已经存在的验证TargetObject
	 */
	private TargetObject createValidateObject(
			com.easyjf.container.annonation.Validator vc, Object target) {
		TargetObject obj = new TargetObject(this);
		obj.setTarget(target);
		this.updateValidateObject(obj, vc);
		return obj;
	}

	/**
	 * 往错误结果集中加入自定义的错误
	 * 
	 * @param name
	 * @param msg
	 * @param type
	 */
	public void addCustomError(String name, String msg, ValidateType type) {
		TargetObject tempTarget = new TargetObject();
		tempTarget.setDefaultMessage(msg);
		tempTarget.setFieldName(name);
		tempTarget.setDisplayName(name);
		tempTarget.setType(type);
		ValidateResult vr = new ValidateResult(tempTarget);
		this.getErrors().addError(vr);

	}

	/**
	 * 根据验证对象配置信息把验证对象添加到缓存中，并返回该对象，以供调用者使用
	 * 
	 * @param vc
	 *            基于annotations的验证对象配置信息
	 * @param target
	 *            验证对象所属的目标对象
	 * @return 具体的验证对象
	 */
	private TargetObject addValidateObject(
			com.easyjf.container.annonation.Validator vc, Object target) {
		TargetObject obj = createValidateObject(vc, target);
		this.addValidateObject(obj);
		return obj;
	}

	public Class findTargetType(String name) {
		return targetTypes.get(name);
	}

	public void addTargetType(String name, Class clz) {
		if (targetTypes.containsKey(name))
			logger.warn(I18n.getLocaleMessage("core.web.entitled.coverage") + name + I18n.getLocaleMessage("core.web.validator.class"));
		targetTypes.put(name, clz);
	}
}
