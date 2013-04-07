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

import java.util.HashMap;
import java.util.Map;

import com.easyjf.container.annonation.InjectDisable;
import com.easyjf.util.I18n;
import com.easyjf.web.Globals;
import com.easyjf.web.Module;
import com.easyjf.web.Page;
import com.easyjf.web.WebForm;
/**
 * 基于向导的Action支持基类
 * @since 1.1
 * @author daxia
 *
 */
public abstract class AbstractWizardAction extends AbstractCmdAction {
	/**
	 * 所有操作
	 */
	private String[] commands;
	/**
	 * 数据
	 */
	@InjectDisable
	private Map datas = new HashMap();
	/**
	 * 操作状态
	 */
	@InjectDisable
	private Map status = new HashMap();
	/**
	 * 记录当前状态
	 */
	@InjectDisable
	private String currentCommand;

	/**
	 * 允许后退
	 */
	@InjectDisable
	private boolean allowBack;
	/**
	 * 允许前进
	 */
	@InjectDisable
    private boolean allowForward;
    /**
     * 是否需要对每一步进行的执行结果进行强制跟踪，只有上一步执行成功后，才能进行入本步骤
     */
	@InjectDisable
    private boolean statusCheck=false;
	/**
	 * 该步骤成功执行
	 */
	@InjectDisable
	protected final static String STATUS_OK="OK";
	
	public void setAllowBack(boolean allowBack) {
		this.allowBack = allowBack;
	}

	public void setAllowForward(boolean allowForward) {
		this.allowForward = allowForward;
	}

	public void setStatusCheck(boolean statusCheck) {
		this.statusCheck = statusCheck;
	}

	public void setCommands(String[] commands) {
		this.commands = commands;
	}

	@Override
	public Page execute(WebForm form, Module module) throws Exception {
		//String cmd=this.getCurrentCommand();//此时currentCommand得到的是前一状态信息
		if(this.statusCheck&&this.getCurrentStep()>0){
		String status=this.getStatus();
		if(!STATUS_OK.equals(status))
			{
			int step=this.getValidatePrevStep();
			if(step<0)step=0;
			//System.out.println("执行"+this.commands[step]);
			this.addError("flow", I18n.getMessage("core.web.please.frist.execute.steps")+this.commands[step]);
			form.getTextElement().put("easyJWebCommand",this.commands[step]);
			}	
		}
		//System.out.println("执行..........."+this.command);
		super.reset();
		Page forward = super.execute(form, module);
		//System.out.println("返回...."+forward);
		/**
		 * 检查执行成功的标记
		 */
		if(statusCheck && STATUS_OK.equals(status))
		{
			this.currentCommand=getNextCommand();
			this.command=this.currentCommand;
		}
		String cmd = getCurrentCommand();
		String prevCmd = getPreviousCommand();
		String nextCmd = getNextCommand();
		/**
		 * 保存上一步下一步参数
		 */
		if (prevCmd!=null && allowBack) {
			form.addResult("prevCmd", prevCmd);
		}
		if (isFinished())
			form.addResult("nextCmd", "Finish");
		else if (nextCmd!=null && allowForward)
			form.addResult("nextCmd", nextCmd);
		/**
		 * 执行跳转
		 */
		if (forward == null && cmd != null && !"".equals(cmd)) {
			forward = this.forwardPage;
			if (forward == null)
				forward = module.findPage(cmd);
			if (forward == null) {
				String templateUrl = module.getViews() + module.getPath() + "/"
						+ cmd;
				String ext = Globals.DEFAULT_TEMPLATE_EXT;
				if (ext.charAt(0) == '.')
					ext = ext.substring(1);
				forward = new Page(this.getClass() + cmd, templateUrl + "."
						+ ext);
			}
		}
		return forward;
	}

	/**
	 * 得到前一个有效步骤
	 * @return
	 */
	protected int getValidatePrevStep()
	{
		int i=this.getCurrentStep();
		if(i<=0)return i;
		if(this.statusCheck)
		{
			for(int j=0;j>i;j++)
			{				
				if(!STATUS_OK.equals(this.getStatus(j)))return j;
			}
		}
		return i-1;
	}
	@Override
	public Page doInit(WebForm form, Module module) {
		if (this.commands != null && this.commands.length > 0)
		{
		//	this.currentCommand=this.commands[0];
			return forward(this.commands[0]);
		}
		else
			return Page.nullPage;
	}

	protected String getCurrentCommand() {
		if (this.commands != null && this.command!=null) {
			for (int i = 0; i < this.commands.length; i++)
				if (this.commands[i].equals(this.command))
					this.currentCommand = this.command;
		}
		return this.currentCommand;
	}

	/**
	 * 得到当前步骤
	 * 
	 * @return
	 */
	protected int getCurrentStep() {
		int ret = -1;
		String cmd = getCurrentCommand();
		if (this.commands != null) {
			for (int i = 0; i < commands.length; i++) {
				if (cmd != null && commands[i].equals(cmd))
					return ret = i;
			}
		}
		return ret;
	}

	/**
	 * 得到前一步骤
	 * 
	 * @return
	 */
	protected String getPreviousCommand() {
		return getCurrentStep() <= 0 ? null : this.commands[this
				.getCurrentStep() - 1];
	}

	/**
	 * 得到下一步骤
	 * 
	 * @return
	 */
	protected String getNextCommand() {
		return !isFinished() ? this.commands[this.getCurrentStep() + 1]
				: null;
	}

	/**
	 * 是否最后一步
	 * 
	 * @return
	 */
	protected boolean isFinished() {
		return (getCurrentStep() >= this.commands.length - 1);
	}

	/**
	 * 保存每一步临时数据
	 * 
	 * @param obj
	 */
	protected void saveTemporaryData(Object obj) {
		this.datas.put(this.getCurrentCommand(), obj);
	}

	/**
	 * 返回当前步骤的临时数据
	 * 
	 * @return
	 */
	protected Object getTemporaryData() {
		return this.datas.get(this.getCurrentStep());
	}
/**
 * 返回某一步骤的临时数据信息
 * @param step
 * @return
 */
	protected Object getTemporaryData(int step)
	{
		if(step<0 || step>=this.commands.length)return null;
		return this.datas.get(this.commands[step]);
	}
	/**
	 * 完成当前步骤
	 */
	protected void setStatusOk()
	{
		this.changeStatus(this.getCurrentCommand(),STATUS_OK);//成功执行，则更改前一状态信息为OK。
	}
	/**
	 * 保存当前步骤的状态信息
	 * 
	 * @param status
	 */
	protected void changeStatus(String status) {
		this.status.put(this.getCurrentCommand(), status);
	}

	/**
	 * 保存某一步骤的状态信息
	 * 
	 * @param cmd
	 * @param status
	 */
	protected void changeStatus(String cmd, String status) {
		if(cmd==null)return;
		this.status.put(cmd, status);
	}
	/**
	 * 返回当前状态信息
	 * @return
	 */
	protected String getStatus()
	{
		return getStatus(this.getCurrentStep());
	}
	/**
	 * 返回某一步的状态信息
	 * @param step
	 * @return 如果该步骤的状态信息存在，则返回该信息，否则返回Null
	 */
	protected String getStatus(int step)
	{
		if(step<0 || step>=this.commands.length)return null;
		return (String)this.status.get(this.commands[step]);
	}
}
