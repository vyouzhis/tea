package org.ppl.BaseClass;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.ppl.common.PorG;
import org.ppl.io.ProjectPath;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class BaseView extends BaseLang{
	protected String html="";
	private Map<String, Object> root;
		
	public void View() {
		if (root == null) {
			echo("root is null");
			return;
		}

		ProjectPath pp = ProjectPath.getInstance();

		Configuration cfg = new Configuration();
		try {
			cfg.setDirectoryForTemplateLoading(new File(pp.ViewDir()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		cfg.setDefaultEncoding("UTF-8");
		try {
			Template temp;
			String[] libPaths = stdClass.split("\\.");
			String path = "";
			for (int i = 2; i < libPaths.length; i++) {
				path += "/"+libPaths[i];
			}
			//echo("html path:"+path);
			temp = cfg.getTemplate(path + ".html");
			StringWriter out = new StringWriter();
			
			temp.setEncoding("UTF-8");
			temp.process(root, out);
			html = out.toString();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Map<String, Object> getRoot() {
		return root;
	}

	public void setRoot(String key, Object obj) {
		if(this.root == null){
			root = new HashMap<String, Object>();
		}
		root.put(key, obj);
	}
}
