package com.easyjf.container.annonation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 用来描述方法及调用角色
 * @author 大峡
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RoleVerify {
	/**
	 * 描述一个方法名称
	 */
	public String value() default "";
	
	/**
	 * 指定一个方法的访问角色
	 * @return
	 */
	public String[] roles();
}
