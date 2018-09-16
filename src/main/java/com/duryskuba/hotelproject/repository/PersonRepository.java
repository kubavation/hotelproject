package com.duryskuba.hotelproject.repository;

import com.duryskuba.hotelproject.model.BasicPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface PersonRepository extends JpaRepository<BasicPerson,Long> {

    @Transactional
    @Modifying
    @Query("Update BasicPerson p set p.status = 'H' where p.id = :id ")
    void deletePerson(@Param("id") Long id);

    @Query("select p.confirmed from BasicPerson p where p.id = :id " )
    Character checkIfPersonIsConfirmed(@Param("id") Long id);

    List<BasicPerson> findByUsernameContaining(String username);

    Optional<BasicPerson> findByEmail(String email);

    Optional<BasicPerson> findByUsername(String username);

    @Query("select p.id from BasicPerson p where p.username = :username")
    Long findPersonIdByUsername(@Param("username") String username);
}
