package com.tenderlitch.core.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.ParameterizedType;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.tenderlitch.core.entity.AbstractEntity;
import com.tenderlitch.core.json.CustomObjectMapper;
import com.tenderlitch.core.service.AppServiceHelper;
import com.tenderlitch.core.service.BaseService;
import com.tenderlitch.core.web.AjaxResponse;
import com.tenderlitch.core.web.PageBounds;
import com.tenderlitch.core.web.PageRequest;

public class BaseAction<T extends AbstractEntity> {

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;

	protected Map<String, String> qm = new HashMap<String, String>();

	/**
	 * 单列模糊查询参数
	 */
	protected String singleQuery;


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

	@SuppressWarnings("unchecked")
	public BaseAction() {
		super();
		// 通过反射取得Entity的Class.
		try {
			entityClass = (Class<T>) ((ParameterizedType) getClass()
					.getGenericSuperclass()).getActualTypeArguments()[0];
			entityClassName = entityClass.getSimpleName();
		} catch (RuntimeException e) {
			logger.error("init BaseAction failed:", e);
		}
	}

	protected Map<String, Object> convertParams(Map<String, String> qm) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (qm != null && qm.size() > 0) {
			params.putAll(qm);
		}
		// 将单列查询参数放入传递给mybatis的参数map中
		if (singleQuery != null) {
			params.put(SINGLE_LIKE_QUERY_MYBATIS_KEY, singleQuery);
		}
		return params;
	}

	/**
	 * 将json数据写入此次响应流中
	 * 
	 * @param str
	 *            json数据
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
			logger.error("BaseAction|getJsonFromRequest() Exception: "
					+ e.getMessage());
			return "";
		}
		return jsonStr;
	}

	@Autowired
	private CustomObjectMapper customObjectMapper;

	public void save(T entity) {
		if (entity != null) {
			if (entity.getSid() != null && entity.getVersion() != null) {
				// 如果传入的数据中附带了sid和version,则做更新操作
				getBaseService().update(entity);
			} else {
				// 如果未附带sid和version,则做插入操作
				getBaseService().insert(entity);
			}
		}
	}

	/**
	 * 分页查询方法
	 * 
	 * @return
	 * @throws Exception
	 */
	// @RequestMapping("/findByPage")
	@ResponseBody
	public AjaxResponse findByPage() throws Exception {
		Map<String, Object> params = preparePageParams();
		PageBounds rowBounds = PageRequest.preparePageBound(request);
		Page<T> page = getBaseService().findByPage(params, rowBounds);

		return AjaxResponse.success(page);
	}

	// //@RequestMapping("/findByParams")
	public AjaxResponse findByParams() throws Exception {
		Map<String, Object> params = preparePageParams();
		List<T> list = getBaseService().findByParams(params);
		return AjaxResponse.success(list);
	}

	/**
	 * 组装筛选条件和排序条件
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected Map<String, Object> preparePageParams() {
		Map<String, Object> params = this.convertParams(this.getQm());
		String sortIndex = (String) request
				.getParameter(REQUEST_SORT_PARAMETER_INDEX);
		if (StringUtils.isNotEmpty(sortIndex)) {
			String sortColumn = getSortColumnRequestKeyByIndex(sortIndex);
			params.put(MAPPER_ORDER_COLUMN_PARAM_KEY, sortColumn);
			String orderDirection = request
					.getParameter(REQUEST_SORT_PARAMETER_DIRECTION);
			if (StringUtils.isNotEmpty(orderDirection)) {
				params.put(MAPPER_ORDER_DIRECTION_PARAM_KEY, orderDirection);
			}
		} else {
			// jquery.dataTable在分页时必然会传递排序方向
			// 此处是给无分页参数,使用默认列(一般是sid)排序时设置排序方向用的
			params.put(MAPPER_ORDER_COLUMN_PARAM_KEY, DEFAULT_SORT_COLUMN);
			params.put(MAPPER_ORDER_DIRECTION_PARAM_KEY, DEFAULT_SORT_DERECTION);
		}
		return params;
	}

	/**
	 * jquery.dataTable传递的排序参数,目前只考虑了单列排序的情况
	 */
	protected static final String REQUEST_SORT_PARAMETER_INDEX = "iSortCol_0",
			REQUEST_SORT_PARAMETER_DIRECTION = "sSortDir_0",
			REQUEST_SORT_PARAMETER_COLUMN_PREFIX = "mDataProp_";

	/**
	 * mybatys mapper中排序参数使用的key
	 */
	protected static final String MAPPER_ORDER_COLUMN_PARAM_KEY = "orderBy",
			MAPPER_ORDER_DIRECTION_PARAM_KEY = "orderDirection";

	/**
	 * 默认的排序方向
	 */
	protected static final String DEFAULT_SORT_DERECTION = "desc";
	/**
	 * 默认的排序列
	 */
	protected static final String DEFAULT_SORT_COLUMN = "sid";

	/**
	 * 通过jquery.dataTable传递的排序列索引值获得排序列
	 * 
	 * @param index
	 * @return
	 */
	private String getSortColumnRequestKeyByIndex(String index) {
		return request.getParameter(REQUEST_SORT_PARAMETER_COLUMN_PREFIX
				+ index);
	}

	/**
	 * 不分页查询方法
	 * 
	 * @return
	 * @throws Exception
	 */
	// @RequestMapping("/read")
	public AjaxResponse read() throws Exception {
		Map<String, Object> params = preparePageParams();
		List<T> entityLs = getBaseService().find(params);

		return AjaxResponse.success(entityLs);
	}

	/**
	 * 取得Service接口对象 默认以实体对象类名的前缀小写形式+Service获取对应的Service对象实例 详见初始化构造函数
	 */
	@SuppressWarnings("unchecked")
	protected BaseService<T> getBaseService() {
		BaseService<T> baseService = (BaseService<T>) findBean(StringUtils
				.uncapitalize(entityClassName) + "ServiceImpl");
		logger.debug("baseService=" + baseService);
		return baseService;
	}

	protected Object findBean(String beanId) {
		return AppServiceHelper.findBean(beanId);
	}

	// 调阅数据
	// @RequestMapping("/findAll")
	public AjaxResponse findAll() {
		List<T> entityLs = getBaseService().findAll();
		return AjaxResponse.success(entityLs); // 返回一个String类型的Json串
	}

	// 保存数据
	// @RequestMapping("/add")
	public void add(@RequestBody T entity) {
		getBaseService().insert(entity);
	}

	// 批量保存数据
	// @RequestMapping("/addBatch")
	public void addBatch(@RequestBody List<T> entityLs) {
		getBaseService().insertAll(entityLs);
	}

	// 批量更新数据
	// @RequestMapping("/update")
	public void update(@RequestBody T entity) {
		getBaseService().update(entity);
	}

	// 更新数据
	// @RequestMapping("/updateBatch")
	public void updateBatch(@RequestBody List<T> entityLs) {
		getBaseService().updateAll(entityLs);
	}

	// 删除数据
	// @RequestMapping("/destroy")
	public void destroy(T entity) {
		getBaseService().delete(entity);
		// return
		// AjaxResponse.success(AppServiceHelper.getMessage("operateSuccess",
		// new String[]{}, request.getLocale()));
	}

	// 与前台数据展示区域绑定的数据汇总
	// @RequestMapping("/getQuerySummary")
	/*
	 * public AjaxResponse getQuerySummary() { Map<String, Object> params =
	 * preparePageParams(); Map<String, Object> summaryMap =
	 * getBaseService().getQuerySummary(params); return
	 * AjaxResponse.success(summaryMap); }
	 */

	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request,
			HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();
	}

	@ModelAttribute
	public void setQueryParam(HttpServletRequest request) {
		// 准备查询参数
		Map<String, String> qm = new HashMap<String, String>();
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String parameterName = (String) parameterNames.nextElement(), paramValue = request
					.getParameter(parameterName);

			if (StringUtils.isEmpty(paramValue))
				continue;

			// 准备单列模糊查询参数
			if (SINGLE_LIKE_QUERY_REQUEST_KEY.equals(parameterName)) {
				this.singleQuery = paramValue;
			}
			// 准备精确查找参数
			else if (parameterName.startsWith("qm.")) {
				String[] parameter = StringUtils.split(parameterName, ".");
				if (parameter.length == 2) {
					qm.put(parameter[1], paramValue);
				}
			}
		}
		this.qm = qm;
	}

	/**
	 * jquery.dataTable中传递的单列模糊查询参数在requestMap中的key
	 */
	protected static final String SINGLE_LIKE_QUERY_REQUEST_KEY = "sSearch",
			SINGLE_LIKE_QUERY_MYBATIS_KEY = "singleQuery";
}
