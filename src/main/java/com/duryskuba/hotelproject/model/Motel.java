package com.duryskuba.hotelproject.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
//
@Entity
@Table(name="MOTEL")
@Getter
@Setter
@NoArgsConstructor
public class Motel extends BasicPlace {

    @OneToMany(mappedBy = "place")
    private List<Room> rooms;
}
