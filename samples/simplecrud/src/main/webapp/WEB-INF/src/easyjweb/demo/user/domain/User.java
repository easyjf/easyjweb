package easyjweb.demo.user.domain;

import java.util.Date;

import com.easyjf.container.annonation.Field;
import com.easyjf.container.annonation.FormPO;
import com.easyjf.container.annonation.Validator;

@FormPO(disInject = "id,loginTimes,lastLoginTime")
public class User {
	private Long id;
	@Field(name = "姓名", validator = @Validator(name = "string", value = "trim;blank;min:5;max:20", required = true))
	private String name;
	
	@Field(name = "密码", validator = @Validator(name = "string", value = "trim;blank;min:5;max:20", required = true))
	private String password;
	
	private String sex;
	@Field(name = "电子邮件", validator = @Validator(name = "email", required = true))
	private String email;	
	private Date bornDate;	
	private Integer loginTimes=0;	
	private Date lastLoginTime;
	private String intro;
	public User() {
	}
	public User(String name, String email, Date bornDate) {
		this.name = name;
		this.email = email;
		this.bornDate = bornDate;
		this.loginTimes = 5;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBornDate() {
		return bornDate;
	}

	public void setBornDate(Date bornDate) {
		this.bornDate = bornDate;
	}

	public Integer getLoginTimes() {
		return loginTimes;
	}

	public void setLoginTimes(Integer loginTimes) {
		this.loginTimes = loginTimes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

}
