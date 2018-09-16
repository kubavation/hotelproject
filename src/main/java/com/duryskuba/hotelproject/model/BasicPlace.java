package com.duryskuba.hotelproject.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/*
*
* ZMIEN NA DZIEDZICZENIE
*
 */

@Entity
@Table(name = "BASIC_PLACE")
@Getter
@Setter
@NoArgsConstructor
public class BasicPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5)
    private String name;

    //@NotNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PERSON_ID")
    private BasicPerson basicPerson;

    @Valid
    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ADDRESS_ID")
    private PlaceAddress placeAddress;

    private Character status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "basicPlace")
    private List<PlaceComment> placeComments;
}
