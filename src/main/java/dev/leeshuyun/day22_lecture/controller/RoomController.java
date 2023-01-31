package dev.leeshuyun.day22_lecture.controller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dev.leeshuyun.day22_lecture.model.Room;
import dev.leeshuyun.day22_lecture.service.RoomService;

//Darryl's repo:https://github.com/darryl1975/day22_lecture

//it's a API so, use Restcontroller
//APi has to conform to the URI standard. stuff in a url https://google.com.sg/rooms/{id}like
// https:// is the protocol
// google.com.sg is the domain
//path is /rooms
//{id}
// POST request is CRUD, it cna do everything, but not the best practice or readability.
// this is why we use get put delete, even though post can do everything
// GET request is READ, only use for read commands .
// PUT request is update, 
// DELETE request is url/{id}
// right here it's localhost:8080/api/rooms
@RequestMapping("/api/rooms")
@RestController
public class RoomController {
    
    @Autowired
    RoomService roomService;

    //it's the same as a SELECT Sql query since it's a get.
    //access using localhost:8080/api/rooms/count
    @GetMapping("/count")
    public Integer getRoomCount(){
        Integer roomCount = roomService.count();
        return roomCount;
    }

    //http://localhost:8080/api/rooms/
    //grabs a whole list of entries in a list of rooms
    @GetMapping("/")
    public ResponseEntity<List<Room>> retrieveAllRooms(){
        List<Room> rooms = new ArrayList<Room>();
        rooms = roomService.findAll();

        //returns a error status code if we didn't find something
        if (rooms.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        //returns 200, the ok status.
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> retrieveRoomById(@PathVariable("id") int id){
        Room room = roomService.findById(id);
        if (room == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(room, HttpStatus.OK);
        }
    }

    //we attach a JSON in Postman to send to this API
    /*{
    "room_type": "deluxe",
    "price": 500
    }*/
    // And it should return 1 or 0
    @PostMapping("/")
    public ResponseEntity<Boolean> createRoom(@RequestBody Room room){
        Room rm = room;
        Boolean result = roomService.save(rm);

        if(result){
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //since this is an Update, this is a PUT mapping
    // it seems like Spring will unpack the JSON string into Room obj for us??
    @PutMapping("/")
    public ResponseEntity<Integer> updateRoom(@RequestBody Room room) {
        Room rm = room;
        int updated = roomService.update(rm);

        if (updated == 1){
            //the 1 is a text body part, it can be changed into a message.
            return new ResponseEntity<>(1, HttpStatus.OK);
        }else{
            
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //delete a room_type by its id number.
    // eg. we want room id super president II room type deleted, we can just send 
    // a DELETE request with the URI http://localhost:8080/api/rooms/6
    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteRoom (@PathVariable("id") Integer id){
        int deleteResult =0;

        deleteResult = roomService.deleteById(id);

        if (deleteResult == 0){
            // 0 the body can be changed to return a message to the browser too
            // 0 means it failed
            return new ResponseEntity<>(0, HttpStatus.NOT_FOUND);
        }else{
            //1 means it succeeded in deleting
            return new ResponseEntity<>(1, HttpStatus.OK);
        }

    }
}
