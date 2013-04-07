package easyjweb.demo.user.mvc;

import com.easyjf.container.annonation.Inject;
import com.easyjf.web.ActionContext;
import com.easyjf.web.Module;
import com.easyjf.web.WebForm;
import com.easyjf.web.core.AbstractPageCmdAction;

import easyjweb.demo.user.domain.User;
import easyjweb.demo.user.service.UserService;

public class UserAction extends AbstractPageCmdAction {
	@Inject
	private UserService service;

	public void setService(UserService service) {
		this.service = service;
	}

	/**
	 * 在执行所有的方法前先执行的方法，可以用来实现简单的权限验证等
	 */
	@Override
	public Object doBefore(WebForm form, Module module) {
		String cmd = (String) form.get("cmd");
		if (cmd != null && !"login".equals(cmd) && !"init".equals(cmd)) {
			User user = (User) ActionContext.getContext().getSession()
					.getAttribute("user");
			if (user == null) {
				form.addResult("msg", "请先登录系统!");
				return page("index");
			}
		}
		return null;
	}

	/**
	 * 登录
	 * 
	 * @param form
	 */
	public void login(WebForm form) {
		String name = (String) form.get("name");
		String password = (String) form.get("password");
		User user = service.login(name, password);
		if (user == null) {
			form.addResult("msg", "用户名或密码不正确，请重新输入!");
			page("index");
		} else {
			ActionContext.getContext().getSession().setAttribute("user", user);
			forward("list");
		}
	}

	/**
	 * 修改用户
	 * 
	 * @param form
	 */
	public void edit(WebForm form) {
		String id = (String) form.get("id");
		User user = this.service.getUser(new Long(id));
		form.addPo(user);
	}

	/**
	 * 查看用户详情
	 * 
	 * @param form
	 */
	public void preview(WebForm form) {
		String id = (String) form.get("id");
		User user = this.service.getUser(new Long(id));
		form.addPo(user);
	}

	/**
	 * 保存新增用户
	 * 
	 * @param form
	 */
	public void save(WebForm form) {
		User user = form.toPo(User.class);
		if(service.getUser(user.getName())!=null)
		{
			this.addError("name","用户名"+user.getName()+"已经存在！");
		}
		if (hasErrors()) {
			page("add");
			return;
		}
		service.saveUser(user);
		forward("list");
	}

	/**
	 * 修改用户
	 * 
	 * @param form
	 */
	public void update(WebForm form) {
		Long id = new Long((String) form.get("id"));
		String name=(String)form.get("name");
		User u2=this.service.getUser(name);
		User user = this.service.getUser(id);
		if(u2!=null && !u2.getId().equals(id))
		{
			this.addError("name","用户名"+name+"已经被其它的用户占用！");			
		}
		form.toPo(user,true);			
		if (hasErrors()) {
			page("edit");
			return;
		}
		service.updateUser(id, user);
		forward("list");
	}

	/**
	 * 删除用户
	 * 
	 * @param form
	 */
	public void delete(WebForm form) {
		String id = (String) form.get("id");
		User user = this.service.getUser(new Long(id));
		service.delUser(user);
		go("list");
	}

	/**
	 * 显示用户列表
	 * 
	 * @param form
	 */
	public void list(WebForm form) {	
		form.addResult("list", service.getAllUser());
	}

	/**
	 * 注销用户
	 */
	public void logout() {
		ActionContext.getContext().getSession().removeAttribute("user");
		page("index");
	}
}
