package com.duryskuba.hotelproject.repository;

import com.duryskuba.hotelproject.model.PlaceRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface PlaceRatingRepository extends JpaRepository<PlaceRating, Long> {

    @Query("select r.rating from PlaceRating r where r.id.placeId = :placeId")
    List<Integer> getRatingsByPlaceId(@Param("placeId") Long placeId);

    @Query("select count(*) from PlaceRating r where r.id.placeId = :placeId")
    Integer getCountOfRatingsByPlaceId(@Param("placeId") Long placeId);

    @Query("select count(*) from PlaceRating r where r.id.placeId = :placeId and r.id.personId = :personId")
    int ifRatingExists(@Param("placeId") Long placeId, @Param("personId") Long personId);

    @Query("select r from PlaceRating r where r.id.placeId = :placeId and r.id.personId = :personId")
    PlaceRating getRatingByPk(@Param("placeId") Long placeId, @Param("personId") Long personId);
}
