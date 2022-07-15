package cn.ac.gsa.utility;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

public class SpringContextUtil{
	private static ApplicationContext applicationContext;

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	public static void setApplicationContext(ApplicationContext applicationContext) {
		SpringContextUtil.applicationContext = applicationContext;
	}

	public static Object getBean(String name)throws BeansException {
		return applicationContext.getBean(name);
	}
}
