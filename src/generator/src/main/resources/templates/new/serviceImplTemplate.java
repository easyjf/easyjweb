##set ($domain = $!domainName.toLowerCase())
package $!{packageName}.service.impl;
import java.io.Serializable;
import java.util.List;

import com.easyjf.core.support.query.IQueryObject;
import com.easyjf.core.support.query.QueryUtil;
import com.easyjf.web.tools.IPageList;
import $!{packageName}.domain.$!{domainName};
import $!{packageName}.service.I$!{domainName}Service;
import $!{packageName}.dao.I$!{domainName}DAO;

#macro (upperCase $str)
#set ($upper=$!str.substring(0,1).toUpperCase())
#set ($l=$!str.substring(1))
$!upper$!l#end

/**
 * $!{domainName}ServiceImpl
 * @author EasyJweb
 * $Id: $!{domainName}ServiceImpl.java,v 0.0.1 $!{nowTime} EasyJweb Exp $
 */
public class $!{domainName}ServiceImpl implements I$!{domainName}Service{
	
	private I$!{domainName}DAO $!{domain}Dao;
	
	public void set#upperCase($!{domain})Dao(I$!{domainName}DAO $!{domain}Dao){
		this.$!{domain}Dao=$!{domain}Dao;
	}
	
	public Long add$!{domainName}($!{domainName} $!{domain}) {	
		this.$!{domain}Dao.save($!{domain});
		return $!{domain}.get$!{id}();
	}
	
	public $!{domainName} get$!{domainName}(Long id) {
		$!{domainName} $!{domain} = this.$!{domain}Dao.get(id);
		if ($!{domain} != null) {
			return $!{domain};
		}
		return null;
	}
	
	public boolean del$!{domainName}(Long id) {	
			$!{domainName} $!{domain} = this.get$!{domainName}(id);
			if ($!{domain} != null) {
				this.$!{domain}Dao.remove(id);
				return true;
			}			
			return false;	
	}
	
	public boolean batchDel$!{domainName}s(List<Serializable> $!{domain}Ids) {
		
		for (Serializable id : $!{domain}Ids) {
			del$!{domainName}((Long) id);
		}
		return true;
	}
	
	public IPageList get$!{domainName}By(IQueryObject queryObject) {	
		return QueryUtil.query(queryObject, $!{domainName}.class,this.$!{domain}Dao);		
	}
	
	public boolean update$!{domainName}(Long id, $!{domainName} $!{domain}) {
		if (id != null) {
			$!{domain}.set$!{id}(id);
		} else {
			return false;
		}
		this.$!{domain}Dao.update($!{domain});
		return true;
	}	
	
}
