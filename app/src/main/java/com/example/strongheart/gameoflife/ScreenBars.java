package com.example.strongheart.gameoflife;

import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by strongheart on 6/12/17.
 */

public class ScreenBars {
    private MainActivity mainActivity;
    private List<ProgressBar> distanceBars;
    private ProgressBar lifeBar;
    private ProgressBar mineBar;
    private JSONArray targets;

    public ScreenBars(Context context) {
        mainActivity = (MainActivity) context;
        distanceBars = new ArrayList<ProgressBar>();
        distanceBars.add((ProgressBar) mainActivity.findViewById(R.id.distanceBar1));
        distanceBars.add((ProgressBar) mainActivity.findViewById(R.id.distanceBar2));
        distanceBars.add((ProgressBar) mainActivity.findViewById(R.id.distanceBar3));
        lifeBar = (ProgressBar) mainActivity.findViewById(R.id.lifeBar1);
        mineBar = (ProgressBar) mainActivity.findViewById(R.id.minebar1);

    }

    public void setTargets (JSONArray targets) {
        this.targets = targets;
        Log.i("targets_json", targets.toString());
    }


    public void updateAllTargets() {
        for(int i=0; i<targets.length(); ++i){
            updateTarget(i);
        }
    };

    private void updateTarget(int targetIndex) {
        try{
            JSONObject jsonObject = targets.getJSONObject(targetIndex);
            ProgressBar progressBar = distanceBars.get(targetIndex);
            int prevValue = progressBar.getProgress();
            int nextValue = (int)(Float.parseFloat(jsonObject.getString("value"))*10000);
            ProgressBarAnimation anim = new ProgressBarAnimation(
                    progressBar, prevValue, nextValue);
            anim.setDuration(1000);
            progressBar.startAnimation(anim);
        }catch (JSONException e) {
            Log.i("TargetJsonExpetion",e.toString());
        }
    }




}
