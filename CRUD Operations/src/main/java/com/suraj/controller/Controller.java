package com.suraj.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.suraj.beans.Student;
import com.suraj.factory.ConnectionFactory;
import com.suraj.factory.ServiceFactory;
import com.suraj.service.Service;

@WebServlet("*.do")
public class Controller extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
	protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String request_uri = request.getRequestURI();
		forwardRequest(request_uri, request, response);
	}
	
	private void forwardRequest(String uri, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		RequestDispatcher dispatcher = null;
		Service service = ServiceFactory.getService();
		if(uri.endsWith("addstudent.do")) {
			dispatcher = request.getRequestDispatcher("./addstudent.html");
			dispatcher.forward(request, response);
		}
		if(uri.endsWith("deletestudent.do")) {
			dispatcher = request.getRequestDispatcher("./deletestudent.html");
			dispatcher.forward(request, response);
		}
		if(uri.endsWith("editstudent.do")) {
			dispatcher = request.getRequestDispatcher("./editstudent.html");
			dispatcher.forward(request, response);
		}
		if(uri.endsWith("searchstudent.do")) {
			dispatcher = request.getRequestDispatcher("./searchstudent.html");
			dispatcher.forward(request, response);
		}
		if(uri.endsWith("add.do")) {
			try {
				int id = Integer.parseInt(request.getParameter("student_id"));
				String name = request.getParameter("student_name");
				String address = request.getParameter("student_address");
				String tempDate = request.getParameter("student_dob");
//				SimpleDateFormat date_formatter = new SimpleDateFormat("dd/MM/yyyy");
//				java.util.Date date = date_formatter.parse(tempDate);
				java.sql.Date dob = java.sql.Date.valueOf(tempDate);
				
				Student student = new Student();
				student.setStudent_id(id);
				student.setName(name);
				student.setAddress(address);
				student.setDob(dob);
				
				if(service.check(id)) {
					dispatcher = request.getRequestDispatcher("./studentfound.html");
					dispatcher.forward(request, response);
					return;
				}
				
				if(service.add(student)) {
					dispatcher = request.getRequestDispatcher("./addsuccess.html");
					dispatcher.forward(request, response);
				}
				
			}
			catch(NumberFormatException e) {
				e.printStackTrace();
			}
		}
		if(uri.endsWith("delete.do")) {
			int id = Integer.parseInt(request.getParameter("student_id"));
			if(!service.check(id)) {
				dispatcher = request.getRequestDispatcher("./nostudent.html");
				dispatcher.forward(request, response);
				return;
			}
			if(service.delete(id)) {
				dispatcher = request.getRequestDispatcher("./deletesuccess.html");
				dispatcher.forward(request, response);
			}
		}
		if(uri.endsWith("edit.do")) {
			int id = Integer.parseInt(request.getParameter("student_id"));
			if(!service.check(id)) {
				dispatcher = request.getRequestDispatcher("./nostudent.html");
				dispatcher.forward(request, response);
				return;
			}
			Student student = service.get(id);
			generateUpdateForm(student, response);
		}
		if(uri.endsWith("update.do")) {
			Student student = new Student();
			int std_id = Integer.parseInt(request.getParameter("student_id"));
			String name = request.getParameter("student_name");
			String address = request.getParameter("student_address");
			java.sql.Date dob = java.sql.Date.valueOf(request.getParameter("student_dob"));
			
			student.setStudent_id(std_id);
			student.setName(name);
			student.setAddress(address);
			student.setDob(dob);
			
			if(service.update(student)) {
				dispatcher = request.getRequestDispatcher("./updatesuccess.html");
				dispatcher.forward(request, response);
			}
		}
		if(uri.endsWith("search.do")) {
			int student_id = Integer.parseInt(request.getParameter("student_id"));
			if(!service.check(student_id)) {
				dispatcher = request.getRequestDispatcher("./nostudent.html");
				dispatcher.forward(request, response);
				return;
			}
			Student student = service.get(student_id);
			
			generateStudentTable(student, response);
		}
		if(uri.endsWith("allstudents.do")) {
			List<Student> students = service.getAll();
			if(students.isEmpty()) {
				dispatcher = request.getRequestDispatcher("./nostudent.html");
				dispatcher.forward(request, response);
				return;
			}
			fixAllStudents(students, response);
		}
	}
	private void fixAllStudents(List<Student> students, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		writer.println(
				"<html>"
				+"<style>"
				+ "{font-size : 1.2rem; color : white;}"
				+ "th, td {color : white; padding : 0.5rem;}"
				+ "</style>"
				+ "<body>"
				+ "<table border = '1' >"
				+ "<tr>"
				+ "<th> Student Id </th>"
				+ "<th> Student Name </th>"
				+ "<th> Student Address </th>" 
				+ "<th> Student DOB </th> </tr>"
				);
		
		for(Student student : students) {
			writer.println(
					 "<tr><td>" + student.getStudent_id() + "</td>"
					+ "<td> " + student.getName() + "</td>"
					+ "<td> " + student.getAddress() + "</td>"
					+ "<td> " + student.getDob() + "</td></tr>"
					);
		}
		writer.println("</body></html>");
	}
	private void generateStudentTable(Student student, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		writer.println("<html>"
				+"<style>"
				+"* {font-size : 1.2rem; color : white;}" 
				+ "th, td {color : white; padding : 0.5rem;}"
				+"</style>"
				+ "<body>"
				+ "<table border = '1'> <tr> <th>Student Id </th>"
				+ "<th> Student Name </th>" 
				+ "<th> Student Address </th>"
				+"<th> Student DOB </th></tr>"
				+ "<tr><td>" + student.getStudent_id() + "</td>"
				+ "<td> " + student.getName() + "</td>"
				+ "<td> " + student.getAddress() + "</td>"
				+ "<td> " + student.getDob() + "</td></tr></body></html>"
				);
	}
	private void generateUpdateForm(Student st, HttpServletResponse response) throws IOException {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<!DOCTYPE html>\r\n"
					+ "<html lang=\"en\">\r\n"
					+ "<head>\r\n"
					+ "  <meta charset=\"UTF-8\">\r\n"
					+ "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
					+ "  <title>update student</title>\r\n"
					+ "  <style>\r\n"
					+ "    body {\r\n"
					+ "      width: fit-content;\r\n"
					+ "      padding: 3rem;\r\n"
					+ "      margin: auto;\r\n"
					+ "      color: white;\r\n"
					+ "    }\r\n"
					+ "    input {\r\n"
					+ "      padding: 1rem;\r\n"
					+ "    }\r\n"
					+ "    input[type=\"text\"], input[type=\"date\"] {\r\n"
					+ "      width: 300px;\r\n"
					+ "      padding: 1rem;\r\n"
					+ "    }\r\n"
					+ "    tr {\r\n"
					+ "      padding: 3rem;\r\n"
					+ "    }\r\n"
					+ "  </style>\r\n"
					+ "</head>\r\n"
					+ "<body>\r\n"
					+ "  <h2>Fill up the below form to add student</h2>\r\n"
					+ "  <form action=\"./update.do\" method=\"post\">\r\n"
					+ "    <table>\r\n"
					+ "      <tr>\r\n"
					+ "        <th>Enter Student Id : </th>\r\n"
					+ "        <td><input readonly type=\"text\" name=\"student_id\" value = "+st.getStudent_id()+"></td>\r\n"
					+ "      </tr>\r\n"
					+ "      <tr>\r\n"
					+ "        <th>Enter Full Name</th>\r\n"
					+ "        <td><input type=\"text\" name=\"student_name\" value ="+st.getName() + "></td>\r\n"
					+ "      </tr>\r\n"
					+ "      <tr>\r\n"
					+ "        <th>Enter Date of Birth</th>\r\n"
					+ "        <td><input type=\"date\" name=\"student_dob\" value = "+st.getDob()+"></td>\r\n"
					+ "      </tr>\r\n"
					+ "      <tr>\r\n"
					+ "        <th>Enter Address</th>\r\n"
					+ "        <td><input type=\"text\" name=\"student_address\" value = "+st.getAddress()+"></td>\r\n"
					+ "      </tr>\r\n"
					+ "      <tr>\r\n"
					+ "        <td></td>\r\n"
					+ "        <td><input type=\"submit\" value=\"Update\"></td>\r\n"
					+ "      </tr>\r\n"
					+ "    </table>\r\n"
					+ "  </form>\r\n"
					+ "</body>\r\n"
					+ "</html>");
	}
	@Override
	public void init() throws ServletException {
		ConnectionFactory.getConnection();
	}
	@Override
	public void destroy() {
		ConnectionFactory.cleanUp();
	}

}
