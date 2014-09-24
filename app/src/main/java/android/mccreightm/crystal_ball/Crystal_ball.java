package android.mccreightm.crystal_ball;

import android.animation.ValueAnimator;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.FloatMath;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;


public class Crystal_ball extends Activity {
    private TextView answerText;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float acceleration;
    private float currentAcceleration;
    private float previousAcceleration;


    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            previousAcceleration = currentAcceleration;
            currentAcceleration = FloatMath.sqrt(x * x + y * y + z * z);
            float delta = currentAcceleration - previousAcceleration;
            acceleration = acceleration * 0.9f + delta;

            if(acceleration > 12){
                MediaPlayer mediaPlayer = MediaPlayer.create(getBaseContext(), R.drawable.crystal_ball);
                mediaPlayer.start();
                answerText = (TextView) findViewById(R.id.answerText);
                answerText.setText(Predictions.get().getPrediction());
                answerText.startAnimation(AnimationUtils.loadAnimation(Crystal_ball.this, android.R.anim.fade_in));
//                Toast toast = Toast.makeText(getApplication(), "Device has shaken", Toast.LENGTH_SHORT);
//                toast.show();

            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crystal_ball);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        acceleration = 0.0f;
        currentAcceleration = SensorManager.GRAVITY_EARTH;
        previousAcceleration = SensorManager.GRAVITY_EARTH;

        ActionBar actionBar = getActionBar();
        actionBar.hide();
    }

    @Override
    protected void onResume() {
        int delay = 5;
        super.onResume();
        sensorManager.registerListener(sensorListener, accelerometer, delay);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorListener);
    }
}
