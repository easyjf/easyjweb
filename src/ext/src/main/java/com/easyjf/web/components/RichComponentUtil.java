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

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.easyjf.beans.BeanWrapper;
import com.easyjf.web.ActionContext;
import com.easyjf.web.ajax.JSonConvertUtil;
import com.easyjf.web.core.ICustomGlobalsUtilBean;
/**
 * 
 * @author 大峡
 *
 */
public class RichComponentUtil implements ICustomGlobalsUtilBean {
	/**
	 * 用来把form中的Components转换成客户端的组件
	 * 
	 * @return 返回生成富客户端界面效果的<script>脚本断
	 */
	public static String getComponents() {
		StringBuffer ret = new StringBuffer("<script>");
		ret.append("Ext.onReady(function(){");
		if (ActionContext.getContext() != null
				&& ActionContext.getContext().getWebInvocationParam() != null) {
			List components = ActionContext.getContext()
					.getWebInvocationParam().getForm().getComponents();
			for (int i = 0; i < components.size(); i++) {
				String varName = "v" + i;
				String initMethod = null;
				boolean init = false, isGlobal = false;
				Object o = components.get(i);
				if (o instanceof BaseComponent) {
					BaseComponent bo = (BaseComponent) o;
					String v1 = bo.getVarName() != null ? bo.getVarName() : bo
							.getId();
					if (v1 != null)
						varName = v1;
					initMethod = (String) bo.get("initMethod");
					init = bo.isInit();
					if (bo.isGlobal())
						isGlobal = true;// 处理全局变量或函数名称
				}
				String v = isGlobal ? varName : "var " + varName;
				String json = "function(){" + getRichComponent(o) + "}()";
				v += "=" + json + ";";
				if (o instanceof ICustomComponent) {
					v += o.getClass().getSimpleName()
							+ "=function(c){if(c)Ext.apply(this,c);";
					if (o instanceof BaseComponent
							&& !((BaseComponent) o).reloadComponents().isEmpty()) {
						Map<String,BaseComponent>  loads = ((BaseComponent) o).reloadComponents();
						for(java.util.Map.Entry<String, BaseComponent> en:loads.entrySet())
						{
							String s=en.getKey();
							v+="if(this."+s+" && this."+s+".rendered ){this."+s+"=new "+en.getValue().clz()+"(this."+s+".initialConfig);}";
						}
					}
					v += "};" + "Ext.extend(" + o.getClass().getSimpleName()
							+ "," + ((ICustomComponent) o).superClz() + ","
							+ varName + ");";
				}
				if (init) {
					v += varName + "."
							+ (initMethod == null ? "render" : initMethod)
							+ "();";
				}
				ret.append(v);
			}
		}
		ret.append("});");
		ret.append("</script>");
		return ret.toString();
	}

	public String getName() {
		return "RC";
	}

	public static String getJSON(Object value) {
		Handler handler = new Handler();
		return handler.getJSON(value);
	}

	public static String getRichComponent(Object value) {
		Handler handler = new Handler();
		return handler.getRichComponent(value);
	}

	static class Handler {
		private java.util.Map vars = new java.util.HashMap();
		private java.util.Stack stack = new java.util.Stack();
		private java.util.Stack declars = new java.util.Stack();
		private int d = 0;

		public String getJSON(Object value) {
			String ret = generatorJSON(value);
			String prefix = "";
			for (int i = 0; i < declars.size(); i++)
				prefix += declars.get(i);
			for (int i = 0; i < stack.size(); i++)
				prefix += stack.get(i);
			return prefix + "return " + ret + ";";
		}

		public String getRichComponent(Object value) {
			String ret = generatorRichComponent(value, false);
			String prefix = "";
			for (int i = 0; i < declars.size(); i++)
				prefix += declars.get(i);
			for (int i = 0; i < stack.size(); i++)
				prefix += stack.get(i);
			return prefix + "return " + ret + ";";
		}

