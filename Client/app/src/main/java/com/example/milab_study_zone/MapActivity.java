package com.example.milab_study_zone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

import java.util.Objects;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private MapView mapView;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");

        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        Button backButton = findViewById(R.id.backButtonMap);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng entreLocation = new LatLng(32.17708300967818, 34.83741586612592);
        LatLng lawLocation = new LatLng(32.17539745249123, 34.83469771667703);
        LatLng psychLocation = new LatLng(32.175146815029606, 34.83557905684961);
        LatLng compsciLocation = new LatLng(32.17679559749888, 34.8352507496006);
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
                .position(hangarLocation)
                .title("Hangar");
        MarkerOptions markerOptions6 = new MarkerOptions()
                .position(libraryLocation)
                .title("Library");
        MarkerOptions markerOptions7 = new MarkerOptions()
                .position(govLocation)
                .title("Government Building");

        googleMap.addMarker(markerOptions1);
        googleMap.addMarker(markerOptions2);
        googleMap.addMarker(markerOptions3);
        googleMap.addMarker(markerOptions4);
        googleMap.addMarker(markerOptions5);
        googleMap.addMarker(markerOptions6);
        googleMap.addMarker(markerOptions7);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(entreLocation);
        builder.include(lawLocation);
        builder.include(psychLocation);
        builder.include(compsciLocation);
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
                intent1.putExtra("username", username);
                intent1.putExtra("password", password);
                startActivity(intent1);
                break;
            case "Law Building":
                Intent intent2 = new Intent(this, LawBuilding.class);
                intent2.putExtra("username", username);
                intent2.putExtra("password", password);
                startActivity(intent2);
                break;
            case "Psychology Building":
                Intent intent3 = new Intent(this, PsychBuilding.class);
                intent3.putExtra("username", username);
                intent3.putExtra("password", password);
                startActivity(intent3);
                break;
            case "Computer Science Building":
                Intent intent4 = new Intent(this, CompSciBuilding.class);
                intent4.putExtra("username", username);
                intent4.putExtra("password", password);
                startActivity(intent4);
                break;
            case "Hangar":
                Intent intent5 = new Intent(this, HangarBuilding.class);
                intent5.putExtra("username", username);
                intent5.putExtra("password", password);
                startActivity(intent5);
                break;
            case "Library":
                Intent intent6 = new Intent(this, LibraryBuilding.class);
                intent6.putExtra("username", username);
                intent6.putExtra("password", password);
                startActivity(intent6);
                break;
            case "Government Building":
                Intent intent7 = new Intent(this, GovBuilding.class);
                intent7.putExtra("username", username);
                intent7.putExtra("password", password);
                startActivity(intent7);
                break;

        }
        return true;
    }

}
