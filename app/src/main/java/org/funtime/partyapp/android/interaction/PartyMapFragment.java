package org.funtime.partyapp.android.interaction;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import org.funtime.partyapp.android.R;
import org.funtime.partyapp.android.util.DefaultOnClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uv on 06/12/2015.
 */
public class PartyMapFragment extends SupportMapFragment implements ConstantCoordinates, AdapterView.OnItemSelectedListener, OnMapReadyCallback {
    private static final String TAG = "PartyMapFragment";
    private static final int TRANSPARENCY_MAX = 100;
    private MapView mapView;
    private CheckBox mChbxTraffic;
    private CheckBox mChbxMyLocation;
    private CheckBox mChbxBuildings;

    private GoogleMap mMap;
    private final List<BitmapDescriptor> mImages = new ArrayList<BitmapDescriptor>();
    private GroundOverlay mGroundOverlay;

    private Spinner mSpinner;

    private int mCurrentEntry = 0;
    private float width;
    private float transparency = 0.5f;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        Activity activity = getActivity();
        if (activity instanceof MapViewActivity) {
            MapViewActivity mva = (MapViewActivity) activity;
            mva.addListenerTransparencyBar();
        }

        // Gets the MapView from the XML layout and creates it
        switch (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity())) {
            case ConnectionResult.SUCCESS:
                Toast.makeText(getActivity(), "SUCCESS", Toast.LENGTH_SHORT).show();
                initMap();

                addListenerSpinner(v);
                addListenerCheckBox(v);

                mapView = (MapView) v.findViewById(R.id.map);
                if (mapView != null) {
                    mapView.onCreate(savedInstanceState);
                }
                break;
            case ConnectionResult.SERVICE_MISSING:
                Toast.makeText(getActivity(), "SERVICE MISSING", Toast.LENGTH_SHORT).show();
                break;
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
                Toast.makeText(getActivity(), "UPDATE REQUIRED", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(getActivity(), GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()), Toast.LENGTH_SHORT).show();
        }

        // Updates the location and zoom of the MapView
        return v;
    }


    void addListenerSpinner(View v) {
        mSpinner = (Spinner) v.findViewById(R.id.layers_spinner);
        if (mSpinner != null) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    this.getContext(), R.array.layers_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner.setAdapter(adapter);
            mSpinner.setOnItemSelectedListener(this);
        } else {
            Log.d(TAG, "mSpinner not found");
        }
    }


    void addListenerCheckBox(View v) {
        mChbxTraffic = (CheckBox) v.findViewById(R.id.traffic);
        if (mChbxTraffic != null)
            mChbxTraffic.setOnClickListener(new DefaultOnClickListener());

        mChbxMyLocation = (CheckBox) v.findViewById(R.id.my_location);
        if (mChbxMyLocation != null)
            mChbxMyLocation.setOnClickListener(new DefaultOnClickListener());

        mChbxBuildings = (CheckBox) v.findViewById(R.id.buildings);
        if (mChbxBuildings != null)
            mChbxBuildings.setOnClickListener(new DefaultOnClickListener());
    }

    private void initMap() {
        MapsInitializer.initialize(getActivity());
        UiSettings settings = getMap().getUiSettings();
        settings.setAllGesturesEnabled(true);
        settings.setMyLocationButtonEnabled(true);
        getMapAsync(this);
    }

    /**
     * Manipulates the mMap once available.
     * This callback is triggered when the mMap is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap map) {
        getMap().addMarker(new MarkerOptions().position(FUSION_CENTER).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_pink_32)).title("Fusion"));

        mImages.clear();
        mImages.add(BitmapDescriptorFactory.fromResource(R.drawable.fusion_map_2012));

        mCurrentEntry = 0;
        width = 2250f;
        mGroundOverlay = map.addGroundOverlay(new GroundOverlayOptions()
                .image(mImages.get(mCurrentEntry)).anchor(0, 1)
                .position(FUSION_MAPCORNER, width)
                .transparency(transparency));

        // Override the default content description on the view, for accessibility mode.
        // Ideally this string would be localised.
        map.setContentDescription("Google Map with ground overlay.");
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(FUSION_CENTER, 14));
    }


    //    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        updateMapType();
    }

    private void updateMapType() {
        // point mMap may not be ready yet.
        if (mMap == null) {
            return;
        } else {
            Log.d(TAG, "mMap not found");
        }

        String layerName = ((String) mSpinner.getSelectedItem());
        if (layerName.equals(getString(R.string.normal))) {
            mMap.setMapType(mMap.MAP_TYPE_NORMAL);
        } else if (layerName.equals(getString(R.string.hybrid))) {
            mMap.setMapType(mMap.MAP_TYPE_HYBRID);


        } else if (layerName.equals(getString(R.string.satellite))) {
            mMap.setMapType(mMap.MAP_TYPE_SATELLITE);
        } else if (layerName.equals(getString(R.string.terrain))) {
            mMap.setMapType(mMap.MAP_TYPE_TERRAIN);
        } else if (layerName.equals(getString(R.string.none_map))) {
            mMap.setMapType(mMap.MAP_TYPE_NONE);
        } else {
            Log.i("LDA", "Error setting layer with name " + layerName);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void switchImage(View view) {
        mCurrentEntry = (mCurrentEntry + 1) % mImages.size();
        mGroundOverlay.setImage(mImages.get(mCurrentEntry));
    }

}
