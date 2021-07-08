package com.example.monitoringandfeedback21;

import android.app.Application;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.monitoringandfeedback21.viewmodel.BaseViewModel;


public class MainViewModel extends BaseViewModel {
    private Handler handler = new Handler(Looper.getMainLooper());
    final LiveData<AccelerationInformation> accelerationLiveData;


    public MainViewModel(@NonNull Application application) {
        super(application);
        accelerationLiveData = new AccelerationLiveData(application.getApplicationContext());

    }

    public LiveData<AccelerationInformation> accelerationInsert() {
        return accelerationLiveData;
    }

    public final class AccelerationLiveData extends LiveData<AccelerationInformation> {
        private final AccelerationInformation accelerationInformation = new AccelerationInformation();
        private SensorManager sm;
        private Sensor accelerometer;
        private Sensor gravitySensor;
        private float[] gravity;

        public void insertSensorData() {
            Runnable r = () -> {
                getDatabase().getAccelerationDao().insert(accelerationInformation);
                    handler.post(() -> {
                        setValue(accelerationInformation);
                    }); // wegen handler, class AccelerationLiveData auf nicht static ändern

            };
            Thread t = new Thread(r);
            t.start();
        }

        public void deleteSensorData() {
            Runnable r = () -> {
                getDatabase().getAccelerationDao().deleteAllData();
            };
            Thread t = new Thread(r);
            t.start();
        }

        public LiveData<AccelerationInformation> getSensorData() {
            return getDatabase().getAccelerationDao().getAllData();
        }


        private SensorEventListener listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                switch (event.sensor.getType()) {
                    case Sensor.TYPE_ACCELEROMETER:
                        float[] values = removeGravity(gravity, event.values);
                        accelerationInformation.setXYZ(values[0], values[1], values[2]);
                        accelerationInformation.setSensor(event.sensor);
                        setValue(accelerationInformation);
                        insertSensorData();
                        break;
                    case Sensor.TYPE_GRAVITY:
                        gravity = event.values;
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        AccelerationLiveData(Context context) {
            sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            if (sm != null) {
                gravitySensor = sm.getDefaultSensor(Sensor.TYPE_GRAVITY);
                accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            } else {
                //should nerver happen
                throw new RuntimeException("Jim is dead!");
            }
        }
        @Override
        protected void onActive() {
            super.onActive();
            sm.registerListener(listener, gravitySensor, SensorManager.SENSOR_DELAY_NORMAL);
            sm.registerListener(listener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }

        @Override
        protected void onInactive() {
            super.onInactive();
            sm.unregisterListener(listener);
        }

        private float[] removeGravity(float[] gravity, float[] values) {

            if (gravity == null) {
                return values;
            }

            final float alpha = 0.8f;
            float g[] = new float[3];
            g[0] = alpha * gravity[0] + (1 - alpha) * values[0];
            g[1] = alpha * gravity[1] + (1 - alpha) * values[1];
            g[2] = alpha * gravity[2] + (1 - alpha) * values[2];

            return new float[]{
                    values[0] - g[0],
                    values[1] - g[1],
                    values[2] - g[2]
            };
        }
    }
}
