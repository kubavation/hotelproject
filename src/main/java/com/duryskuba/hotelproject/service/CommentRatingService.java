package com.duryskuba.hotelproject.service;

import com.duryskuba.hotelproject.exception.AuthException;
import com.duryskuba.hotelproject.exception.ResourceAlreadyExistsException;
import com.duryskuba.hotelproject.model.BasicPerson;
import com.duryskuba.hotelproject.model.CommentRating;
import com.duryskuba.hotelproject.model.CommentRatingPK;
import com.duryskuba.hotelproject.repository.CommentRatingRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class CommentRatingService {

    private CommentRatingRepository commentRatingRepository;
    private PersonService personService;
    private AuthenticationService authenticationService;

    public CommentRatingService(CommentRatingRepository commentRatingRepository,
                                PersonService personService,
                                AuthenticationService authenticationService) {
        this.commentRatingRepository = commentRatingRepository;
        this.personService = personService;
        this.authenticationService = authenticationService;
    }

    public Integer getCommentRatingByCommentId(Long commentId) {
       return this.commentRatingRepository.getRatingOfComment(commentId)
                .stream().mapToInt(Integer::intValue).sum();
    }

    public void addNewRatingToComment(Long commentId, BasicPerson person) {
        //BasicPerson person = this.authenticationService.getCurrentlyLoggedPerson(principal);

        final Long personId = person.getId();
        if(checkIfPersonsRatingExists(commentId,personId))
            throw new ResourceAlreadyExistsException();

        CommentRating commentRating = new CommentRating();
        commentRating.setStatus('A');
        commentRating.setId(new CommentRatingPK(commentId,personId));
        commentRating.setRating(1);
        this.commentRatingRepository.save(commentRating);
    }

    private boolean checkIfPersonsRatingExists(Long commentId, Long personId) {
        return commentRatingRepository.ifRatingExists(commentId,personId) > 0;
    }


    public void deleteCommentRating(Long commentId, Principal principal) {

        BasicPerson person = this.authenticationService.getCurrentlyLoggedPerson(principal);
        this.commentRatingRepository.delete(
                commentRatingRepository.findByPk(commentId,person.getId())
        );

    }
}
