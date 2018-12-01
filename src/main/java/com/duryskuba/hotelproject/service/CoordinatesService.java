package com.duryskuba.hotelproject.service;

import com.duryskuba.hotelproject.model.BasicPlace;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.io.IOException;

@Service
public class CoordinatesService {

    private double lat;
    private double lan;


    private String countryName;
    private String cityName;
    private String street;
    private int placeNumber;


    public CoordinatesService init(String countryName, String cityName, String street, int placeNumber) {
        this.countryName = countryName;
        this.cityName = cityName;
        this.street = street;
        this.placeNumber = placeNumber;

        return this;
    }

    public double[] calculateCoords(final BasicPlace place) {
        WebClient client  = new WebClient(BrowserVersion.CHROME);
        HtmlPage page = null;
        System.out.println("-----1");
        try {
            page = client.getPage("http://gpx-poi.com");
        } catch (IOException e) {
            throw new IllegalArgumentException("Wrong url");
        }

        final HtmlForm form = page.getHtmlElementById("gpx");
        final HtmlTextInput cityField = form.getInputByName("city");
        final HtmlTextInput countryField = form.getInputByName("country");
        final HtmlTextInput straddrField = form.getInputByName("staddr");

        System.out.println("-----2");
        cityField.setText(place.getPlaceAddress().getCity());
        countryField.setText(place.getPlaceAddress().getCountry());
        straddrField.setText(place.getPlaceAddress().getStreet() + " " + place.getPlaceAddress().getPlaceNumber());

        System.out.println("-----3");
        HtmlButtonInput in = form.getInputByValue("Go!");
        try {
            in.click();
            client.waitForBackgroundJavaScript(900);
        } catch (IOException e) {
            throw new IllegalArgumentException("coords failed");
        }

        System.out.println("-----4");
        final HtmlTextInput lngField = form.getInputByName("lng");
        final HtmlTextInput latField = form.getInputByName("lat");

        System.out.println("-----5");
        this.lan = Double.parseDouble(lngField.getText());

        System.out.println("-----6");
        this.lat = Double.parseDouble(latField.getText());

        System.out.println("-----7");
        return new double[] { this.lat, this.lan };
    }



}

