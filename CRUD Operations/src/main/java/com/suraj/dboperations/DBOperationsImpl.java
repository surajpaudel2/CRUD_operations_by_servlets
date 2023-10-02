package com.suraj.dboperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.suraj.beans.Student;
import com.suraj.factory.ConnectionFactory;

public class DBOperationsImpl implements DBOperations {

	@Override
	public boolean addStudent(Student std) {
		try {
			Connection con = ConnectionFactory.getConnection();
			PreparedStatement ps = con.prepareStatement("insert into register values (?, ?, ?, ?)");
			ps.setInt(1, std.getStudent_id());
			ps.setString(2, std.getName());
			ps.setString(3, std.getAddress());
			ps.setDate(4, std.getDob());
			
			if(ps.executeUpdate() > 0) {
				return true;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public List<Student> getStudents() {
		ArrayList<Student> students = new ArrayList<Student>();
		try {
			Connection con = ConnectionFactory.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from register");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Student student = new Student();
				student.setStudent_id(rs.getInt(1));
				student.setName(rs.getString(2));
				student.setAddress(rs.getString(3));
				student.setDob(rs.getDate(4));
				students.add(student);
			}
			return students;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean checkExistedStudent(int id) {
		try {
			Connection con = ConnectionFactory.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from register where student_id = ?");
			ps.setInt(1, id);
			if(ps.executeQuery().next()) {
				return true;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateStudent(Student std) {
		try {
			Connection con = ConnectionFactory.getConnection();
			PreparedStatement ps = con.prepareStatement("update register set name = ?, address = ?, dob = ? where student_id = ?");
			ps.setString(1, std.getName());
			ps.setString(2, std.getAddress());
			ps.setDate(3, std.getDob());
			ps.setInt(4, std.getStudent_id());
			
			if(ps.executeUpdate() > 0) {
				return true;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Student getStudent(int id) {
		try {
			Connection con = ConnectionFactory.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from register where student_id = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			Student student = new Student();
			
			if(rs.next()) {
				student.setStudent_id(rs.getInt(1));
				student.setName(rs.getString(2));
				student.setAddress(rs.getString(3));
				student.setDob(rs.getDate(4));
			}
			return student;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean deleteStudent(int id) {
		try {
			Connection con = ConnectionFactory.getConnection();
			PreparedStatement ps = con.prepareStatement("delete from register where student_id = ?");
			ps.setInt(1, id);
			if(ps.executeUpdate() > 0) {
				return true;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
