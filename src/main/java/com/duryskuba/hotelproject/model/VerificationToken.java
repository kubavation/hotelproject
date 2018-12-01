package com.duryskuba.hotelproject.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "VERIFICATION_TOKEN")
@NoArgsConstructor
@Getter
@Setter
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;

    @Column(name = "EXPIRATION_DATE")
    private LocalDateTime expirationDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private BasicPerson person;

}
