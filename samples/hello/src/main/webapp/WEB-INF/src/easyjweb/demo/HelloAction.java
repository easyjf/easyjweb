package easyjweb.demo;

import java.util.Date;

import com.easyjf.web.WebForm;
import com.easyjf.web.core.AbstractPageCmdAction;

public class HelloAction extends AbstractPageCmdAction {
	public void index(WebForm form) {
		form.addResult("msg", "您好，这是EasyJWeb的第一个程序!");		
		form.addResult("date", new Date());		
	}
}
