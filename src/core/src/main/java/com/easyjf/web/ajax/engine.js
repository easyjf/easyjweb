EasyAjax = function() {
};
EasyAjax._defaultPath = "easyajax";
EasyAjax._ajaxCallPath = "$html.forward('easyajax.ajaxCall')";
EasyAjax.DEBUG = false;
EasyAjax.preHook = null;
EasyAjax.postHook = null;
EasyAjax._randomId = function() {
	var random = Math.floor(Math.random() * 10001);
	var id = (random + "_" + new Date().getTime()).toString();
	return id;
}
EasyAjax._execute = function(scriptName, method, paraTypes) {
	var args = [];
	for (var i = 0; i < arguments.length - 3; i++) {
		args[i] = arguments[i + 3];
	}
	var params, callData, paras = "";
	var lastArg = args[args.length - 1];
	if (typeof lastArg == "function") {
		// 最后一个参数是否为回调函数
		callData = {
			callback : args.pop(),
			scope : this
		};
		params = args;
	} else if (lastArg != null && typeof lastArg == "object"
			&& lastArg.callback != null
			&& typeof lastArg.callback == "function") {
		// 最后一个参数是回调对象
		callData = args.pop();
		params = args;
	} else {
		callData = {
			callback : function() {
			},
			scope : this
		};
		params = args;
	}
	for (var i = 0; i < params.length; i++) {
		paras += EasyAjax.parseParameter(i, params[i]);
	}
	if (paraTypes != null) {
		for (var i = 0; i < paraTypes.length; i++) {
			if (paraTypes[i] != null)
				paras += "ajax-call-p" + i + ".java-type=" + paraTypes[i] + "&";
		}
	}

	if (paras)
		paras = paras.replace(/%/g, "%25");//处理特殊字符% 
	var remoteCallId = EasyAjax._randomId();
	EasyAjax.currentCallId = remoteCallId;
	if (EasyAjax.DEBUG)
		alert(paras);
	if (EasyAjax.preHook)
		EasyAjax.preHook();
	var myAjax = new Ajax.Request(EasyAjax._ajaxCallPath, {
		evalScripts : true,
		method : "post",
		parameters : "scriptName=" + scriptName + "&methodName=" + method
				+ "&remoteCallId=" + remoteCallId + "&" + paras,
		onComplete : function(data) {
			if (EasyAjax.postHook)
				EasyAjax.postHook();
			if (EasyAjax.DEBUG)
				alert(data.responseText);
			try {
				var s = data.responseText
						+ ";EasyAjax._response(json_result,callData.callback,callData.scope);";
				eval(s);
			} catch (e) {
			}
		}
	});
}
EasyAjax._response = function(result, callback, scope) {
	try {
		if (!result.error) {
			if (EasyAjax.currentCallId == result.remoteCallId)
				callback.call(scope, result.obj);
		} else {
			alert(result.obj);
		}
	} catch (e) {
		alert(e);
	}
}
EasyAjax.parseParameter = function(index, obj) {
	var s = "";
	var prex = null;
	var propertyPrex = "ajax-call-p" + index;
	if (obj == null)
		return propertyPrex + ".value=null&";
	if (arguments.length > 2)
		prex = arguments[2];
	if (typeof obj == "object") {
		if (obj.constructor == Array) {
			s += propertyPrex + ".value=[";
			for (var i = 0; i < obj.length; i++) {
				s += this._paraseJSon2String(obj[i]);
				if (i < obj.length - 1)
					s += ",";
			}
			s += "]";
		} else {
			for (property in obj) {
				if ((typeof obj[property]) != "object") {
					s += propertyPrex + (prex == null ? "" : "." + prex) + "."
							+ property + "=" + obj[property] + "&";
				} else
					s += this.parseParameter(index, obj[property], prex == null
									? property
									: prex + "." + property);
			}
		}
	} else
		s = propertyPrex + ".value=" + obj;
	s += "&";
	return s;
}

