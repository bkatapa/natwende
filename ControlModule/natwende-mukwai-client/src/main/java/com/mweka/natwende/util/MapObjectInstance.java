package com.mweka.natwende.util;

import java.util.Map;

import java.util.HashMap;
import java.io.Serializable;
import java.lang.reflect.Field;

public class MapObjectInstance implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -673409389280385686L;

	/*
    public static void main(String[] args) {
        Map<String, Object> map = parameters(new MapObjectInstance());
        map.entrySet().forEach(entry -> {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        });
    }
	*/
    public static synchronized Map<String, Object> parameters(Object obj) {
        Map<String, Object> map = new HashMap<>();
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try { 
                map.put(field.getName(), field.get(obj)); 
            } 
            catch (IllegalAccessException | IllegalArgumentException e) { 
            }
        }
        return map;
    }
}