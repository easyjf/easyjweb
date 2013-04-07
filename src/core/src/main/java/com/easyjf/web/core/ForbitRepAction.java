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
package com.easyjf.web.core;

import javax.servlet.http.HttpServletRequest;


/**
 * 
 * <p>
 * Title: 用户提交Id的验证 * </p>
 * <p>
 * Description: 用户提交Id的验证
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: www.easyjf.com
 * </p>
 * 
 * @author 张钰
 * @version 0.1
 */
public class ForbitRepAction {
   
    private static ForbitRepProcessor forbit = ForbitRepProcessor.getInstance();
    
   protected String generateForbitRep(HttpServletRequest request) {
        return forbit.generateForbitRep(request);
    }

   protected boolean isForbitRepValid(HttpServletRequest request) {

        return forbit.isForbitRepValid(request, false);

    }


    protected boolean isForbitRepValid(HttpServletRequest request, boolean reset) {

        return forbit.isForbitRepValid(request, reset);

    }


    protected void resetForbitRep(HttpServletRequest request) {

        forbit.resetForbitRep(request);

    }

    protected void saveForbitRep(HttpServletRequest request) {
        forbit.saveForbitRep(request);
    }
   
   
}
