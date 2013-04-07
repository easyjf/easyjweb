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
package com.easyjf.web.security.acegi;

import javax.servlet.FilterConfig;

import org.acegisecurity.util.FilterToBeanProxy;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;

import com.easyjf.container.Container;
import com.easyjf.container.impl.SingletonContainerLoader;
import com.easyjf.container.impl.SpringContainer;
import com.easyjf.web.ActionServlet;
import com.easyjf.web.Globals;

/**
 * Extend Acegi's FilterToBeanProxy , obtain Spring's ApplicationContext through EasyJWeb .
 * 
 * <p>
 * <b>Attention :</b>
 * 
 * There are still a lot of problem to be resolve .
 * 
 * 1.EasyJWeb框架的初始化存在非常致命的问题，初始化逻辑写死在 {@link ActionServlet} 中使得无法在其他地方如 Filter,Listener 中对框架进行初始化。
 * 
 * 2.无法透明的加载配置文件。
 * 
 * <p>
 * <b>Resolvent</b>
 * 
 * 1.设计一个初始化框架的Loader，把初始化框架的工作委托给Loader。
 * 
 * 2.设计一个通用的ResourceLoader，封装从文件系统和类路径加载资源的细节，可以参考Spring对资源的处理。
 * 
 * <p>
 * 在InnerContainer中有一个对parent container的引用，目前主要是解决Acegi的问题，通过该类可以解决这个问题。
 * 
 * 类似的问题都可以通过这种方式解决，没有必要在InnerContainer中增加对parent container的引用，引起框架结构的混乱。
 * 
 * 
 * @author ecsoftcn@hotmail.com
 * 
 * @version $Id: EasyJWebFilterToBeanProxy.java, 2007-4-15 上午03:10:52 Tony Exp $
 */
public class EasyJWebFilterToBeanProxy extends FilterToBeanProxy {

	/*
	 * @see org.acegisecurity.util.FilterToBeanProxy#getContext(javax.servlet.FilterConfig)
	 */
	@Override
	protected ApplicationContext getContext(FilterConfig filterConfig) {

		String configFile = filterConfig.getInitParameter(Globals.CONTAINER_CONFIG_LOCATION);
		if (configFile == null || configFile.trim().length() == 0) {
			throw new IllegalArgumentException(Globals.CONTAINER_CONFIG_LOCATION + " can't be null !");
		}

		//-------------------- 构造Container的逻辑委托给SingletonContainerLoader执行 ---------------------
		// Document doc = null;
		// try {
		// doc = new SAXReader().read(SingletonContainerLoader.class.getResourceAsStream(configFile));
		// } catch (DocumentException e) {
		// throw new IllegalArgumentException(configFile + "is not a valid config file !");
		// }
		//
		// DefaultContainer container = new DefaultContainer();
		// container.registerBeanDefinitions(BeanConfigReader.parseBeansFromDocument(doc));
		// container.refresh();
		//--------------------------------------------------------------------------------------------- 
		
		Container container = SingletonContainerLoader.getInstance(configFile);

		SpringContainer springContainer = (SpringContainer) container.getBean(Globals.SPRING_INTEGERATION_CONTAINER);
		BeanFactory factory=springContainer.getBeanFactory();
		if(factory instanceof ApplicationContext)return (ApplicationContext)factory;
		else return null;
	}

}
