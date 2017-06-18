package com.example.strongheart.gameoflife;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by strongheart on 6/7/17.
 */

public class GameMapFragment extends Fragment {
    private List<ProgressBar> distanceBars;
    private TextView textView1;
    private int i = 0;
    private JSONObject client;
    private JSONObject bomb;

    private GameMapAnimationLayout gameMapAnimationLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.game_map, container, false);
        gameMapAnimationLayout = (GameMapAnimationLayout) v.findViewById(R.id.gameMapAnimantionLayoutView);
        //v.setClipToOutline(true);
        //View v = new GameMapAnimationLayout(getActivity());
        //textView1 = (TextView) v.findViewById(R.id.textView1);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        Button myButton = (Button) getView().findViewById(R.id.button1);
//
//        distanceBars = new ArrayList<ProgressBar>();
//        distanceBars.add((ProgressBar) getActivity().findViewById(R.id.distanceBar1));
//        distanceBars.add((ProgressBar) getActivity().findViewById(R.id.distanceBar2));
//        distanceBars.add((ProgressBar) getActivity().findViewById(R.id.distanceBar3));
//        final ProgressBar lifeBar = (ProgressBar) getActivity().findViewById(R.id.lifeBar1);
//
//        myButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                if ( i < lifeBar.getMax() ) {
//                    i += 1000;
//                    lifeBar.setProgress(i);
//                }else {
//                    lifeBar.setProgress(0);
//                    i = 0;
//                }
//            }
//        });
    }

    @Override
    public void onPause() {
        super.onPause();
        gameMapAnimationLayout.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        gameMapAnimationLayout.resume();
    }

    public void setClient (JSONObject client) {
        this.client = client;
        Log.i("clients_json", client.toString());

        //textView1.setText(clients.toString());
    }

    public void setBomb (JSONObject bomb) {
        this.bomb = bomb;
        if(bomb != null) {
            Log.i("bomb_json", bomb.toString());
        }
        //textView1.setText(bombs.toString());
    }

    public void updateClient() {
        int animationLayoutWidth = gameMapAnimationLayout.getWidth();
        int animationLayoutHeight = gameMapAnimationLayout.getHeight();

        ArrayList<String> listColors = new ArrayList<String>();
        if (gameMapAnimationLayout.getClientCircles() != null) {
            int len = gameMapAnimationLayout.getClientCircles().size();
            for (int i=0;i<len;i++){
                listColors.add(gameMapAnimationLayout.getClientCircles().get(i).getColor());
            }
        }

        try{
            float clientX_Coor =  animationLayoutWidth * Float.parseFloat(client.getString("x"));
            float clientY_Coor =  animationLayoutHeight * Float.parseFloat(client.getString("y"));
            String clientColor = client.getString("color");

            if (!listColors.contains(clientColor)) {
                gameMapAnimationLayout.generateClientCircle(clientX_Coor, clientY_Coor,
                        50, clientColor);
                Log.i("generate", "yes");
            }else{
                gameMapAnimationLayout.updateClientCircle(clientX_Coor, clientY_Coor,
                        50, clientColor);
                Log.i("updated", "yes");
            }
        }catch (JSONException e) {
        Log.i("ClientJsonExpetion",e.toString());
        }
    }
    public void updateBomb() {};

//    private void updateClient(int clientIndex) {
//        try{
//            JSONObject jsonObject = clients.getJSONObject(clientIndex);
//            ProgressBar progressBar = distanceBars.get(targetIndex);
//            int prevValue = progressBar.getProgress();
//            int nextValue = (int)(Float.parseFloat(jsonObject.getString("value"))*10000);
//            ProgressBarAnimation anim = new ProgressBarAnimation(
//                    progressBar, prevValue, nextValue);
//            anim.setDuration(1000);
//            progressBar.startAnimation(anim);
//        }catch (JSONException e) {
//            Log.i("TargetJsonExpetion",e.toString());
//        }
//    }


}
