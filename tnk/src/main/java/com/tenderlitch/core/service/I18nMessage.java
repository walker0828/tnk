package com.tenderlitch.core.service;

import java.util.Locale;

public interface I18nMessage {
	public String getMessage(String key, Object[] params, Locale locale);
	public String getMessageDirect(String key, Object[] params, Locale locale);
}
