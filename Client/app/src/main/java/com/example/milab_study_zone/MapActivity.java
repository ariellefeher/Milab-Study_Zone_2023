package com.example.milab_study_zone;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng entreLocation = new LatLng(32.17708300967818, 34.83741586612592);
        LatLng lawLocation = new LatLng(32.17539745249123, 34.83469771667703);
        LatLng psychLocation = new LatLng(32.175146815029606, 34.83557905684961);
        LatLng compsciLocation = new LatLng(32.17679559749888, 34.8352507496006);
        LatLng ivcherLocation = new LatLng(32.17732583354259, 34.83602497318829);
        LatLng hangarLocation = new LatLng(32.17628784318536, 34.83731263693219);
        LatLng libraryLocation = new LatLng(32.175806589096375, 34.83649321454971);
        LatLng govLocation = new LatLng(32.17553765186704, 34.83542852289298);

        MarkerOptions markerOptions1 = new MarkerOptions()
                .position(entreLocation)
                .title("Entrepreneurship Building");
        MarkerOptions markerOptions2 = new MarkerOptions()
                .position(lawLocation)
                .title("Law Building");
        MarkerOptions markerOptions3 = new MarkerOptions()
                .position(psychLocation)
                .title("Psychology Building");
        MarkerOptions markerOptions4 = new MarkerOptions()
                .position(compsciLocation)
                .title("Computer Science Building");
        MarkerOptions markerOptions5 = new MarkerOptions()
                .position(ivcherLocation)
                .title("Ivcher Auditorium");
        MarkerOptions markerOptions6 = new MarkerOptions()
                .position(hangarLocation)
                .title("Hangar");
        MarkerOptions markerOptions7 = new MarkerOptions()
                .position(libraryLocation)
                .title("Library");
        MarkerOptions markerOptions8 = new MarkerOptions()
                .position(govLocation)
                .title("Government Building");

        googleMap.addMarker(markerOptions1);
        googleMap.addMarker(markerOptions2);
        googleMap.addMarker(markerOptions3);
        googleMap.addMarker(markerOptions4);
        googleMap.addMarker(markerOptions5);
        googleMap.addMarker(markerOptions6);
        googleMap.addMarker(markerOptions7);
        googleMap.addMarker(markerOptions8);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(entreLocation);
        builder.include(lawLocation);
        builder.include(psychLocation);
        builder.include(compsciLocation);
        builder.include(ivcherLocation);
        builder.include(hangarLocation);
        builder.include(libraryLocation);
        builder.include(govLocation);
        LatLngBounds bounds = builder.build();

        int padding = 50; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        googleMap.moveCamera(cu);

        googleMap.setOnMarkerClickListener(this);

        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        // Launch a separate activity when the marker is clicked
        String title = marker.getTitle();
        switch (title) {
            case "Entrepreneurship Building":
                Intent intent1 = new Intent(this, EntreBuilding.class);
                startActivity(intent1);
                break;
            case "Law Building":
                Intent intent2 = new Intent(this, LawBuilding.class);
                startActivity(intent2);
                break;
            case "Psychology Building":
                Intent intent3 = new Intent(this, PsychBuilding.class);
                startActivity(intent3);
                break;
            case "Computer Science Building":
                Intent intent4 = new Intent(this, CompSciBuilding.class);
                startActivity(intent4);
                break;
            case "Ivcher":
                Intent intent5 = new Intent(this, IvcherBuilding.class);
                startActivity(intent5);
                break;
            case "Hangar":
                Intent intent6 = new Intent(this, HangarBuilding.class);
                startActivity(intent6);
                break;
            case "Library":
                Intent intent7 = new Intent(this, LibraryBuilding.class);
                startActivity(intent7);
                break;
            case "Government Building":
                Intent intent8 = new Intent(this, GovBuilding.class);
                startActivity(intent8);
                break;

        }
        return true;
    }
}
