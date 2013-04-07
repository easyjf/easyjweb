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
package com.easyjf.web.components;
/**
 * 
 * @author daxia
 *
 */
public abstract class BoxComponent extends BaseComponent {
	private Integer width;
	private Integer height;
	
	private Boolean autoHeight;
	private Boolean autoWidth;
	
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public Boolean getAutoHeight() {
		return autoHeight;
	}
	public void setAutoHeight(Boolean autoHeight) {
		this.autoHeight = autoHeight;
	}
	public Boolean getAutoWidth() {
		return autoWidth;
	}
	public void setAutoWidth(Boolean autoWidth) {
		this.autoWidth = autoWidth;
	}
}
