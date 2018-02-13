package com.example.solal.festivalnationaldufilmdanimation;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;



public class InfoFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    private MapView mapView;
//    private GoogleMap googleMap;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        View v =inflater.inflate(R.layout.tab_info,container,false);
        mapView = v.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);


        return v;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        IconGenerator iconFactory = new IconGenerator(this.getContext());

        LatLng cinemaTNB = new LatLng(48.107967, -1.672562);
        Marker markerTNB = mMap.addMarker(
                new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon("Cinéma TNB")))
                        .position(cinemaTNB)
//                        .anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV())
        );

        LatLng cinemaArvor = new LatLng(48.115995, -1.679114);
        Marker markerArvor = mMap.addMarker(
                new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon("Cinéma Arvor")))
                        .position(cinemaArvor)

        );

        float middleLat = (float) (cinemaTNB.latitude+cinemaArvor.latitude)/2;
        float middleLong = (float) (cinemaTNB.longitude+cinemaArvor.longitude)/2;

        LatLng defaultCamPosition = new LatLng(middleLat, middleLong);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(defaultCamPosition));
        mMap.setMaxZoomPreference(30.0f);
        mMap.setMinZoomPreference(14.5f);

        MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(this.getContext(), R.raw.map_style);
                googleMap.setMapStyle(style);
        onMarkerClick(markerArvor);
        onMarkerClick(markerTNB);
    }


    public void onMarkerClick(Marker theMarker)
    {
        theMarker.hideInfoWindow();
    }

}



