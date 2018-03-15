package com.example.solal.festivalnationaldufilmdanimation

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.solal.festivalnationaldufilmdanimation.entity.Place
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.google.maps.android.ui.IconGenerator
import java.util.ArrayList
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.LatLng
import android.widget.RelativeLayout
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions










class InfoActivity : OnMapReadyCallback, MainActivity() {

    lateinit var mapView: MapView
    lateinit var placeNameView: TextView
    lateinit var addressView: TextView
    private var mMap: GoogleMap? = null

    private var cinemaArvor: LatLng? = LatLng(48.116137, -1.679034)
    private val cinemaTNB: LatLng? = LatLng(48.107960,  -1.672563)

    private val ESRA: LatLng? = LatLng(48.129036, -1.642040)
    private val GL: LatLng? = LatLng(48.235920, -1.622803)

    private val ECDG: LatLng? = LatLng(48.106697,  -1.676723)
    private val F3B: LatLng? = LatLng(48.108728, -1.673121)

    private val markerTNB: Marker? = null
    private var markerArvor: Marker? = null

//    private var layout1cinema: RelativeLayout?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        manageNav()

        placeNameView = findViewById(R.id.name_place0)
        addressView = findViewById(R.id.address_place0)

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
