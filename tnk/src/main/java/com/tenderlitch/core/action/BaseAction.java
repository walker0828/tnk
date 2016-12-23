package com.tenderlitch.core.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.Page;
import com.tenderlitch.core.bean.Response;
import com.tenderlitch.core.entity.AbstractEntity;
import com.tenderlitch.core.json.CustomObjectMapper;
import com.tenderlitch.core.query.page.PageBounds;
import com.tenderlitch.core.service.AppServiceHelper;
import com.tenderlitch.core.service.BaseService;
import com.tenderlitch.core.web.LoginUtil;


public class BaseAction<T>  {
	
	protected HttpServletRequest request;  
    protected HttpServletResponse response;  
    protected HttpSession session; 
	
	public static final String STATUS = "status";
	public static final String WARN = "warn";
	public static final String MESSAGE = "message";

	protected Map<String, String> qm = new HashMap<String, String>();
	// the model
	protected T entity;

	// model's Class
	protected Class<T> entityClass;

	// model's ClassName
	protected String entityClassName;

	// list页面显示的对象列表
	protected List<T> entities;

	// logger for subclass
	private static Log logger = LogFactory.getLog(BaseAction.class); 

	public Map<String, String> getQm() {
		return qm;
	}

	public void setQm(Map<String, String> qm) {
		this.qm = qm;
	}
	
	/**
	 * 实现ModelDriven接口方法，返回模型对象
	 * @see ModelDriven
	 */
	public T getModel() {
		return entity;
	}
	// 获取Application
	// protected ServletContext getApplication() {
	// return ServletActionContext.getServletContext();
	// }

	@SuppressWarnings("unchecked")
	public BaseAction() {
		super();
		// 通过反射取得Entity的Class.
		try {
			entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
			entityClassName = entityClass.getSimpleName();
		} catch (RuntimeException e) {
			//logger.error("error detail:", e);
		}

	}

	protected Map<String, Object> convertParams(Map<String, String> qm) {
		Map<String, Object> params = new HashMap<String, Object>();
		Set<Map.Entry<String, String>> p = qm.entrySet();
		for (Map.Entry<String, String> me : p) {
			String name = me.getKey();
			String value = me.getValue();
			if (StringUtils.isNotEmpty(value)) {
				params.put(name, value);
			}
		}
		return params;
	}


