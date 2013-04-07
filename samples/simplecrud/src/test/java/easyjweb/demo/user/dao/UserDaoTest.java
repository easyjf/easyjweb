package easyjweb.demo.user.dao;

import java.util.Date;
import java.util.List;

import junit.framework.TestCase;
import easyjweb.demo.user.dao.impl.UserDaoImpl;
import easyjweb.demo.user.dao.impl.UserDaoJdbcImpl;
import easyjweb.demo.user.domain.User;

/**
 * UserDao的测试代码
 * 
 * @author 大峡
 * 
 */
public class UserDaoTest extends TestCase {
	private UserDao userDao;

	@Override
	protected void setUp() throws Exception {
		userDao=new UserDaoJdbcImpl();		
		super.setUp();
	}

	/**
	 * 测试get方法
	 */
	public void testGet() {
		User user = userDao.get(1l);
		assertNotNull(user);
		assertTrue(user.getId().equals(1l));
	}

	/**
	 * 测试save方法
	 * 
	 */
	public void testSave() {
		User user = new User("小王", "小王@easyjf.com", new Date());
		userDao.save(user);
		User u = userDao.get(user.getId());
		assertEquals("小王", u.getName());
	}

	/**
	 * 测试remove方法
	 */
	public void testRemove() {
		User user = new User("小王", "小王@easyjf.com", new Date());
		userDao.save(user);
		User u = userDao.get(user.getId());
		assertNotNull(u);
		userDao.remove(user.getId());
		u = userDao.get(user.getId());
		assertNull(u);
	}

	/**
	 * 测试list方法
	 */
	public void testList() {
		List<User> list = userDao.list();
		int l = list.size();
		User user = new User("小王", "小王@easyjf.com", new Date());
		userDao.save(user);
		list = userDao.list();
		assertTrue(list.size() == l + 1);
	}

	/**
	 * 测试update方法
	 */
	public void testUpdate() {
		User user = new User("小王", "小王@easyjf.com", new Date());
		userDao.save(user);
		User u = userDao.get(user.getId());
		u.setEmail("xiaowang@easyjf.com");
		userDao.update(u.getId(), u);
		User u2 = userDao.get(user.getId());
		assertEquals("xiaowang@easyjf.com",u2.getEmail());
	}
}
