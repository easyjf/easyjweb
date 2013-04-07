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
package com.easyjf.core.support;

import java.util.Collection;

import com.easyjf.core.dao.GenericDAO;
import com.easyjf.core.support.query.GenericPageList;

public class GenericPageListForObject extends GenericPageList {
	public GenericPageListForObject(Class cls, String scope, Collection paras,
			GenericDAO dao) {
		super(cls, scope, paras, dao);
	}
	
	public void doList(int currentPage, int pageSize) {
		String subScope="";
		if(scope.indexOf("where")>0){
			subScope=scope.substring(scope.indexOf("where")+5);
		}
		super.doList(pageSize, currentPage, scope, subScope);
	} 
}
