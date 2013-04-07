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
package com.easyjf.core.service;

import java.io.Serializable;

import com.easyjf.core.support.query.IQueryObject;
import com.easyjf.web.tools.IPageList;
/**
 * 包含普通Crud应用的Service
 * @author 大峡
 *
 */
public interface EntityCrudService {

	Object get(Serializable id);

	IPageList query(IQueryObject queryObject);

	void remove(Serializable id);

	void save(Object obj);

	void update(Object obj);
}
