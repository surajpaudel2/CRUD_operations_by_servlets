package com.suraj.factory;

import com.suraj.dboperations.DBOperations;
import com.suraj.dboperations.DBOperationsImpl;

public class DBFactory {
	private static DBOperations db;
	static {
		db =  new DBOperationsImpl();
	}
	public static DBOperations getDB() {
		return db;
	}
}
