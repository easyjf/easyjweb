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
package com.easyjf.core.support.query;

/**
 * 基础的查询对象类，包装了page信息和order信息
 * 
 * @author stefanie wu
 * 
 */
public class BaseQueryObject extends QueryObject {
	protected boolean isDel = true;

	protected boolean all = false;

	public void setAll(boolean all) {
		this.all = all;
	}

	public boolean isDel() {
		return isDel;
	}

	public void setDel(boolean isDel) {
		this.isDel = isDel;
	}

	@Override
	public String getQuery() {
		if (!all) {
			if (isDel) {
				addQuery("obj.status >= 0", null);
			} else {
				addQuery("obj.status = -1", null);
			}
		}
		return super.getQuery();
	}
}
