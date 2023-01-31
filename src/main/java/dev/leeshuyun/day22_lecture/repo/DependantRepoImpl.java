package dev.leeshuyun.day22_lecture.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.ResultSetSupportingSqlParameter;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import dev.leeshuyun.day22_lecture.model.Dependant;
import dev.leeshuyun.day22_lecture.model.Employee;

public class DependantRepoImpl implements DependantRepo{
    @Autowired
    JdbcTemplate jdbcTemplate;

    
    @Override
    List<Dependant> retrieveDependantList(){
        String selectSQL = "select ";

        return jdbcTemplate.query(selectSQL, new ResultSetExtractor<List<Dependant>>(){

            @Override
            public List<Dependant> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<Dependant> dependants = new ArrayList<>();

                while (rs.next()){
                    Dependant dep = new Dependant();
                    dep.setId(rs.getInt("dep_id"));
                    dep.setFullname(rs.getString("fullname"));
                    dep.setBirthDate(rs.getDate("birthdate"));
                    dep.setRelationship(rs.getString("relationship"));
                    dep.setEmployee(new Employee()); // no need a list here, children only one dad

                    //filling in our employee obj
                    Employee emp = new Employee();
                    emp.setId(rs.getInt("emp_id"));
                    emp.setFirstName(rs.getString("first_name"));
                    emp.setLastName(rs.getString("last_name"));
                    emp.setSalary(rs.getInt("salary"));
                }
            }
        });
    }

}
