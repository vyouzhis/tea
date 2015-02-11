package org.ppl.BaseClass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseQueue {
	private Map<String, List<Object>> queue;
	private int maxQueue;
	static BaseQueue source;

	public static BaseQueue getInstance() {
		if (source == null) {
			source = new BaseQueue(0);
		}

		return source;
	}
	
	public BaseQueue(int max) {
		// TODO Auto-generated constructor stub
		queue = new HashMap<>();
		maxQueue = max;
	}

	public void in(Object o) {

	}

	public Object out() {
		return null;
	}
}
