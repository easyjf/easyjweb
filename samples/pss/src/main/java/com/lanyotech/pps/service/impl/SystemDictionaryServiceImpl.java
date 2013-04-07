package com.lanyotech.pps.service.impl;
import java.io.Serializable;
import java.util.List;

import com.easyjf.core.support.query.IQueryObject;
import com.easyjf.core.support.query.QueryUtil;
import com.easyjf.web.tools.IPageList;
import com.lanyotech.pps.domain.SystemDictionary;
import com.lanyotech.pps.service.ISystemDictionaryService;
import com.lanyotech.pps.dao.ISystemDictionaryDAO;


/**
 * SystemDictionaryServiceImpl
 * @author EasyJWeb 1.0-m2
 * $Id: SystemDictionaryServiceImpl.java,v 0.0.1 2010-6-5 15:28:29 EasyJWeb 1.0-m2 Exp $
 */
public class SystemDictionaryServiceImpl implements ISystemDictionaryService{
	
	private ISystemDictionaryDAO systemDictionaryDao;
	
	public void setSystemDictionaryDao(ISystemDictionaryDAO systemDictionaryDao){
		this.systemDictionaryDao=systemDictionaryDao;
	}
	
	public Long addSystemDictionary(SystemDictionary systemDictionary) {	
		this.systemDictionaryDao.save(systemDictionary);
		if (systemDictionary != null && systemDictionary.getId() != null) {
			return systemDictionary.getId();
		}
		return null;
	}
	
	public SystemDictionary getSystemDictionary(Long id) {
		SystemDictionary systemDictionary = this.systemDictionaryDao.get(id);
		return systemDictionary;
		}
	
	public boolean delSystemDictionary(Long id) {	
			SystemDictionary systemDictionary = this.getSystemDictionary(id);
			if (systemDictionary != null) {
				this.systemDictionaryDao.remove(id);
				return true;
			}			
			return false;	
	}
	
	public boolean batchDelSystemDictionarys(List<Serializable> systemDictionaryIds) {
		
		for (Serializable id : systemDictionaryIds) {
			delSystemDictionary((Long) id);
		}
		return true;
	}
	
	public IPageList getSystemDictionaryBy(IQueryObject queryObject) {	
		return QueryUtil.query(queryObject, SystemDictionary.class,this.systemDictionaryDao);		
	}
	
	public boolean updateSystemDictionary(Long id, SystemDictionary systemDictionary) {
		if (id != null) {
			systemDictionary.setId(id);
		} else {
			return false;
		}
		this.systemDictionaryDao.update(systemDictionary);
		return true;
	}	
	
}
