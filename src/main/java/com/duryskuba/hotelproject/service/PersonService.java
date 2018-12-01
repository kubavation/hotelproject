package com.duryskuba.hotelproject.service;

import com.duryskuba.hotelproject.email.EmailService;
import com.duryskuba.hotelproject.model.BasicPerson;
import com.duryskuba.hotelproject.model.PlaceComment;
import com.duryskuba.hotelproject.repository.PersonRepository;
import com.duryskuba.hotelproject.repository.RoleRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Basic;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class PersonService {

    private PersonRepository personRepository;
    private EmailService emailService;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public PersonService(PersonRepository personRepository,
                         EmailService emailService,
                         RoleRepository roleRepository,
                         BCryptPasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.emailService = emailService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public BasicPerson createNewPerson(BasicPerson person) {

        person.setConfirmed('N');
        person.setStatus('A');
        person.setCreationDate(LocalDateTime.now());
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRoles(
                new HashSet<>(Arrays.asList(
                        roleRepository.findByRole("USER"))
                )
        );
        this.personRepository.save(person);
//        this.emailService.sendMessageFromTemplate(
//                person.getEmail(),"Greetings!",new String[]{person.getUsername(),person.getEmail()});
        return person;
    }

    public void deletePerson(Long id) {
        this.personRepository.deletePerson(id);
    }

    public List<BasicPerson> showAllActivePeople() {
        //+zmien na aktywnych?
        return personRepository.findAll();
    }

    public Optional<BasicPerson> getPersonById(Long id) {
        return this.personRepository.findById(id);
    }

    public void updatePerson(@Valid BasicPerson person, Long id) {

        Character confirmation = this.personRepository.checkIfPersonIsConfirmed(id);
        if(confirmation == null || confirmation.equals('N'))
            person.setConfirmed('N');
        else
            person.setConfirmed('Y');

        // + data
        person.setId(id);
        person.setStatus('A');
        this.personRepository.save(person);
    }

    public List<BasicPerson> getPeopleWhereUsernameLike(String like) {
        return personRepository.findByUsernameContaining(like);
    }

    public Long getPersonIdByUsername(String username) {
        return this.personRepository.findPersonIdByUsername(username);
    }

    public Optional<BasicPerson> getPersonByUsername(String username) {
        return this.personRepository.findByUsername(username);
    }


    public void addComment(BasicPerson person, @Valid PlaceComment comment, List<PlaceComment> comments) {
        comments.add(comment);
        person.setPlaceComments(comments);
        this.personRepository.save(person);
    }

    public Optional<BasicPerson> getPersonByEmail(String email) {
        return this.personRepository.findByEmail(email);
    }

}
