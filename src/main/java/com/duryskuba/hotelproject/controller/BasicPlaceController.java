package com.duryskuba.hotelproject.controller;

import com.duryskuba.hotelproject.exception.ResourceNotFoundException;
import com.duryskuba.hotelproject.model.BasicPerson;
import com.duryskuba.hotelproject.model.UserPrincipal;
import com.duryskuba.hotelproject.service.BasicPlaceService;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import com.duryskuba.hotelproject.model.BasicPlace;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Basic;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.logging.LogManager;

@RestController
public class BasicPlaceController {

    private BasicPlaceService basicPlaceService;



    public BasicPlaceController(final BasicPlaceService basicPlaceService) {
        this.basicPlaceService = basicPlaceService;
    }

    @GetMapping("/place/{id}")
    public BasicPlace getBasicPlaceById(@PathVariable Long id) {
        return this.basicPlaceService.getPlaceById(id).orElseThrow(
                () -> new ResourceNotFoundException(ResourceNotFoundException.DEFAULT_MESSAGE + " " +  id.toString())
        );
    }

    @GetMapping("/places")
    public List<BasicPlace> getAllPlaces() {
        return basicPlaceService.getAllPlaces();
    }

    @PostMapping("/places")
    public ResponseEntity<Object> createNewPlace(@Valid @RequestBody BasicPlace place,Principal principal) {

        this.basicPlaceService.createNewPlace(place,principal);
        return new ResponseEntity<>(place,new HttpHeaders(), HttpStatus.CREATED);
    }

    @DeleteMapping("/place/{id}")
    public ResponseEntity<Object> deletePlace(@PathVariable Long id) {
        this.basicPlaceService.deletePlace(id);
        return new ResponseEntity<>(id,new HttpHeaders(),HttpStatus.OK);
    }

    @PutMapping("/place/{id}")
    public ResponseEntity<Object> updatePerson(@RequestBody @Valid BasicPlace basicPlace,@PathVariable Long id,
                                               Principal principal) {
        Optional<BasicPlace> oldPlace = basicPlaceService.getPlaceById(id);
        if(!oldPlace.isPresent())
            throw new ResourceNotFoundException(ResourceNotFoundException.DEFAULT_MESSAGE + id);

        this.basicPlaceService.updatePlace(basicPlace,id,principal);
        return new ResponseEntity<>(basicPlace,new HttpHeaders(),HttpStatus.OK);
    }


}
