package com.easyjf.container.annonation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 用来标注Action权限,也可以用来描述一个模块
 * @author 大峡
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionVerify {
	/**
	 * 描述模块名称
	 * @return
	 */
	public String value() default "";
	/**
	 * 用来指定本模块的访问角色
	 * @return
	 */
	public String[] roles() default {};
	/**
	 * 是否禁用权限系统
	 * @return
	 */
	boolean disable() default false;
}
