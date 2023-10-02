package com.suraj.dboperations;

import java.util.List;

import com.suraj.beans.Student;

public interface DBOperations {
	boolean addStudent(Student std);
	List<Student> getStudents();
	boolean checkExistedStudent(int id);
	boolean updateStudent(Student std);
	Student getStudent(int id);
	boolean deleteStudent(int id);
}
