package com.lanyotech.pps.service.impl;
import java.io.Serializable;
import java.util.List;

import com.easyjf.core.support.query.IQueryObject;
import com.easyjf.core.support.query.QueryObject;
import com.easyjf.core.support.query.QueryUtil;
import com.easyjf.web.tools.IPageList;
import com.lanyotech.pps.domain.BaseCount;
import com.lanyotech.pps.service.IBaseCountService;
import com.lanyotech.pps.dao.IBaseCountDAO;


/**
 * BaseCountServiceImpl
 * @author EasyJWeb 1.0-m2
 * $Id: BaseCountServiceImpl.java,v 0.0.1 2010-6-22 0:00:56 EasyJWeb 1.0-m2 Exp $
 */
public class BaseCountServiceImpl implements IBaseCountService{
	
	private IBaseCountDAO baseCountDao;
	
	public void setBaseCountDao(IBaseCountDAO baseCountDao){
		this.baseCountDao=baseCountDao;
	}
	
	public Long addBaseCount(BaseCount baseCount) {	
		this.baseCountDao.save(baseCount);
		if (baseCount != null && baseCount.getId() != null) {
			return baseCount.getId();
		}
		return null;
	}
	
	public BaseCount getBaseCount(Long id) {
		BaseCount baseCount = this.baseCountDao.get(id);
		return baseCount;
		}
	
	public boolean delBaseCount(Long id) {	
			BaseCount baseCount = this.getBaseCount(id);
			if (baseCount != null) {
				this.baseCountDao.remove(id);
				return true;
			}			
			return false;	
	}
	
	public boolean batchDelBaseCounts(List<Serializable> baseCountIds) {
		
		for (Serializable id : baseCountIds) {
			delBaseCount((Long) id);
		}
		return true;
	}
	
	public IPageList getBaseCountBy(IQueryObject queryObject) {	
		return QueryUtil.query(queryObject, BaseCount.class,this.baseCountDao);		
	}
	
	public boolean updateBaseCount(Long id, BaseCount baseCount) {
		if (id != null) {
			baseCount.setId(id);
		} else {
			return false;
		}
		this.baseCountDao.update(baseCount);
		return true;
	}

	public String getNewSn(String entityName) {
		QueryObject qo=new QueryObject();
		qo.addQuery("obj.entityName",entityName,"=");
		List list=this.getBaseCountBy(qo).getResult();
		int sequence=1;
		if(list!=null&&list.size()>0){
			BaseCount bc=(BaseCount)list.get(0);
			sequence=bc.getSequence()+1;
		}
		return String.format("%1$08d", sequence);
	}

	public void recordNewSn(String entityName, Integer sequence) {
		QueryObject qo = new QueryObject();
		qo.addQuery("obj.entityName", entityName, "=");
		List list = this.getBaseCountBy(qo).getResult();
		if (list != null && list.size() > 0) {
			BaseCount bc = (BaseCount) list.get(0);
			if (bc.getSequence() < sequence) {
				bc.setSequence(sequence);
				this.updateBaseCount(bc.getId(), bc);
			}
		} else {
			BaseCount bc = new BaseCount();
			bc.setEntityName(entityName);
			bc.setSequence(sequence);
			this.addBaseCount(bc);
		}
	}
}
