package com.tenderlitch.core.exception;


/**
 * 无权限系统资源异常
 */
public class ResourceNotAvailableException extends BaseRuntimeException {

    private static final long serialVersionUID = -927028338979047567L;
    
    public ResourceNotAvailableException(String resource) {
        super("global.error.code.not.available", new String[] {});
        this.resource=resource;
    }

    private String resource;
    
	public String getResource() {
		return resource;
	}
    
}
