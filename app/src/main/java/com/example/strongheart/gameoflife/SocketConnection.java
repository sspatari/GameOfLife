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
            mSocket = IO.socket("http://192.168.0.167:4606");
        } catch (URISyntaxException e) {}
    }

    public SocketConnection(Context context) {
        this.mainActivity = (MainActivity) context;
        mSocket.on("getColor", onGetColor);
        mSocket.on("getClient", onClientDetect);
        mSocket.on("getBomb", onBombDetect);
        mSocket.on("getTargets", onTargetsDetect);
        mSocket.connect();
    }

    private Emitter.Listener onClientDetect = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    Log.i("getClient", data.toString());
//                        JSONArray targets;
//                        try {
                    //targets = data.getJSONObject(i).getString("value");
                    //color = data.getString("status");
//                        } catch (JSONException e) {
//                            return;
//                        }

                    // add the message to view
                    changeClient(data);
                }
            });
        }
    };

    private Emitter.Listener onBombDetect = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    if(data != null) {
                        Log.i("getBomb", data.toString());
                    }
                    // add the message to view
                    changeBomb(data);
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

    private void changeClient(JSONObject data) {
        mainActivity.getGameMap().setClient(data);
        mainActivity.getGameMap().updateClient();

    public void emmitMessage(JSONable coordinates) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("Message", coordinates.toJson());

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mSocket.emit("message", obj);
    }

    private void changeClients(JSONArray data) {
        mainActivity.getGameMap().setClients(data);
        mainActivity.getGameMap().updateAllClients();
//        gameMap.setData();
//        textView1 = (TextView) findViewById(R.id.fragment1).findViewById(R.id.textView1);
//        textView1.setText(targets);
    }

    private void changeBomb(JSONObject data) {
        mainActivity.getGameMap().setBomb(data);
        mainActivity.getGameMap().updateBomb();
    }

    private void changeTargets(JSONArray data) {
        mainActivity.getScreenBars().setTargets(data);
        mainActivity.getScreenBars().updateAllTargets();
    }

    public void dispose() {
        mSocket.disconnect();
        mSocket.off("getColor", onTargetsDetect);
        mSocket.off("getClient", onClientDetect);
        mSocket.off("getBomb", onBombDetect);
        mSocket.off("getTargets", onTargetsDetect);
    }


}
