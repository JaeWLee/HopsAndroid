package com.mineru.hops.Function;

/**
 * Created by mineru on 2017-11-24.
 */
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.mineru.hops.R;

import java.nio.charset.Charset;

public class Hopping2 extends AppCompatActivity
        implements NfcAdapter.CreateNdefMessageCallback {

    NfcAdapter mNfcAdapter;
    String webAdress="http://www.mineru.co.kr";
    String currentId="";
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hopping);
        auth = FirebaseAuth.getInstance();
        currentId=""+auth.getCurrentUser().getUid();

        mNfcAdapter =NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        mNfcAdapter.setNdefPushMessageCallback(this, this);

    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        NdefMessage msg = new NdefMessage(new NdefRecord[]{ createMimeRecord(webAdress)});
        return msg;
    }

    public NdefRecord createMimeRecord(String mimeType) {
        NdefRecord uriRecord = new NdefRecord(NdefRecord.TNF_ABSOLUTE_URI,
                mimeType.getBytes(Charset.forName("US-ASCII")),
                new byte[0], new byte[0]);
        return uriRecord;
    }
}
