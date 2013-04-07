package com.lanyotech.pps.service.impl;
import java.io.Serializable;
import java.util.List;

import com.easyjf.core.support.query.IQueryObject;
import com.easyjf.core.support.query.QueryUtil;
import com.easyjf.web.tools.IPageList;
import com.lanyotech.pps.domain.Client;
import com.lanyotech.pps.service.IClientService;
import com.lanyotech.pps.dao.IClientDAO;


/**
 * ClientServiceImpl
 * @author EasyJWeb 1.0-m2
 * $Id: ClientServiceImpl.java,v 0.0.1 2010-6-12 19:25:45 EasyJWeb 1.0-m2 Exp $
 */
public class ClientServiceImpl implements IClientService{
	
	private IClientDAO clientDao;
	
	public void setClientDao(IClientDAO clientDao){
		this.clientDao=clientDao;
	}
	
	public Long addClient(Client client) {	
		this.clientDao.save(client);
		if (client != null && client.getId() != null) {
			return client.getId();
		}
		return null;
	}
	
	public Client getClient(Long id) {
		Client client = this.clientDao.get(id);
		return client;
		}
	
	public boolean delClient(Long id) {	
			Client client = this.getClient(id);
			if (client != null) {
				this.clientDao.remove(id);
				return true;
			}			
			return false;	
	}
	
	public boolean batchDelClients(List<Serializable> clientIds) {
		
		for (Serializable id : clientIds) {
			delClient((Long) id);
		}
		return true;
	}
	
	public IPageList getClientBy(IQueryObject queryObject) {	
		return QueryUtil.query(queryObject, Client.class,this.clientDao);		
	}
	
	public boolean updateClient(Long id, Client client) {
		if (id != null) {
			client.setId(id);
		} else {
			return false;
		}
		this.clientDao.update(client);
		return true;
	}	
	
}
