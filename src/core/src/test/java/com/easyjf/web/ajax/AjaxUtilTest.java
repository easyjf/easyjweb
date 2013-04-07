package com.easyjf.web.ajax;

import java.util.Date;

import junit.framework.TestCase;
import com.easyjf.web.ajax.Region;
public class AjaxUtilTest extends TestCase {
	public void testObjectToJson() {
		
		Region region = new Region("地区1", "1111", null);
		Region region2 = new Region("地区2", "222", region);
		new Region("地区3", "333", region);
		java.util.List list = new java.util.ArrayList();
		list.add("dddd");
		list.add(region);
		list.add(111);
		java.util.Map map = new java.util.HashMap();
		map.put("s", "String");
		map.put("num", 50);
		map.put("decimal", new java.math.BigDecimal(15));
		map.put("region", region);
		System.out.println("String：" + AjaxUtil.getJSON("test"));
		System.out.println("Number：" + AjaxUtil.getJSON(13));
		System.out.println("Date：" + AjaxUtil.getJSON(new Date()));
		System.out
				.println("Simple Array："
						+ AjaxUtil.getJSON(new String[] { "rrrr", "erewre",
								"dsfsdf" }));
		System.out.println("Simple Object：" + AjaxUtil.getJSON(region2));
		System.out.println("复杂的Region：" + AjaxUtil.getJSON(region));
		System.out.println("数组Region："
				+ AjaxUtil.getJSON(new Region[] { region, region2 }));
		System.out.println("集合List：" + AjaxUtil.getJSON(list));
		System.out.println("Map：" + AjaxUtil.getJSON(map));
	}
	public void testMethod()
	{
	
		
	}
}
