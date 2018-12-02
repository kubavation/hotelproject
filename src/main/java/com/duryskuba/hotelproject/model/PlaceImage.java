package com.duryskuba.hotelproject.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "PLACE_IMAGE")
@Getter
@Setter
@NoArgsConstructor
public class PlaceImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FILE_NAME")
    private String fileName;

    @ManyToOne
    @JoinColumn(name = "place_id")
    private BasicPlace place;




}
