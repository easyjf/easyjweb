package com.easyjweb.demo.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.easyjf.container.annonation.Field;
import com.easyjf.container.annonation.FormPO;
import com.easyjf.container.annonation.Validator;

/**
 * $!{domainName}
 * 
 * @author EasyJweb $$Id: $!{domainName}.java,v 0.0.1 $!{nowTime} EasyJweb code Generate Engine 0.0.1 Exp $
 */

@Entity
@FormPO(name = "$!{domainName}" , label = "$!{domainName}的中文别名")
public class $!{domainName} implements Serializable {
	
	@Field(gener = false)
	private static final long serialVersionUID =#if($!{svuid.equals("-")}) 1L#else $!{svuid}#end;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	
#foreach ($field in $fields)
	@Column(length = $!{field.length})
	@Field(name = "$!{field.alias}", validator = @Validator(name =#if($!{field.validatorName}) "$!{field.validatorName}"#else "string"#end, value = "required;min:1;max:$!{field.length}"))
	private $!{field.Type} $!{field.name};
	
#end

#foreach ($field in $fields)
	public $!{field.Type} get$!{field.Name}() {
		return $!{field.name};
	}
	
	public void set$!{field.Name}($!{field.Type} $!{field.name}) {
		this.$!{field.name} = $!{field.name};
	}
#end

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
}
