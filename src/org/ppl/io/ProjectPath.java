package org.ppl.io;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class ProjectPath {
	static ProjectPath pp = null;
	
	public static ProjectPath getInstance() {
		if (pp == null) {
			pp = new ProjectPath();
		}

		return pp;
	}
	
	public URI ViewDir() {
		// String path = getServletContext().getRealPath("/");
		return getPath("/theme/default");
	}

	public URI DataDir() {
		return getPath("/Data");
	}

	private URI getPath(String baseName) {
		
		URL path = ProjectPath.class.getClassLoader().getResource(baseName);
		URI p = null;
		try {
			p = path.toURI();
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return p;
	}
}
