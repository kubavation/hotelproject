package com.duryskuba.hotelproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "PLACE_COMMENT")
@Getter
@Setter
@NoArgsConstructor
public class PlaceComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PERSON_ID")
    //@NotNull
    //@JsonIgnore
    private BasicPerson basicPerson;

    @ManyToOne(fetch = FetchType.EAGER) // ? EAGER ?
    @JoinColumn(name = "PLACE_ID")
    //@NotNull
   // @JsonIgnore
    private BasicPlace basicPlace;

    @NotNull
    @Size(max = 255)
    private String text; // mooze zmiana na BLOB ?

    //@NotNull
    @Column(name = "CREATION_DATE")
    private LocalDateTime creationDate;

   // @NotNull
    private Character status;




}
