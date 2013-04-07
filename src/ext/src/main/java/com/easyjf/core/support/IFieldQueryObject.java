package com.easyjf.core.support;

import java.util.List;

import com.easyjf.core.support.query.PageObject;

/**
 * 根据一个对象的某些属性查询的查询对象
 * 
 * @author stefanie_wu
 * 
 */
public interface IFieldQueryObject {

	/**
	 * 得到一个查询条件语句,其中参数用站位符?1,?2...，也可以不用占位符如title=? and
	 * status=1
	 * 
	 * @return 返回的条件语句
	 */
	String getQuery();

	/**
	 * 得到查询语句需要的参数对象列表
	 * 
	 * @return 参数对象列表
	 */
	List getParameters();

	/**
	 * 得到关于分页页面信息对象
	 * 
	 * @return 包装分页信息对象
	 */
	PageObject getPageObj();

	/**
	 * 得到要查询的属性，即select <obj.fields1,obj.fields2...>
	 * 
	 * @return
	 */
	String getSelectFields();

	/**
	 * 添加要查询的属性
	 * 
	 * @param fields
	 *            要查询的属性列表，比如addField("name","address.city");就会把这些属性做为查询的属性，配合传入的查询对象，生成的查询语句就像select
	 *            user.name,user.address.city where...
	 */
	IFieldQueryObject addField(String... fields);

	/**
	 * 批量往查询对象中添加查询选项，可以是一个完整的具体查询语句，如title='111' and
	 * status>0，也可以是包括?号的语句，如title=? and status>0 <code>
	 * NewsDocQueryObject query=new NewsDocQueryObject();
	 * query.addQuery("title=?",new Object[]{"新闻"});
	 * </code>
	 * 
	 * @param scope
	 *            查询条件
	 * @param paras
	 *            参数值，如果
	 */
	IFieldQueryObject addQuery(String scope, Object[] paras);

	/**
	 * 添加排序规则，比如addOrder("name",OrderType.DESC);则查询的语句为select
	 * .... order by name desc; 可以多次添加排序规则，按照添加的顺序排列，
	 * 比如addOrder("name",OrderType.DESC);addOrder("born",OrderType.ASC)
	 * 则生成的排序规则为select ... order by name desc,born asc;
	 * 
	 * @param field
	 *            要排序的属性
	 * @param orderType
	 *            排列的类型
	 * @return
	 */
	IFieldQueryObject addOrder(String field, OrderType orderType);

	/**
	 * 往查询条件中逐个加入查询条件
	 * 
	 * @param field
	 *            属性名称
	 * @param para
	 *            参数值
	 * @param expression
	 *            表达式,如果为null，则使用=。
	 */
	IFieldQueryObject addQuery(String field, Object para, String expression);

	List<String> getQueryFields();
}
