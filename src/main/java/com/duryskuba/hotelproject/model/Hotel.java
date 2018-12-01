package com.duryskuba.hotelproject.model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "HOTEL")
public class Hotel extends BasicPlace {

    //lista pokojow
    @OneToMany(mappedBy = "place")
    private List<Room> rooms;
}
