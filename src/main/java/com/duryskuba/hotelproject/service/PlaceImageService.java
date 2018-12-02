package com.duryskuba.hotelproject.service;

import com.duryskuba.hotelproject.exception.ResourceNotFoundException;
import com.duryskuba.hotelproject.model.BasicPlace;
import com.duryskuba.hotelproject.model.PlaceImage;
import com.duryskuba.hotelproject.repository.PlaceImageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

@Service
public class PlaceImageService {

    private PlaceImageRepository placeImageRepository;
    private BasicPlaceService placeService;

    @Value("${images.url}")
    private String imgUrl;

    public PlaceImageService(PlaceImageRepository placeImageRepository, BasicPlaceService placeService) {
        this.placeImageRepository = placeImageRepository;
        this.placeService = placeService;
    }

    public List<PlaceImage> getImagesByPlaceId(final Long placeId) {
        return this.placeImageRepository.findPlaceImagesByPlaceId(placeId);
    }


    @Transactional
    public void addNewImage(byte[] image, final BasicPlace place) {
        PlaceImage placeImage = new PlaceImage();
        placeImage.setPlace(place);

        this.placeService.getPlaceById(place.getId())
                .map(p ->
                    this.placeService.updateImagesAndSave(p,placeImage)
                ).orElseThrow(ResourceNotFoundException::new);

        this.placeImageRepository.save(placeImage);
        this.placeImageRepository.flush();

        if(Files.notExists(Paths.get(imgUrl + place.getId())))
            new File(imgUrl + place.getId()).mkdir();

        final Long imgId = placeImage.getId();
        File file = new File(imgUrl + place.getId() + "\\"  +imgId + ".jpg");

        try {
            ImageIO.write(ImageIO.read(new ByteArrayInputStream(image)),
                    "jpg", file);
        } catch(IOException ex) {
            throw new IllegalArgumentException(ex);
        }

    }


}
