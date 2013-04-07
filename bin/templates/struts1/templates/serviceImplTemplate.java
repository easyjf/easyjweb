##set ($domain = $!domainName.toLowerCase())
package $!{packageName}.service.impl;
import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

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
 * @author EasyJWeb 1.0-m2
 * $Id: $!{domainName}ServiceImpl.java,v 0.0.1 $!{nowTime} EasyJWeb 1.0-m2 Exp $
 */
@Transactional
public class $!{domainName}ServiceImpl implements I$!{domainName}Service{
	
	private I$!{domainName}DAO $!{domain}Dao;
	
	public void set#upperCase($!{domain})Dao(I$!{domainName}DAO $!{domain}Dao){
		this.$!{domain}Dao=$!{domain}Dao;
	}
	
	public ${idType} add$!{domainName}($!{domainName} $!{domain}) {	
		this.$!{domain}Dao.save($!{domain});
		if ($!{domain} != null && $!{domain}.get$!{id}() != null) {
			return $!{domain}.get$!{id}();
		}
		return null;
	}
	
	public $!{domainName} get$!{domainName}(${idType} id) {
		$!{domainName} $!{domain} = this.$!{domain}Dao.get(id);
		return $!{domain};
		}
	
	public boolean del$!{domainName}(${idType} id) {	
			$!{domainName} $!{domain} = this.get$!{domainName}(id);
			if ($!{domain} != null) {
				this.$!{domain}Dao.remove(id);
				return true;
			}			
			return false;	
	}
	
	public boolean batchDel$!{domainName}s(List<Serializable> $!{domain}Ids) {
		
		for (Serializable id : $!{domain}Ids) {
			del$!{domainName}((${idType}) id);
		}
		return true;
	}
	
	public IPageList get$!{domainName}By(IQueryObject queryObject) {	
		return QueryUtil.query(queryObject, $!{domainName}.class,this.$!{domain}Dao);		
	}
	
	public boolean update$!{domainName}(${idType} id, $!{domainName} $!{domain}) {
		if (id != null) {
			$!{domain}.set$!{id}(id);
		} else {
			return false;
		}
		this.$!{domain}Dao.update($!{domain});
		return true;
	}	
	
}
