package easyjweb.demo.user.dao;

import java.util.List;

import easyjweb.demo.user.domain.User;

/**
 * User模型的数据访问接口
 * 
 * @author 大峡
 * 
 */
public interface UserDao {

	/**
	 * 从持久层根据id加载一个User对象
	 * 
	 * @param id
	 *            主键值
	 * @return 如果持久层包括该对象，则返回该User，否则返回null
	 */
	User get(Long id);

	/**
	 * 把一个User实例化到持久层中
	 * 
	 * @param user
	 *            要持久化的user
	 */
	void save(User user);

	/**
	 * 更新一个持久化对象
	 * 
	 * @param id
	 *            要更新的id
	 * @param user
	 *            更新的对象
	 */
	void update(Long id, User user);

	/**
	 * 从持久层永久删除指定id的User对象
	 * 
	 * @param id
	 */
	void remove(Long id);

	/**
	 * 从持久层读取所有的User对象
	 * 
	 * @return 所有持久化的User对象，如果没有则返回包含0个元素的空列表
	 */
	List<User> list();
}
