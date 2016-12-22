package com.tenderlitch.core.web;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tenderlitch.core.exception.NotLoginException;
import com.tenderlitch.core.vo.UpcUserVO;
public class LoginUtil { 

private static final Log logger = LogFactory.getLog(LoginUtil.class);

private static final ThreadLocal<UpcUserVO> threadUser = new ThreadLocal<UpcUserVO>();

private static final String DEFAULT_USER_ID = "NA";

private static final String DEFAULT_LOGIN_GROUP = "NA";

private static final String DEFAULT_TRANSACTION_ID = "NA";

private static final String DEFAULT_LOGIN_SEQ = "NA";

private static final String DEFAULT_LOGIN_DEPT = "NA";


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

public static String getUpcUserGroup() {
    UpcUserVO upcUserVO = threadUser.get();

    String userGroup = null;
    if (upcUserVO != null) {
        userGroup = upcUserVO.getUpcUserGroup();
    }
//    logger.debug("user group get from threadLocal=" + userGroup);
    if (StringUtils.isBlank(userGroup))
        userGroup = DEFAULT_USER_ID;
    return userGroup;
}

public static String getUpcUserId() {
    UpcUserVO upcUserVO = threadUser.get();
    String userId = null;
    if (upcUserVO != null) {
        userId = upcUserVO.getUpcUserId();
    }

    // logger.debug("user pin get from threadLocal="+pin);
    if (userId == null)
        userId = DEFAULT_LOGIN_GROUP;
    return userId;
}

public static String getUpcUserSeq() {
    UpcUserVO upcUserVO = threadUser.get();
    String seq = null;
    if (upcUserVO != null) {
        seq = upcUserVO.getUpcUserSeq();
    }

    // logger.debug("user pin get from threadLocal="+pin);
    if (seq == null)
        seq = DEFAULT_LOGIN_SEQ;
    return seq;
}

public static String getUpcUserDeptId() {
    UpcUserVO upcUserVO = threadUser.get();

    String userDeptId = null;
    if (upcUserVO != null) {
    	userDeptId = upcUserVO.getUpcUserDeptId();
    }
//    logger.debug("user group get from threadLocal=" + userGroup);
    if (StringUtils.isBlank(userDeptId))
    	userDeptId = DEFAULT_LOGIN_DEPT;
    return userDeptId;
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

public static String getL4FacilityIds(){
	UpcUserVO upcUserVO = threadUser.get();
    String l4FacilityIds = null;
    if (upcUserVO != null) {
    	l4FacilityIds = upcUserVO.getL4FacilityIds();
    }
    return l4FacilityIds;
}

public static String getFacilityIds(){
	UpcUserVO upcUserVO = threadUser.get();
    String facilityIds = null;
    if (upcUserVO != null) {
    	facilityIds = upcUserVO.getFacilityIds();
    }
    return facilityIds;
}

public static String getUpcUserPost() {
    UpcUserVO upcUserVO = threadUser.get();
    String userPost = null;
    if (upcUserVO != null) {
    	userPost = upcUserVO.getUpcUserPost();
    }

    // logger.debug("user pin get from threadLocal="+pin);
    if (userPost == null)
    	userPost = DEFAULT_LOGIN_GROUP;
    return userPost;
}


public static String getTransactionId() {
    UpcUserVO upcUserVO = threadUser.get();
    String transactonId = null;
    if (upcUserVO != null) {
    	transactonId = upcUserVO.getTransactionId();
    }

    // logger.debug("user pin get from threadLocal="+pin);
    if (transactonId == null)
    	transactonId = DEFAULT_TRANSACTION_ID;
    return transactonId;
}

public static Map<String,List<String>> getBusinessIDs() {
    UpcUserVO upcUserVO = threadUser.get();
    Map<String,List<String>> businessIDs = null;
    if (upcUserVO != null) {
    	businessIDs = upcUserVO.getBusinessIDs();
    }
    return businessIDs;
}

public static String getCrewId() {
    return getUpcUserGroup();
}

public static String getShiftId() {
    return getUpcUserSeq();
}

public static String getUpcUserPassword() {
    UpcUserVO upcUserVO = threadUser.get();
    String userPassword = "";
    if (upcUserVO != null) {
    	userPassword = upcUserVO.getPassword();
    }

    return userPassword;
}

	/**
	 * 销毁
	 */
	public static void destroy() {
		threadUser.remove();
	}
}

