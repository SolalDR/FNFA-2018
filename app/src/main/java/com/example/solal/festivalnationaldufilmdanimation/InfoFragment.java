package com.example.solal.festivalnationaldufilmdanimation;


import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.solal.festivalnationaldufilmdanimation.entity.Place;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import java.util.ArrayList;


public class InfoFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    private MapView mapView;
    private TextView placeNameView;
    private TextView addressView;



//    private GoogleMap googleMap;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.




        View v =inflater.inflate(R.layout.tab_info,container,false);


        placeNameView = v.findViewById(R.id.name_place);
        addressView = v.findViewById(R.id.address_place);

        mapView = v.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        int widthMap = mapView.getWidth();
        mapView.setMinimumHeight(widthMap);


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
        MyApplication app = (MyApplication )this.getActivity().getApplication();
        final ArrayList<Place> places = app.getManager().findAllPlaces();
        final ArrayList<Marker> markers = new ArrayList<Marker>();


        IconGenerator iconFactory = new IconGenerator(this.getContext());

        Marker marker = null;
        for(int i = 0; i<places.size(); i++){
            marker = mMap.addMarker(
                    new MarkerOptions()
                            .icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(places.get(i).getName())))
                            .position(new LatLng(places.get(i).getLat(), places.get(i).getLon()))
            );

            markers.add(marker);
        }

        displayPlace(places.get(0));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(places.get(0).getLat(), places.get(0).getLon())));
        mMap.setMaxZoomPreference(30.0f);
        mMap.setMinZoomPreference(8.5f);
        UiSettings mUiSettings = mMap.getUiSettings();
        mUiSettings.isScrollGesturesEnabled();

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){
            @Override
            public boolean onMarkerClick(Marker marker) {
                for(int i=0; i<markers.size(); i++){
                    if( markers.get(i).getId().equals(marker.getId())){
                        displayPlace(places.get(i));
                        break;
                    }
                }
                return false;
            }
        });

        MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(this.getContext(), R.raw.map_style);
        googleMap.setMapStyle(style);

    }

    public void displayPlace(Place place){
        placeNameView.setText(place.getName());
        addressView.setText(place.getAddress());
    }


    public void onMarkerClick(Marker theMarker)
    {
        theMarker.hideInfoWindow();
    }

}



