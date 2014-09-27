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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class Crystal_ball extends Activity {
    private TextView answerText;
    private ImageView background;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float acceleration;
    private float currentAcceleration;
    private float previousAcceleration;

    long previousTime;
    long currentTime;
    long delay;

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

            previousTime = currentTime;
            currentTime = System.currentTimeMillis();

            long elapsed = currentTime - previousTime;
            delay += elapsed;

            if(acceleration > 12 && delay >= 3000){
                MediaPlayer mediaPlayer = MediaPlayer.create(getBaseContext(), R.drawable.crunch);
                mediaPlayer.start();
                answerText = (TextView) findViewById(R.id.answerText);
                answerText.setText(Predictions.get().getPrediction());
                answerText.startAnimation(AnimationUtils.makeInAnimation(Crystal_ball.this, true));
                delay = 0;
                background = (ImageView) findViewById(R.id.background);
                background.setImageResource(R.drawable.fortunecookie3);
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

        previousTime = System.currentTimeMillis();
        currentTime = System.currentTimeMillis();
        delay = 5000;

        ActionBar actionBar = getActionBar();
        actionBar.hide();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorListener);
    }
}
