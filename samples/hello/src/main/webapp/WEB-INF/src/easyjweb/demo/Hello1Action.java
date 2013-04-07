package easyjweb.demo;

import java.util.Date;

import com.easyjf.web.IWebAction;
import com.easyjf.web.Module;
import com.easyjf.web.Page;
import com.easyjf.web.WebForm;

public class Hello1Action implements IWebAction{
	public Page execute(WebForm form, Module module) throws Exception {
		form.addResult("msg", "您好，这是EasyJWeb的第一个程序!");
		form.addResult("date", new Date());
		return new Page("/hello/index.html");
	}
}
