package org.lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ppl.BaseClass.BaseTheme;

import com.alibaba.fastjson.JSON;

public class jsonapi extends BaseTheme {

	@Override
	public void Show() {
		// TODO Auto-generated method stub
		List<Map<String, String>> test = new ArrayList<Map<String,String>>();
		for (int i = 0; i < 10; i++) {
			Map<String, String> m = new HashMap<String, String>();
			m.put("name", "my name"+i);
			m.put("uid", "my uid"+i);
			test.add(m);
		}
		
		String json = JSON.toJSONString(test);
		
		super.setAjax(true);
		super.setHtml(json);
	}

}
