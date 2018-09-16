package com.duryskuba.hotelproject.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "PLACE_ADDRESS")
@Getter
@Setter
@NoArgsConstructor
public class PlaceAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 4)
    private String country;

    @NotNull
    @Size(min = 4)
    private String city;

    @NotNull
    @Size(min = 4)
    private String street;

    @NotNull
    @Column(name = "PLACE_NUMBER")
    private Integer placeNumber;

    public PlaceAddress(@NotNull @Size(min = 4) String country,
                        @NotNull @Size(min = 4) String city,
                        @NotNull @Size(min = 4) String street,
                        @NotNull Integer placeNumber) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.placeNumber = placeNumber;
    }
}
