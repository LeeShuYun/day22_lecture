package dev.leeshuyun.day22_lecture.repo;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import dev.leeshuyun.day22_lecture.model.Room;

//don't annotate also ok, but just use it because it's more obvious.
//this is only for the table called room inside our hotelreservation DB
// the Repo is the one that interfaces with DB
@Repository
public class RoomRepoImpl implements RoomRepo{
    @Autowired
    JdbcTemplate jdbcTemplate; //we can give it any names we want

    //counts the number of rooms
    String countSQL = "SELECT count(*) from room";

    String selectSQL = "select * from room";

    String selectByIdSQL = "select * from room where id = ?";

    //first ? is the roomtype, second ? is price
    String insertSQL = "insert into room(room_type, price) values (?, ?)"; 

    //updating 
    String updateSQL = "update room set room_type = ?, price = ? where id = ?";

    //deleting
    String deleteSQL = "delete from room where id = ?";

    @Override
    public int count() {
        Integer result = 0; 
        //result needs an integer, so we write Integer.class to cast it to Integer
        result = jdbcTemplate.queryForObject(countSQL, Integer.class); 
        //add this if there's a warning from the jdbcTemplate that we need to handle null
        //if not just return 0
        if (result == null){
            return 0;
        }else{
            return result;
        }
       
    }

    //we take a Room object and put it into the SQL 
    @Override
    public Boolean save(Room room) {
        //flag to tell if we saved it properly
        Boolean saved = false;
        //use a  string sql, preparedstatementcallback
        //prepared statement call back need a method override, just quick fix it. 
        //the prepared statementcallback needs to be the same type as the boolean, so boolean
        //uses insertSQL string to input our Room data into the database
        //saved will hold the result of executing our SQL query. 1 for success, 0 for fail.
        saved = jdbcTemplate.execute(insertSQL, new PreparedStatementCallback<Boolean>() {

            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                //gets our Room Object attributes and add to the SQL query
                ps.setString(1, room.getRoomType());
                ps.setInt(2, room.getPrice());

                //fires the query to the DB
                Boolean result = ps.execute(); 
                return result;
            }
            
        });
        //returns 1 if save successful, 0 if not
        return saved;
    }

    @Override
    public List<Room> findAll() {
        List<Room> resultList = new ArrayList<Room>();
        //BeanPropertyRowMapper will automatically map our values to the Room object for us
        //it's a Spring thing.
        resultList = jdbcTemplate.query(selectSQL, BeanPropertyRowMapper.newInstance(Room.class));
        return resultList;
    }

    //returns the room object with the item
    @Override
    public Room findById(Integer id) {
        //select * from room where id = ?
        return jdbcTemplate.queryForObject(
            selectByIdSQL, BeanPropertyRowMapper.newInstance(Room.class), id);
    }

    //update room set room_type = ?, price = ? where id = ?
    //
    @Override
    public int update(Room room) {
        int updated = 0;
        //we can use this for insert as well
        //update room set room_type = ?, price = ? where id = ?
        //parameter 1 is  the roomtype, 2 is price, 3 is id. we set each one
        //update don't need to execute, because it will happen automatically
        updated = jdbcTemplate.update(updateSQL, new PreparedStatementSetter(){

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, room.getRoomType());
                ps.setInt(2, room.getPrice());
                ps.setInt(3, room.getId());                
            }
        });
        return updated;
    }

    @Override
    public int deleteById(Integer id) {
        int deleted = 0;

        //the more long winded way of writing the Setter. what it actually looks like
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, id);                
            }
        };
        deleted = jdbcTemplate.update(deleteSQL, pss);

        return deleted;
    }

}
