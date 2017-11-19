package com.yy.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Set;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 *转换器 1:将JavaBean 转换成Map 2:将JSONObject 转换成Map
 * 
 */
public class BeanConverter {
	/**
	 * 将javaBean转换成Map
	 * 
	 * @param javaBean
	 *            javaBean
	 * @return Map对象
	 */
	public static HashMap<String, Object> toMap(Object javaBean) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		Method[] methods = javaBean.getClass().getDeclaredMethods();

		for (Method method : methods) {
			try {
				if (method.getName().startsWith("get")) {
					String field = method.getName();
					field = field.substring(field.indexOf("get") + 3);
					field = field.toLowerCase().charAt(0) + field.substring(1);

					Object value = method.invoke(javaBean, (Object[]) null);
					result.put(field, null == value ? "" : value.toString());
				}
			} catch (Exception e) {
			}
		}

		return result;
	}

	/**
	 * 将json对象转换成Map
	 * 
	 * @param jsonObject
	 *            json对象
	 * @return Map对象
	 */
	public static HashMap<String, Object> toMap(JSONObject jsonObject) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		Set<?> keys = jsonObject.keySet();
		for (Object key : keys) {
			Object o = jsonObject.get(key);
			if (o instanceof JSONArray) {
				Object oBack = reflect((JSONArray) o);
				if (null != oBack) {
					result.put((String) key, oBack);
				} else {
					continue;
				}
			} else if (o instanceof JSONObject) {
				result.put((String) key, o);
			} else {
				result.put((String) key, o);
			}
		}

		return result;
	}

	/**
	 * 将JSONArray对象转换成Map-List集合
	 * 
	 * @see JSONHelper#reflect(JSONObject)
	 * @param json
	 * @return
	 */
	public static Object reflect(JSONArray json) {
		for (Object o : json) {
			if (null != o && !o.equals("")) {
				if (o instanceof JSONArray) {
					reflect((JSONArray) o);
				} else {
					return o;
				}
			}
		}
		return null;
	}
	public static void main(String[] args) {
		//Object obj = Utility.requestEscape(request.getParameter("pa_params"));
		//JSONObject jsonObject = JSONObject.fromObject(obj);
		//HashMap<String, Object> pameterMap = BeanConverter.toMap(jsonObject);
	}
}
