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
import java.nio.file.Path;
import java.nio.file.Paths;
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


    public void test() {
        System.out.println("TEXT");
    }

    @Transactional
    public void addNewImage(byte[] image, final BasicPlace place) {

        System.out.println("XD22");
        PlaceImage placeImage = new PlaceImage();
        //placeImage.setPlace(place);

        System.out.println("XD3");

        this.placeService.getPlaceById(place.getId())
                .map(p ->
                    this.placeService.updateImagesAndSave(p,placeImage)
                ).orElseThrow(ResourceNotFoundException::new);

        this.placeImageRepository.save(placeImage);
        this.placeImageRepository.flush();

        // todo sprawdz czy jest folder jesli nie to utworz
        if(Files.notExists(Paths.get("/images/" + place.getId()))) {
            new File("/images/" + place.getId()).mkdir();
        }

        final Long imgId = placeImage.getId();
        File file = new File("/images/" + place.getId() + "/"  +imgId + ".jpg");

        try {
            ImageIO.write(ImageIO.read(new ByteArrayInputStream(image)),
                    "jpg", file);
        }catch(IOException ex) {
            System.out.println(ex);
            throw new IllegalArgumentException(ex);
        }
        // todo logika tworzenia file
        System.out.println("END");

    }


}
