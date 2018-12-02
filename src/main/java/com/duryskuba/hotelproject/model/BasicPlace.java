package com.duryskuba.hotelproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
public class BasicPlace {   // todo abstract??

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5)
    private String name;

    //@NotNull
    //@JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PERSON_ID")
    private BasicPerson basicPerson;

    //@JsonIgnore         // todo wyrzuc te ignory?
    //@Valid
    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ADDRESS_ID")
    private PlaceAddress placeAddress;

    //@JsonIgnore
    private Character status;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "basicPlace")
    private List<PlaceComment> placeComments;

//    @OneToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "EQUIPMENT_ID")
//    private Equipment equipment;

   // @JsonIgnore
   // @OneToMany(mappedBy = "place", fetch = FetchType.EAGER)
   // private List<PlaceImage> images;
}
