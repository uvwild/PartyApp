package org.funtime.partyapp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.GroundOverlay;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_HYBRID;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NONE;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_SATELLITE;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_TERRAIN;

//public class MapViewActivity extends FragmentActivity implements OnMapReadyCallback {
public class MapViewActivity extends FragmentActivity implements AdapterView.OnItemSelectedListener, SeekBar.OnSeekBarChangeListener, OnMapReadyCallback,ConstantCoordinates
        //implements AdapterView.OnItemSelectedListener,  /*
{
    private static final String TAG = "MapViewActivity";
    private static final int TRANSPARENCY_MAX = 100;

    private final List<BitmapDescriptor> mImages = new ArrayList<BitmapDescriptor>();
    private GroundOverlay mGroundOverlay;

    private Spinner mSpinner;
    private SeekBar mTransparencyBar;

    private int mCurrentEntry = 0;
    private float width;
    private float transparency;
    private GoogleMap mMap;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "switch to mapview ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        mTransparencyBar = (SeekBar) findViewById(R.id.transparencySeekBar);
        if (mTransparencyBar != null) {
            mTransparencyBar.setMax(TRANSPARENCY_MAX);
            mTransparencyBar.setProgress(0);
            mTransparencyBar.setOnSeekBarChangeListener(this);
        } else {
            Log.d(TAG,"mTransparencyBar not found");
        }
        mSpinner = (Spinner) findViewById(R.id.layers_spinner);
        if (mSpinner != null) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    this, R.array.layers_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner.setAdapter(adapter);
            mSpinner.setOnItemSelectedListener(this);
        } else {
            Log.d(TAG,"mSpinner not found");
        }

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        updateMapType();
    }

    private void updateMapType() {
        // point mMap may not be ready yet.
        if (mMap == null) {
            SupportMapFragment mapFragment =
                    (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mMap = mapFragment.getMap();
        }
        if (mMap == null) {
            return;
        } else {
            Log.d(TAG,"mMap not found");
        }

        String layerName = ((String) mSpinner.getSelectedItem());
        if (layerName.equals(getString(R.string.normal))) {
            mMap.setMapType(MAP_TYPE_NORMAL);
        } else if (layerName.equals(getString(R.string.hybrid))) {
            mMap.setMapType(MAP_TYPE_HYBRID);


        } else if (layerName.equals(getString(R.string.satellite))) {
            mMap.setMapType(MAP_TYPE_SATELLITE);
        } else if (layerName.equals(getString(R.string.terrain))) {
            mMap.setMapType(MAP_TYPE_TERRAIN);
        } else if (layerName.equals(getString(R.string.none_map))) {
            mMap.setMapType(MAP_TYPE_NONE);
        } else {
            Log.i("LDA", "Error setting layer with name " + layerName);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
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
    public void onMapReady(GoogleMap map) {
//        map.getUiSettings().setMyLocationButtonEnabled(false);
//        map.setMyLocationEnabled(true);
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(FUSION_CENTER, 15);
////        map.moveCamera(CameraUpdateFactory.newLatLngZoom(FUSION_CENTER, 11));
//        map.animateCamera(cameraUpdate);
////        Marker hamburg = map.addMarker(new MarkerOptions().position(HAMBURG).title("Hamburg"));
////        Marker fusion = map.addMarker(new MarkerOptions().position(FUSION_CENTER).title("Fusion"));
//
//        mImages.clear();
//        mImages.add(BitmapDescriptorFactory.fromResource(R.drawable.fusion_map_2012));
//
//        mCurrentEntry = 0;
//        width = 2500f;
//        transparency = 0.5f;
//        mGroundOverlay = map.addGroundOverlay(new GroundOverlayOptions()
//                .image(mImages.get(mCurrentEntry)).anchor(0, 1)
//                .position(FUSION_MAPCORNER, width)
//                .transparency(transparency));
//
//        mTransparencyBar.setOnSeekBarChangeListener(this);
//
//        // Override the default content description on the view, for accessibility mode.
//        // Ideally this string would be localised.
//        map.setContentDescription("Google Map with ground overlay.");
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (mGroundOverlay != null) {
            mGroundOverlay.setTransparency((float) progress / (float) TRANSPARENCY_MAX);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void switchImage(View view) {
        mCurrentEntry = (mCurrentEntry + 1) % mImages.size();
        mGroundOverlay.setImage(mImages.get(mCurrentEntry));
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

        // just exit now
        finish();
    }
}
