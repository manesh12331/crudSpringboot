package com.emp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emp.exception.ResourceNotFoundException;
import com.emp.model.Employee;
import com.emp.repository.EmpRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {
	
	@Autowired 
	private EmpRepository empRepository;
	
	@PostMapping("/addEmployee")
    public Employee createEmployee(@Validated @RequestBody Employee employee) {
       return empRepository.save(employee);
    }
	
	@GetMapping("/employees")
	public List<Employee>  getAllEmployee(){
		return empRepository.findAll();
		
	}
	
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id){
		Employee employee=empRepository.findById(id).
				orElseThrow(()-> new ResourceNotFoundException("Employee Not exist with id"+id));
		return ResponseEntity.ok(employee);
	}
	
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,@RequestBody Employee employeeDetails){
		Employee employee=empRepository.findById(id).
				orElseThrow(()-> new ResourceNotFoundException("Employee Not exist with id"+id));
		
		System.out.println("in spring");
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmailId(employeeDetails.getEmailId());
		
		Employee updateEmployee=empRepository.save(employee);
		return ResponseEntity.ok(updateEmployee);
	}
	@DeleteMapping("/employees/{id}")
	public  ResponseEntity<Map<String,Boolean>> deleteEmployee(@PathVariable Long id){
		Employee employee=empRepository.findById(id).
				orElseThrow(()-> new ResourceNotFoundException("Employee Not exist with id"+id));
		empRepository.delete(employee);
		Map<String,Boolean> response= new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	
	}
	

}
