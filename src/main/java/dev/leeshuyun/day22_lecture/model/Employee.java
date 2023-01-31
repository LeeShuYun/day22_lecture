package dev.leeshuyun.day22_lecture.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//this class maps to the employee table in hotelreservation MySQL database.
//id, first_name, last_name, salary are all columns of data in the DB
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer salary;

    //when one employee has many dependants to one Employee.
    private List<Dependant> dependants;
}
