package com.duryskuba.hotelproject.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class PlaceRatingPK implements Serializable {

    @NotNull
    @Column(name = "PLACE_ID")
    private Long placeId;

    @NotNull
    @Column(name = "PERSON_ID")
    private Long personId;

    public PlaceRatingPK(@NotNull Long placeId, @NotNull Long personId) {
        this.placeId = placeId;
        this.personId = personId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceRatingPK that = (PlaceRatingPK) o;
        return Objects.equals(placeId, that.placeId) &&
                Objects.equals(personId, that.personId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(placeId, personId);
    }


}
