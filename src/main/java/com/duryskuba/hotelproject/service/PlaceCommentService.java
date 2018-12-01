package com.duryskuba.hotelproject.service;

import com.duryskuba.hotelproject.exception.AuthException;
import com.duryskuba.hotelproject.model.BasicPerson;
import com.duryskuba.hotelproject.model.BasicPlace;
import com.duryskuba.hotelproject.model.PlaceComment;
import com.duryskuba.hotelproject.repository.PlaceCommentRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PlaceCommentService {

    private PlaceCommentRepository placeCommentRepository;
    private PersonService personService;
    private AuthenticationService authenticationService;

    public PlaceCommentService(PlaceCommentRepository placeCommentRepository,
                               PersonService personService,
                               AuthenticationService authenticationService) {
        this.placeCommentRepository = placeCommentRepository;
        this.personService = personService;
        this.authenticationService = authenticationService;
    }

    public List<PlaceComment> getPlaceCommentsByHotelId(final Long hotelId) {
        return this.placeCommentRepository.getAllCommentsByHotelId(hotelId);
    }

    public void createNewComment(PlaceComment comment,final BasicPlace place,final BasicPerson person) {
        comment.setStatus('A');
        comment.setCreationDate(LocalDateTime.now());
        comment.setBasicPerson(person);
        comment.setBasicPlace(place);

        this.placeCommentRepository.save(comment);
    }

    public void deleteComment(Long commentId) {
        this.placeCommentRepository.delete(
                this.placeCommentRepository.getOne(commentId));
    }

    public Optional<PlaceComment> getPlaceCommentById(Long id) {
        return this.placeCommentRepository.findById(id);
    }

    public List<PlaceComment> getCommentsOfPerson(Long userId) {
        return this.placeCommentRepository.getAllCommentsOfPerson(userId);
    }
}
