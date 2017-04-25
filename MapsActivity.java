package com.example.nara.map;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    AlertDialog.Builder builder;
    ImageButton btnter, btnsat, btnse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Ask for Allow For use GPS
        //ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);

        builder = new AlertDialog.Builder(MapsActivity.this);
        btnter = (ImageButton) findViewById(R.id.btnter);
        btnsat = (ImageButton) findViewById(R.id.btnsat);
        btnse = (ImageButton) findViewById(R.id.btnse);

        btnse.setImageResource(R.drawable.ic_search_black);
        btnter.setImageResource(R.drawable.ic_terrain_black);
        btnsat.setImageResource(R.drawable.ic_satellite_black);
    }

    public void btnonclick(View v) {
        switch (v.getId()) {
            case R.id.btnter:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                btnter.setVisibility(View.GONE);
                btnsat.setVisibility(View.VISIBLE);
                break;

            case R.id.btnsat:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                btnter.setVisibility(View.VISIBLE);
                btnsat.setVisibility(View.GONE);
                break;


            case R.id.btnse:
                EditText et1 = (EditText) findViewById(R.id.et1);
                EditText et2 = (EditText) findViewById(R.id.et2);
                String l1 = et1.getText().toString();
                String l2 = et2.getText().toString();

                // Check input
                if (l1.equals("") && l2.equals("")) {
                    builder.setMessage("Please Put Latitude and Longitude");
                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();

                } else if (l1.equals("")) {
                    builder.setMessage("Please Put Latitude");
                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();

                } else if (l2.equals("")) {
                    builder.setMessage("Please Put Longitude");
                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();

                } else {
                    // Remove Marker
                    mMap.clear();

                    //Convert to Double
                    double lan = Double.valueOf(l1);
                    double lnn = Double.valueOf(l2);

                    //Find Location
                    LatLng nl = new LatLng(lan, lnn);


                    // Get Place Name from LatLong


                    String pname = "Location Name";

                    mMap.addMarker(new MarkerOptions().position(nl).title(pname));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nl, 12.0f));
                    mMap.getUiSettings().setZoomControlsEnabled(true);

                    CircleOptions circleOptions = new CircleOptions();
                    circleOptions.center(nl);
                    circleOptions.radius(500);
                    mMap.addCircle(circleOptions);

                    break;
                }
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(18.772436, 98.973631))
                .title("CLBS"));
    }
}

