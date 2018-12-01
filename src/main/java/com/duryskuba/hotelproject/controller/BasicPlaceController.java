package com.duryskuba.hotelproject.controller;

import com.duryskuba.hotelproject.exception.AuthException;
import com.duryskuba.hotelproject.exception.ResourceNotFoundException;
import com.duryskuba.hotelproject.model.*;
import com.duryskuba.hotelproject.service.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
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
    private PlaceCommentService placeCommentService;
    private PlaceRatingService placeRatingService;
    private CommentRatingService commentRatingService;

    @Autowired
    private CoordinatesService coordinatesService;

    @Autowired
    private UserDetailsServiceImplementation userDetailsServiceImplementation;

    public BasicPlaceController(final BasicPlaceService basicPlaceService,
                                final PlaceCommentService placeCommentService,
                                final PlaceRatingService placeRatingService,
                                final CommentRatingService commentRatingService) {

        this.basicPlaceService = basicPlaceService;
        this.placeCommentService = placeCommentService;
        this.placeRatingService = placeRatingService;
        this.commentRatingService = commentRatingService;
    }


    @GetMapping("/place/{id}/coords")
    public double[] getPlaceCoords(@PathVariable  Long id) {
        System.out.println("COORDS TEST");
        Optional<BasicPlace> placeOption = this.basicPlaceService.getPlaceById(id);
        if(!placeOption.isPresent())
            throw new ResourceNotFoundException(ResourceNotFoundException.DEFAULT_MESSAGE + " " +  id.toString());
        return this.coordinatesService.calculateCoords(placeOption.get());
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
    public ResponseEntity<Object> createNewPlace(@RequestBody BasicPlace place) {
        System.out.println("elo");
        BasicPerson pe = this.userDetailsServiceImplementation.getLoggedPerson();
        System.out.println(pe);
        System.out.println("elo2");
        this.basicPlaceService.createNewPlace(place,pe);
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


    ///

    @GetMapping("/place/{id}/comments")
    public List<PlaceComment> getPlaceComments(@PathVariable Long id) {
        return this.placeCommentService.getPlaceCommentsByHotelId(id);
    }

    @GetMapping("/place/{id}/comment/{commentId}")
    public PlaceComment getPlaceCommentById(@PathVariable("commentId") Long id) {
        return this.placeCommentService.getPlaceCommentById(id)
                    .orElseThrow(ResourceNotFoundException::new);
    }



    @PostMapping("/place/{id}/comments")
    public ResponseEntity<Object> createNewCommentForPlace(@RequestBody @Valid PlaceComment comment,
                                                   @PathVariable Long id) {

        Optional<BasicPlace> oldPlace = basicPlaceService.getPlaceById(id);
        if(!oldPlace.isPresent())
            throw new ResourceNotFoundException(ResourceNotFoundException.DEFAULT_MESSAGE + id);

        BasicPerson loggedPerson = this.userDetailsServiceImplementation.getLoggedPerson();
        this.basicPlaceService.addComment(comment, oldPlace.get(), loggedPerson);

        return new ResponseEntity<>(comment,new HttpHeaders(),HttpStatus.CREATED);
    }


    @DeleteMapping("/place/{place_id}/comments/{commentId}")
    public ResponseEntity<Object> deletePlaceComment(@PathVariable("commentId") Long id) {
        BasicPerson loggedPerson = this.userDetailsServiceImplementation.getLoggedPerson();

        /*
        + trzeba sprawdzic czy komentarz nalezy do autora !!! todo
         */

        this.placeCommentService.deleteComment(id);
        return new ResponseEntity<>(id,new HttpHeaders(),HttpStatus.OK);
    }



    //rating
    @GetMapping("/place/{placeId}/rating")
    public Double getRatingOfPlace(@PathVariable("placeId")  Long placeId) {

        Optional<BasicPlace> optionalPlace = basicPlaceService.getPlaceById(placeId);
        if(!optionalPlace.isPresent())
            throw new ResourceNotFoundException(ResourceNotFoundException.DEFAULT_MESSAGE + " " +  placeId.toString());

        return placeRatingService.calculateRatingOfPlace(placeId);
    }

    @GetMapping("/place/{placeId}/rating/count")
    public int getCountOfRatingByPlace(@PathVariable("placeId")  Long placeId) {

        Optional<BasicPlace> optionalPlace = basicPlaceService.getPlaceById(placeId);
        if(!optionalPlace.isPresent())
            throw new ResourceNotFoundException(ResourceNotFoundException.DEFAULT_MESSAGE + " " +  placeId.toString());

        return placeRatingService.getCountOfRatingsByPlaceId(placeId);
    }

    @GetMapping("/place/{placeId}/rating/exists")
    public boolean checkIfUserRatingAlreadyExists(@PathVariable("placeId")  Long placeId) {
        return this.placeRatingService
                .checkIfPersonsRatingExists(placeId,
                this.userDetailsServiceImplementation.getLoggedPerson() != null
                ? this.userDetailsServiceImplementation.getLoggedPerson().getId()
                : null);
    }


    @PostMapping("/place/{placeId}/rating")
    public ResponseEntity<Object> createNewPlaceRating(@RequestBody PlaceRating placeRating,
                                                       @PathVariable("placeId")  Long placeId) {

        Optional<BasicPlace> optionalPlace = basicPlaceService.getPlaceById(placeId);
        if(!optionalPlace.isPresent())
            throw new ResourceNotFoundException(ResourceNotFoundException.DEFAULT_MESSAGE + " " +  placeId.toString());

        BasicPerson loggedPerson = this.userDetailsServiceImplementation.getLoggedPerson();
        this.placeRatingService.createNewRating(placeRating,placeId,loggedPerson);
        return new ResponseEntity<>(placeRating,new HttpHeaders(),HttpStatus.CREATED);
    }

    @DeleteMapping("/place/{placeId}/rating")
    public ResponseEntity<Object> deleteRating(@PathVariable Long placeId) {
        BasicPerson loggedPerson = this.userDetailsServiceImplementation.getLoggedPerson();
        this.placeRatingService.deleteRating(placeId,loggedPerson); //todo + sprawdzanie czy to twoj glos??!
        return new ResponseEntity<>(placeId,new HttpHeaders(),HttpStatus.OK);
    }



    //commentRating

    @GetMapping("/place/{placeId}/comment/{commentId}/rating")
    public Integer getRatingOfComment(@PathVariable("commentId") Long commentId) {
        return this.commentRatingService.getCommentRatingByCommentId(commentId);
    }

    @PostMapping("/place/{placeId}/comment/{commentId}/rating")
    public ResponseEntity<Object> addCommentRating(@PathVariable("commentId") Long commentId) {

        Optional<PlaceComment> placeComment = this.placeCommentService.getPlaceCommentById(commentId);
        if(!placeComment.isPresent())
            throw new ResourceNotFoundException(ResourceNotFoundException.DEFAULT_MESSAGE + " " +  commentId.toString());

        BasicPerson loggedPerson = this.userDetailsServiceImplementation.getLoggedPerson();
        this.commentRatingService.addNewRatingToComment(commentId,loggedPerson);

        return new ResponseEntity<>(commentId,new HttpHeaders(),HttpStatus.CREATED);
    }


    @DeleteMapping("/place/{placeId}/comment/{commentId}/rating")
    public ResponseEntity<Object> deleteCommentRating(@PathVariable("commentId") Long commentId,
                                                   Principal principal) {

        Optional<PlaceComment> placeComment = this.placeCommentService.getPlaceCommentById(commentId);
        if(!placeComment.isPresent())
            throw new ResourceNotFoundException(ResourceNotFoundException.DEFAULT_MESSAGE + " " +  commentId.toString());

        this.commentRatingService.deleteCommentRating(commentId,principal);

        return new ResponseEntity<>(commentId,new HttpHeaders(),HttpStatus.OK);
    }
}
