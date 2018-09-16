package com.duryskuba.hotelproject.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "COMMENT_RATING")
@Getter
@Setter
@NoArgsConstructor
public class CommentRating {

    @EmbeddedId
    private CommentRatingPK id;

//    @ManyToOne
//    @JoinColumn(name = "COMMENT_ID")
//    private PlaceComment placeComment;
//
//    @ManyToOne
//    @JoinColumn(name = "PERSON_ID")
//    private BasicPerson basicPerson;

    @NotNull
    private Long rating;

    @NotNull
    private Character status;

}
