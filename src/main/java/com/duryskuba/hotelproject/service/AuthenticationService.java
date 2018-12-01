package com.duryskuba.hotelproject.service;

import com.duryskuba.hotelproject.exception.AuthException;
import com.duryskuba.hotelproject.model.BasicPerson;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class AuthenticationService {

    private PersonService personService;

    public AuthenticationService(PersonService personService) {
        this.personService = personService;
    }

    public BasicPerson getCurrentlyLoggedPerson(Principal principal) {
        return this.personService.getPersonByUsername(
                principal != null ? principal.getName() : "").orElseThrow(AuthException::new);
    }

    public String getNameOfCurrentlyLoggedUser() {
        return ((Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getName();
    }

}
