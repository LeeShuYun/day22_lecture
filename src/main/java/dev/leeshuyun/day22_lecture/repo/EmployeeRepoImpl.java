package dev.leeshuyun.day22_lecture.repo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import dev.leeshuyun.day22_lecture.model.Dependant;
import dev.leeshuyun.day22_lecture.model.Employee;

//
@Repository
public class EmployeeRepoImpl implements EmployeeRepo{
    @Autowired
    JdbcTemplate jdbcTemplate; //for executing query

    @Override
    public List<Employee> retrieveEmployeeList(){
        //make sure the queries work in workbench before you paste them here. add a " "
        //this query will return a list of employees.
        String selectSQL = "select e.id emp_id, e.first_name, e.last_name, e.salary, " +
        "d.id dep_id, d.fullname, d.relationship, d.birth_date " +
        "from employee e " +
        "inner join dependant d " + 
        "on e.id = d.employee_id ";

        //now we need to map the results to SQL
        //resultset extractor will grab all our data out
        return jdbcTemplate.query(selectSQL, new ResultSetExtractor<List<Employee>>(){

            @Override
            public List<Employee> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<Employee> employees = new ArrayList<Employee>();

                //going through the Resultset which contains the data from our query
                //each row is a new line
                //we're plucking data out one row per iteration
                while (rs.next()){
                    //make a new Employee Object to hold our data
                    Employee emp = new Employee();
                    //set each column of data to the corresponding Employee instance variable 
                    emp.setId(rs.getInt("emp_id"));
                    emp.setFirstName(rs.getString("first_name"));
                    emp.setLastName(rs.getString("last_name"));
                    emp.setSalary(rs.getInt("salary"));
                    emp.setDependants(new ArrayList<Dependant>()); //creating a list of dependants

                    Dependant dep = new Dependant();
                    dep.setId(rs.getInt("dep_id"));
                    dep.setFullname(rs.getString("fullname"));
                    dep.setRelationship(rs.getString("relationship"));
                    dep.setBirthDate(rs.getDate("birth_date"));
                    
                    //if this is a new list and empty
                    if(employees.size() == 0){
                        //add the dependants to the employee
                        emp.getDependants().add(dep);
                        //then save the current employee obj to list
                        employees.add(emp);
                    }else{ //if list contains something already
                        //check if the current employee already exists inside our list
                        List<Employee> eList = employees.stream()
                                                        .filter(e-> e.getId() == emp.getId())
                                                        .collect(Collectors.toList());
                        
                        //if we haven't got any records of this employee, put the new record in
                        if (eList.size() == 0){
                            emp.getDependants().add(dep);
                            employees.add(emp);
                        }else{
                            //if the employee does exist... we grab thefirst guy in our elist
                            // look for index of that first guy and add the dependant
                            int idx = employees.indexOf(eList.get(0));
                            if (idx >=0){
                                //grabs the dependant record and adds it in
                                employees.get(idx).getDependants().add(dep);
                            }
                        }
                    }
                        
                }
                return employees;
            }
            
        });
    }
}
