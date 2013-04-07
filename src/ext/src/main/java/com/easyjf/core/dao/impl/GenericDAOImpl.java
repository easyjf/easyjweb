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
package com.easyjf.core.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;

import com.easyjf.core.dao.CanotRemoveObjectException;
import com.easyjf.core.dao.GenericDAO;
import com.easyjf.util.I18n;

public class GenericDAOImpl<T> extends JpaDaoSupport implements GenericDAO<T> {

	private Class<T> clazz;

	public GenericDAOImpl(Class<T> clazz) {
		this.clazz = clazz;
	}

	/*
	 * public void setClazz(Class<T> clazz) { this.clazz = clazz; } public
	 * Class<T> getClazz() { return clazz; }
	 */
	public T get(Serializable id) {
		if (id == null)
			return null;
		return this.getJpaTemplate().find(clazz, id);
	}

	public List<T> find(final String queryStr, final Object[] params,
			final int begin, final int max) {
		List<T> ret = (List<T>) this.getJpaTemplate().execute(
				new JpaCallback() {
					public Object doInJpa(EntityManager em)
							throws PersistenceException {
						
						String clazzName = clazz.getName();
						StringBuffer sb = new StringBuffer("select obj from ");
						sb.append(clazzName).append(" obj").append(" where ")
								.append(queryStr);
						Query query = em.createQuery(sb.toString());
						int parameterIndex = 1;
						if (params != null && params.length > 0) {
							for (Object obj : params) {
								query.setParameter(parameterIndex++, obj);
							}
						}
						if (begin >= 0 && max > 0) {
							query.setFirstResult(begin);
							query.setMaxResults(max);
						}
						if (begin >= 0 && max > 0) {
							query.setFirstResult(begin);
							query.setMaxResults(max);
						}
						return query.getResultList();
					}
				});
		if (ret != null && ret.size() >= 0) {
			return ret;
		} else {
			return new ArrayList<T>();
		}
	}

	public List query(final String queryStr, final Object[] params,
			final int begin, final int max) {
		List ret = (List) this.getJpaTemplate().execute(new JpaCallback() {

			public Object doInJpa(EntityManager em) throws PersistenceException {
				Query query = em.createQuery(queryStr);
				int parameterIndex = 1;
				if (params != null && params.length > 0) {
					for (Object obj : params) {
						query.setParameter(parameterIndex++, obj);
					}
				}
				if (begin >= 0 && max > 0) {
					query.setFirstResult(begin);
					query.setMaxResults(max);
				}
				return query.getResultList();
			}
		});
		if (ret != null && ret.size() >= 0) {
			return ret;
		} else {
			return new ArrayList();
		}
	}

	public List query(String jpql, Object[] params) {
		return query(jpql,params,0,-1);
	}

	public List query(String jpql) {
		return query(jpql,null);
	}

	public void remove(Serializable id) throws CanotRemoveObjectException {
		if (id == null)
			throw new java.lang.IllegalArgumentException(I18n
					.getLocaleMessage("ext.Id.value.can.not.be.empty"));
		T object = this.get(id);
		if (object != null) {
			try {
				this.getJpaTemplate().remove(object);
			} catch (Exception e) {
				throw new CanotRemoveObjectException(e.getMessage());
			}
		}
	}

	public <T> T save(T instance) {
		this.getJpaTemplate().persist(instance);
		return instance;
	}

	public T getBy(final String propertyName, final Object value) {
		if (propertyName == null || "".equals(propertyName) || value == null)
			throw new IllegalArgumentException(
					I18n
							.getLocaleMessage("ext.Call.parameter.is.not.correct.attribute.names.and.values.are.not.empty"));
		List<T> ret = (List<T>) this.getJpaTemplate().execute(
				new JpaCallback() {

					public Object doInJpa(EntityManager em)
							throws PersistenceException {
						String clazzName = clazz.getName();
						StringBuffer sb = new StringBuffer("select obj from ");
						sb.append(clazzName).append(" obj");
						Query query = null;
						if (propertyName != null && value != null) {
							sb.append(" where obj.").append(propertyName)
									.append(" = :value");
							query = em.createQuery(sb.toString()).setParameter(
									"value", value);
						} else {
							query = em.createQuery(sb.toString());
						}
						return query.getResultList();
					}
				});
		if (ret != null && ret.size() == 1) {
			return ret.get(0);
		} else if (ret != null && ret.size() > 1) {
			throw new java.lang.IllegalStateException(
					"worning  --more than one object find!!");
		} else {
			return null;
		}
	}

