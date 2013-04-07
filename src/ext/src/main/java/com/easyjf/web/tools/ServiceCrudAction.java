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

import com.easyjf.core.service.EntityCrudService;
import com.easyjf.core.support.query.IQueryObject;

/**
 * 基于包含Crud操作的Service增删改查应用的基类,该类需要子类提供一个EntityCrudService才能工作。
 * 
 * @author 大峡
 * 
 */
public abstract class ServiceCrudAction extends AbstractCrudAction {

	public abstract EntityCrudService getService();

	@Override
	protected Object findEntityObject(Serializable id) {
		return getService().get(id);
	}

	@Override
	protected IPageList queryEntity(IQueryObject queryObject) {
		return getService().query(queryObject);
	}

	@Override
	protected void removeEntity(Serializable id) {
		getService().remove(id);
	}

	@Override
	protected void saveEntity(Object obj) {
		getService().save(obj);
	}

	@Override
	protected void updateEntity(Object obj) {
		getService().update(obj);
	}
}
