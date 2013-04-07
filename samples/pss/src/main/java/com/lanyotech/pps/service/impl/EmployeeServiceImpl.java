package com.lanyotech.pps.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.commontemplate.util.MD5;

import com.easyjf.core.support.query.IQueryObject;
import com.easyjf.core.support.query.QueryUtil;
import com.easyjf.web.tools.IPageList;
import com.lanyotech.pps.dao.IEmployeeDAO;
import com.lanyotech.pps.domain.Employee;
import com.lanyotech.pps.service.IEmployeeService;
import com.lanyotech.pps.service.LogicException;
import com.lanyotech.pps.service.UserContext;

/**
 * EmployeeServiceImpl
 * 
 * @author EasyJWeb 1.0-m2 $Id: EmployeeServiceImpl.java,v 0.0.1 2010-6-6
 *         18:34:50 EasyJWeb 1.0-m2 Exp $
 */
public class EmployeeServiceImpl implements IEmployeeService {

	private IEmployeeDAO employeeDao;

	public void setEmployeeDao(IEmployeeDAO employeeDao) {
		this.employeeDao = employeeDao;
	}

	public Long addEmployee(Employee employee) {
		String pwd = MD5.encode(employee.getPassword());
		employee.setPassword(pwd);
		this.employeeDao.save(employee);
		if (employee != null && employee.getId() != null) {
			return employee.getId();
		}
		return null;
	}

	public Employee getEmployee(Long id) {
		Employee employee = this.employeeDao.get(id);
		return employee;
	}

	public boolean delEmployee(Long id) {
		Employee employee = this.getEmployee(id);
		if (employee != null) {
			this.employeeDao.remove(id);
			return true;
		}
		return false;
	}

	public boolean batchDelEmployees(List<Serializable> employeeIds) {

		for (Serializable id : employeeIds) {
			delEmployee((Long) id);
		}
		return true;
	}

	public IPageList getEmployeeBy(IQueryObject queryObject) {
		return QueryUtil.query(queryObject, Employee.class, this.employeeDao);
	}

	public boolean updateEmployee(Long id, Employee employee) {
		if (id != null) {
			employee.setId(id);
		} else {
			return false;
		}
		this.employeeDao.update(employee);
		return true;
	}

	public Employee login(String name, String password, String ip) throws LogicException {
		Employee emp = this.employeeDao.getBy("name", name);
		if (emp == null)
			throw new LogicException("用户名不存在");
		String pwd = MD5.encode(password);
		if (!pwd.equals(emp.getPassword()))
			throw new LogicException("用户密码不正确！");
		// 保存用户登录日志信息
		emp.setLastLoginIp(ip);
		emp.setLoginTimes(emp.getLoginTimes() + 1);
		emp.setLastLoginTime(new Date());
		this.updateEmployee(emp.getId(), emp);
		// 存Session
		UserContext.setUser(emp);
		return emp;
	}

	public void logout() {
		Employee emp = UserContext.getUser();
		if (emp != null) {
			emp.setLastLogoutTime(new Date());
			this.updateEmployee(emp.getId(), emp);
			UserContext.setUser(null);
		}
	}

	public void changePassword(String oldPassword, String newPassword) throws LogicException {
		String pwd = MD5.encode(oldPassword);
		Employee emp = UserContext.getUser();
		if (emp != null) {
			if (!pwd.equals(emp.getPassword())) {
				throw new LogicException("旧密码不正确！");
			}
			emp.setPassword(MD5.encode(newPassword));
			this.updateEmployee(emp.getId(), emp);
		}
	}

}
