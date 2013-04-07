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
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

public class EntityMangerDaoSurport<T> implements IDAO<T> {

	private static EntityManagerFactory emf;

	private static ThreadLocal<EntityManager> em;

	public EntityManager getEntityManager() {
		EntityManager manager = em.get();
		if (manager == null) {
			manager = emf.createEntityManager();
			em.set(manager);
		}
		return manager;
	}

	public boolean del(Object obj) {
		getEntityManager().getTransaction().begin();
		if (!getEntityManager().contains(obj))
			obj = getEntityManager().merge(obj);
		getEntityManager().remove(obj);
		getEntityManager().getTransaction().commit();
		return true;
	}

	public int execute(String sql) {
		return execute(sql, null);
	}

	public int execute(String sql, Collection paras) {
		Query query = getEntityManager().createNativeQuery(sql);
		int parameterIndex = 0;
		if (paras != null && paras.size() > 0) {
			for (Object obj : paras) {
				query.setParameter(parameterIndex++, obj);
			}
		}
		return query.executeUpdate();
	}

	public T get(Class<T> clz, Serializable id) {
		return getEntityManager().find(clz, id);
	}

	public T getBy(Class<T> clz, String fieldName, Serializable value) {
		Query query = getEntityManager().createQuery(
				"from " + clz + " where fieldName=?");
		query.setParameter(0, value);
		return (T) query.getSingleResult();
	}

	public List query(Class clz, String scope) {
		return query(clz, scope, null);
	}

	public List query(Class clz, String scope, Collection paras) {
		return query(clz, scope, null, -1, -1);
	}

	public List query(Class clz, String scope, Collection paras, int begin,
			int max) {
		Query query = getEntityManager().createQuery(
				"from " + clz + " where " + scope);
		int parameterIndex = 0;
		if (paras != null && paras.size() > 0) {
			for (Object obj : paras) {
				query.setParameter(parameterIndex++, obj);
			}
		}
		if (begin >= 0 && max > 0) {
			query.setFirstResult(begin);
			query.setMaxResults(max);
		}
		return query.getResultList();
	}

	public boolean save(Object obj) {
		getEntityManager().getTransaction().begin();
		getEntityManager().persist(obj);
		getEntityManager().getTransaction().commit();
		return true;
	}

	public Object uniqueResult(String sql) {
		return uniqueResult(sql, null);
	}

	public Object uniqueResult(String sql, Collection paras) {
		Query query = getEntityManager().createQuery(sql);
		int parameterIndex = 0;
		if (paras != null && paras.size() > 0) {
			for (Object obj : paras) {
				query.setParameter(parameterIndex++, obj);
			}
		}
		return query.getSingleResult();
	}

	public boolean update(Object obj) {
		getEntityManager().getTransaction().begin();
		getEntityManager().merge(obj);
		getEntityManager().getTransaction().commit();
		return true;
	}
}
