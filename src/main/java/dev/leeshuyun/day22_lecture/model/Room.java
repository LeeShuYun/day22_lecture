package dev.leeshuyun.day22_lecture.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//this is lombok annotation.
@Data //shows that this is a database obj
@NoArgsConstructor //adds constructor
@AllArgsConstructor 
public class Room {

    private Integer id;

    private String roomType;
    
    private Integer price;
}