EasyAjax._paraseJSon2String = function(obj) {
	if (obj == null)
		return null;
	if (typeof obj == "object") {
		if (obj.constructor == Array) {
			var s = "[";
			for (var i = 0; i < obj.length; i++) {
				s += this._paraseJSon2String(obj[i]);
				if (i < obj.length - 1)
					s += ",";
			}
			s += "]";
			return s;
		} else {
			var s = "{";
			for (p in obj) {
				s += p + "=" + this._paraseJSon2String(obj[p]) + ",";
			}
			// s=s.substring(0,s.length()-1);
			s += "}";
			return s;
		}
	} else
		return obj;
}
EasyAjax.setPreHook = function(hook) {
	EasyAjax.preHook = hook;
}
EasyAjax.setPostHook = function(hook) {
	EasyAjax.postHook = hook;
}
EasyAjax.closeLoadingMessage = function() {
	EasyAjax.setPreHook(null);
	EasyAjax.setPostHook(null);
}
EasyAjax.useLoadingMessage = function(message) {
	var loadingMessage;
	if (message)
		loadingMessage = message;
	else
		loadingMessage = "Loading";
	EasyAjax.setPreHook(function() {
				var disabledZone = $('disabledZone');
				if (!disabledZone) {
					disabledZone = document.createElement('div');
					disabledZone.setAttribute('id', 'disabledZone');
					disabledZone.style.position = "absolute";
					disabledZone.style.zIndex = "1000";
					disabledZone.style.left = "0px";
					disabledZone.style.top = "0px";
					disabledZone.style.width = "100%";
					disabledZone.style.height = "100%";
					document.body.appendChild(disabledZone);
					var messageZone = document.createElement('div');
					messageZone.setAttribute('id', 'messageZone');
					messageZone.style.position = "absolute";
					messageZone.style.top = "0px";
					messageZone.style.right = "0px";
					messageZone.style.background = "red";
					messageZone.style.color = "white";
					messageZone.style.fontFamily = "Arial,Helvetica,sans-serif";
					messageZone.style.padding = "4px";
					disabledZone.appendChild(messageZone);
					var text = document.createTextNode(loadingMessage);
					messageZone.appendChild(text);
				} else {
					$('messageZone').innerHTML = loadingMessage;
					disabledZone.style.visibility = 'visible';
				}
			});
	EasyAjax.setPostHook(function() {
				$('disabledZone').style.visibility = 'hidden';
			});
};

