package com.tenderlitch.core.exception;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.tenderlitch.core.exception.BaseRuntimeException;
import com.tenderlitch.core.service.AppServiceHelper;

public class CommonExceptionHandler implements HandlerExceptionResolver { 
	private static Log logger = LogFactory.getLog(CommonExceptionHandler.class);  
  
	private String defaultErrorView;  
    
    public String getDefaultErrorView() {  
        return defaultErrorView;  
    }  
  
    public void setDefaultErrorView(String defaultErrorView) {  
        this.defaultErrorView = defaultErrorView;  
    }  
    
    public ModelAndView resolveException(HttpServletRequest request, 
            HttpServletResponse response, Object handler, Exception ex) { 
    	logger.error("Action Exception",ex);
		if (ex  instanceof BaseRuntimeException) {
			ajaxJson(response,false, ((BaseRuntimeException) ex).getMessage());
		} else {			
			ajaxJson(response,false, AppServiceHelper.getMessage("RuntimeException", new String[]{ex.getMessage()}, request.getLocale()));
		}			
		return null;
    }
  
	protected String ajaxJson(HttpServletResponse response,boolean result, String message) {
		String strs = "{\"success\":" + result + ",\"message\":\"" + message + "\"}";
		logger.debug(strs);
		jsonWriter(response,strs);
		return strs;
	}

	protected void jsonWriter(HttpServletResponse response,String str) {
		Writer writer = null;
		try {
			try {
				response.setCharacterEncoding("UTF-8");
				writer = response.getWriter();
				writer.write(str);
			} finally {
				writer.flush(); // 强制输出所有内容
				writer.close(); // 关闭
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}  
