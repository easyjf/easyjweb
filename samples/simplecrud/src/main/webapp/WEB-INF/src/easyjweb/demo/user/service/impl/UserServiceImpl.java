package easyjweb.demo.user.service.impl;

import java.util.Date;
import java.util.List;

import com.easyjf.container.annonation.Bean;

import easyjweb.demo.user.dao.UserDao;
import easyjweb.demo.user.domain.User;
import easyjweb.demo.user.service.UserService;

@Bean
public class UserServiceImpl implements UserService {
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public User login(String userName, String password) {
		List<User> users = getAllUser();
		for (User user : users) {
			// 检测用户名密码，如果正确则
			if (userName.equals(user.getName())
					&& password.equals(user.getPassword())) {
				user.setLoginTimes(user.getLoginTimes() + 1);// 更改登录次数
				user.setLastLoginTime(new Date());// 更改登录时间
				this.updateUser(user.getId(), user);// 持久化更改后的user
				return user;
			}
		}
		return null;
	}

	public User getUser(String name) {
		List<User> users = getAllUser();
		for (User user : users) {
			if (user.getName().equals(name))
				return user;
		}
		return null;
	}

	public void delUser(User user) {
		userDao.remove(user.getId());
	}

	public User getUser(Long id) {
		return userDao.get(id);
	}

	public List<User> getAllUser() {
		return userDao.list();
	}

	public void saveUser(User user) {
		User u = this.getUser(user.getName());
		if (u != null)
			throw new java.lang.RuntimeException("当前用户名已经被占用!");
		userDao.save(user);
	}

	public void updateUser(Long id, User user) {
		User u = this.getUser(user.getName());
		if (u != null && !u.getId().equals(id))
			throw new java.lang.RuntimeException("当前用户名已经被占用!");
		userDao.update(id, user);
	}
}