		public String generatorJSON(Object value) {
			String ret = "";
			String varName = "v" + (d++);
			if (value == null)
				ret += "null";
			else if (JSonConvertUtil.isSimpleType(value.getClass())) {// 简单类型
				if (value instanceof Date)
					return "new Date(" + ((Date) value).getTime() + ")";
				else if (value instanceof String || value.getClass().isEnum()) {
					String sv = value.toString();
					return "\"" + sv + "\"";
					/*
					 * sv = sv.replaceAll("\"", "&quot;"); return "\"" +
					 * CommUtil.convert2unicode(sv, true) +
					 * "\".replace(/&quot;/g, '\"')";
					 */
				} else
					return "" + value;
			} else {
				vars.put(value, varName);
				if (value instanceof java.util.Map) {
					ret = handlerMap(varName, value);
				} else if (value instanceof Collection) {
					ret = handlerCollection(varName, value);
				} else if (value.getClass().isArray()) {
					ret = handlerArray(varName, value);
				} else if (value instanceof IRichComponent) {
					varName = getVarName(value, varName);
					vars.put(value, varName);
					if (value instanceof Function) {
						ret = handlerRichFunction(varName, (Function) value);
					} else
						ret = handlerRichComponent(varName,
								(IRichComponent) value, true);
				} else {
					ret = handlerObject(varName, value);
				}
			}
			return ret;
		}

		public String handlerCollection(String varName, Object value) {
			String ret = "";
			declars.push("var " + varName + "=[];");
			java.util.Iterator it = ((Collection) value).iterator();
			int i = 0;
			while (it.hasNext()) {
				Object v = it.next();
				if (v == null) {
					ret += "null";
				} else {
					ret += varName + "[" + i + "]=";
					String o = (String) vars.get(v);
					if (o == null)
						o = generatorJSON(v);
					ret += o + ";";
				}
				i++;
			}
			if (ret.endsWith(";;"))
				ret = ret.substring(0, ret.length() - 1);
			stack.push(ret);
			ret = varName;
			return ret;
		}

		public String handlerArray(String varName, Object value) {
			String ret = "";
			declars.push("var " + varName + "=[];");
			int max = Array.getLength(value);
			for (int i = 0; i < max; i++) {
				Object v = Array.get(value, i);
				if (v == null) {
					ret += "null";
				} else {
					String o = (String) vars.get(v);
					ret += varName + "[" + i + "]=";
					if (o == null)
						o = generatorJSON(v);
					ret += o + ";";
				}
			}
			if (ret.endsWith(";;"))
				ret = ret.substring(0, ret.length() - 1);
			stack.push(ret);
			ret = varName;
			return ret;
		}

		public String handlerMap(String varName, Object value) {
			String ret = "";
			declars.push("var " + varName + "={};");
			Map map = (Map) value;
			java.util.Iterator it = map.keySet().iterator();
			while (it.hasNext()) {
				Object propertyName = (String) it.next();
				ret += varName + "." + propertyName + "=";
				Object v = map.get(propertyName);
				if (v == null) {
					ret += "null;";
				} else {
					String o = (String) vars.get(v);
					if (o == null)
						o = generatorJSON(v);
					ret += o + ";";
				}
			}
			if (ret.endsWith(";;"))
				ret = ret.substring(0, ret.length() - 1);
			stack.push(ret);
			ret = varName;
			return ret;
		}

		public String handlerObject(String varName, Object value) {
			String ret = "";
			declars.push("var " + varName + "={};");
			BeanWrapper wrapper = new BeanWrapper(value);
			java.beans.PropertyDescriptor[] pds = wrapper
					.getPropertyDescriptors();
			for (int i = 0; i < pds.length; i++) {
				String propertyName = pds[i].getName();
				if (!"class".equals(propertyName)
						&& wrapper.isReadableProperty(propertyName)) {
					Object v = wrapper.getPropertyValue(propertyName);
					ret += varName + "." + propertyName + "=";
					if (v == null) {
						ret += "null;";
					} else {
						String o = (String) vars.get(v);
						if (o == null)
							o = generatorJSON(v);
						ret += o + ";";
					}
				}
			}
			if (ret.endsWith(";;"))
				ret = ret.substring(0, ret.length() - 1);
			stack.push(ret);
			ret = varName;
			return ret;
		}

