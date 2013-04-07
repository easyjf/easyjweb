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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义与WebForm交互对象的属性。
 * <pre> 
 * 该标签只能用于类级别，一般是直接用在模型(域)对象上面。该标签的作用如下：
 * 1、指定模型(域)对象的名称，标识名称等，以便于显示友好的界面及提示等信息；
 * 2、用来批量对对象中的属性进行保护，如disInect，disRead等。
 * 3、用来批量指定对象中属性的验证信息，从面简单化验证配置。
 * 4、用来指定与WebForm进行交互其它特定信息 
 * &#064;FormPO(name="person",validators={&#064;Validator(name="required",field="name,sex,heigth,borndate")})
 * public class Person
 * {
 * ...
 * }
 * }
 * 
 * </pre>
 * @author 大峡、stef_wu
 * 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FormPO {
	/**
	 * 模型对象的标识，相当于name的值，在基于ajax的验证器中，客户端通过传进一个name，在服务器端通过这个name找到对应的对象
	 * <pre>
	 * &#064;FormPO("person")
	 *  public class Person { }
	 *  </pre>
	 * @return 实体或域对象的名称
	 */
	public String value() default "";

	/**
	 * 模型对象的名称及标识，若需要使用ajax形式的验证，则必须指定域或实体类的名称 参照value属性
	 * 
	 * @return 实体或域对象的名称
	 */
	public String name() default "";

	/**
	 * 指定实体(模型)对象的标识名称，从而能生成更加易于理解的名称 若不指定该值，则会使用name()的值作为模型(域)对象的标识名称 <code>
	 * &#064;FormPO(name="person",label="人物")
	 * </code>
	 * @return 返回对象的标识名称
	 */
	public String label() default "";

	/**
	 * 指定可通过toPo自动注入的属性，默认为全部可自动注入;如果设置了该值，则表示除指定可注入的属性以外，其它所有属性都为不能自动注入；
	 * 在EasyJWeb中，可以直接通在模型(域)对象上通过标签来指定只允许注入哪些属性值。如果一个属性值为可注入的，则可以使用WebForm.toPo(obj)方法，把WebForm中与obj属性名称相同的属性值设置到obj中。
	 * 需要注入的属性与逗号(,)作为分逗符。<pre>
	 * &#064;FormPO(inject="name,bornDate")
	 * public class Person
	 * {
	 * private Long id;
	 * private String name;
	 * private Date bornDate;
	 * private Integer loginTimes;
	 * }
	 * </pre>
	 * @return 返回指定允许注入的属性名
	 */
	public String inject() default "";

	/**
	 * 指定不可通过toPO自动注入的属性，当试图通过toPO更新该属性时，将会被忽略，并在日志中提示相关信息
	 * 在EasyJWeb中，一个模型(域)对象的属性默认情况下全部都是可注入的，我们可以通过disInject来指定不可注入的属性。
	 * 多个不可注入的属性使用逗号(,)作为分隔。 如上面inject示例，也可以写成下面的形式：
	 *  <pre>
	 * &#064;FormPO(disInject="id,loginTimes")
	 *  public class Person {
	 *   private Long id;
	 *   private String name; private Date
	 *   bornDate; private Integer loginTimes; }
	 *  </pre>
	 * @return 模型(域)对象中不可注入的属性
	 */
	public String disInject() default "";

	/**
	 * 指定不能直接通过addPO方法把数据添加到前台的属性，对应Field标签的readable=false属性
	 * 假如我们不想把bornDate这一个涉及用户隐私的数据显示到前台，则可以使用下面的注解 
	 *<pre>
	 * &#064;FormPO(disRead="bornDate")
	 * public class Person
	 * {
	 * }
	 * </pre>
	 * @return 不可以直接批量添加到WebForm中的一个或多个属性，若为多个属性则以逗号(,)作为分隔
	 */
	public String disRead() default "";

	/**
	 * 验证器,可以在PO中指定完整的验证器。 用来指定模型(域)对象中各属性的验证器，还可以使用复合验证器。
	 * 当很多属性的验证规则都一样的时候，通过FormPO中的validators来指定验证器，将会省略很多重复注解信息。 如下面的示例： <pre>
	 * &#064;FormPO(validators={&#064;Validator=(name="required",field="name,bornDate")}
	 * public class Person
	 * {
	 * private Long id;
	 * private String name;
	 * private Date bornDate;
	 * private Integer loginTimes;
	 * }
	 * </pre>
	 * @return 返回模型(域)对象的验证器
	 */
	public Validator[] validators() default {};

}
