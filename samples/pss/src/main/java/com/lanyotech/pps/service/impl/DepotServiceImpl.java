package com.lanyotech.pps.service.impl;
import java.io.Serializable;
import java.util.List;

import com.easyjf.core.support.query.IQueryObject;
import com.easyjf.core.support.query.QueryUtil;
import com.easyjf.web.tools.IPageList;
import com.lanyotech.pps.domain.Depot;
import com.lanyotech.pps.service.IDepotService;
import com.lanyotech.pps.dao.IDepotDAO;


/**
 * DepotServiceImpl
 * @author EasyJWeb 1.0-m2
 * $Id: DepotServiceImpl.java,v 0.0.1 2010-6-12 19:25:40 EasyJWeb 1.0-m2 Exp $
 */
public class DepotServiceImpl implements IDepotService{
	
	private IDepotDAO depotDao;
	
	public void setDepotDao(IDepotDAO depotDao){
		this.depotDao=depotDao;
	}
	
	public Long addDepot(Depot depot) {	
		this.depotDao.save(depot);
		if (depot != null && depot.getId() != null) {
			return depot.getId();
		}
		return null;
	}
	
	public Depot getDepot(Long id) {
		Depot depot = this.depotDao.get(id);
		return depot;
		}
	
	public boolean delDepot(Long id) {	
			Depot depot = this.getDepot(id);
			if (depot != null) {
				this.depotDao.remove(id);
				return true;
			}			
			return false;	
	}
	
	public boolean batchDelDepots(List<Serializable> depotIds) {
		
		for (Serializable id : depotIds) {
			delDepot((Long) id);
		}
		return true;
	}
	
	public IPageList getDepotBy(IQueryObject queryObject) {	
		return QueryUtil.query(queryObject, Depot.class,this.depotDao);		
	}
	
	public boolean updateDepot(Long id, Depot depot) {
		if (id != null) {
			depot.setId(id);
		} else {
			return false;
		}
		this.depotDao.update(depot);
		return true;
	}	
	
}
