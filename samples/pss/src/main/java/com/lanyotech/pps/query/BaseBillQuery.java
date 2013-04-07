package com.lanyotech.pps.query;

import com.easyjf.core.support.query.QueryObject;
import com.easyjf.util.StringUtils;

public class BaseBillQuery extends QueryObject {

	@Override
	public void customizeQuery() {
		if(!StringUtils.hasLength(this.getOrderBy())){
			this.setOrderBy("id");
			this.setOrderType("desc");
		}
		super.customizeQuery();
	}

}
