package com.duryskuba.hotelproject.service;

import com.duryskuba.hotelproject.exception.ResourceNotFoundException;
import com.duryskuba.hotelproject.model.BasicPerson;
import com.duryskuba.hotelproject.model.UserPrincipal;
import com.duryskuba.hotelproject.repository.PersonRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {

    private PersonRepository personRepository;

    private BasicPerson basicPerson;

    public UserDetailsServiceImplementation(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<BasicPerson> userOptional = personRepository.findByUsername(username);

        if(!userOptional.isPresent())
            throw new UsernameNotFoundException(username);

        this.basicPerson = userOptional.get();
        return new UserPrincipal(userOptional.get());
    }

    public BasicPerson getLoggedPerson() {
        return this.basicPerson;
    }
}
