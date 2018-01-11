package com.mineru.hops;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by mineru on 2018-01-10.
 */

public class IncomingCallBroadcastReceiver extends BroadcastReceiver {
    public static final String TAG = "PHONE STATE";
    private static String mLastState;

    private final Handler mHandler = new Handler(Looper.getMainLooper());
    @Override public void onReceive(final Context context, Intent intent) {
        Log.d(TAG,"onReceive()");
        /** * http://mmarvick.github.io/blog/blog/lollipop-multiple-broadcastreceiver-call-state/ * 2번 호출되는 문제 해결 */
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        mLastState = state;

        if (state.equals(mLastState))
            return;
        else
            mLastState = state;


        if (mLastState.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            Log.d(TAG,"Success");
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            final String phone_number = PhoneNumberUtils.formatNumber(incomingNumber);
            Intent serviceIntent = new Intent(context, CallingService.class);
            serviceIntent.putExtra(CallingService.EXTRA_CALL_NUMBER, phone_number);
            context.startService(serviceIntent);
        }
    }

}
