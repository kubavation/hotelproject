package com.duryskuba.hotelproject.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "PLACE_RATING")
@NoArgsConstructor
@Setter
@Getter
public class PlaceRating {

    @EmbeddedId
    private PlaceRatingPK id;

    @NotNull
    private Integer rating;

    //@NotNull
    private Character status;
}
