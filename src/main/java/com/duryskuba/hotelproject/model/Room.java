package com.duryskuba.hotelproject.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ROOM")
@NoArgsConstructor
@Setter
@Getter
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NUMBER_OF_PEOPLE")
    private Integer numberOfPeople;

    @Column(name = "TOTAL_COST")
    private BigDecimal totalCost;

    @ManyToOne
    @JoinColumn(name = "place_id")
    private BasicPlace place;

}
