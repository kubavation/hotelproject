package com.duryskuba.hotelproject.repository;

import com.duryskuba.hotelproject.model.BasicPlace;
import com.duryskuba.hotelproject.model.PlaceComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface BasicPlaceRepository extends JpaRepository<BasicPlace,Long> {

    @Transactional
    @Modifying
    @Query("Update BasicPlace p set p.status = 'H' where p.id = :id ")
    void deletePlace(@Param("id") Long id);
}
