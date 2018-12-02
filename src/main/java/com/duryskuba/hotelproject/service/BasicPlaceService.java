package com.duryskuba.hotelproject.service;

import com.duryskuba.hotelproject.exception.AuthException;
import com.duryskuba.hotelproject.exception.ResourceNotFoundException;
import com.duryskuba.hotelproject.model.*;
import com.duryskuba.hotelproject.repository.BasicPlaceRepository;
import com.duryskuba.hotelproject.repository.PlaceAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class BasicPlaceService {

    private BasicPlaceRepository basicPlaceRepository;
    private PlaceAddressService placeAddressService;
    private PersonService personService;
    private PlaceCommentService placeCommentService;
    private AuthenticationService authenticationService;

    public BasicPlaceService(BasicPlaceRepository basicPlaceRepository,
                             PlaceAddressService placeAddressService,
                             PersonService personService,
                             PlaceCommentService placeCommentService,
                             AuthenticationService authenticationService) {
        this.basicPlaceRepository = basicPlaceRepository;
        this.placeAddressService = placeAddressService;
        this.personService = personService;
        this.placeCommentService = placeCommentService;
        this.authenticationService = authenticationService;
    }

    public List<BasicPlace> getAllPlaces() {
        return basicPlaceRepository.findAll();
    }

    public Optional<BasicPlace> getPlaceById(Long id) {
        return basicPlaceRepository.findById(id);
    }

    @Transactional
    public void createNewPlace(BasicPlace place, BasicPerson person) {//Principal principal) { //?

        //BasicPerson person = this.authenticationService.getCurrentlyLoggedPerson(principal);
        System.out.println(place == null);
        final PlaceAddress placeAddress = place.getPlaceAddress();
        System.out.println(placeAddress == null ? "null2" : placeAddress.getCity());

        PlaceAddress newPlaceAddress = this.placeAddressService.createAddressFromParameters(placeAddress.getCountry(),
                                        placeAddress.getCity(),placeAddress.getStreet(),placeAddress.getPlaceNumber());

        this.placeAddressService.createNewAddress(newPlaceAddress);
        place.setStatus('A');
        place.setBasicPerson(person);
        place.setPlaceAddress(newPlaceAddress);

        this.basicPlaceRepository.save(place);
    }

    public void deletePlace(Long id) {
        this.basicPlaceRepository.deletePlace(id);
    }

    public void updatePlace(@Valid BasicPlace basicPlace, Long id,BasicPerson loggedPerson) {

        final Optional<BasicPlace> oldBasicPlace = this.basicPlaceRepository.findById(id);
        final Optional<BasicPerson> actualUser = this.personService.getPersonByUsername(loggedPerson.getUsername()); // todo wyjebac?

        final PlaceAddress oldPlaceAddress = oldBasicPlace.get().getPlaceAddress();

        PlaceAddress newPlaceAddress = basicPlace.getPlaceAddress();
        newPlaceAddress.setId(oldPlaceAddress.getId());
        this.placeAddressService.createNewAddress(newPlaceAddress);

        //place
        //todo +placeComments ??3 + PLACE IMAGES
        basicPlace.setPlaceAddress(newPlaceAddress);
        basicPlace.setId(id);
        basicPlace.setStatus('A');
        basicPlace.setBasicPerson(actualUser.get());
        basicPlace.setPlaceComments(oldBasicPlace.get().getPlaceComments());
        this.basicPlaceRepository.save(basicPlace);
    }

    @Transactional
    public void addComment(@Valid PlaceComment comment, BasicPlace basicPlace, BasicPerson person) {
        this.placeCommentService.createNewComment(comment,basicPlace,person);
        this.addNewComment(basicPlace,comment);
        this.personService.addComment(person, comment,
             this.placeCommentService.getCommentsOfPerson(person.getId()));
    }

    private void addNewComment(BasicPlace basicPlace, @Valid PlaceComment comment) {
        List<PlaceComment> comments = basicPlace.getPlaceComments();
        comments.add(comment);
        basicPlace.setPlaceComments(comments);
        this.basicPlaceRepository.save(basicPlace);
    }


    public BasicPlace updateImagesAndSave(BasicPlace place, PlaceImage placeImage) {
        place.getImages().add(placeImage);
        this.basicPlaceRepository.save(place);
        return place;
    }


}
