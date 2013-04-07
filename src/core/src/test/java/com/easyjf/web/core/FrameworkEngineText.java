package com.easyjf.web.core;

import junit.framework.TestCase;

public class FrameworkEngineText extends TestCase {
public void testElimateScript()
{
	String s="<script src=''></script>";
	System.out.println(FrameworkEngine.eliminateScript(s));
}
}
