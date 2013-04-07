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
package com.easyjf.web.tools;

import java.io.Serializable;

import com.easyjf.beans.BeanUtils;
import com.easyjf.core.support.query.IQueryObject;
import com.easyjf.core.support.query.QueryObject;
import com.easyjf.util.CommUtil;
import com.easyjf.web.Module;
import com.easyjf.web.Page;
import com.easyjf.web.WebForm;

/**
 * 
 * <p>
 * Title: 通过添删改查(CRUD)处理Action类
 * </p>
 * <p>
 * Description: 处理普通数据表的添删改查(CRUD)处理的抽象类，用户只需继承该Action，并根据自身的情况实现其中的模板方法即可。
 * 该抽象类除了提供了一些固定的系统命令以外，还提供了CmdAction的功能，也即要使用系统外命令的时候，如XX直接使用doXX(WebForm form,
 * Module module)即可。
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: www.easyjf.com
 * </p>
 * 
 * @author 蔡世友
 * @version 1.0
 */
public abstract class AbstractCrudAction extends com.easyjf.web.core.AbstractPageCmdAction{
	@SuppressWarnings("unchecked")
	protected abstract Class entityClass();

	/**
	 * 默认页，直接转到doList执行
	 * 
	 * @param form
	 */
	public Page doInit(WebForm form, Module module) {
		return go("list");
	}

	public Page doAdd(WebForm form, Module module) {
		return doNew(form, module);
	}

	/**
	 * 导入添加new界面的页面
	 * 
	 */
	public Page doNew(WebForm form, Module module) {
		return module.findPage("edit");
	}

	/**
	 * 与doSave方法执行同样的功能。
	 * 
	 * @param form
	 */
	public void doCreate(WebForm form) {
		doSave(form);
	}

	/**
	 * 在添加页面点击提交按钮时执行数据保存及创建的操作
	 * 
	 * @param form
	 */
	@SuppressWarnings("unchecked")
	public Page doSave(WebForm form) {
		Object obj = form.toPo(entityClass());
		if (hasErrors()) {
			form.set("id", null);
			return forward("new");
		}
		saveEntity(obj);
		return go("list");
	}

	/**
	 * 导入编辑页面，根据id值导入
	 * 
	 * @param form
	 */
	public void doEdit(WebForm form) {
		Object obj = findEntityObject(getIdValue(form));
		form.addPo(obj);
	}

	/**
	 * 数据查看页面
	 * 
	 * @param form
	 */
	public void doPreview(WebForm form) {
		Object obj = findEntityObject(getIdValue(form));
		form.addPo(obj);
	}

	/**
	 * 数据更新页面
	 * 
	 * @param form
	 */
	public Page doUpdate(WebForm form) {
		Object obj = findEntityObject(getIdValue(form));
		form.toPo(obj, true);
		if (hasErrors())
			return forward("new");
		updateEntity(obj);
		return go("list");
	}

	/**
	 * Del方法的别名，执行删除操作
	 * 
	 * @param form
	 */
	public void doDelete(WebForm form) {
		doDel(form);
	}

	/**
	 * 根据id删除数据
	 * 
	 * @param form
	 */
	public void doDel(WebForm form) {
		removeEntity(getIdValue(form));
		go("list");
	}

	/**
	 * 根据ids删除数据
	 * 
	 * @param form
	 */
	public void doBatchDelete(WebForm form) {
		String mulitId = CommUtil.null2String(form.get("mulitId"));
		if (mulitId.endsWith(","))
			mulitId = mulitId.substring(0, mulitId.length() - 1);
		String[] idsStr = mulitId.split(",");
		if (idsStr.length > 0) {
			for (String id : idsStr) {
				if (!"".equals(id)) {
					removeEntity(Long.parseLong(id));
				}
			}
		}
		go("list");
	}

	/**
	 * 列表页面
	 * 
	 * @param form
	 */
	@SuppressWarnings("unchecked")
	public void doList(WebForm form) {
		IPageList pageList = queryEntity((IQueryObject) form
				.toPo(getQueryClass()));
		CommUtil.saveIPageList2WebForm(pageList, form);
	}

	/**
	 * 从WebForm中得到实体id的值
	 * 
	 * @param form
	 * @return
	 */
	protected Serializable getIdValue(WebForm form) {
		return (Serializable) BeanUtils.convertType(form.get(getIdName()),
				getIdClass());
	}

	/**
	 * 得到查询对象的类型，默认为通用查询对象QueryObject.class
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Class getQueryClass() {
		return QueryObject.class;
	}

	/**
	 * 得到主键的id类型，默认为Long
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Class getIdClass() {
		return Long.class;
	}

	/**
	 * 得到Entity的主键属性名称，默认为id
	 * 
	 * @return
	 */
	protected String getIdName() {
		return "id";
	}

	/**
	 * 保存实体
	 * 
	 * @param obj
	 *            需要保存的持久对象
	 */
	protected abstract void saveEntity(Object obj);

	/**
	 * 查询实体
	 * 
	 * @param id
	 *            根据id查询实体对象
	 * @return 如果找到指定id的实体，则返回该实体对象，否则返回null
	 */
	protected abstract Object findEntityObject(Serializable id);

	/**
	 * 更新实体
	 * 
	 * @param obj
	 *            需要更新的实体对象
	 */
	protected abstract void updateEntity(Object obj);

	/**
	 * 删除实体
	 * 
	 * @param id
	 *            需要删除的实体id值
	 */
	protected abstract void removeEntity(Serializable id);

	/**
	 * 查询实体
	 * 
	 * @param queryObject
	 *            查询参数
	 * @return 根据指定的查询参数，查询实体。
	 */
	protected abstract IPageList queryEntity(IQueryObject queryObject);
}