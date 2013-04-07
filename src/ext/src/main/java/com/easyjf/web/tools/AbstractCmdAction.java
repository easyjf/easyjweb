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
package com.easyjf.web.tools;


/**
 * 
 * <p>
 * Title: 通过命令式Action类
 * </p>
 * <p>
 * Description: 这是一个对Action命令进行封装的抽象类,用于为提供命令式WebAction的写法用户直接调用,可以减少书写繁锁的if语句
 * </p>
 * easyJWebCommand命令del(或Del)对应Action类的doDel方法
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: www.easyjf.com
 * </p>
 * 
 * 1:35 2007-6-28 CHANGELOG 改变execute(WebForm form,Module module) 为
 * execute(WebForm f,Module m) ----DRY 改变easyJWebCommand为cmd，同时支持
 * @deprecated use com.easyjf.web.core.AbstractCmdAction insteaded
 * @author 蔡世友 郭朝斌
 * @version 1.0
 */
public abstract class AbstractCmdAction extends com.easyjf.web.core.AbstractCmdAction {

}
