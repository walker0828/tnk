package com.tenderlitch.core.json;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tenderlitch.core.web.AjaxResponse;


/**
 * 定制 Jackson 的 ObjectMapper
 *
 * @author 
 * @since 1.0.0
 */
public class CustomObjectMapper extends ObjectMapper {
	
	public static void main(String[] args) throws JsonProcessingException {
		CustomObjectMapper a=new CustomObjectMapper();
		AjaxResponse b=AjaxResponse.success();
		System.out.println(a.writeValueAsString(b));
	}

	private boolean camelCaseToLowerCaseWithUnderscores = false;
    private String dateFormatPattern;

    public void setCamelCaseToLowerCaseWithUnderscores(boolean camelCaseToLowerCaseWithUnderscores) {
        this.camelCaseToLowerCaseWithUnderscores = camelCaseToLowerCaseWithUnderscores;
    }

    public void setDateFormatPattern(String dateFormatPattern) {
        this.dateFormatPattern = dateFormatPattern;
    }

    @Override
	public String writeValueAsString(Object value)
			throws JsonProcessingException {
		// TODO Auto-generated method stub
    	System.out.println(value);
		String result= super.writeValueAsString(value);
		return result;
	}

	public void init() {
        // 排除值为空属性
        setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 进行缩进输出
        configure(SerializationFeature.INDENT_OUTPUT, true);        

        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        
        // 将驼峰转为下划线
        //CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES
        if (camelCaseToLowerCaseWithUnderscores) {
            setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
        }
        // 进行日期格式化
        if (!StringUtils.isEmpty(dateFormatPattern)) {
            DateFormat dateFormat = new SimpleDateFormat(dateFormatPattern);
            setDateFormat(dateFormat);
        }        
    }
    
	private static final long serialVersionUID = 6515721075366639190L;
}
