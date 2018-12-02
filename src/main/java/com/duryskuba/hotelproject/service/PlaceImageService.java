package com.duryskuba.hotelproject.service;

import com.duryskuba.hotelproject.exception.ResourceNotFoundException;
import com.duryskuba.hotelproject.model.BasicPlace;
import com.duryskuba.hotelproject.model.PlaceImage;
import com.duryskuba.hotelproject.repository.PlaceImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
public class PlaceImageService {

    private PlaceImageRepository placeImageRepository;
    private BasicPlaceService placeService;


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

        final Long imgId = placeImage.getId();

        File file = new File(imgId + ".jpg");

        // todo sprawdz czy jest folder jesli nie to utworz
        //if(Files.exists())

        try {
            ImageIO.write(ImageIO.read(new ByteArrayInputStream(image)),
                    "jpg", file);
        }catch(IOException ex) {
            throw new IllegalArgumentException(ex);
        }
        // todo logika tworzenia file

    }


}
