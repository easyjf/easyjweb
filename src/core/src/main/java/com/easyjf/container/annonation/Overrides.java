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

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
/**
 * 用于类的注释，用来定义全局的名称替换属性, 用来标注一组需要替换或重载的属性
 * 该标注主要用来辅助WebForm.toPo方法，使其能够正确地把WebForm中的对象设置到具体的模型对象中* 
 * 当在一个模型(域)对象中需要对一些名称重载时，可以使用该标签在类声明上一次性定义多个重载的属性。
 * 如下面的例子:
 * Person对象
 * <pre>
 * &#064;Overrides({&#064;OverrideProperty(name="name",newName="xm"),&#064;OverrideProperty(name="bornDate",newName="csrq")})
 * public class Person
 * {
 * private String name;
 * private Date bornDate;
 * }
 * 
 * 页面模板中的html表单内容
 * 
 * <form ...>
 * <input type=text name="xm" value="$!name"/>
 * <input type=text name="csrq" value="$!bornDate"/>
 * </form>
 *
 * Action中的使用WebForm对Person对象赋值
 * PersonAction
 * 
 * Person p=form.toPo(Person.class);
 * System.out.println(p.getName());
 * System.out.println(p.getBornDate());
 * </pre> 
 * @author 大峡
 *
 */
@Target(ElementType.TYPE )
@Retention(RUNTIME)
public @interface Overrides {
	OverrideProperty[] value() default {};
}
