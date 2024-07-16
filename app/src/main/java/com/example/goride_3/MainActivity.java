package com.example.goride_3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap gMap;
    private List<LatLng> scooterPositions; // Liste der Scooter-Positionen
    private ProgressBar progressBar;
    private ImageView loadingImage;
    private FrameLayout mapContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        loadingImage = findViewById(R.id.loadingImage);
        mapContainer = findViewById(R.id.map_container);

        progressBar.setVisibility(View.VISIBLE); // Zeige den Ladescreen
        loadingImage.setVisibility(View.VISIBLE);

        // Definiere die Scooter-Positionen
        scooterPositions = new ArrayList<>();
        scooterPositions.add(new LatLng(48.1537651, 11.5638620));
        scooterPositions.add(new LatLng(48.1512259, 11.5643539));
        scooterPositions.add(new LatLng(48.1482110, 11.5622752));
        scooterPositions.add(new LatLng(48.1477367, 11.5679528));
        scooterPositions.add(new LatLng(48.1543836, 11.5679839));
        scooterPositions.add(new LatLng(48.1478032, 11.5676165));
        scooterPositions.add(new LatLng(48.1573418, 11.5621853));
        scooterPositions.add(new LatLng(48.1512219, 11.5583132));
        scooterPositions.add(new LatLng(48.1511226, 11.5731814));
        scooterPositions.add(new LatLng(48.1435744, 11.5653138));
        scooterPositions.add(new LatLng(48.1436608, 11.5734546));



        // Warte für einen Moment, bevor die Karte geladen wird (Simuliere Ladezeit)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Lade die Karte
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.id_map);
                mapFragment.getMapAsync(MainActivity.this);
            }
        }, 2000); // Beispiel: Warte 2 Sekunden, bevor die Karte geladen wird




    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;

        // Verstecke den Ladescreen, da die Karte bereit ist
        progressBar.setVisibility(View.GONE);
        loadingImage.setVisibility(View.GONE);
        mapContainer.setVisibility(View.VISIBLE);

        // Definierter Standort (Beispiel: Ein persönlich festgelegter Standort)
        LatLng definedLocation = new LatLng(48.1510281, 11.5651156);
        float zoomLevel = 16.0f; // Zoom Level

        // Kamera auf den definierten Standort bewegen
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(definedLocation, zoomLevel));

        // Marker für den definierten Standort setzen und Größe anpassen
        MarkerOptions markerOptions = new MarkerOptions()
                .position(definedLocation)
                .title("Mein Standort")
                .icon(BitmapDescriptorFactory.fromBitmap(scaleBitmap(R.drawable.blue_dot_icon, 0.13f))) // Blue dots Icon verwenden und skalieren
                .anchor(0.5f, 0.5f); // Mittelpunkt des Icons

        gMap.addMarker(markerOptions);

        // Setze den benutzerdefinierten Stil für die Karte
        setMapStyle(gMap);

        // Iteriere durch die Scooter-Positionen und füge Marker hinzu
        for (LatLng position : scooterPositions) {
            MarkerOptions scooterMarkerOptions = new MarkerOptions()
                    .position(position)
                    .title("Scooter")
                    .icon(BitmapDescriptorFactory.fromBitmap(scaleBitmap(R.drawable.scooter_icon, 0.06f))) // Scooter Icon verwenden und skalieren
                    .anchor(0.5f, 0.5f); // Mittelpunkt des Icons

            gMap.addMarker(scooterMarkerOptions);
        }

        // Marker Klick-Listener, um das Bottom Sheet anzuzeigen
        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // Bottom Sheet anzeigen
                BottomSheetFragment bottomSheet = new BottomSheetFragment();
                bottomSheet.show(getSupportFragmentManager(), "BottomSheetFragment");
                return true; // Event wurde behandelt
            }
        });

        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // Überprüfe, ob der Marker ein Scooter ist (du musst möglicherweise den Titel oder eine andere Eigenschaft des Markers verwenden, um dies zu bestimmen)
                if (marker.getTitle().equals("Scooter")) {
                    // Bottom Sheet anzeigen und den Titel des Scooters übergeben
                    BottomSheetFragment bottomSheet = BottomSheetFragment.newInstance(marker.getTitle());
                    bottomSheet.show(getSupportFragmentManager(), "BottomSheetFragment");
                    return true; // Event wurde behandelt
                }
                return false; // Event wurde nicht behandelt (andere Marker können weiterhin Standardverhalten haben)
            }
        });
    }

    private void setMapStyle(GoogleMap googleMap) {
        // Benutzerdefinierter Kartenstil
        MapStyleOptions styleOptions = new MapStyleOptions(
                "[\n" +
                        "  {\n" +
                        "    \"featureType\": \"landscape.man_made\",\n" +
                        "    \"stylers\": [\n" +
                        "      { \"color\": \"#d3e5ee\" },\n" +
                        "      { \"saturation\": -50 },\n" +
                        "      { \"lightness\": -15 }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"featureType\": \"poi\",\n" +
                        "    \"stylers\": [\n" +
                        "      { \"visibility\": \"off\" }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"featureType\": \"road.arterial\",\n" +
                        "    \"elementType\": \"geometry\",\n" +
                        "    \"stylers\": [\n" +
                        "      { \"color\": \"#e5ebeb\" },\n" +
                        "      { \"lightness\": -10 }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"featureType\": \"road.highway\",\n" +
                        "    \"stylers\": [\n" +
                        "      { \"color\": \"#a4adb2\" }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"featureType\": \"road.local\",\n" +
                        "    \"elementType\": \"geometry\",\n" +
                        "    \"stylers\": [\n" +
                        "      { \"color\": \"#e5ebeb\" },\n" +
                        "      { \"lightness\": -10 }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"featureType\": \"transit\",\n" +
                        "    \"stylers\": [\n" +
                        "      { \"visibility\": \"off\" }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"featureType\": \"water\",\n" +
                        "    \"stylers\": [\n" +
                        "      { \"color\": \"#3b89b0\" },\n" +
                        "      { \"saturation\": -40 },\n" +
                        "      { \"lightness\": 25 }\n" +
                        "    ]\n" +
                        "  }\n" +
                        "]");
        googleMap.setMapStyle(styleOptions);
    }

    // Skalieren des Bitmaps
    private Bitmap scaleBitmap(int resId, float scaleFactor) {
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), resId);
        int width = Math.round(originalBitmap.getWidth() * scaleFactor);
        int height = Math.round(originalBitmap.getHeight() * scaleFactor);
        return Bitmap.createScaledBitmap(originalBitmap, width, height, false);
    }
}
