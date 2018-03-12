package com.example.solal.festivalnationaldufilmdanimation

import android.os.Bundle
import android.widget.TextView
import com.example.solal.festivalnationaldufilmdanimation.entity.Place
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.google.maps.android.ui.IconGenerator
import java.util.ArrayList


class InfoActivity : OnMapReadyCallback, MainActivity() {

    lateinit var mapView: MapView
    lateinit var placeNameView: TextView
    lateinit var addressView: TextView
    private var mMap: GoogleMap? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        manageNav()

        placeNameView = findViewById(R.id.name_place)
        addressView = findViewById(R.id.address_place)

        mapView = findViewById(R.id.map)
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)

        val widthMap = mapView.width
        mapView.minimumHeight = widthMap

    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val places = app.manager.findAllPlaces()
        val markers = getMarkers(places)

        displayPlace(places[0])

        mMap?.apply {
            this.moveCamera(CameraUpdateFactory.newLatLng(LatLng(places[0].lat, places[0].lon)))
            this.setMaxZoomPreference(30.0f)
            this.setMinZoomPreference(8.5f)
            val mUiSettings = this.uiSettings
            mUiSettings.isScrollGesturesEnabled

            this.setOnMarkerClickListener { marker ->
                for (i in markers.indices) {
                    if (markers[i].id == marker.id) {
                        displayPlace(places[i])
                        break
                    }
                }
                false
            }
        }

        val style = MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style)
        googleMap.setMapStyle(style)
    }


    fun getMarkers(places:ArrayList<Place>): ArrayList<Marker> {
        val markers = ArrayList<Marker>()
        val iconFactory = IconGenerator(this)
        var marker: Marker? = null
        for (i in places.indices) {
            marker = mMap!!.addMarker(
                    MarkerOptions()
                            .icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(places[i].name)))
                            .position(LatLng(places[i].lat, places[i].lon))
            )
            markers.add(marker)
        }
        return markers
    }



    fun displayPlace(place: Place) {
        placeNameView.text = place.name
        addressView.text = place.address
    }
}
