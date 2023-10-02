package com.suraj.service;

import java.util.List;

import com.suraj.beans.Student;
import com.suraj.dboperations.DBOperations;
import com.suraj.factory.DBFactory;

public class ServiceImpl implements Service {
	DBOperations dbo = DBFactory.getDB();
	@Override
	public boolean add(Student std) {
		return dbo.addStudent(std);
	}

	@Override
	public boolean check(int std_id) {
		return dbo.checkExistedStudent(std_id);
	}

	@Override
	public List<Student> getAll() {
		return dbo.getStudents();
	}

	@Override
	public boolean update(Student std) {
		return dbo.updateStudent(std);
	}

	@Override
	public Student get(int std_id) {
		return dbo.getStudent(std_id);
	}

	@Override
	public boolean delete(int std_id) {
		return dbo.deleteStudent(std_id);
	}


}
