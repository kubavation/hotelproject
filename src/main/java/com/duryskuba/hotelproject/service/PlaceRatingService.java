package com.duryskuba.hotelproject.service;

import com.duryskuba.hotelproject.exception.AuthException;
import com.duryskuba.hotelproject.exception.ResourceAlreadyExistsException;
import com.duryskuba.hotelproject.model.BasicPerson;
import com.duryskuba.hotelproject.model.PlaceRating;
import com.duryskuba.hotelproject.model.PlaceRatingPK;
import com.duryskuba.hotelproject.repository.PlaceRatingRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class PlaceRatingService {

    private PlaceRatingRepository placeRatingRepository;
    private PersonService personService;
    private AuthenticationService authenticationService;

    public PlaceRatingService(PlaceRatingRepository placeRatingRepository,
                              PersonService personService,
                              AuthenticationService authenticationService) {
        this.placeRatingRepository = placeRatingRepository;
        this.personService = personService;
        this.authenticationService = authenticationService;
    }

    public List<Integer> getRatingsByPlaceId(Long placeId) {
          return placeRatingRepository.getRatingsByPlaceId(placeId);
    }

    public Integer getCountOfRatingsByPlaceId(Long placeId) {
        return placeRatingRepository.getCountOfRatingsByPlaceId(placeId);
    }

    public Double calculateRatingOfPlace(Long placeId) {
        try {
            return getRatingsByPlaceId(placeId)
                   .stream().mapToDouble(i -> i)
                   .average().getAsDouble();
        }catch(Exception ex) {
            return 0.0;
        }
    }


    public void createNewRating(PlaceRating placeRating, Long placeId, BasicPerson user) {
        //BasicPerson person = this.authenticationService.getCurrentlyLoggedPerson(principal);

        final Long personId = user.getId();
        if(checkIfPersonsRatingExists(placeId,personId)) {
            PlaceRating oldPlaceRating = this.placeRatingRepository.getRatingByPk(placeId,personId);
            oldPlaceRating.setRating(placeRating.getRating());
            this.placeRatingRepository.save(oldPlaceRating);
        }
        else {    //throw new ResourceAlreadyExistsException(); //todo zmien na juz istnieje exception
            placeRating.setId(new PlaceRatingPK(placeId, personId));
            placeRating.setStatus('A');
            this.placeRatingRepository.save(placeRating);
        }
    }

    public boolean checkIfPersonsRatingExists(Long placeId, Long personId) {
        return placeRatingRepository.ifRatingExists(placeId,personId) > 0 ;
    }

    public void deleteRating(Long placeId, BasicPerson user) {
        //BasicPerson person = this.authenticationService.getCurrentlyLoggedPerson(principal);
        this.placeRatingRepository.delete(placeRatingRepository.getRatingByPk(placeId,user.getId())); //todo
    }
}
