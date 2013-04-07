/*
 * Copyright 2006-2008 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.easyjf.container.annonation;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.easyjf.web.POLoadDao;

/**
 * 该标签用来加载关联<br>
 * 在EasyJWeb中提供了一个非常实用的方法把Form中的数据传入模型(域)对象中，toPo过程中，我们可以把前台表单中的普通类型的属性值传入到模型对象中
 * 但对于一些关联对象，我们在表单中只能存放关联对象的id，而在赋值的时候需要通过这个id从业务（或持久）层中加载这个对象，然后设置到指定的属性中，此时可以使用POLoad这个标签，他可以处理这个自动关联。
 * 请看下面的例子： <pre>
 * public class BBSDir
 * {
 * private Long id;
 * private String title;
 * }
 * public class BBSDoc
 * {
 * &#064;Id
 * private Long id;
 * private String title;
 * &#064;POLoad(name="dirId")
 * &#064;ManyToOne
 * private BBSDir dir;
 *   
 * }
 *  html表单中的内容： 
 * <form ..>
 * 请选择目录：<select name="dirId">#foreach($info in $dirList)<option value="$info.id">$!info.title</option>#end</select>
 * <input type=text name="title" value="$!title"/>
 * </form>
 * 
 * BBSDocAction中的doSave方法：
 * public Page doSave(WebForm form,Module module)
 * {
 * BBSDoc doc=form.toPo(BBSDoc.class);
 * System.out.println(doc.getDir().getTitle());
 * }
 * 
 * 在默认的情况下，EasyJWeb部是使用POLoadDao的实现来加载关联的对象，因此需要我们的容器中有一个POLoadDao的实现Bean。如下面的配置使用JPA的DAO来加载关联对象：
 * 
 * <bean id="jpaPoLoader" class="com.easyjf.core.dao.impl.JpaPOLoaderImpl">
 <property name="entityManagerFactory" ref="entityManagerFactory" />
 </bean>
 </pre>
 * @author 大峡
 * 
 */
@Target( { METHOD, CONSTRUCTOR, FIELD })
@Retention(RUNTIME)
public @interface POLoad {
	/**
	 * 指定属性主表单属性名称，默认值为属性的名称，比如加载dir。 如果在表单中该属性的名称换成其它值，如dirId，则需要在标签中注名
	 * 
	 * @return 返回要加载的对象主键在表单中的名称
	 */
	String name() default "";

	/**
	 * 指定实体主键的类型，默认值为Long。
	 * 
	 * @return 加载对象的主键类型
	 */
	Class pkClz() default Long.class;

	/**
	 * 指定加载器，默认为POLoadDao的实现 在实际加载的过程中，是通这个POLoadDao来进行对象加载。
	 * 
	 * @see com.easyjf.web.tools.POLoadDao.class
	 * @return 返回我写的加载器，默认为POLoadDao这个接口
	 */
	Class loadDao() default POLoadDao.class;
}
