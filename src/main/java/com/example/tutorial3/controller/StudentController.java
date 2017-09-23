package com.example.tutorial3.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.tutorial3.service.InMemoryStudentService;
import com.example.tutorial3.service.StudentService;
import com.example.tutorial3.model.StudentModel;

@Controller
public class StudentController {
	private final StudentService studentService;
	
	public StudentController() {
		studentService = new InMemoryStudentService();
	}
	
	@RequestMapping("/student/add")
	public String add(@RequestParam(value = "npm", required = true) String npm,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "gpa", required = true) double gpa) {
		StudentModel student = new StudentModel(name, npm, gpa);
		studentService.addStudent(student);
		return "add";
	}
	
	@RequestMapping("/student/view")
	public String view(Model model, @RequestParam(value = "npm", required = true) String npm) {
		StudentModel student = studentService.selectStudent(npm);
		model.addAttribute("student", student);
		return "view";
	}
	
	@RequestMapping(value = "/student/view/{studentID}", method=RequestMethod.GET)
	public String viewID(@PathVariable String studentID, Model model) {
		StudentModel student = studentService.selectStudent(studentID);
		if(student.getNpm() == null){
			return "error";
		}
		else {
			model.addAttribute("student", student);
			return "view";
		}
	}
	
	@RequestMapping(value = "/student/delete/{studentID}", method=RequestMethod.GET)
	public String deleteID(@PathVariable String studentID) {
		StudentModel student = studentService.selectStudent(studentID);
		if(student.getNpm() == null){
			return "error";
		}
		else {
			studentService.deleteStudent(student);
			return "delete";
		}
	}
	
	@RequestMapping("/student/viewall")
	public String viewAll(Model model) {
		List<StudentModel> students = studentService.selectAllStudents();
		model.addAttribute("students", students);
		return "viewall";
	}
}
