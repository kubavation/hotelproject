package com.duryskuba.hotelproject.repository;

import com.duryskuba.hotelproject.model.PlaceComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface PlaceCommentRepository extends JpaRepository<PlaceComment,Long> {

    @Query("from PlaceComment p where p.basicPlace.id = :id and p.status = 'A'")
    List<PlaceComment> getAllCommentsByHotelId(@Param("id") Long hotelId);

    @Query("select p from PlaceComment p where p.basicPerson.id = :id")
    List<PlaceComment> getAllCommentsOfPerson(@Param("id") Long userId);

}
