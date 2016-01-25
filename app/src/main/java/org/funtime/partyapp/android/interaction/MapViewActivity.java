package org.funtime.partyapp.android.interaction;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.GroundOverlay;

import org.funtime.partyapp.android.R;

import java.util.ArrayList;
import java.util.List;

//public class MapViewActivity extends FragmentActivity implements OnMapReadyCallback {
public class MapViewActivity extends FragmentActivity implements ConstantCoordinates, SeekBar.OnSeekBarChangeListener
        //implements AdapterView.OnItemSelectedListener,  /*
{
    private static final String TAG = "MapViewActivity";
    private static final int TRANSPARENCY_MAX = 100;

    private final List<BitmapDescriptor> mImages = new ArrayList<BitmapDescriptor>();
    private GroundOverlay mGroundOverlay;

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
    }

    void addListenerTransparencyBar() {
        mTransparencyBar = (SeekBar) findViewById(R.id.transparencySeekBar);
        if (mTransparencyBar != null) {
            mTransparencyBar.setMax(TRANSPARENCY_MAX);
            mTransparencyBar.setProgress(0);
            mTransparencyBar.setOnSeekBarChangeListener(this);
        } else {
            Log.d(TAG, "mTransparencyBar not found");
        }
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
