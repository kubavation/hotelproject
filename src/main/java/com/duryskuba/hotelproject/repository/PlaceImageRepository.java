package com.duryskuba.hotelproject.repository;

import com.duryskuba.hotelproject.model.PlaceImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface PlaceImageRepository extends JpaRepository<PlaceImage,Long> {

    @Query("select i from PlaceImage i where i.place.id = :id")
    List<PlaceImage> findPlaceImagesByPlaceId(@Param("id") Long placeId);
}
