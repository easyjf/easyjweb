package com.lanyotech.pps.service;

import java.util.List;

import com.lanyotech.pps.domain.Client;

public interface IClientSimpleService {
	/**
	 * 从持久层对象中获得
	 * 
	 * @param id
	 * @return
	 */
	Client getClient(Long id);

	/**
	 * 把一个对象写入持久化设备中
	 * 
	 * @param obj
	 */
	void saveClient(Client obj);

	/**
	 * 永久删除一个对象
	 * 
	 * @param id
	 */
	void removeClient(Long id);

	/**
	 * 修改持久化中的对象
	 * 
	 * @param obj
	 */
	void updateClient(Client obj);

	/**
	 * 从持久化中根据指定的条件及参数查询符合条件的Client对象 通过begin及max参数，可以控制分页
	 * 
	 * @param query
	 *            查询条件，比如sn=? and title like ?等
	 * @param params
	 *            查询参数集，对应查询条件中的每一个?或占位符
	 * @param begin
	 *            从符合条件的指定位置开始取数据
	 * @param max
	 *            最多返回的记录数
	 * @return
	 */
	List<Client> findClient(String scope, Object[] params, int begin, int max);
}
