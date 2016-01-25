package org.funtime.partyapp.android.util;

import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

/**
 * Created by uv on 19.01.2016.
 */
public class DefaultOnClickListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        String viewName = v.getResources().getResourceName(v.getId());
        Toast.makeText(v.getContext(), String.format("%s %s", viewName, ((CheckBox) v).isChecked() ? "On" : "Off"), Toast.LENGTH_SHORT).show();
    }
}

