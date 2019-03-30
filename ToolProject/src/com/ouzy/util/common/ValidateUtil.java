package com.ouzy.util.common;

import java.util.Collection;
import java.util.Map;

public class ValidateUtil {

	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object value) {
		if (value == null)
			return true;
		if (value instanceof String) {
			if (((String) value).length() == 0)
				return true;
		} else if (value instanceof Collection) {
			if (((Collection) value).size() == 0)
				return true;
		} else if (value instanceof Map) {
			if (((Map) value).size() == 0)
				return true;
		} else if ((value instanceof String[])
				&& ((String[]) value).length == 0)
			return true;
		return false;
	}

	public static boolean isEmpty(String s) {
		return s == null || s.length() == 0;
	}

	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Collection c) {
		return c == null || c.size() == 0;
	}

	public static boolean isNotEmpty(String s) {
		return s != null && s.length() > 0;
	}

	@SuppressWarnings("rawtypes")
	public static boolean isNotEmpty(Collection c) {
		return c != null && c.size() > 0;
	}
}
