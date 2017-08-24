package com.example.dptoredes.leccion2;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.NotificationManager;
import android.view.View;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class MainActivity extends Activity implements DownloadResultReceiver.Receiver, SensorEventListener {

    private ListView listView = null;

    private ArrayAdapter<String> arrayAdapter = null;

    private DownloadResultReceiver mReceiver;

    private long last_update = 0, last_movement = 0;
    private float prevX = 0, prevY = 0, prevZ = 0;
    private float curX = 0, curY = 0, curZ = 0;

    /*final String url = "http://javatechig.com/api/get_category_posts/?dev=1&slug=android";*/

    final String url = "https://jsonplaceholder.typicode.com/posts";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Allow activity to show indeterminate progressbar */
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        /* Set activity layout */
        setContentView(R.layout.activity_main);

        /* Initialize listView */
        //listView = (ListView) findViewById(R.id.listView);

        /* Starting Download Service */
        mReceiver = new DownloadResultReceiver(new Handler());
        mReceiver.setReceiver(this);
        Intent intent = new Intent(Intent.ACTION_SYNC, null, this, DownloadService.class);

        /* Send optional extras to Download IntentService */
        intent.putExtra("url", url);
        intent.putExtra("receiver", mReceiver);
        intent.putExtra("requestId", 101);

        startService(intent);
    }


    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case DownloadService.STATUS_RUNNING:

                setProgressBarIndeterminateVisibility(true);
                break;
            case DownloadService.STATUS_FINISHED:
                /* Hide progress & extract result from bundle */
                setProgressBarIndeterminateVisibility(false);

                String[] results = resultData.getStringArray("result");

                /* Update ListView with result */

                arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, results);
                listView.setAdapter(arrayAdapter);

                break;
            case DownloadService.STATUS_ERROR:
                /* Handle the error */
                String error = resultData.getString(Intent.EXTRA_TEXT);
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
            synchronized (this) {
                long current_time = event.timestamp;

                curX = event.values[0];
                curY = event.values[1];
                curZ = event.values[2];

                if (prevX == 0 && prevY == 0 && prevZ == 0) {
                    last_update = current_time;
                    last_movement = current_time;
                    prevX = curX;
                    prevY = curY;
                    prevZ = curZ;
                }

                long time_difference = current_time - last_update;
                if (time_difference > 0) {
                    float movement = Math.abs((curX + curY + curZ) - (prevX - prevY - prevZ)) / time_difference;
                    int limit = 1500;
                    float min_movement = 1E-6f;
                    if (movement > min_movement) {
                        if (current_time - last_movement >= limit) {
                            Toast.makeText(getApplicationContext(), "Hay movimiento de " + movement, Toast.LENGTH_SHORT).show();
                        }
                        last_movement = current_time;
                    }
                    prevX = curX;
                    prevY = curY;
                    prevZ = curZ;
                    last_update = current_time;
                }


                ((TextView) findViewById(R.id.txtAccX)).setText("Acelerómetro X: " + curX);
                ((TextView) findViewById(R.id.txtAccY)).setText("Acelerómetro Y: " + curY);
                ((TextView) findViewById(R.id.txtAccZ)).setText("Acelerómetro Z: " + curZ);
            }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void onResume() {
        super.onResume();
        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensors = sm.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (sensors.size() > 0) {
            sm.registerListener(this, sensors.get(0), SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    protected void onStop() {
        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sm.unregisterListener(this);
        super.onStop();
    }
}