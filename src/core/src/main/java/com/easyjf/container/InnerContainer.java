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
package com.easyjf.container;

/**
 * 具有层次结构的容器 通过这种结构的容器，使得我们可以在一个容器中存放包含其它的容器，从而形成一个容器集合，实现超级IOC容器的概念。
 * 
 * @author 大峡
 * 
 */
public interface InnerContainer extends Container {
	/**
	 * 对容器进行初始化，主要进行配置文件的加载，容器中各种参数的初始化等，不同的容器，具有不同的初始化流程。
	 * 
	 */
	void init();

	/**
	 * 设置本容器的父容器，在实际应用中，我们可以根据需要给当前容器设置一个父容器，从而使两个容器形成关联。当通过子容器查找bean的时候，若找不到，则会进一步到其父容器中进行查找。
	 * 
	 * @param container 一个跟当前容器没有任何关系的容器
	 */
	void setParent(Container container);

	/**
	 * 得到当前容器的父容器
	 * @return 若当前容器存在父容器，则返回该父容器，否则返回null
	 */
	Container getParent();
}
