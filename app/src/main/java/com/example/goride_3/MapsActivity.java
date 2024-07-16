package com.example.goride_3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<Scooter> scooterList; // Liste der Scooter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Erstelle eine Liste von Scootern (Beispiel)
        scooterList = new ArrayList<>();
        scooterList.add(new Scooter(new LatLng(51.5074, -0.1278), "Scooter 1"));
        scooterList.add(new Scooter(new LatLng(48, 11), "Scooter 2"));
        // Weitere Scooter hinzufügen nach Bedarf

        // Lade die Google Map Ansicht asynchron
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Marker für jeden Scooter hinzufügen
        for (Scooter scooter : scooterList) {
            mMap.addMarker(new MarkerOptions()
                    .position(scooter.getPosition())
                    .title(scooter.getTitle()));
        }

        // ClickListener für Marker setzen
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // Hier das Bottom Sheet Fragment öffnen und Informationen über den Scooter übergeben
                openBottomSheet(marker.getTitle()); // Beispiel: Titel des Markers übergeben
                return true;
            }
        });

        // Lade das Layout der Leiste
        View buttonBar = getLayoutInflater().inflate(R.layout.button_bar, null);

        // Füge die Leiste dem Hauptlayout hinzu
        // (hier wird angenommen, dass dein Hauptlayout in activity_maps.xml ein RelativeLayout ist)
        RelativeLayout mainLayout = findViewById(R.id.map_container); // Ersetze 'main_layout' mit der ID deines Hauptlayouts
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM); // Platziere die Leiste am unteren Rand
        mainLayout.addView(buttonBar, params);

        // Finde die Buttons in der Leiste
        Button button1 = buttonBar.findViewById(R.id.button1);
        Button button2 = buttonBar.findViewById(R.id.button2);

        // Setze OnClickListener für die Buttons
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Führe Aktion für Button 1 aus
                System.out.println("Button 1 geklickt!");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Führe Aktion für Button 2 aus
                System.out.println("Button 2 geklickt!");
            }
        });
    }

    private void openBottomSheet(String title) {
        // Hier das Bottom Sheet Fragment öffnen und Informationen übergeben
        BottomSheetFragment bottomSheetFragment = BottomSheetFragment.newInstance(title);
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }
}