	public List executeNamedQuery(final String queryName,
			final Object[] params, final int begin, final int max) {
		List ret = (List) this.getJpaTemplate().execute(new JpaCallback() {

			public Object doInJpa(EntityManager em) throws PersistenceException {
				Query query = em.createNamedQuery(queryName);
				int parameterIndex = 1;
				if (params != null && params.length > 0) {
					for (Object obj : params) {
						query.setParameter(parameterIndex++, obj);
					}
				}
				if (begin >= 0 && max > 0) {
					query.setFirstResult(begin);
					query.setMaxResults(max);
				}
				return query.getResultList();
			}
		});
		if (ret != null && ret.size() >= 0) {
			return ret;
		} else {
			return new ArrayList();
		}
	}

	public void update(T instance) {
		this.getJpaTemplate().merge(instance);
	}

	public void setClazzType(Class clazz) {
		this.clazz = clazz;
	}

	public Class getClassType() {
		return this.clazz;
	}

	public List executeNativeNamedQuery(final String nnq) {
		Object ret = this.getJpaTemplate().execute(new JpaCallback() {

			public Object doInJpa(EntityManager em) throws PersistenceException {
				Query query = em.createNamedQuery(nnq);
				return query.getResultList();
			}
		});
		return (List) ret;
	}
	public List executeNativeNamedQuery(final String nnq,final Object[] params) {
		Object ret = this.getJpaTemplate().execute(new JpaCallback() {

			public Object doInJpa(EntityManager em) throws PersistenceException {
				Query query = em.createNamedQuery(nnq);
				int parameterIndex = 1;
				if (params != null && params.length > 0) {
					for (Object obj : params) {
						query.setParameter(parameterIndex++, obj);
					}
				}
				return query.getResultList();
			}
		});
		return (List) ret;
	}
	public List executeNativeQuery(final String nnq, final Object[] params,
			final int begin, final int max) {
		List ret = (List) this.getJpaTemplate().execute(new JpaCallback() {

			public Object doInJpa(EntityManager em) throws PersistenceException {
				Query query = em.createNativeQuery(nnq);
				int parameterIndex = 1;
				if (params != null && params.length > 0) {
					for (Object obj : params) {
						query.setParameter(parameterIndex++, obj);
					}
				}
				if (begin >= 0 && max > 0) {
					query.setFirstResult(begin);
					query.setMaxResults(max);
				}
				return query.getResultList();
			}
		});
		if (ret != null && ret.size() >= 0) {
			return ret;
		} else {
			return new ArrayList();
		}
	}

	public int executeNativeSQL(final String nnq) {
		Object ret = this.getJpaTemplate().execute(new JpaCallback() {

			public Object doInJpa(EntityManager em) throws PersistenceException {
				Query query = em.createNativeQuery(nnq);
				return query.executeUpdate();
			}
		});
		return (Integer) ret;
	}
	public int batchUpdate(final String jpql, final Object[] params) {
		Object ret = this.getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {
				Query query = em.createQuery(jpql);
				int parameterIndex = 1;
				if (params != null && params.length > 0) {
					for (Object obj : params) {
						query.setParameter(parameterIndex++, obj);
					}
				}
				return query.executeUpdate();
			}
		});
		return (Integer) ret;
	}

	public void flush() {
		this.getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {
				Session session = (Session) em.getDelegate();
				session.flush();
				return null;
			}
		});
	}
	public Object getSingleResult(final String jpql,final Object []params){
		List list = this.query(jpql, params, 0, 1);
		if(list!=null && !list.isEmpty())
			return list.get(0);
		else
			return null;
	}
}