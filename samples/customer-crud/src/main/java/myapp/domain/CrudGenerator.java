package myapp.domain;

import com.easyjf.generator.Generator;

public class CrudGenerator {
	public static void main(String[] args) {
		Generator generator = Generator.getInstance();
		/*
		 * String[] domains = { "com.easyjf.necton.base.domain.ContentFilter",
		 * "com.easyjf.necton.base.domain.DynamicStaticPair",
		 * "com.easyjf.necton.base.domain.Message",
		 * "com.easyjf.necton.news.domain.NewsDir",
		 * "com.easyjf.necton.news.domain.NewsDoc",
		 * "com.easyjf.necton.news.domain.NewsReview",
		 * "com.easyjf.necton.news.domain.Review",
		 * "com.easyjf.necton.ec.domain.Orders",
		 * "com.easyjf.necton.ec.domain.Product",
		 * "com.easyjf.necton.ec.domain.ProductDir",
		 * "com.easyjf.necton.news.domain.Template"};
		 */
		String[] domains = { "com.easyjf.necton.base.domain.DynamicStaticPair" };
		generator.setTemplateDir("./templates");
		generator.setPri("./");
		try {
			for (int i = 0; i < domains.length; i++)
				generator.doGenerator(new String[] { domains[i] });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
