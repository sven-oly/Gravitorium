package com.ccproductionsmenlopark.sensorium;

import java.lang.Math;
import java.util.List;

import android.content.Context;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.hardware.SensorEvent;
import android.hardware.Sensor;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View;



public class Gravitonium extends ActionBarActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    List<Sensor> mDeviceSensors;
    private Sensor mGrav;

    private View mBgdView;
    private View colorBox;
    private TextView mGravX;
    private TextView mGravY;
    private TextView mGravZ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensorium);

        mSensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);

        mDeviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        mGrav = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

        mBgdView = (View)findViewById(R.id.bdgView);
        colorBox = (View)findViewById(R.id.colorBox);

        mGravX = (TextView)findViewById(R.id.gravX);
        mGravY = (TextView)findViewById(R.id.gravY);
        mGravZ = (TextView)findViewById(R.id.gravZ);
    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mGrav, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sensorium, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        float gravXVal = event.values[0];
        float gravYVal = event.values[1];
        float gravZVal = event.values[2];

        mGravX.setText(String.valueOf(gravXVal));
        mGravY.setText(String.valueOf(gravYVal));
        mGravZ.setText(String.valueOf(gravZVal));

        DoSomething(event.values);
    }

    public void DoSomething(float [] values) {
        int maxDim = 0;
        float maxVal = Math.abs(values[0]);
        if (Math.abs(values[1]) > maxVal) {
            maxVal = Math.abs(values[1]);
            maxDim = 1;
        }
        if (Math.abs(values[2]) > maxVal) {
            maxVal = Math.abs(values[2]);
            maxDim = 2;
        }

        if (maxDim == 0) {
            colorBox.setBackgroundColor(0xffaa0000);
        } else if (maxDim == 1) {
            colorBox.setBackgroundColor(0xff00AA00);

        } else {
            colorBox.setBackgroundColor(0xff0000aa);
        }

    }
}
