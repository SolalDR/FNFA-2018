package com.example.solal.festivalnationaldufilmdanimation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.content.Intent
import android.widget.TextView
import com.example.solal.festivalnationaldufilmdanimation.entity.Place
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.google.maps.android.ui.IconGenerator
import java.util.ArrayList


class InfoActivity : OnMapReadyCallback, AppCompatActivity() {


    private var mMap: GoogleMap? = null
    private var mapView: MapView? = null
    private var placeNameView: TextView? = null
    private var addressView: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        manageNav()

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.


        placeNameView = findViewById(R.id.name_place)
        addressView = findViewById(R.id.address_place)

        mapView = findViewById(R.id.map)
        mapView!!.onCreate(savedInstanceState)
        mapView!!.onResume()
        mapView!!.getMapAsync(this)

        val widthMap = mapView!!.width
        mapView!!.minimumHeight = widthMap

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val app = application as MyApplication
        val places = app.manager.findAllPlaces()
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

        val style = MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style)
        googleMap.setMapStyle(style)
    }

    fun displayPlace(place: Place) {
        placeNameView!!.text = place.name
        addressView!!.text = place.address
    }

    private fun manageNav(){
        var home = findViewById<ImageButton>(R.id.homeBtn)
        var info = findViewById<ImageButton>(R.id.infoBtn)
        var program = findViewById<ImageButton>(R.id.programBtn)
        var star = findViewById<ImageButton>(R.id.starBtn)

        home.setOnClickListener(View.OnClickListener{
            startActivity(Intent(this, HomeActivity::class.java))
        })

        info.setOnClickListener(View.OnClickListener{
            startActivity(Intent(this, InfoActivity::class.java))
        })

        program.setOnClickListener(View.OnClickListener{
            startActivity(Intent(this, ProgramActivity::class.java))
        })

        star.setOnClickListener(View.OnClickListener{
            startActivity(Intent(this, FavoriteActivity::class.java))
        })
    }
}
