package com.duryskuba.hotelproject.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "BASIC_PERSON")
@Getter
@Setter
@NoArgsConstructor
public class BasicPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FIRST_NAME")
    @NotNull
    private String firstName;

    @Column(name = "LAST_NAME")
    @NotNull
    private String lastName;

    @NotNull
    private String city;

    @NotNull
    private String country;

    @Email
    @NotNull
    private String email;

    @Pattern(regexp = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}")
    private String phone;

    @NotNull
    @Size(min = 6,max = 50)
    @Column(unique = true)
    private String username;

    @NotNull // dodaj regex na cyfre
    private String password;

    private Integer age;

    private Character sex;

    @Column(name = "CREATION_DATE")
    private LocalDateTime creationDate;

    //a lub h
    private Character status;

    private Character confirmed;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "basicPerson")
    private List<PlaceComment> placeComments;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
               joinColumns = @JoinColumn(name = "user_id"),
               inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

}
