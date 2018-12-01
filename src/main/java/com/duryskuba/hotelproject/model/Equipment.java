package com.duryskuba.hotelproject.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "PLACE_EQUIPMENT")
@NoArgsConstructor
@Getter
@Setter
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean wifi;
    private boolean tv;
    private boolean balcony;
    private boolean fridge;
    private boolean heating;
    private boolean sauna;
    private boolean barbacue;
    @Column(name = "CAR_PARK")
    private boolean carPark;
    @Column(name = "WATER_POOL")
    private boolean waterPool;
    @Column(name = "BEACH_EQUIPMENT")
    private boolean beachEquipment;
    @Column(name = "AIR_CONDITION")
    private boolean airCondition;
    @Column(name = "ELECTRIC_KETTLE")
    private boolean electricKettle;
    //todo wiecej?

}
