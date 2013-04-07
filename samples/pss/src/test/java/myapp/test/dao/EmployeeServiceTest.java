package myapp.test.dao;

import com.lanyotech.pps.domain.Employee;
import com.lanyotech.pps.service.IEmployeeService;
import com.lanyotech.pps.service.LogicException;

public class EmployeeServiceTest extends BaseJpaTests {
	private IEmployeeService employeeService;

	public void testAddEmployee() {
		Employee e = new Employee();
		e.setName("test");
		e.setPassword("test");
		assertNull(e.getId());
		employeeService.addEmployee(e);
		assertNotNull(e.getId());
		//this.setComplete();
	}

	public void testLogin() {
		Employee e = new Employee();
		e.setName("test");
		e.setPassword("test");
		employeeService.addEmployee(e);
		try {
			Employee user = employeeService.login("test", "aaa", "1111");
			this.fail("密码不对居然也可以登录！");
		} catch (LogicException exce) {
		}
		Employee user = employeeService.login("test", "test", "1111");
		this.assertNotNull("用户名及密码都对了，应该要登录成功！", user);
	}

	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
}
