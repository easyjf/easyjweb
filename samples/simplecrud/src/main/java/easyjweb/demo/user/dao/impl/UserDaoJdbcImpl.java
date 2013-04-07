package easyjweb.demo.user.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import com.easyjf.container.annonation.Bean;
import easyjweb.demo.user.dao.UserDao;
import easyjweb.demo.user.domain.User;
/**
 * UserDao的另一个实现，该实现用来读取数据库
 * 
 * @author 大峡
 * 
 */
public class UserDaoJdbcImpl implements UserDao {

	private javax.sql.DataSource dataSource;

	public UserDaoJdbcImpl() {
		java.util.Properties p = new java.util.Properties();
		try {
			p.load(this.getClass().getResourceAsStream("/db.properties"));
			String driver = p.getProperty("database.driverClassName");
			String url = p.getProperty("database.url");
			String userName = p.getProperty("database.username");
			String password = p.getProperty("database.password");
			BasicDataSource bdds = new BasicDataSource();
			bdds.setDriverClassName(driver);
			bdds.setUrl(url);
			bdds.setUsername(userName);
			bdds.setPassword(password);
			this.dataSource = bdds;
		} catch (java.io.IOException e) {
		}
	}

	public UserDaoJdbcImpl(javax.sql.DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setDataSource(javax.sql.DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public User get(final Long id) {
		User user = (User) new SqlExecute() {
			Object doExecute() throws java.sql.SQLException {
				stat = conn.prepareStatement("select * from User where id=?");
				stat.setLong(1, id);
				rs = stat.executeQuery();
				if (rs.next()) {
					return rs2user(rs);
				} else
					return null;
			}
		}.execute();
		return user;
	}

	public List<User> list() {
		java.util.List<User> users = (List<User>) new SqlExecute() {
			Object doExecute() throws java.sql.SQLException {
				stat = conn.prepareStatement("select * from User");
				rs = stat.executeQuery();
				java.util.List<User> list = new java.util.ArrayList<User>();
				while (rs.next()) {
					list.add(rs2user(rs));
				}
				return list;
			}
		}.execute();
		return users;
	}

	public void remove(Long id) {
		new SqlExecute() {
			Object doExecute() throws java.sql.SQLException {
				stat = conn.prepareStatement("delete * from User where id=?");
				return stat.executeUpdate();
			}
		}.execute();
	}

	public void save(final User user) {
		new SqlExecute() {
			Object doExecute() throws java.sql.SQLException {
				stat = conn
						.prepareStatement("insert into User(name,password,sex,email,bornDate,loginTimes,lastLoginTime,intro,id) values(?,?,?,?,?,?,?,?,?)");
				List<User> list = list();
				user.setId(new Long(list == null ? 1 : list.size() + 1));
				stat.setString(1, user.getName());
				stat.setString(2, user.getPassword());
				stat.setString(3, user.getSex());
				stat.setString(4, user.getEmail());
				stat.setDate(5, user.getBornDate() == null ? null
						: new java.sql.Date(user.getBornDate().getTime()));
				stat.setInt(6, user.getLoginTimes());
				stat.setDate(7, user.getLastLoginTime() == null ? null
						: new java.sql.Date(user.getLastLoginTime().getTime()));
				stat.setString(8, user.getIntro());
				stat.setLong(9, user.getId());
				return stat.executeUpdate();
			}
		}.execute();
	}

	public void update(final Long id, final User user) {
		new SqlExecute() {
			Object doExecute() throws java.sql.SQLException {
				stat = conn
						.prepareStatement("update User set name=?,password=?,sex=?,email=?,bornDate=?,loginTimes=?,lastLoginTime=?,intro=? where id=?");
				stat.setString(1, user.getName());
				stat.setString(2, user.getPassword());
				stat.setString(3, user.getSex());
				stat.setString(4, user.getEmail());
				stat.setDate(5, user.getBornDate() == null ? null
						: new java.sql.Date(user.getBornDate().getTime()));
				stat.setInt(6, user.getLoginTimes());
				stat.setDate(7, user.getLastLoginTime() == null ? null
						: new java.sql.Date(user.getLastLoginTime().getTime()));
				stat.setString(8, user.getIntro());
				stat.setLong(9, user.getId());
				return stat.executeUpdate();
			}
		}.execute();
	}

	private User rs2user(java.sql.ResultSet rs) throws java.sql.SQLException {
		Long id = rs.getLong("id");
		String name = rs.getString("name");
		String password = rs.getString("password");
		String sex = rs.getString("sex");
		String email = rs.getString("email");
		Date bornDate = rs.getDate("bornDate");
		Integer loginTimes = rs.getInt("loginTimes");
		Date lastLoginTime = rs.getDate("lastLoginTime");
		String intro = rs.getString("intro");
		User u = new User(name, email, bornDate);
		u.setId(id);
		u.setPassword(password);
		u.setLoginTimes(loginTimes);
		u.setLastLoginTime(lastLoginTime);
		u.setSex(sex);
		u.setIntro(intro);
		return u;
	}

	abstract class SqlExecute {
		protected java.sql.Connection conn = null;
		protected java.sql.PreparedStatement stat = null;
		protected java.sql.ResultSet rs = null;

		Object execute() {
			try {
				conn = dataSource.getConnection();
				return doExecute();
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
				try {
					if (rs != null)
						rs.close();
					if (stat != null)
						stat.close();
				} catch (java.sql.SQLException e2) {
					throw new java.lang.RuntimeException(e2.getMessage());
				}
			} finally {
				try {
					if (conn != null && !conn.isClosed())
						conn.close();
				} catch (java.sql.SQLException e2) {
					throw new java.lang.RuntimeException(e2.getMessage());
				}
			}
			return null;
		}

		abstract Object doExecute() throws java.sql.SQLException;
	}
}
