package com.lanyotech.pps.service.impl;

import java.util.List;

import com.lanyotech.pps.dao.IClientSimpleDAO;
import com.lanyotech.pps.domain.Client;
import com.lanyotech.pps.service.IClientSimpleService;

public class ClientSimpleServiceImpl implements IClientSimpleService{
	private IClientSimpleDAO clientSimpleDAO;
	
	public List<Client> findClient(String scope, Object[] params, int begin, int max) {
		return clientSimpleDAO.find(scope, params, begin, max);
	}

	public Client getClient(Long id) {
		return clientSimpleDAO.get(id);
	}

	public void removeClient(Long id) {
		clientSimpleDAO.remove(id);
	}

	public void saveClient(Client obj) {
		clientSimpleDAO.save(obj);
	}

	public void updateClient(Client obj) {
		clientSimpleDAO.update(obj);
	}

	public void setClientSimpleDAO(IClientSimpleDAO clientSimpleDAO) {
		this.clientSimpleDAO = clientSimpleDAO;
	}
}
