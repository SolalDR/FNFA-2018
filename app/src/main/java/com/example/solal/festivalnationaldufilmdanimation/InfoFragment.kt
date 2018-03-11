package com.example.solal.festivalnationaldufilmdanimation

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.solal.festivalnationaldufilmdanimation.entity.Place
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.ui.IconGenerator

import java.util.ArrayList


class InfoFragment : Fragment(), OnMapReadyCallback {
    private var mMap: GoogleMap? = null
    private var mapView: MapView? = null
    private var placeNameView: TextView? = null
    private var addressView: TextView? = null


    //    private GoogleMap googleMap;
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.


        val v = inflater!!.inflate(R.layout.tab_info, container, false)


        placeNameView = v.findViewById(R.id.name_place)
        addressView = v.findViewById(R.id.address_place)

        mapView = v.findViewById(R.id.map)
        mapView!!.onCreate(savedInstanceState)
        mapView!!.onResume()
        mapView!!.getMapAsync(this)

        val widthMap = mapView!!.width
        mapView!!.minimumHeight = widthMap


        return v
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

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val app = this.activity.application as MyApplication
        val places = app.manager.findAllPlaces()
        val markers = ArrayList<Marker>()
        val iconFactory = IconGenerator(this@InfoFragment.context)
        var marker: Marker? = null
        for (i in places.indices) {
            marker = mMap!!.addMarker(
                    MarkerOptions()
                            .icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(places[i].name)))
                            .position(LatLng(places[i].lat, places[i].lon))
            )
            markers.add(marker)
        }

        displayPlace(places[0])

        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(LatLng(places[0].lat, places[0].lon)))
        mMap!!.setMaxZoomPreference(30.0f)
        mMap!!.setMinZoomPreference(8.5f)
        val mUiSettings = mMap!!.uiSettings
        mUiSettings.isScrollGesturesEnabled

        mMap!!.setOnMarkerClickListener { marker ->
            for (i in markers.indices) {
                if (markers[i].id == marker.id) {
                    displayPlace(places[i])
                    break
                }
            }
            false
        }

        val style = MapStyleOptions.loadRawResourceStyle(this@InfoFragment.context, R.raw.map_style)
        googleMap.setMapStyle(style)
    }

    fun displayPlace(place: Place) {
        placeNameView!!.text = place.name
        addressView!!.text = place.address
    }
}



