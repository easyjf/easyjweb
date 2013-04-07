package easyjweb.demo.user.dao.impl;

import java.util.Date;
import java.util.List;

import com.easyjf.container.annonation.Bean;

import easyjweb.demo.user.dao.UserDao;
import easyjweb.demo.user.domain.User;
@Bean(inject = "none")
public class UserDaoImpl implements UserDao {
	private List<User> users = new java.util.ArrayList<User>();

	public UserDaoImpl()
	{
		if(list().size()<1){
		User u=new User("admin","admin@easyjf.com",new Date());
		u.setPassword("admin");
		save(u);
		}
	}
	public User get(Long id) {
		for (User u : this.users) {
			if (u.getId().equals(id))
				return u;
		}
		return null;
	}

	public List<User> list() {
		return users;
	}

	public void remove(Long id) {
		User user = get(id);
		users.remove(user);
	}

	public void save(User user) {
		user.setId(new Long(users.size()+1));//设置id值
		if (!users.contains(user)) {
			users.add(user);
		}
	}

	public void update(Long id, User user) {
		User u = get(id);
		u.setName(user.getName());
		u.setPassword(user.getPassword());
		u.setEmail(user.getEmail());
		u.setBornDate(user.getBornDate());
		u.setSex(user.getSex());
		u.setLoginTimes(user.getLoginTimes());
	}
}
