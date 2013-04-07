package com.easyjf.web.validate;

import java.util.Date;

import com.easyjf.container.annonation.Field;
import com.easyjf.container.annonation.FormPO;
import com.easyjf.container.annonation.Validator;

@FormPO(validators = { @com.easyjf.container.annonation.Validator(name = "required", field = "name,bornDate") })
public class MyBean {
	private Long id;

	@Field(name="姓名",fieldName="name",validator=@com.easyjf.container.annonation.Validator(name="string",required=true,value="min:5;minMsg:字符数必须超过5个;max:8;trim;blank"))
	private String name;

	private Date bornDate;

	@Field(validator=@Validator(name = "regex", value = "expression:\\d{11}", required = true))
	private String tel;
	
	@Field(name="身份证号", validators = { @Validator(name="regex",value="msg:请填写有效的证件号码。;expression:\\d{15}|\\d{17}[\\dXx]", required = true) })
	private String idcard;
	
	public Date getBornDate() {
		return bornDate;
	}

	public void setBornDate(Date bornDate) {
		this.bornDate = bornDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	
}
