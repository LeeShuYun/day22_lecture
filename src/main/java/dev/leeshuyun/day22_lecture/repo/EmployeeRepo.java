package dev.leeshuyun.day22_lecture.repo;

import java.util.List;

import dev.leeshuyun.day22_lecture.model.Employee;


public interface EmployeeRepo {
    List<Employee> retrieveEmployeeList();
}
