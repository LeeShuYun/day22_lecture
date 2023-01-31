package dev.leeshuyun.day22_lecture.repo;

import java.util.List;
import dev.leeshuyun.day22_lecture.model.Room;

//interface is created here because we need to make it hard to reverse engineer our code
//in other words, this interface is for SECURITY sake!
public interface RoomRepo {
    int count();

    //create 
    Boolean save(Room room);

    //read all 
    List<Room> findAll();

    //read one record, one row is contained in Room
    Room findById(Integer id);

    //Update 
    int update(Room room);

    //Delete 
    int deleteById(Integer id);

}
