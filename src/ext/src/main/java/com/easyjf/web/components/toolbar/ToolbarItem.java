package com.easyjf.web.components.toolbar;

import com.easyjf.web.components.BaseComponent;
import com.easyjf.web.components.Function;

public class ToolbarItem extends BaseComponent {
	@Override
	public String getXtype() {
		return "tbitem";
	}

	public String clz() {
		return "Ext.Toolbar.Item";
	}

	public static class Fill extends ToolbarItem {
		@Override
		public String getXtype() {
			return "tbfill";
		}

		@Override
		public String clz() {
			return "Ext.Toolbar.Fill";
		}

	}

	public static class Separator extends ToolbarItem {
		@Override
		public String getXtype() {
			return "tbseparator";
		}

		@Override
		public String clz() {
			return "Ext.Toolbar.Separator";
		}
	}

	public static class Spacer extends ToolbarItem {
		
		@Override
		public String getXtype() {
			return "tbspacer";
		}

		@Override
		public String clz() {
			return "Ext.Toolbar.Spacer";
		}
	}

	public static class Button extends com.easyjf.web.components.Button {
		public Button() {
			this(null);
		}

		public Button(String text) {
			this(text,null);
		}

		public Button(String text, Function handler) {
			super(text,handler);
		}

		@Override
		public String getXtype() {
			return "tbbutton";
		}

		@Override
		public String clz() {
			return "Ext.Toolbar.Button";
		}
	}

	public static class SplitButton extends com.easyjf.web.components.SplitButton {
		public SplitButton() {

		}

		public SplitButton(String text) {

		}

		public SplitButton(String text, Function handler) {

		}
		@Override
		public String getXtype() {
			return "tbsplit";
		}

		@Override
		public String clz() {
			return "Ext.Toolbar.SplitButton";
		}
	}

	public static class TextItem extends ToolbarItem {
		private String text;

		public TextItem() {
			this(null);
		}

		public TextItem(String text) {
			this.text = text;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		@Override
		public String getXtype() {
			return "tbtext";
		}

		@Override
		public String clz() {
			return "Ext.Toolbar.TextItem";
		}
	}
}