// ///下面开始属于EasyAjax的属性
EasyAjaxUtil = {};
EasyAjaxUtil.showPageInfo = function(tag) {
	if (!this.pageList) {
		this.pageList = {
			rowCount : 0,
			pages : 0,
			pageSize : 15,
			currentPage : 1,
			result : null
		};
	}
	var currentPage = this.pageList.currentPage;
	var pages = this.pageList.pages;
	var s = "第<strong>" + currentPage + "</strong>页 共<strong>" + pages
			+ "</strong>页<span>[共<b>" + this.pageList.rowCount
			+ "</b>条记录]</span> 　　分页：";
	if (currentPage > 1) {
		s += "<a href=# onclick='return EasyAjaxUtil.gotoPage(1)'>首页</a> ";
		s += "<a href=# onclick='return EasyAjaxUtil.gotoPage("
				+ (currentPage - 1) + ")'>上一页</a> ";
	}
	var beginPage = currentPage - 3 < 1 ? 1 : currentPage - 3;
	if (beginPage < pages) {
		s += "第　";
		for (var i = beginPage, j = 0; i <= pages && j < 6; i++, j++) {
			if (i == currentPage)
				s += "<font color=red>" + i + "</font> ";
			else
				s += "<a href=# onclick='return EasyAjaxUtil.gotoPage(" + i
						+ ")'>" + i + "</a> ";
		}
		s += "页　";
	}
	if (currentPage < pages) {
		s += "<a href=# onclick='return EasyAjaxUtil.gotoPage("
				+ (currentPage + 1) + ")'>下一页</a> ";
		s += "<a href=# onclick='return EasyAjaxUtil.gotoPage(" + pages
				+ ")'>末页</a> ";
	}
	s += " 转到指定页:<input type=text id='directGotoSomePage' size=2 class='form_text'>　<input type=button onclick=EasyAjaxUtil.gotoPage($('directGotoSomePage').value) value='跳转' class='button'>";
	if (!tag)
		tag = $("pageInfo");
	tag.innerHTML = s;
}
EasyAjaxUtil.gotoPage = function(i) {
	if (i < 1) {
		alert("页码必须大于0");
		return;
	}
	if (i > this.pageList.pages) {
		alert("页码不能超过" + this.pageList.pages);
		return;
	}
	if (!this.queryPage)
		this.queryPage = this._defaultQueryPage;
	this.queryPage(i);
}
EasyAjaxUtil._defaultQueryPage = function(i) {
	if (this.listData)
		this.listData(i);
}
EasyAjaxUtil.getCalendar = function(objId) {

	var obj = $(objId);
	if (!obj) {
		// 如果是文本框的onmousedown世间是由脚本绑定的,且没有参数 modified by qiuchun
		if (event.srcElement) {
			var pchild = event.srcElement;
			if (pchild.type && pchild.type.toLowerCase() == "text")
				obj = pchild;
		}
	}
	var x = event.screenX;
	var y = event.screenY;
	var result = window
			.showModalDialog(
					'easywidget.ejf?easyJWebCommand=calendar',
					'Calendar',
					"dialogLeft:"
							+ x
							+ "px;dialogTop:"
							+ y
							+ "px;dialogWidth:195px;dialogHeight:230px;help:no;status:no");
	if (result != null)
		obj.value = result;
	// eval(arguments[0]+".value=result");
	return false;
}
EasyAjaxUtil.loadPage = function(url, paras, targetId) {
	var resultId = targetId;
	if (!resultId)
		resultId = "main";
	if (arguments.length > 2
			&& (typeof arguments[arguments.length - 1]) == "string") {
		resultId = arguments[arguments.length - 1];
	}
	paras = paras || "";
	if (EasyAjax.preHook)
		EasyAjax.preHook();
	var myAjax = new Ajax.Request(url, {
				evalScripts : true,
				method : "post",
				parameters : paras,
				onComplete : function(data) {
					if (EasyAjax.postHook)
						EasyAjax.postHook();
					if (EasyAjax.DEBUG)
						alert(data.responseText);
					try {
						if (typeof targetId == "function")
							targetId(data);
						else {
							var s = "Element.update('" + resultId
									+ "',data.responseText);";
							eval(s);
						}
					} catch (e) {
						alert(e);
					}
				}
			});
}
/**
 * 使用ajax的方式来提交表单，表单执行的结果返回到targetId指定的html中
 */
EasyAjaxUtil.ajaxSubmit = function(formName, targetId) {
	var target = targetId ? targetId : "main";
	var theForm = $(formName);
	var url = theForm.action;
	var paras = Form.serialize(theForm);
	EasyAjaxUtil.loadPage(url, paras, target);
}

EasyAjaxUtil.loadStaticPage = function(url) {
	var callBack = null;
	var resultId = "main";
	if (arguments.length > 1
			&& (typeof arguments[arguments.length - 1]) == "function") {
		callBack = arguments[arguments.length - 1];
	}
	if (arguments.length > 2
			&& (typeof arguments[arguments.length - 2]) == "string") {
		resultId = arguments[arguments.length - 2];
	}
	$(resultId).innerHTML = "<font color=blue>正在加载数据，请稍候...</font>";
	var myAjax = new Ajax.Request(
			"easywidget.ejf?easyJWebCommand=loadPage&url=" + url, {
				evalScripts : true,
				method : 'POST',
				onComplete : function(req) {
					$(resultId).innerHTML = "完成加载！";
					Element.update(resultId, req.responseText);
					if (callBack != null)
						callBack.call(this);
				}
			});
}

/*
 * 模式对话框，用于弹出输入信息
 */
