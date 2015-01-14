package com.lansq.test.utils;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

/**
 * 
 * @author lansq
 * 
 *         用于对数据转换
 */
public class MopaasResult {
	private static Logger logger = Logger.getLogger(MopaasResult.class);
	private JSONObject result = new JSONObject();

	public MopaasResult() {
		result.put("returnCode", "000");
		result.put("returnMessage", "成功");
	}

	public void setCodeAndMsg(String code, String msg) {
		result.put("returnCode", code);
		result.put("returnMessage", msg);
	}

	public void add(String key, String value) {
		result.put(key, value);
	}

	public JSONObject createJson(String fieldMap) {
		JSONObject cJson = new JSONObject();
		if (fieldMap == null) {
			return cJson;
		}
		String[] fields = fieldMap.split(";");
		int lenght = fields.length;
		for (int i = 0; i < lenght; i++) {
			if (lenght <= 0) {
				continue;
			}
			String[] field = fields[i].split("=");
			String fromName = field[0];
			String toName = field.length == 1 ? field[0] : field[1];
			cJson.put(fromName, toName);
		}
		return cJson;
	}

	public JSONObject returnResult() {
		return result;
	}

	public void asynchronousPrintResult(HttpServletResponse response, Object value) {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.print(value.toString());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			logger.error("asynchronousPrintResult error!" + e);
		} finally {
			if (writer != null) {
				writer.flush();
				writer.close();
			}
		}
	}

	public String createXml(String returnCode, String returnMessage) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		buffer.append("<result>");
		buffer.append("<returnCode>" + returnCode + "</returnCode>");
		buffer.append("<returnMessage>" + returnMessage + "</returnMessage>");
		buffer.append("</result>");
		return buffer.toString();
	}
}