	/**
	 * 将json数据写入此次响应流中
	 * 
	 * @param str json数据
	 */
	protected void jsonWriter(String str) {
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

	/**
	 * 读取request中的json信息
	 * 
	 * @return jsonStr
	 */
	public String getJsonFromRequest() {
		String jsonStr = null;
		try {
			BufferedReader br = request.getReader();
			jsonStr = br.readLine();
		} catch (IOException e) {
			logger.error("BaseAction|getJsonFromRequest() Exception: " + e.getMessage());
			return "";
		}
		return jsonStr;
	}

    @Autowired
    private CustomObjectMapper customObjectMapper;
    
	/**
	 * 将 json 解析成相应实体对象集合
	 * 对 jsonToObject() 进行了功能扩展，该方法可以获得实体对象的集合
	 * 
	 * @param json json数据
	 * @param cla 实体类
	 * @param <T> 实体对象类型
	 * @return 实体对象集合
	 */
	protected List<T> getObjectsFromJsonByObjectMapper(String json, Class<T> cla) {
		if (StringUtils.isNotEmpty(json) && cla != null) {
			try {
				List<T> list = new ArrayList<T>();
				String[] strs = null; // 多条记录Json串，分割成单独的。将每一条记录作为一个数组元素

				if (json.startsWith("[") && json.endsWith("]")) // 删除多条记录会进入此分支
				{
				} else if (json.startsWith("{") && json.endsWith("}")) // 删除一条记录会进入此分支
				{
					strs = json.split("$"); // 因为没有$这个字符，故字符串会被整体分割成一个数组
				}

				for (String str : strs) {
					/** 使用customObjectMapper将json转化为object
					  * 原因：action与前台js传值转json方式保持一致
					  */
					list.add(customObjectMapper.readValue(str, cla));
					//list.add(gson.fromJson(str, cla));
				}
				return list;

			} catch (Exception e) {
				System.out.println("BaseAction : getListObjectsFromJson() function Excetion!");
				e.printStackTrace();
			}
		}
		return null;
	}
    
	
	// 保存数据
	@RequestMapping("/addBatchFromJson")
	public Response add() {
		String jsonStr = getJsonFromRequest();
		List<T> entityLs = getObjectsFromJsonByObjectMapper(jsonStr, entityClass);
		for (T entity : entityLs) {
			AbstractEntity ae = (AbstractEntity)entity;
			ae.setCreatedBy(LoginUtil.getUpcUserId());
			ae.setCreatedDt(new Date());
		}
		getBaseService().insertAll(entityLs);
		return new Response().success(AppServiceHelper.getMessage("operateSuccess", new String[]{}, request.getLocale()));
	}
	
	// 更新数据
	@RequestMapping("/updateBatchFromJson")
		public Response update() {
			String jsonStr = getJsonFromRequest();
			List<T> entityLs = getObjectsFromJsonByObjectMapper(jsonStr, entityClass);
			for (T entity : entityLs) {
				AbstractEntity ae = (AbstractEntity)entity;
				ae.setUpdatedBy(LoginUtil.getUpcUserId());
				ae.setUpdatedDt(new Date());		
			}
			getBaseService().updateAll(entityLs);
			return new Response().success(AppServiceHelper.getMessage("operateSuccess", new String[]{}, request.getLocale()));
		}


	/**
	 * 分页查询方法
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findByPage")
	public Response findByPage() throws Exception {
		Map<String, Object> params = preparePageParams();
		PageBounds rowBounds = preparePageBound(request);
		Page<T> page = getBaseService().findByPage(params, rowBounds);
		
		return new Response().success(new com.tenderlitch.core.query.page.Page<T>(page)); 
	}
	
	
	@RequestMapping("/findByParams")
	public Response findByParams() throws Exception {
		Map<String, Object> params = preparePageParams();
		List<T> list = getBaseService().findByParams(params);
		return new Response().success(list); 
	}
	
	/**
	 * 组装分页对象
	 * @param request
	 * @return
	 */
	protected PageBounds preparePageBound(HttpServletRequest request) {
		int page = 1;
		int limit=10;
		if(!StringUtils.isBlank(request.getParameter("page"))){
			page = Integer.valueOf((String)request.getParameter("page"));
		}
		if(!StringUtils.isBlank(request.getParameter("limit"))){
			limit = Integer.valueOf((String)request.getParameter("limit"));
		}
		
		PageBounds pageBounds = new PageBounds(page, limit);
		return pageBounds;
	}
	
	/**
	 * 组装查询参数
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected Map<String, Object> preparePageParams() {
		Map<String, Object> params = this.convertParams(this.getQm());
		String sortStr = (String)request.getParameter("sort");
		if(null!=sortStr){
			sortStr = sortStr.substring(1,sortStr.length()-1);
//			Map<String,String> sortMap =customObjectMapper.readValue(sortStr, Map.class);
			//设置排序参数
//			params.put("order", StringUtil.str2DbColumn(sortMap.get("property")) + " " + sortMap.get("direction"));
		}
		return params;
	}
	
	/**
	 * 不分页查询方法
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/read")
	public Response read() throws Exception {
		Map<String, Object> params = preparePageParams();
		List<T> entityLs = getBaseService().find(params);
		
		return new Response().success(entityLs); 
	}

	/**
	 * 取得Service接口对象
	 * 默认以实体对象类名的前缀小写形式+Service获取对应的Service对象实例
	 * 详见初始化构造函数
	 */
	@SuppressWarnings("unchecked")
	protected BaseService<T> getBaseService() {
		BaseService<T> baseService = (BaseService<T>) findBean(StringUtils.uncapitalize(entityClassName)
				+ "ServiceImpl");
		logger.debug("baseService=" + baseService);
		return baseService;
	}

	protected Object findBean(String beanId) {
		return AppServiceHelper.findBean(beanId);
	}
	
	// 调阅数据
	@RequestMapping("/findAll")
	public Response findAll() {
		List<T> entityLs = getBaseService().findAll();
		return new Response().success(entityLs); // 返回一个String类型的Json串
	}
	
	// 保存数据
	@RequestMapping("/add")
	public Response add(@RequestBody T entity) {
		getBaseService().insert(entity);
		return new Response().success(AppServiceHelper.getMessage("operateSuccess", new String[]{}, request.getLocale()));
	}

	// 批量保存数据
	@RequestMapping("/addBatch")
	public Response addBatch(@RequestBody List<T> entityLs) {
		getBaseService().insertAll(entityLs);
		return new Response().success(AppServiceHelper.getMessage("operateSuccess", new String[]{}, request.getLocale()));
	}
		
	// 批量更新数据
	@RequestMapping("/update")
	public Response update(@RequestBody T entity) {
		AbstractEntity ae = (AbstractEntity)entity;
		ae.setUpdatedBy(LoginUtil.getUpcUserId());
		ae.setUpdatedDt(new Date());	
		getBaseService().update(entity);
		return new Response().success(AppServiceHelper.getMessage("operateSuccess", new String[]{}, request.getLocale()));
	}
	
	// 更新数据
	@RequestMapping("/updateBatch")
	public Response updateBatch(@RequestBody List<T> entityLs) {
		for (T entity : entityLs) {
			AbstractEntity ae = (AbstractEntity)entity;
			ae.setUpdatedBy(LoginUtil.getUpcUserId());
			ae.setUpdatedDt(new Date());		
		}
		getBaseService().updateAll(entityLs);
		return new Response().success(AppServiceHelper.getMessage("operateSuccess", new String[]{}, request.getLocale()));
	}

	// 删除数据
	@RequestMapping("/destroy")
	public Response destroy() {
		String jsonStr = getJsonFromRequest();
		List<T> entityLs = getObjectsFromJsonByObjectMapper(jsonStr, entityClass);
		getBaseService().deleteAll(entityLs);
		return new Response().success(AppServiceHelper.getMessage("operateSuccess", new String[]{}, request.getLocale()));
	}
	
	//与前台数据展示区域绑定的数据汇总
	@RequestMapping("/getQuerySummary")
	public Response getQuerySummary() {
		Map<String, Object> params = preparePageParams();
		Map<String, Object> summaryMap = getBaseService().getQuerySummary(params);
		return new Response().success(summaryMap);
	}
	
    @ModelAttribute  
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){  
        this.request = request;  
        this.response = response;  
        this.session = request.getSession();  
    } 
    
	@ModelAttribute
	public void setQueryParam(HttpServletRequest request) {
		Map<String, String> qm = new HashMap<String, String>();
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String parameterName = (String) parameterNames.nextElement();
			if (parameterName.startsWith("qm.")) {
				String paramValue = request.getParameter(parameterName);
				String[] parameter = StringUtils.split(parameterName,".");
				if (parameter.length == 2) {
					qm.put(parameter[1], paramValue);
				}
			}
		}
		this.qm = qm;
	}
}
