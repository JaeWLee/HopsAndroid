package com.mineru.hops.Function;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;
import com.mineru.hops.R;

/**
 * Created by mineru on 2017-09-15.
 */

public class Hopping extends Activity implements SensorEventListener {

    private long        m_lLastTime;
    private float        m_fSpeed;
    private float        m_fCurX,  m_fCurY,  m_fCurZ;
    private float        m_fLastX,  m_fLastY,  m_fLastZ;

    private static final int  SHAKE_THRESHOLD = 1000;

    private SensorManager m_senMng;
    private Sensor m_senAccelerometer;

    Vibrator vibrator;
    long[] pattern = {0,200};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hopping);

        m_senMng = (SensorManager)getSystemService(SENSOR_SERVICE);
        m_senAccelerometer = m_senMng.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    public void onStart()
    {
        Log.i("kmsTest", "onStart()");
        super.onStart();
        if(m_senAccelerometer != null)
            m_senMng.registerListener(this, m_senAccelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    public void onStop()
    {
        Log.i("kmsTest", "onStop()");
        super.onStop();
        if(m_senMng != null)
            m_senMng.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // 반드시 제 정의가 필요한 메서드지만 이 예제에서는 사용되지 않음

    }

    public void onSensorChanged(SensorEvent event)
    {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            long lCurTime  = System.currentTimeMillis();
            long lGabOfTime  = lCurTime - m_lLastTime;

            // 0.1초보다 오래되면 다음을 수행 (100ms)
            if(lGabOfTime > 140)
            {
                m_lLastTime = lCurTime;

                m_fCurX = event.values[SensorManager.DATA_X];
                m_fCurY = event.values[SensorManager.DATA_Y];
                m_fCurZ = event.values[SensorManager.DATA_Z];

                // 변위의 절대값에  / lGabOfTime * 10000 하여 스피드 계산
                m_fSpeed = Math.abs(m_fCurX + m_fCurY + m_fCurZ - m_fLastX - m_fLastY - m_fLastZ) / lGabOfTime * 10000;

                // 임계값보다 크게 움직였을 경우 다음을 수행
                if(m_fSpeed > SHAKE_THRESHOLD)
                {
                    Log.i("kmsTest", "Shake");
                    Toast.makeText(this, "흔들기!", Toast.LENGTH_SHORT).show();
                    vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(pattern, -1);
                    Intent intent = new Intent(Hopping.this, Hopping2.class);
                    startActivity(intent);
                    finish();
                }

                // 마지막 위치 저장
                // m_fLastX = event.values[0]; 그냥 배열의 0번 인덱스가 X값
                m_fLastX = event.values[SensorManager.DATA_X];
                m_fLastY = event.values[SensorManager.DATA_Y];
                m_fLastZ = event.values[SensorManager.DATA_Z];
            }
        }
    }

    @Override
    protected void onDestroy() {
        if(vibrator != null) vibrator.cancel();
        super.onDestroy();
    }
}
