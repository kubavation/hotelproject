package com.duryskuba.hotelproject.repository;

import com.duryskuba.hotelproject.model.PlaceAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface PlaceAddressRepository extends JpaRepository<PlaceAddress,Long> {
}
