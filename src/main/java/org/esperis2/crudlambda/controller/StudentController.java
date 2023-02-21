package org.esperis2.crudlambda.controller;

import java.util.List;

import org.esperis2.crudlambda.model.Student;
import org.esperis2.crudlambda.repository.StudentRepository;
import org.esperis2.crudlambda.utils.StudentGenerator;
import org.esperis2.crudlambda.utils.StudentStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/students")
public class StudentController {
	@Autowired
	private StudentRepository studentsRepo;
	
	@GetMapping
	public String index(Model model) {
		List<Student> students = studentsRepo.findAll();
		model.addAttribute("students", students);
		return "student/index";
	}
	
	@GetMapping("/create")
	public String create(Model model) {
		Student s = new Student();
		model.addAttribute("student", s);
		return "student/create";
	}
	
	@PostMapping("/create")
	public String store(@ModelAttribute("student") Student student, Model model) {
		studentsRepo.save(student);
		return "redirect:/students";
	}
	
	@GetMapping("/{id}")
	public String show(@PathVariable(name="id") Student student, Model model) {
		model.addAttribute("student", student);
		return "student/show";
	}
	
	@PostMapping("/createRandom")
	public String createRandom(@RequestParam(name="userInput") Integer userInput, Model model) {
		StudentGenerator generator = new StudentGenerator();
		List<Student> randomStudents = generator.generate(userInput);
		studentsRepo.saveAll(randomStudents);
		return "redirect:/students";
	}
	
	@GetMapping("/stats")
	public String stats(Model model) {
		StudentStats stats = new StudentStats(studentsRepo.findAll());
		model.addAttribute("stats", stats);
		return "student/stats";
	}
	
	/**
	 * - Media voti totale
	 * - Media voti per città/nazionalità
	 * - Per ogni città, studente migliore (media voti più alta)
	 */
	/*
	 * 1. Creare template della view per la pagina statistiche
	 * 2. Creare metodo del controller che serve la pagina statistiche
	 * 3. Creare funzioni che calcolano le statistiche
	 */
	
}
