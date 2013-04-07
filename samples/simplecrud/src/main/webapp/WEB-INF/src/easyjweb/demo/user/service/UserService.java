package easyjweb.demo.user.service;

import java.util.List;

import easyjweb.demo.user.domain.User;
/**
 * 用户系统的业务逻辑接口　
 * @author 大峡
 *
 */
public interface UserService {
	/**
	 * 根据id查询用户
	 * 
	 * @param id
	 * @return
	 */
	User getUser(Long id);
	/**
	 * 根据用户名得到用户
	 * @param name 用户名
	 * @return 如果存在用户名指定的用户，则返回该用户，否则返回null
	 */
	User getUser(String name);
	

	/**
	 * 保存用户
	 * 
	 * @param user
	 */
	void saveUser(User user);

	/**
	 * 修改用户信息
	 * 
	 * @param user
	 */
	void updateUser(Long id,User user);

	/**
	 * 删除用户
	 * 
	 * @param user
	 */
	void delUser(User user);

	/**
	 * 查询所有用户
	 * @return 返回用户列表
	 */
	List<User> getAllUser();
	
	/**
	 * 用户登录
	 * @param userName　用户名
	 * @param password 密码
	 * @return　如果登录成功则返回该用户的详细信息，如果登录失败则返回null
	 */
	User login(String userName,String password);
	
}