		public String generatorRichComponent(Object value, boolean isChild) {
			String ret = "";
			String varName = "v" + (d++);
			if (value == null)
				ret += "null";
			else if (value instanceof IRichComponent) {
				varName = getVarName(value, varName);
				vars.put(value, varName);
				if (value instanceof Function) {
					ret = handlerRichFunction(varName, (Function) value);
				} else
					ret = handlerRichComponent(varName, (IRichComponent) value,
							isChild);
			} else
				return generatorJSON(value);
			return ret;
		}

		private String getVarName(Object value, String autoName) {
			String varName = autoName;
			if (value instanceof BaseComponent) {
				BaseComponent bo = (BaseComponent) value;
				String v1 = bo.getVarName() != null ? bo.getVarName() : bo
						.getId();
				if (v1 != null)
					varName = v1;
			}
			return varName;
		}

		public String handlerRichFunction(String varName, Function value) {
			String ret = "";
			declars.push("var " + varName + ";");
			ret = varName + "=function(";
			if (value.getParas() != null) {
				for (int i = 0; i < value.getParas().length; i++) {
					ret += value.getParas()[i];
					if (i < value.getParas().length - 1)
						ret += ",";
				}
			}
			ret += "){";
			ret += value.getCode();
			ret += "};";
			stack.push(ret);
			return varName;
		}

		private boolean isEmpty(Object v) {
			if (v == null)
				return true;
			if (v instanceof Collection) {
				return ((Collection) v).isEmpty();
			} else if (v instanceof Map) {
				return ((Map) v).isEmpty();
			}
			return false;
		}

		protected boolean isComponentSystem(String name) {
			return "init,lazy,global,initMethod,initProperty,varName,"
					.indexOf(name + ",") >= 0;
		}

		public String handlerRichComponent(String varName,
				IRichComponent value, boolean isChild) {
			String ret = "";
			boolean isGlobal = false;
			if (value instanceof BaseComponent) {
				if (((BaseComponent) value).isGlobal())
					isGlobal = true;
			}
			declars.push((isGlobal ? "" : "var ") + varName + "={};");
			BeanWrapper wrapper = new BeanWrapper(value);
			java.beans.PropertyDescriptor[] pds = wrapper
					.getPropertyDescriptors();
			for (int i = 0; i < pds.length; i++) {
				String propertyName = pds[i].getName();
				if (!"class".equals(propertyName)
						&& wrapper.isReadableProperty(propertyName)
						&& !isComponentSystem(propertyName)) {
					Object v = wrapper.getPropertyValue(propertyName);
					if (!isEmpty(v)) {
						ret += varName + "." + propertyName + "=";
						String o = (String) vars.get(v);
						if (o == null) {
							o = generatorRichComponent(v, true);
						}
						ret += o + ";";

					}
				}
			}
			Map map = ((IRichComponent) value).readProperties();
			java.util.Iterator it = map.keySet().iterator();
			while (it.hasNext()) {
				Object propertyName = (String) it.next();
				Object v = map.get(propertyName);
				if (!isEmpty(v)) {
					ret += varName + "." + propertyName + "=";
					String o = (String) vars.get(v);
					if (o == null) {
						o = generatorRichComponent(v, true);
					}
					ret += o + ";";
				}
			}
			if (!(value instanceof ICustomComponent)
					&& (value instanceof BaseComponent)
					&& (!isChild || !((BaseComponent) value).isLazy())
					&& value.clz() != null)// 如果是一级组件，则需要直接初始化
			{
				ret += getInitComponent(varName, (BaseComponent) value);
			}
			stack.push(ret);
			ret = varName;
			return ret;
		}

		protected String getInitComponent(String varName, BaseComponent value) {
			String ret = "";
			String proxyName = "temp";
			ret += "var " + proxyName + "=" + varName + ";";
			if (value.getInitProperty() != null)
				ret += varName + "=new " + value.clz() + "(" + proxyName + "."
						+ value.getInitProperty() + ");";
			else
				ret += varName + "=new " + value.clz() + "(" + proxyName + ");";
			if (value.getCallback() != null)// 如果需要使用回调来还原相关信息
			{
				ret += "if (" + proxyName + ".callback)" + proxyName
						+ ".callback.call(" + varName + "," + proxyName + ");";
			}
			return ret;
		}
	}
}
