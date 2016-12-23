package com.tenderlitch.core.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tenderlitch.core.exception.NotLoginException;
import com.tenderlitch.core.vo.UpcUserVO;

public class LoginUtil {

	private static final Log logger = LogFactory.getLog(LoginUtil.class);

	private static final ThreadLocal<UpcUserVO> threadUser = new ThreadLocal<UpcUserVO>();

	private static final String DEFAULT_USER_ID = "NA";

	public static void init(UpcUserVO upcUserVO) {
		threadUser.set(upcUserVO);
	}

	public static Long getUpcUserSid() {
		UpcUserVO upcUserVO = threadUser.get();

		Long userSid = null;
		if (upcUserVO != null) {
			userSid = upcUserVO.getSid();
		}
		return userSid;
	}

	public static String getUpcUserId() {
		UpcUserVO upcUserVO = threadUser.get();
		String userId = null;
		if (upcUserVO != null) {
			userId = upcUserVO.getUpcUserId();
		}

		// logger.debug("user pin get from threadLocal="+pin);
		if (userId == null)
			userId = DEFAULT_USER_ID;
		return userId;
	}

	public static void checkIfHasValidLoginInfo() {
		String pin = getUpcUserId();
		if (pin == null || pin.equals(DEFAULT_USER_ID)) {
			logger.warn("Not login access!");
			throw new NotLoginException();
		}
	}

	public static boolean isHaveValidLoginInfo() {
		String pin = getUpcUserId();
		return !(pin == null || pin.equals(DEFAULT_USER_ID));
	}

	/**
	 * 销毁
	 */
	public static void destroy() {
		threadUser.remove();
	}
}
