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
package com.easyjf.util.regx;

/**
 * ubb过滤实现
 * 
 * @author stef_wu
 * 
 */
public class UBBFilter extends RegexFilter {

	public UBBFilter(String source) {
		super(source);
		String abgcolor = "#CCCCCC";

		this.regex = "(\\[IMG\\])(http://.[^\\[]*)(\\[\\/IMG\\])";
		this.rpStr = "<p><IMG SRC=\"$2\" border=0 onload=\"javascript:if(this.width>screen.width*.68)this.width=screen.width*.68\"></p>";
		this.doFiltration();

		regex = "(\\[EMAIL\\])(.[^\\[]*)(\\[\\/EMAIL\\])";
		rpStr = "<A HREF=\"mailto:$2\">$2</A>";
		this.doFiltration();

		regex = "(\\[EMAIL=(.[^\\[]*)\\])(.[^\\[]*)(\\[\\/EMAIL\\])";
		rpStr = "<A HREF=\"mailto:$2\" TARGET=_blank>$3</A>";
		this.doFiltration();

		regex = "^(http://[A-Za-z0-9\\./=\\?%\\-&_~`@':+!]+)";
		rpStr = "<a target=_blank href=$1>$1</a>";
		this.doFiltration();

		regex = "(http://[A-Za-z0-9\\./=\\?%\\-&_~`@':+!]+)$";
		rpStr = "<a target=_blank href=$1>$1</a>";
		this.doFiltration();

		regex = "[^>=\"](http://[A-Za-z0-9\\./=\\?%\\-&_~`@':+!]+)";
		rpStr = "<a target=_blank href=$1>$1</a>";
		this.doFiltration();

		regex = "^(ftp://[A-Za-z0-9\\./=\\?%\\-&_~`@':+!]+)";
		rpStr = "<a target=_blank href=$1>$1</a>";
		this.doFiltration();

		regex = "(ftp://[A-Za-z0-9\\./=\\?%\\-&_~`@':+!]+)$";
		rpStr = "<a target=_blank href=$1>$1</a>";
		this.doFiltration();

		regex = "[^>=\"](ftp://[A-Za-z0-9\\.\\/=\\?%\\-&_~`@':+!]+)";
		rpStr = "<a target=_blank href=$1>$1</a>";
		this.doFiltration();

		regex = "^(rtsp://[A-Za-z0-9\\./=\\?%\\-&_~`@':+!]+)";
		rpStr = "<a target=_blank href=$1>$1</a>";
		this.doFiltration();

		regex = "(rtsp://[A-Za-z0-9\\./=\\?%\\-&_~`@':+!]+)$";
		rpStr = "<a target=_blank href=$1>$1</a>";
		this.doFiltration();

		regex = "[^>=\"](rtsp://[A-Za-z0-9\\.\\/=\\?%\\-&_~`@':+!]+)";
		rpStr = "<a target=_blank href=$1>$1</a>";
		this.doFiltration();

		regex = "^(mms://[A-Za-z0-9\\./=\\?%\\-&_~`@':+!]+)";
		rpStr = "<a target=_blank href=$1>$1</a>";
		this.doFiltration();

		regex = "(mms://[A-Za-z0-9\\./=\\?%\\-&_~`@':+!]+)$";
		rpStr = "<a target=_blank href=$1>$1</a>";
		this.doFiltration();

		regex = "[^>=\"](mms://[A-Za-z0-9\\.\\/=\\?%\\-&_~`@':+!]+)";
		rpStr = "<a target=_blank href=$1>$1</a>";
		this.doFiltration();

		regex = "(\\[HTML\\])(.[^\\[]*)(\\[\\/HTML\\])";
		rpStr = "<table width='100%' border='0' cellspacing='0' cellpadding='6' bgcolor='"
				+ abgcolor + "'><td><b>以下内容为程序代码:</b><br>$2</td></table>";
		this.doFiltration();

		regex = "(\\[code\\])(.[^\\[]*)(\\[\\/code\\])";
		rpStr = "<table width='100%' border='0' cellspacing='0' cellpadding='6' bgcolor='"
				+ abgcolor + "'><td><b>以下内容为程序代码:</b><br>$2</td></table>";
		this.doFiltration();

		// regex =
		// "(\\[color=(.[^\\[]*)\\])(.[^\\[]*)(\\[\\/color\\])";
		regex = "(\\[color=(.[^\\[]*)\\])(.*)(\\[\\/color\\])";
		rpStr = "<font color=$2>$3</font>";
		this.doFiltration();

		regex = "(\\[face=(.[^\\[]*)\\])(.[^\\[]*)(\\[\\/face\\])";
		rpStr = "<font face=$2>$3</font>";
		this.doFiltration();

		regex = "(\\[align=(.[^\\[]*)\\])(.*)(\\[\\/align\\])";
		rpStr = "<div align=$2>$3</div>";
		this.doFiltration();

		regex = "(\\[QUOTE\\])(.*)(\\[\\/QUOTE\\])";
		rpStr = "<table cellpadding=0 cellspacing=0 border=0 WIDTH=94% bgcolor=#000000 align=center><tr><td><table width=100% cellpadding=5 cellspacing=1 border=0><TR><TD BGCOLOR='"
				+ abgcolor + "'>$2</table></table><br>";
		this.doFiltration();

		// regex = "(\\[fly\\])(.*)(\\[\\/fly\\])";
		// rpStr = "<marquee width=90% behavior=alternate
		// scrollamount=3>$2</marquee>";
		// this.doFiltration();

		// regex = "(\\[move\\])(.*)(\\[\\/move\\])";
		// rpStr = "<MARQUEE scrollamount=3>$2</marquee>";
		// this.doFiltration();

		regex = "\\[GLOW=*([0-9]*),*(#*[a-z0-9]*),*([0-9]*)\\](.[^\\[]*)\\[\\/GLOW]";
		rpStr = "<table width=$1 style=\"filter:glow(color=$2, strength=$3)\">$4</table>";
		this.doFiltration();

		regex = "\\[SHADOW=*([0-9]*),*(#*[a-z0-9]*),*([0-9]*)\\](.[^\\[]*)\\[\\/SHADOW]";
		rpStr = "<table width=$1 style=\"filter:shadow(color=$2, strength=$3)\">$4</table>";
		this.doFiltration();

		// regex = "(\\[i\\])(.[^\\[]*)(\\[\\/i\\])";
		regex = "(\\[i\\])(.*)(\\[\\/i\\])";
		rpStr = "<i>$2</i>";
		this.doFiltration();

		// regex = "(\\[u\\])(.[^\\[]*)(\\[\\/u\\])";
		regex = "(\\[u\\])(.*)(\\[\\/u\\])";
		rpStr = "<u>$2</u>";
		this.doFiltration();

		// regex = "(\\[b\\])(.[^\\[]*)(\\[\\/b\\])";
		regex = "(\\[b\\])(.*)(\\[\\/b\\])";
		rpStr = "<b>$2</b>";
		this.doFiltration();

		// regex =
		// "(\\[size=1\\])(.[^\\[]*)(\\[\\/size\\])";
		regex = "(\\[size=1\\])(.*)(\\[\\/size\\])";
		rpStr = "<font size=1>$2</font>";
		this.doFiltration();

		// regex =
		// "(\\[size=2\\])(.[^\\[]*)(\\[\\/size\\])";
		regex = "(\\[size=2\\])(.*)(\\[\\/size\\])";
		rpStr = "<font size=2 >$2</font>";
		this.doFiltration();

		// regex =
		// "(\\[size=3\\])(.[^\\[]*)(\\[\\/size\\])";
		regex = "(\\[size=3\\])(.*)(\\[\\/size\\])";
		rpStr = "<font size=3 >$2</font>";
		this.doFiltration();

		// regex =
		// "(\\[size=4\\])(.[^\\[]*)(\\[\\/size\\])";
		regex = "(\\[size=4\\])(.*)(\\[\\/size\\])";
		rpStr = "<font size=4 >$2</font>";
		this.doFiltration();

		regex = "(\\[center\\])(.[^\\[]*)(\\[\\/center\\])";
		rpStr = "<center>$2</center>";
		this.doFiltration();

	}

}
