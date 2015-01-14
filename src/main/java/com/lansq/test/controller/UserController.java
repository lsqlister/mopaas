package com.lansq.test.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lansq.test.domain.User;
import com.lansq.test.service.IUserService;
import com.lansq.test.utils.MopaasResult;

@Controller
@Scope("prototype")
public class UserController extends BaseController {

	@Autowired
	private IUserService userService;

	@RequestMapping(value = "/user.do", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView user(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MopaasResult result = new MopaasResult();
		List<User> users = userService.getAll();
		String json = "";
		for (int i = 0; i < users.size(); i++) {
			if (i < users.size() - 1)
				json = json + result.createJson(users.get(i).toString()) + ",";
			else
				json = json + result.createJson(users.get(i).toString());
		}
		result.add("list", "[" + json + "]");
		result.asynchronousPrintResult(response, result.returnResult());
		return null;
	}

	@RequestMapping(value = "upload.do", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView upload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DiskFileItemFactory diskFactory = new DiskFileItemFactory();
		diskFactory.setSizeThreshold(100 * 1024 * 1024);// 创建最小值
		ServletFileUpload upload = new ServletFileUpload(diskFactory);
		upload.setSizeMax(1024 * 1024 * 50);// 设置最大上传值
		List<FileItem> fileItems = null;
		FileOutputStream fos = null;
		String filename = "";
		InputStream stream = null;
		try {
			fileItems = upload.parseRequest(request);
			Iterator<FileItem> iter = fileItems.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (item.isFormField()) {
				} else {
					System.out.println("");
					filename = item.getName();
					stream = item.getInputStream();
				}
			}// end while()
		} catch (Exception e) {
			e.printStackTrace();
		}

		File path = new File(System.getenv("MOPAAS_FILESYSTEM890_LOCAL_PATH") + "/"
				+ System.getenv("MOPAAS_FILESYSTEM890_NAME"));
		logger.info("upload_path====>" + path);
		if (!path.exists()) {
			path.mkdirs();
		}
		try {
			fos = new FileOutputStream(path + "/" + filename);
			byte[] buffer = new byte[1024];
			int b = 0;
			while ((b = stream.read(buffer)) != -1) {
				fos.write(buffer, 0, b);
			}
			fos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (stream != null)
				try {
					stream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}

		return null;
	}

	@RequestMapping(value = "download.do", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView download(HttpServletRequest request, HttpServletResponse response) throws Exception {
		FileInputStream in = null;
		ServletOutputStream os = null;
		try {
			String filename = request.getParameter("filename").trim();
			String path = System.getenv("MOPAAS_FILESYSTEM890_LOCAL_PATH") + "/" + System.getenv("MOPAAS_FILESYSTEM890_NAME");
			logger.info("download_path====>" + path);
			File file = new File(path + "/" + filename);
			String browser = request.getHeader("User-agent").toLowerCase();
			response.setHeader("Accept-Ranges", "bytes");
			if (browser.indexOf("msie") != -1) {
				String enableFileName = new String(filename);
				response.setHeader("Content-Disposition", "attachment; filename=" + enableFileName);
			} else {
				String enableFileName = "=?UTF-8?B?" + (new String(Base64.encodeBase64(filename.getBytes("UTF-8")))) + "?=";
				response.setHeader("Content-Disposition", "attachment; filename=" + enableFileName);
			}
			response.setHeader("Content-Length", file.length() + "");
			response.setContentType("application/octet-stream;");
			in = new FileInputStream(file);
			os = response.getOutputStream();
			byte[] b = new byte[1024];
			int i = -1;
			while ((i = in.read(b)) != -1) {
				os.write(b, 0, i);
			}
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			if (in != null)
				in.close();
			if (os != null)
				os.close();
		}
		return null;
	}
}
