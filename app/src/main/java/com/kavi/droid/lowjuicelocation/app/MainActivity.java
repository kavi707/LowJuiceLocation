package com.kavi.droid.lowjuicelocation.app;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kavi.droid.lowjuicelocation.LowJuiceLocator;
import com.kavi.droid.lowjuicelocation.OnLocationChangeListener;
import com.kavi.droid.lowjuicelocation.exceptions.AirplaneModeException;
import com.kavi.droid.lowjuicelocation.exceptions.UnknownNetworkTypeException;
import com.kavi.droid.lowjuicelocation.models.LocationDetail;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;

public class MainActivity extends AppCompatActivity implements OnLocationChangeListener, OnMapReadyCallback {

    private Context context = this;
    private Button tryMeBtn;
    private TextView longitudeText, latitudeText, addressText;
    private SupportMapFragment mapFragment;
    private GoogleMap getGoogleMap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpView();
    }

    private void setUpView() {

        tryMeBtn = (Button) findViewById(R.id.try_me_btn);
        longitudeText = (TextView) findViewById(R.id.longitude_text);
        latitudeText = (TextView) findViewById(R.id.latitude_text);
        addressText = (TextView) findViewById(R.id.address_text);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        tryMeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueWithPermissions();
            }
        });
    }

    private void continueWithPermissions() {
        if (ActivityCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, ACCESS_NETWORK_STATE)
                        == PackageManager.PERMISSION_GRANTED ) {

            try {
                LowJuiceLocator.getInstance(context, MainActivity.this).refreshLocation();
            } catch (AirplaneModeException | UnknownNetworkTypeException e) {
                e.printStackTrace();
                if (e instanceof AirplaneModeException) {
                    Toast.makeText(context, R.string.airplane_mode_error_text, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, R.string.unknown_network_type_error_text, Toast.LENGTH_LONG).show();
                }
            }
        } else {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{ACCESS_NETWORK_STATE, ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION,
                    }, 0);
        }
    }

    @Override
    public void onLocationChange(LocationDetail locationDetail) {
        if (locationDetail != null) {
            longitudeText.setText(String.valueOf(locationDetail.getLocation().getLongitude()));
            latitudeText.setText(String.valueOf(locationDetail.getLocation().getLatitude()));
            addressText.setText(String.valueOf(locationDetail.getAddress()));

            getGoogleMap.clear();

            if (getGoogleMap != null) {
                LatLng defaultLocation = new LatLng(locationDetail.getLocation().getLatitude(),
                        locationDetail.getLocation().getLongitude());
                getGoogleMap.addMarker(new MarkerOptions().position(defaultLocation)
                        .title(getString(R.string.network_locations_label)));
                getGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(locationDetail.getLocation().getLatitude(),
                                locationDetail.getLocation().getLongitude()),
                        13.0f));
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //Permission Granted Successfully. Write working code here.
                    if (ActivityCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION)
                                    == PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(context, ACCESS_NETWORK_STATE)
                                    == PackageManager.PERMISSION_GRANTED ) {
                        try {
                            LowJuiceLocator.getInstance(context, MainActivity.this).refreshLocation();
                        } catch (AirplaneModeException | UnknownNetworkTypeException e) {
                            e.printStackTrace();

                            if (e instanceof AirplaneModeException) {
                                Toast.makeText(context, R.string.airplane_mode_error_text, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, R.string.unknown_network_type_error_text, Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                } else {
                    continueWithPermissions();
                }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        getGoogleMap = googleMap;

        LatLng defaultLocation = new LatLng(0, 0);
        getGoogleMap.addMarker(new MarkerOptions().position(defaultLocation)
                .title(getString(R.string.default_location_label)));
        getGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(defaultLocation));
    }
}
