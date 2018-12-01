package com.duryskuba.hotelproject.controller;

import com.duryskuba.hotelproject.exception.ResourceNotFoundException;
import com.duryskuba.hotelproject.model.BasicPerson;
import com.duryskuba.hotelproject.service.CoordinatesService;
import com.duryskuba.hotelproject.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class PersonController {

    private PersonService personService;


    public PersonController(final PersonService personService) {
        this.personService = personService;
    }

    //+ zmien na people?
    @GetMapping("/people")
    public List<BasicPerson> showAllPeople() {
        return personService.showAllActivePeople();
    }

    @GetMapping("/person/{id}")
    public BasicPerson showPersonById(@PathVariable Long id) {
        return personService.getPersonById(id).orElseThrow(
                () -> new ResourceNotFoundException(ResourceNotFoundException.DEFAULT_MESSAGE + " " + id.toString())
        );
    }

    @PostMapping("/people")
    public ResponseEntity<Object> createPerson(@Valid @RequestBody BasicPerson person) {
        this.personService.createNewPerson(person);
        return new ResponseEntity<>(person,new HttpHeaders(),HttpStatus.CREATED);
    }

    @DeleteMapping("/person/{id}")
    public ResponseEntity<Object> deletePerson(@PathVariable Long id) {
        this.personService.deletePerson(id);
        return new ResponseEntity<>(id,new HttpHeaders(),HttpStatus.OK);
    }

    @PutMapping("/person/{id}")
    public ResponseEntity<Object> updatePerson(@RequestBody @Valid BasicPerson person,@PathVariable Long id) {
        Optional<BasicPerson> oldPerson = personService.getPersonById(id);
        if(!oldPerson.isPresent())
            throw new ResourceNotFoundException(ResourceNotFoundException.DEFAULT_MESSAGE + id);

        this.personService.updatePerson(person,id);
        return new ResponseEntity<>(person,new HttpHeaders(),HttpStatus.OK);
    }

//    @GetMapping("/people/username/like/{username}")
//    public List<BasicPerson> getPeopleWhereUsernameLike(@PathVariable String username) {
//        return personService.getPeopleWhereUsernameLike(username);
//    }

      @GetMapping("/people/username/{username}")
      public boolean ifPersonByUsernameExists(@PathVariable String username) {
        return this.personService.getPersonByUsername(username).isPresent();
      }


    @GetMapping("/people/email/{email:.+}")
    public boolean getPersonByEmailExists(@PathVariable String email) {
        System.out.println(email + "   " + this.personService.getPersonByEmail(email).isPresent());
        return this.personService.getPersonByEmail(email).isPresent();
    }


    @GetMapping("/person/by/username/{username}")
    public BasicPerson getPersonByUsername(@PathVariable String username) {
        return this.personService.getPersonByUsername(username)
                .orElseThrow(ResourceNotFoundException::new);
    }


}
