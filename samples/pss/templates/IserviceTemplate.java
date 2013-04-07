package $!{packageName}.service;

import java.io.Serializable;
import java.util.List;
import com.easyjf.web.tools.IPageList;
import com.easyjf.core.support.query.IQueryObject;
import $!{packageName}.domain.$!{domainName};
/**
 * $!{domainName}Service
 * @author EasyJWeb 1.0-m2
 * $Id: $!{domainName}Service.java,v 0.0.1 $!{nowTime} EasyJWeb 1.0-m2 Exp $
 */
public interface I$!{domainName}Service {
	/**
	 * 保存一个$!{domainName}，如果保存成功返回该对象的id，否则返回null
	 * 
	 * @param instance
	 * @return 保存成功的对象的Id
	 */
	Long add$!{domainName}($!{domainName} instance);
	
	/**
	 * 根据一个ID得到$!{domainName}
	 * 
	 * @param id
	 * @return
	 */
	$!{domainName} get$!{domainName}(Long id);
	
	/**
	 * 删除一个$!{domainName}
	 * @param id
	 * @return
	 */
	boolean del$!{domainName}(Long id);
	
	/**
	 * 批量删除$!{domainName}
	 * @param ids
	 * @return
	 */
	boolean batchDel$!{domainName}s(List<Serializable> ids);
	
	/**
	 * 通过一个查询对象得到$!{domainName}
	 * 
	 * @param properties
	 * @return
	 */
	IPageList get$!{domainName}By(IQueryObject queryObject);
	
	/**
	  * 更新一个$!{domainName}
	  * @param id 需要更新的$!{domainName}的id
	  * @param dir 需要更新的$!{domainName}
	  */
	boolean update$!{domainName}(Long id,$!{domainName} instance);
}
