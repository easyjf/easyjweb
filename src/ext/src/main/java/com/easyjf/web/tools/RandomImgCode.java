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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.easyjf.web.ActionContext;
import com.easyjf.web.IWebAction;
import com.easyjf.web.Module;
import com.easyjf.web.Page;
import com.easyjf.web.WebForm;

import java.awt.image.*;
import javax.imageio.*;
/**
 * 验证码图片输出
 * @author 天一
 *
 */
public class RandomImgCode implements IWebAction {
	public Page execute(WebForm form, Module module) throws Exception {
		HttpServletRequest request = ActionContext.getContext().getRequest();
		HttpServletResponse response = ActionContext.getContext().getResponse();
		response.setContentType("image/jpeg");
		ImageCode image = new ImageCode();
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		try {
			// 输出图象到页面
			BufferedImage img = image.creatImage();
			request.getSession().setAttribute("rand", image.getSRand());
			ImageIO.write(img, "JPEG", response.getOutputStream());
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			System.out.println("错误:" + e);
		}
		return null;
	}

}
