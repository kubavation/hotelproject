package com.duryskuba.hotelproject.service;

import com.duryskuba.hotelproject.model.PlaceAddress;
import com.duryskuba.hotelproject.repository.PlaceAddressRepository;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class PlaceAddressService {

    private PlaceAddressRepository placeAddressRepository;

    public PlaceAddressService(final PlaceAddressRepository placeAddressRepository) {
        this.placeAddressRepository = placeAddressRepository;
    }

    public PlaceAddress createNewAddress(@Valid PlaceAddress placeAddress) {
        this.placeAddressRepository.save(placeAddress);
        return placeAddress;
    }

    public PlaceAddress createAddressFromParameters(final String country, final String city,
                final String street, final Integer placeNumber) {
        return new PlaceAddress(country,city,street,placeNumber);
    }
}
