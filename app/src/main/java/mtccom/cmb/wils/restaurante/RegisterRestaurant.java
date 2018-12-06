package mtccom.cmb.wils.restaurante;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class RegisterRestaurant extends AppCompatActivity implements OnMapReadyCallback {

    private MapView map;
    private GoogleMap mMap;
    private Geocoder geocoder;
    private TextView street;
    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_restaurant);

        map= findViewById(R.id.mapView);
        map.onCreate(savedInstanceState);
        map.onResume();
        MapsInitializer.initialize(this);
        map.getMapAsync(this);
        geocoder = new Geocoder(getBaseContext(),Locale.getDefault());
        street=findViewById(R.id.street);
        next= findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendData();
            }
        });
    }
    public void sendData(){

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //-19.5882679,-65.7571527,17
        LatLng sydney = new LatLng(-19.5882679, -65.7571527);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Lugar").zIndex(17).draggable(true));
        mMap.setMinZoomPreference(15);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
               String street_string = getStreet(marker.getPosition().latitude, marker.getPosition().longitude);
               street.setText(street_string);
            }
        });
    }
    public String getStreet (Double lat, Double lon){
        List<Address> address;
        String result="";
        try {
            address=geocoder.getFromLocation(lat, lon, 1);
            result += address.get(0).getThoroughfare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
