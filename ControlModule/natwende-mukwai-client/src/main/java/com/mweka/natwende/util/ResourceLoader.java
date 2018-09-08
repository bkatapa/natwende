package com.mweka.natwende.util;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class ResourceLoader {
	
	public static Properties getAsProperties(String name) {
		
		Properties props = new Properties();
		URL url = ResourceLoader.getAsUrl(name);

		if (url != null) {
			try {
				// Load the properties using the URL (from the CLASSPATH).
				props.load(url.openStream());
			} catch (IOException e) {
			}
		}

		return props;
	}

	public static URL getAsUrl(String name) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		return classLoader.getResource(name);
	}

}
