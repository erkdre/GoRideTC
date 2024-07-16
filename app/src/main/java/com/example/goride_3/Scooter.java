package com.example.goride_3;

import com.google.android.gms.maps.model.LatLng;

public class Scooter {
    private LatLng position;
    private String title;
    // Weitere Attribute je nach Bedarf

    public Scooter(LatLng position, String title) {
        this.position = position;
        this.title = title;
    }

    public LatLng getPosition() {
        return position;
    }

    public String getTitle() {
        return title;
    }
}
