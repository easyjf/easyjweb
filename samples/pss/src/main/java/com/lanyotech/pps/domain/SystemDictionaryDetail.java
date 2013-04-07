package com.lanyotech.pps.domain;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.easyjf.container.annonation.POLoad;
import com.easyjf.util.CommUtil;
import com.easyjf.web.ajax.IJsonObject;
@Entity
@Table(name = "systemdictionarydetail")
public class SystemDictionaryDetail implements IJsonObject {
	@Id
	@GeneratedValue(strategy = javax.persistence.GenerationType.TABLE)
	private Long id;

	@ManyToOne(fetch=FetchType.LAZY)
	@POLoad
	private SystemDictionary parent;//系统目录

	private String title;

	private String tvalue;	
	/**
	 * 排序
	 */
	private Integer sequence;
	
	/**
	 * 说明
	 */
	private String intro;

	
	public Object toJSonObject() {
		Map map=CommUtil.obj2mapExcept(this, new String[]{"parent"});
		if(parent!=null){
			map.put("parent", CommUtil.obj2map(parent, new String[]{"id","sn","title"}));
		}
		return map;
	}

	public Long getId() {
		return id;
	}

	public SystemDictionary getParent() {
		return parent;
	}

	public String getTitle() {
		return title;
	}

	public String getTvalue() {
		return tvalue;
	}

	public Integer getSequence() {
		return sequence;
	}

	public String getIntro() {
		return intro;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setParent(SystemDictionary parent) {
		this.parent = parent;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTvalue(String tvalue) {
		this.tvalue = tvalue;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}
}