ModalDialog = {};
ModalDialog.result = null;// 选择结果
ModalDialog.callback = null;// 回调函数
ModalDialog.dialogData = null;// 下拉选择框中的数据
ModalDialog.show = function(width, height, url, callback, title) {
	var x = event.clientX, y = event.clientY + 10;
	if (x > 880)
		x = 880;
	var modalDialog = $("modalDialog");
	if (!title)
		title = "";
	if (callback)
		ModalDialog.callback = callback;
	if (!modalDialog) {
		modalDialog = document.createElement('div');
		modalDialog.setAttribute('id', 'modalDialog');
		modalDialog.style.position = "absolute";
		modalDialog.style.zIndex = "1000";
		modalDialog.style.background = "#BBD6FD";
		modalDialog.style.border = "1px solid #008400";
		modalDialog.innerHTML = "<table  cellSpacing=0 cellPadding=0 width=100%><TR><TD height=20 align=center class=titletxt bgcolor=#C1DAFF style=\"height:16px; border-bottom:1px solid #008400; color: #5575CA\">请选择"
				+ title + "</TD></TR></table>";
		modalDialog.innerHTML += "<div id=\"modalDialogContent\"></div>";
		modalDialog.innerHTML += "<select id=\"modalDialogSelectList\" class=\"intro\" size=\"10\" onDblClick=\"ModalDialog.choice(this.selectedIndex);\" style=\"width:99%\"></select>";
		modalDialog.innerHTML += "<table><TR><TD height=20 align=\"center\" class=\"titletxt\" style=\"padding-bottom:2px;\"><input type=\"button\" name=\"ok\" value=\"确定\" onClick=\"ModalDialog.choice($('modalDialogSelectList').selectedIndex);\" class=\"button\" />　<input type=\"button\" name=\"cancel\" value=\"取消\" onClick=\"ModalDialog.cancel();\" class=\"button\" />　<input type=\"button\"  value=\"关闭\" onClick=\"ModalDialog.close();\" class=\"button\" /></TD></TR></TBODY></TABLE>";
		// modalDialog.innerHTML="层的测试";
		document.body.appendChild(modalDialog);
	}
	modalDialog.style.left = x + "px";
	modalDialog.style.top = y + "px";
	modalDialog.style.width = width;
	modalDialog.style.height = height;
	if (url)
		loadPage(url, null, "modalDialogContent");
	modalDialog.style.visibility = "visible";
}
ModalDialog.setX = function(x) {
	if ($("modalDialog"))
		$("modalDialog").style.left = x + "px";
}
ModalDialog.setY = function(y) {
	if ($("modalDialog"))
		$("modalDialog").style.top = y + "px";
}
ModalDialog.close = function() {
	var modalDialog = $("modalDialog");
	if (modalDialog) {
		// modalDialog.style.visibility="hidden";
		Element.remove(modalDialog);
	}
}
ModalDialog.choice = function(index) {
	if (this.dialogData) {
		if (index < this.dialogData.length) {
			ModalDialog.result = this.dialogData[index];
			if (this.callback)
				ModalDialog.callback(ModalDialog.result);
		} else
			alert("返回的坐标值超过对话框中的数据行数!");
	} else
		alert("对话框中没有数据");
	this.close();
}
ModalDialog.choiceCustomValue = function(result) {
	ModalDialog.result = result;
	if (ModalDialog.callback)
		ModalDialog.callback(result);
	this.close();
}
ModalDialog.cancel = function() {
	ModalDialog.result = null;
	if (ModalDialog.callback)
		ModalDialog.callback(null);
	this.close();
}
ModalDialog.defaultQueryResult = function(pageList) {
	$("modalDialogSelectList").options.length = 0;
	ModalDialog.dialogData = pageList.result;
	if (pageList.rowCount > 0) {
		for (var i = 0; i < pageList.result.length; i++) {
			$("modalDialogSelectList").options[i] = new Option(
					pageList.result[i].title, pageList.result[i].id + ";"
							+ pageList.result[i].title);
		}
	}
}
EasyAjaxUtil.ModalDialog = ModalDialog;

// 动态加载Json数组中的内容
EasyAjaxUtil.daymicLoadSelect = function(id, url) {
	if (EasyAjax.preHook)
		EasyAjax.preHook();
	EasyAjaxUtil.daymicLoadUrl(url, function(req) {
		var ret = eval(req.responseText);
		ret.pop();
		$(id).options.length = 1;
		for (var i = 0; i < ret.length; i++) {
			$(id).options[$(id).length] = new Option(ret[i].title, ret[i].value);
		}
		if (EasyAjax.postHook)
			EasyAjax.postHook();
	});
}

// 动态加载指定url
EasyAjaxUtil.daymicLoadUrl = function(url, callback) {
	var pars = null;
	if (EasyAjax.preHook)
		EasyAjax.preHook();
	var myAjax = new Ajax.Request(url, {
				evalScripts : true,
				method : 'POST',
				onComplete : function(req) {
					if (EasyAjax.postHook)
						EasyAjax.postHook();
					callback(req);
				}
			});
}
