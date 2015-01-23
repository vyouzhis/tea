package org.ppl.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.ppl.io.ProjectPath;

public class PorG {
	static PorG source;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Map<String, String> sporg;
	private String Context_Path;
	private List<String> rmc;
	
	public static PorG getInstance() {
		if (source == null) {
			source = new PorG();
		}

		return source;
	}

	public void Init(HttpServletRequest req, HttpServletResponse res) {
		request = req;
		response = res;
		sporg = new HashMap<String, String>();
		ParserParame();
	}

	public void getFile() {
		System.out.println("getFile");
		ProjectPath pp = ProjectPath.getInstance();

		URI path = pp.DataDir();
		System.out.println("getFile:" + path);
		Part filePart;
		try {
			
			PrintWriter writer = response.getWriter();
			filePart = request.getPart("files");
			if (filePart != null) {
				final String fileName = getFileName(filePart);
				OutputStream out = null;
				InputStream filecontent = null;

				try {
					out = new FileOutputStream(new File(path + File.separator
							+ fileName));
					filecontent = filePart.getInputStream();

					int read = 0;
					final byte[] bytes = new byte[1024];

					while ((read = filecontent.read(bytes)) != -1) {
						out.write(bytes, 0, read);
					}
					writer.println("New file " + fileName + " created at "
							+ path);
					writer.println("File " + fileName + " being uploaded to "
							+ path);
				} catch (FileNotFoundException fne) {
					writer.println("You either did not specify a file to upload or are "
							+ "trying to upload a file to a protected or nonexistent "
							+ "location.");
					writer.println("<br/> ERROR: " + fne.getMessage());

					writer.println("Problems during file upload. Error:"
							+ fne.getMessage());

				}
				writer.close();
			} else {
				
				writer.println("file is null");
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("ill" + e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("io" + e.getMessage());
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("servlet" + e.getMessage());
		}
	}

	private String getFileName(final Part part) {
		final String partHeader = part.getHeader("content-disposition");
		System.out.println("Part Header = " + partHeader);
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim()
						.replace("\"", "");
			}
		}
		return null;
	}

	public String getKey(String key) {
		if (sporg.containsKey(key)) {
			return sporg.get(key);
		}
		return null;
	}

	@SuppressWarnings({ "unused", "null" })
	private void ParserParame() {
		long maxFileSize = (2 * 1024 * 1024);

		int maxMemSize = (2 * 1024 * 1024);
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ProjectPath pp = ProjectPath.getInstance();
		File file = new File(pp.DataDir());
		// Set factory constraints
		factory.setSizeThreshold(maxMemSize);
		factory.setRepository(file);

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		// Set overall request size constraint
		upload.setSizeMax(maxFileSize);
		// upload.parseRequest(RequestContext)
		// Parse the request

		String contentType = request.getContentType();

		Enumeration<String> parameterNames = request.getParameterNames();

		while (parameterNames.hasMoreElements()) {

			String paramName = parameterNames.nextElement();
			String[] paramValues = request.getParameterValues(paramName);

			String pres = Check(paramValues[0]);
			sporg.put(paramName, pres);
		}
		if (contentType != null
				&& (contentType.indexOf("multipart/form-data") != -1)) {
			final String encoding = "UTF-8";
			try {
				ServletRequestContext context = new ServletRequestContext(request) ;

				List<FileItem> items = upload.parseRequest(context);
				Iterator<FileItem> iter = items.iterator();

				while (iter.hasNext()) {
					FileItem item = iter.next();

					if (item.isFormField()) {
						// processFormField(item);

						String name = item.getFieldName();
						String value = item.getString();

						try {
							value = new String(value.getBytes("iso8859-1"),
									"UTF-8");

						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						sporg.put(name, Check(value));
					} else {
						// processUploadedFile(item);
						String fieldName = item.getFieldName();
						String fileName = item.getName();
						// String contentType = item.getContentType();
						boolean isInMemory = item.isInMemory();
						long sizeInBytes = item.getSize();

					}
				}
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private String Check(String value) {
		value = value.replace("'", "&apos;");
		value = value.replaceAll("&", "&amp;");
		value = value.replace("\"", "&quot;");
		value = value.replace("\t", "&nbsp;&nbsp;");
		value = value.replace(" ", "&nbsp;");
		value = value.replace("<", "&lt;");
		value = value.replaceAll(">", "&gt;");
		return value.trim();
	}

	public String getContext_Path() {
		return Context_Path;
	}

	public void setContext_Path(String context_Path) {
		Context_Path = context_Path;
	}
	
	public void UrlServlet(List<String> arg){
		this.rmc = arg;
	}
	
	public List<String> getRmc() {
		return rmc;
	}
	
	public Map<String, String> getAllpg() {
		return sporg;
	}
}
