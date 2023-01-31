package dev.leeshuyun.day22_lecture.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.leeshuyun.day22_lecture.repo.EmployeeRepo;
import dev.leeshuyun.day22_lecture.model.Employee;

@RequestMapping("/api/employees")
@RestController
public class EmployeeController {
    @Autowired
    EmployeeRepo empRepo;
    
    @GetMapping("/")
    public List<Employee> retrieveEmployees(){
        List<Employee> employees = empRepo.retrieveEmployeeList();

        if (employees.isEmpty()){
            return null; //there's no employee data inside the Db
        }else{
            return employees; //return employees list if there is data this is possible
        }
    }
}
