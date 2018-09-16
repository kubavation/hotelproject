package com.duryskuba.hotelproject.controller;

import com.duryskuba.hotelproject.exception.ResourceNotFoundException;
import com.duryskuba.hotelproject.model.BasicPerson;
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
    @GetMapping("/persons")
    public List<BasicPerson> showAllPeople() {
        return personService.showAllActivePeople();
    }

    @GetMapping("/person/{id}")
    public BasicPerson showPersonById(@PathVariable Long id) {
        return personService.getPersonById(id).orElseThrow(
                () -> new ResourceNotFoundException(ResourceNotFoundException.DEFAULT_MESSAGE + " " + id.toString())
        );
    }

    @PostMapping("/persons")
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

    @GetMapping("/persons/username/{username}")
    public List<BasicPerson> getPeopleWhereUsernameLike(@PathVariable String username) {
        return personService.getPeopleWhereUsernameLike(username);
    }
}
