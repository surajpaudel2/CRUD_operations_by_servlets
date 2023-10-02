package com.suraj.factory;

import com.suraj.service.Service;
import com.suraj.service.ServiceImpl;

public class ServiceFactory {
	private static Service service;
	static {
		service = new ServiceImpl();
	}
	public static Service getService() {
		return service;
	}
}
