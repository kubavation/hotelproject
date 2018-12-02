package com.duryskuba.hotelproject.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.List;
//
@Entity
@Table(name="MOTEL")
@Getter
@Setter
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "place_id", referencedColumnName = "id")
public class Motel extends BasicPlace {

    @OneToMany(mappedBy = "place")
    private List<Room> rooms;
}
