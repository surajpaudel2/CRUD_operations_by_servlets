package com.suraj.service;

import java.util.List;

import com.suraj.beans.Student;

public interface Service {
	boolean add(Student std);
	boolean check(int std_id);
	List<Student> getAll();
	boolean update(Student std);
	Student get(int std_id);
	boolean delete(int std_id);
}
