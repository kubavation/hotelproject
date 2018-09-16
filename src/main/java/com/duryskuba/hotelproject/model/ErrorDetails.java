package com.duryskuba.hotelproject.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorDetails {
    private LocalDateTime date;
    private String message;
    private String details;

    public ErrorDetails(LocalDateTime date, String message, String details) {
        this.date = date;
        this.message = message;
        this.details = details;
    }
}
