Ext.QuickTips.init();
LoginWindow = Ext.extend(Ext.Window, {
	width : 240,
	height : 130,
	title : "用户登录",
	buttonAlign : "center",
	layout : "fit",
	login : function() {
		this.fp.getForm().submit({
					url : "login.ejf?cmd=login",
					waitMsg : "正在登录",
					waitTitle : "请稍候",
					success : function(form, action) {
						//登录成功，路转到主页
						location.href='manage.ejf';
					},
					failure : function(form, action) {
						if (action.failureType == Ext.form.Action.SERVER_INVALID) {
							Ext.Msg.alert("登录失败", action.result.errors?action.result.errors.msg:"未知原因");
						}
                        form.findField("name").focus();
					}
				});
	},
	reset : function() {
		this.fp.getForm().reset();
	},
	createFormPanel : function() {
		return new Ext.form.FormPanel({
					labelWidth : 60,
					frame : true,
					labelAlign : "right",
					items : [{
								xtype : "textfield",
								name : "name",
								fieldLabel : "用户名",
								allowBlank : false
							}, {
								xtype : "textfield",
								name : "password",
								inputType : "password",
								fieldLabel : "密码",
								allowBlank : false
							}]
				})
	},
	initComponent : function() {
		this.buttons = [{
					text : "确定",
					handler : this.login,
					scope : this
				}, {
					text : "重置",
					handler : this.reset,
					scope : this
				}];
		this.keys = {
			key : 13, // or Ext.EventObject.ENTER
			fn : this.login,
			scope : this
		};
		LoginWindow.superclass.initComponent.call(this);
		this.fp = this.createFormPanel();
		this.add(this.fp);
	}
})
Ext.onReady(function() {
			var win = new LoginWindow();
			win.show();
			Ext.removeNode(Ext.getDom("loading"));

		});