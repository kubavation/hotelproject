package com.duryskuba.hotelproject.service;

import com.duryskuba.hotelproject.exception.ResourceNotFoundException;
import com.duryskuba.hotelproject.model.BasicPerson;
import com.duryskuba.hotelproject.model.BasicPlace;
import com.duryskuba.hotelproject.model.PlaceAddress;
import com.duryskuba.hotelproject.repository.BasicPlaceRepository;
import com.duryskuba.hotelproject.repository.PlaceAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class BasicPlaceService {

    private BasicPlaceRepository basicPlaceRepository;

    @Autowired
    private PlaceAddressService placeAddressService;

    @Autowired
    private PersonService personService;

    public BasicPlaceService(final BasicPlaceRepository basicPlaceRepository) {
        this.basicPlaceRepository = basicPlaceRepository;
    }


    public List<BasicPlace> getAllPlaces() {
        return basicPlaceRepository.findAll();
    }

    public Optional<BasicPlace> getPlaceById(Long id) {
        return basicPlaceRepository.findById(id);
    }

    @Transactional
    public void createNewPlace(BasicPlace place, Principal principal) {
        String username = principal.getName();
        BasicPerson person = this.personService.getPersonByUsername(username)
                .orElseThrow(UnsupportedOperationException::new); // zmien na log in exception?)

        final PlaceAddress placeAddress = place.getPlaceAddress();

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

    public void updatePlace(@Valid BasicPlace basicPlace, Long id, Principal principal) {

        final Optional<BasicPlace> oldBasicPlace = this.basicPlaceRepository.findById(id);
        final Optional<BasicPerson> actualUser = this.personService.getPersonByUsername(principal.getName());

        //Address
        final PlaceAddress oldPlaceAddress = oldBasicPlace.get().getPlaceAddress();

        PlaceAddress newPlaceAddress = basicPlace.getPlaceAddress();
        newPlaceAddress.setId(oldPlaceAddress.getId());
        this.placeAddressService.createNewAddress(newPlaceAddress);

        //place
        //+placeComments
        basicPlace.setPlaceAddress(newPlaceAddress);
        basicPlace.setId(id);
        basicPlace.setStatus('A');
        basicPlace.setBasicPerson(actualUser.get());
        this.basicPlaceRepository.save(basicPlace);
    }
}
