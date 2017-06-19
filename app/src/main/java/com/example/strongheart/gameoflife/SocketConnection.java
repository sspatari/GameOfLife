package com.example.strongheart.gameoflife;

import android.content.Context;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

/**
 * Created by hackintosh on 6/9/17.
 */

public class SocketConnection {
    private MainActivity mainActivity;
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://192.168.0.213:4606");
        } catch (URISyntaxException e) {}
    }

    public SocketConnection(Context context) {
        this.mainActivity = (MainActivity) context;
        Log.d("Sooooooocket","" + mSocket);
        //mSocket.on("getColor", onGetColor);
        mSocket.on("getClients", onClientsDetect);
        mSocket.on("getBombs", onBombsDetect);
        mSocket.on("getTargets", onTargetsDetect);
        mSocket.connect();
    }

    private Emitter.Listener onClientsDetect = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONArray data = (JSONArray) args[0];
                    Log.i("getClients", data.toString());
//                        JSONArray targets;
//                        try {
                    //targets = data.getJSONObject(i).getString("value");
                    //color = data.getString("status");
//                        } catch (JSONException e) {
//                            return;
//                        }

                    // add the message to view
                    changeClients(data);
                }
            });
        }
    };

    private Emitter.Listener onBombsDetect = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONArray data = (JSONArray) args[0];
                    Log.i("getBombs", data.toString());
                    // add the message to view
                    changeBombs(data);
                }
            });
        }
    };

    private Emitter.Listener onTargetsDetect = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONArray data = (JSONArray) args[0];
                    Log.i("getTargets", data.toString());
                    // add the message to view
                    changeTargets(data);
                }
            });
        }
    };

    private Emitter.Listener onGetColor = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                   Log.i("getColor", data.toString());
                    // add the message to view
                    initClientColor(data);
                }
            });
        }
    };

    private void initClientColor(JSONObject data) {
        mainActivity.setInitClientColor(data);
    }

    public void emmitMessage(JSONObject coordinates) {
        JSONObject obj; //= new JSONObject();
        //obj.put("Message", coordinates.toJson());
        obj = new JSONObject();
        try {
            obj.put("x", Float.parseFloat(coordinates.getString("x"))/3);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            obj.put("y", Float.parseFloat(coordinates.getString("y"))/3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //obj = coordinates.toJson();
        Log.e("OBJ JSON","" + obj);
        mSocket.emit("updateClient", obj);
    }


    private void changeClients(JSONArray data) {
        mainActivity.getGameMap().setClients(data);
        mainActivity.getGameMap().updateAllClients();
//        gameMap.setData();
//        textView1 = (TextView) findViewById(R.id.fragment1).findViewById(R.id.textView1);
//        textView1.setText(targets);
    }

    private void changeBombs(JSONArray data) {
        mainActivity.getGameMap().setBombs(data);
        mainActivity.getGameMap().updateAllBombs();
    }

    private void changeTargets(JSONArray data) {
        mainActivity.getScreenBars().setTargets(data);
        mainActivity.getScreenBars().updateAllTargets();
    }

    public void dispose() {
        mSocket.disconnect();
        mSocket.off("getColor", onTargetsDetect);
        mSocket.off("getClients", onClientsDetect);
        mSocket.off("getBombs", onBombsDetect);
        mSocket.off("getTargets", onTargetsDetect);
    }
}
