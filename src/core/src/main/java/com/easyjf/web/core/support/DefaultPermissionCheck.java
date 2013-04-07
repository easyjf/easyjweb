package com.easyjf.web.core.support;

import java.lang.reflect.Method;

import com.easyjf.container.annonation.PermissionVerify;
import com.easyjf.container.annonation.RoleVerify;
import com.easyjf.util.AuthorizationUtil;
import com.easyjf.web.ActionContext;
import com.easyjf.web.Page;
import com.easyjf.web.core.FrameworkEngine;
import com.easyjf.web.interceptor.IActionCommandInterceptor;

/**
 * 默认的权限检测类实现，通过AuthorizationUtil来得到当前用户所属的角色 并根据在Method上的标签来指定方法的执行角色
 * 
 * @author 大峡
 * 
 */
public class DefaultPermissionCheck implements IActionCommandInterceptor {
	public boolean checkPermission(Method method) {
		AuthorizationUtil authorizationUtil = getAuthorizationUtil();
		if (authorizationUtil != null) {
			PermissionVerify pv=this.getClass().getAnnotation(PermissionVerify.class);
			boolean module=false;
			if(pv!=null && pv.roles().length>0){
				for(String s:pv.roles()){
					if(authorizationUtil.is(s)){
						module=true;
						break;
					}
				}
			}
			else module=true;
			if(!module)return false;//如果模块检查通不过，则直接返回
			RoleVerify rv = method.getAnnotation(RoleVerify.class);
			if(rv!=null){
			String[] rs = rv.roles();
			if (rs != null && rs.length>0) {
				for (String s : rs){
					if (authorizationUtil.is(s))
						return true;
				}
				return false;
			}
			}
		}
		return true;
	}
	private AuthorizationUtil getAuthorizationUtil(){
		return  (AuthorizationUtil) FrameworkEngine.getContainer().getBean(AuthorizationUtil.class);
	}
	public void doAfter(Method method) {		
		
	}
	public Page doBefore(Method method) {
		PermissionVerify pv=method.getDeclaringClass().getAnnotation(PermissionVerify.class);
		RoleVerify rv = method.getAnnotation(RoleVerify.class);
		if((pv!=null && !pv.disable())||rv!=null||FrameworkEngine.getWebConfig().isPermissionVerify()){//执行权限查找
			if(!this.checkPermission(method)){//如果权限通不过		
				AuthorizationUtil authorizationUtil =getAuthorizationUtil();
				if (authorizationUtil != null && authorizationUtil.getUser()==null) {
					 ActionContext.getContext().getResponse().setHeader("LoginRequired", "true");					 
				}
				ActionContext.getContext().getResponse().setHeader("Unauthorized", "true");
				ActionContext.getContext().getResponse().setHeader("Cache-Control", "no-cache");
				Page p=ActionContext.getContext().getWebInvocationParam().getModule().findPage("PERMISSION_PAGE");//允许自定义配置权限不足显示页面						
				if(p==null)p=new Page("PERMISSION_PAGE","classpath:com/easyjf/web/exception/permissionError.html");
			    return p;
			}
		}
		return null;
	}	
}
