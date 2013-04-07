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

import com.easyjf.core.dao.GenericDAO;
import com.easyjf.core.support.query.IQueryObject;
import com.easyjf.core.support.query.QueryUtil;

/**
 * 添加改查模型的基类，实现基本的添删改查、分页等功能。该类通过子类中提供的具体的entityClass方法及getDao方法来进行工作。
 * 子类一般的实现如下所示：
 * 
 * <pre>
 * public class PersonAction extends CrudAction {
 * 	&#064;Inject(name = &quot;personDao&quot;)
 * 	private GenericDAO&lt;Person&gt; dao;
 * 
 * 	public void setDao(GenericDAO&lt;Person&gt; dao) {
 * 		this.dao = dao;
 * 	}
 * 
 * 	protected Class entityClass() {
 * 		return Person.class;
 * 	}
 * 
 * 	public GenericDAO getDao() {
 * 		return dao;
 * 	}
 * }
 * </pre>
 * 
 * @author 大峡,williamRaym
 * 
 */
abstract public class CrudAction extends AbstractCrudAction {

	public abstract GenericDAO getDao();

	@Override
	protected Object findEntityObject(Serializable id) {
		return getDao().get(id);
	}

	@Override
	protected IPageList queryEntity(IQueryObject queryObject) {
		return QueryUtil.query(queryObject, entityClass(), getDao());
	}

	@Override
	protected void removeEntity(Serializable id) {
		getDao().remove(id);
	}

	@Override
	protected void saveEntity(Object obj) {
		getDao().save(obj);
	}

	@Override
	protected void updateEntity(Object obj) {
		getDao().update(obj);
	}
}
