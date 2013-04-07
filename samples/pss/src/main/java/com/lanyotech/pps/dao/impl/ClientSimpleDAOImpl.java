package com.lanyotech.pps.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;

import com.easyjf.util.StringUtils;
import com.lanyotech.pps.dao.IClientSimpleDAO;
import com.lanyotech.pps.domain.Client;

public class ClientSimpleDAOImpl extends JpaDaoSupport implements IClientSimpleDAO {
	@SuppressWarnings("unchecked")
	public List<Client> find(final String scope, final Object[] params, final int begin,
			final int max) {
		return this.getJpaTemplate().executeFind(new JpaCallback() {
			public Object doInJpa(EntityManager entitymanager) throws PersistenceException {
				StringBuffer sql = new StringBuffer("select obj from Client obj ");
				if (StringUtils.hasLength(scope)) {
					sql.append(" where ").append(scope);
				}
				Query query = entitymanager.createQuery(sql.toString());
				if (params != null) {
					for (int i = 0; i < params.length; i++)
						query.setParameter(i + 1, params[i]);
				}
				if (begin > 0) {
					query.setFirstResult(begin);
					query.setMaxResults(max);
				}
				return query.getResultList();
			}
		});
	}

	public Client get(Long id) {
		return this.getJpaTemplate().find(Client.class, id);
	}

	public void remove(Long id) {
		this.getJpaTemplate().remove(get(id));
	}

	public void save(Client obj) {
		this.getJpaTemplate().persist(obj);
	}

	public void update(Client obj) {
		this.getJpaTemplate().merge(obj);
	}
}
