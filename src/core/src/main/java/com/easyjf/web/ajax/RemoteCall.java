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
package com.easyjf.web.ajax;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.easyjf.beans.BeanUtils;
import com.easyjf.beans.BeanWrapper;
import com.easyjf.util.ClassUtils;
import com.easyjf.web.core.FrameworkEngine;
import com.easyjf.web.exception.FrameworkException;
import com.easyjf.web.exception.ValidateException;

/**
 * 这个类主要用来执行远程调操作
 * 
 * @author 大峡
 * @since 2006-12-29
 */
public class RemoteCall {
	private static final Logger logger = Logger.getLogger(RemoteCall.class);
	private String serviceName;
	private String methodName;
	private Map paras;

	public RemoteCall(String serviceName, String methodName, Map paras) {
		this.serviceName = serviceName;
		this.methodName = methodName;
		this.paras = paras;
	}

	public String execute() throws Exception {
		Object service = AjaxUtil.getServiceContainer().getService(serviceName);
		if (service == null)
			throw new ServiceNotAvailableException(serviceName);
		CallParameter[] ps = convertJsonParamater(paras);
		Class[] clzs = new Class[ps.length];		
		Object[] objs = new Object[ps.length];
		convertCallParameter2Obj(clzs, objs, ps);
		Method method = findRemoteMethod(service, clzs);
		Object result = method.invoke(service, objs);
		return AjaxUtil.getJSON(result);
	}

	/**
	 * 查找匹配的方法
	 * @param service
	 * @param clzs
	 * @return
	 * @throws Exception
	 */
	private Method findRemoteMethod(Object service, Class[] clzs)
			throws Exception {
		java.lang.reflect.Method ret = null;
		if(clzs==null)clzs=new Class[0];
		if (AjaxUtil.getServiceContainer().getSignatures(serviceName,
				methodName) != null) {
			Method[] ms = service.getClass().getMethods();
			for (Method m : ms) {
				if (m.getName().equals(methodName))
				{
					ret=m;
					for(int i=0;i<m.getParameterTypes().length&&i<clzs.length;i++)
					{
					if(!m.getParameterTypes()[i].isAssignableFrom(clzs[i]))ret=null;	
					}					
				}
			}			
		} else {
			ret = service.getClass().getMethod(methodName, clzs);
		}
		if (ret == null)
			throw new ServiceNotAvailableException(serviceName + "."
					+ methodName);
		if (!AjaxUtil.isRemoteMethod(serviceName, ret))
			throw new MethodNotAvailableException(ret);
		return ret;
	}

	public CallParameter[] convertJsonParamater(Map map) {
		java.util.Map propertys = new java.util.HashMap();
		java.util.Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
			String name = (String) it.next();
			logger.debug("remote call paramater's name:" + name);
			logger.debug("parameter value:" + map.get("name"));
			if (name.length() > 10
					&& name.substring(0, 11).equals("ajax-call-p")) {
				String index = name.substring(11, name.indexOf('.', 11));
				addProperty(propertys, Integer.parseInt(index), name
						.substring(name.indexOf('.', 11) + 1), map.get(name));
			}
		}
		List ps = new ArrayList(propertys.values());
		java.util.Collections.sort(ps, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				return ((CallParameter) arg0).getIndex()
						- ((CallParameter) arg1).getIndex();
			}
		});
		CallParameter[] cps = new CallParameter[ps.size()];
		ps.toArray(cps);
		return cps;
	}

	public void convertCallParameter2Obj(Class[] clzs, Object[] objs,
			CallParameter[] ps) {
		for (int i = 0; i < ps.length; i++) {
			CallParameter parameter = ps[i];
			clzs[i] = ps[i].getType();
			if (ClassUtils.isPrimitiveOrWrapper(clzs[i])
					|| clzs[i] == String.class) {
				if (parameter.getPropetys().get("value") != null)
					objs[i] = JSonConvertUtil.simpleConvert((String) parameter
							.getPropetys().get("value"), clzs[i]);// ,new
				// Object[]{});
			} else if (Number.class.isAssignableFrom(clzs[i])
					|| java.util.Date.class.isAssignableFrom(clzs[i])) {
				objs[i] = com.easyjf.beans.BeanUtils.convertType(parameter
						.getPropetys().get("value"), clzs[i]);// ,new
				// Object[]{});
			} else if (clzs[i].isArray()) {
				// 数据组转换
				objs[i] = JSonConvertUtil.arrayConvert((String) parameter
						.getPropetys().get("value"), clzs[i]);
			}
			else if(Map.class.isAssignableFrom(clzs[i])){
				Map map =new java.util.HashMap();
				java.util.Iterator pvs = parameter.getPropetys().entrySet()
				.iterator();
				while (pvs.hasNext()) {
					Map.Entry en = (Map.Entry) pvs.next();
					String propertyName = (String) en.getKey();					
					map.put((String) en.getKey(), en.getValue());
				}
				objs[i]=map;
			}				
			else if (Collection.class.isAssignableFrom(clzs[i])) {				
				objs[i] = JSonConvertUtil.collectionConvert((String) parameter
						.getPropetys().get("value"), clzs[i]);
			}			
			else {				
				objs[i] = BeanUtils.instantiateClass(clzs[i]);
				FrameworkEngine.form2Obj(parameter.getPropetys(),objs[i]);
				if(FrameworkEngine.getValidateManager().getErrors().hasError())
				{
					throw new ValidateException();
				}
				/*BeanWrapper wrapper = new BeanWrapper(objs[i]);
				java.util.Iterator pvs = parameter.getPropetys().entrySet()
						.iterator();
				while (pvs.hasNext()) {
					Map.Entry en = (Map.Entry) pvs.next();
					String propertyName = (String) en.getKey();
					if (propertyName.indexOf('.') > 0) {
						initBeanProperty(objs[i], propertyName);
					}
					if (wrapper.isWritableProperty(propertyName))
						wrapper.setPropertyValue((String) en.getKey(), en
								.getValue());
				}*/
			}
		}
	}

	public void addProperty(java.util.Map map, int index, String name,
			Object value) {
		CallParameter p = (CallParameter) map.get("property" + index);
		if (p == null) {
			p = new CallParameter();
			p.setIndex(index);
			// p.setName(name);
			map.put("property" + index, p);
		}
		if ("java-type".equalsIgnoreCase(name)) {
			try {
				p.setType(ClassUtils.forName((String) value));
			} catch (Exception e) {
				throw new FrameworkException(e.getMessage());
			}
		} else
			p.addProperty(name, value);
	}

	// 使用默认构造子初始化一个二级属性
	public void initBeanProperty(Object bean, String propertyName) {
		String p = propertyName.substring(0, propertyName.indexOf('.'));
		BeanWrapper wrapper = new BeanWrapper(bean);
		if (wrapper.getPropertyValue(p) == null)
			wrapper.setPropertyValue(p, BeanUtils.instantiateClass(wrapper
					.getPropertyDescriptor(p).getPropertyType()));
		String nextp = propertyName.substring(propertyName.indexOf('.') + 1);
		if (nextp.indexOf('.') > 0) {
			initBeanProperty(wrapper.getPropertyValue(p), nextp);
		}
	}
}
