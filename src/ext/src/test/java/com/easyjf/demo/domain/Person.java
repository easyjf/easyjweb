package com.easyjf.demo.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.easyjf.container.annonation.Field;
import com.easyjf.container.annonation.FormPO;
import com.easyjf.container.annonation.Validator;

@Entity
@FormPO(name="person")
public class Person implements Serializable {
	
	@Field(gener=false)
	private static final long serialVersionUID = -2017284548665009745L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)

	private Long id;

	@Column(length = 32)
	@Field(name="姓名",validator=@Validator(name="string",value="required;min:2;max:16"))
	private String name;

	@Column(length = 6)
	@Field(name="性别",validator=@Validator(name="string",value="required;min:1;max:3"))
	private String sex;

	@Column(length = 40)
	@Field(name="电子邮箱",validator=@Validator(name="email",required=true))
	private String mail;
	
	@Field(name="年龄",validator=@Validator(name="range",value="required;min:0;max:150"))
	private int age;

	@Field(name="生日")
	private Date birthday;

	@Column(length = 200)
	@Field(name="简介")
	public String intro;
	
	@ManyToOne
	private Person parent;

	@OneToMany(mappedBy = "parent")
	private List<Person> children;

	@Field(name="小数")
	private java.math.BigDecimal scale = new java.math.BigDecimal(15);

	public Person() {

	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Person(Person parent) {
		this.parent = parent;
	}

	public Person(String name) {
		this.name = name;
	}

	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public Person(String name, String sex) {
		this.name = name;
		this.sex = sex;
	}

	public Person(String name, String sex, int age) {
		this.name = name;
		this.sex = sex;
		this.age = age;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public List getChildren() {
		return children;
	}

	public void setChildren(List children) {
		this.children = children;
	}

	public Person getParent() {
		return parent;
	}

	public void setParent(Person parent) {
		this.parent = parent;
	}

	public java.math.BigDecimal getScale() {
		return scale;
	}

	public void setScale(java.math.BigDecimal scale) {
		this.scale = scale;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String toString() {
		return "name:" + name + ";sex:" + sex + ";age:" + age + ";birthday:"
				+ birthday + "\nparent=" + parent;
	}
}
