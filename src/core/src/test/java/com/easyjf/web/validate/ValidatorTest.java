package com.easyjf.web.validate;

import java.util.Map;

import com.easyjf.beans.BeanUtils;
import com.easyjf.container.annonation.Name;
import com.easyjf.web.core.FrameworkEngine;
import com.easyjf.web.validate.Validators.RequiredValidator;

import junit.framework.TestCase;

public class ValidatorTest extends TestCase {
	public void testValidator() {
		MyBean bean = new MyBean();
		java.beans.PropertyDescriptor property = BeanUtils
				.getPropertyDescriptor(bean.getClass(), "name");
		ValidatorManager vm = new ValidatorManager();
		vm.reload();
		TargetObject pto = new TargetObject(vm);
		pto.addValidator(new Validators().new RequiredValidator());
		pto.setDisplayName("姓名");
		pto.setFieldName("name");
		pto.doValidate("");
		//assertTrue(pto.getErrors().getEmpty());
		pto.doValidate(null);
		//assertFalse(pto.getErrors().getEmpty());
	}

	public void testValidateSimple() {
		ValidatorManager vm = new ValidatorManager();
		vm.reload();
		MyBean bean = new MyBean();
		java.beans.PropertyDescriptor property = BeanUtils
				.getPropertyDescriptor(bean.getClass(), "name");
		TargetObject validateObject = vm.findValidateObject(property);
		validateObject.doValidate("ddd");
		System.out.println(validateObject.getErrors());
	}

	public void testValidateByForm2Obj() {
		MyBean bean = new MyBean();
		Map form = new java.util.HashMap();
		form.put("name", "dd");
		form.put("bornDate", "2006-6-1");
		FrameworkEngine.form2Obj(form, bean);
		assertTrue(FrameworkEngine.getValidateManager().getErrors().hasError());
		assertNull(bean.getName());
	}

	public void testValidateByForm2Obj2() {
		MyBean bean = new MyBean();
		Map form = new java.util.HashMap();
		form.put("name", "12345");
		form.put("bornDate", "2006-6-1");
		FrameworkEngine.form2Obj(form, bean);
		//assertFalse(FrameworkEngine.getValidateManager().getErrors().hasError());
		//assertNotNull(bean.getName());
	}

	public void testPropertyTargetObject() {
		MyBean bean = new MyBean();

		java.beans.PropertyDescriptor property = BeanUtils
				.getPropertyDescriptor(bean.getClass(), "name");
		System.out.println(property.getWriteMethod().getDeclaringClass());
	}

	public void testAnnonation() {
		Name name = MyBean.class.getAnnotation(Name.class);

	}



	public void testValidatorsConfig() {
		ValidatorManager vm = new ValidatorManager();
		vm.reload();
		MyBean bean = new MyBean();
		// 测试在bornDate属性上配置参数
		java.beans.PropertyDescriptor property = BeanUtils
				.getPropertyDescriptor(bean.getClass(), "bornDate");
		TargetObject validateObject = vm.findValidateObject(property);
		//assertNotNull(validateObject);
		//assertEquals(validateObject.getValidators().size(), 1);

		// 测试在name属性上的配置参数
		property = BeanUtils.getPropertyDescriptor(bean.getClass(), "name");
		validateObject = vm.findValidateObject(property);
		assertNotNull(validateObject);
		//assertEquals(validateObject.getValidators().size(), 2);
	//	assertEquals(validateObject.getDisplayName(), "姓名");
		validateObject = vm.findValidateObject(property);
		// 在id上面没有参数
		property = BeanUtils.getPropertyDescriptor(bean.getClass(), "id");
		validateObject = vm.findValidateObject(property);
		assertNull("在id属性上具有配置参数", validateObject);
	}

	public void testParaValue() {
		String value = "min:1;max:rr\\;2;trim";// 使用\;来代表分号
		TargetObject obj = new TargetObject();
		obj.setValues(value);
		assertTrue(3 == obj.getValues().size());
		assertEquals("rr;2", obj.getValue("max"));
		assertTrue((Boolean) obj.getValue("trim"));
	}
	/**
	 * 验证电话号码
	 */
	public void testPhoneNumber() {
		MyBean bean = new MyBean();
		Map form = new java.util.HashMap();
		form.put("name", "dd12345");
		form.put("bornDate", "2006-6-1");
		form.put("tel", "12345678901");
		form.put("idcard", "111111111111111");
		FrameworkEngine.form2Obj(form, bean);
		assertTrue(FrameworkEngine.getValidateManager().getErrors().hasError());
		//assertNotNull(bean.getName());
		System.out.println(FrameworkEngine.getValidateManager().getErrors().getMessage("idcard"));
	}
}
