package com.example.smsactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 11;
    EditText textCall;
    EditText textSMS;
    Button btnCall;
    Button btnSMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        callByNumber();
    }

    public void callByNumber() {
        btnCall = findViewById(R.id.btnCall);
        btnSMS = findViewById(R.id.buttonSMS);
        textCall = findViewById(R.id.EditTextCall);
        textSMS = findViewById(R.id.EditTextSMS);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission
                        (MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions
                            (MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
                } else {
                    String number = textCall.toString();
                    Uri call =Uri.parse("tel:" + number);
                    Intent intent = new Intent(Intent.ACTION_CALL,call);


//                    intent.setData(Uri.parse("tel: "+ number));
                    if (intent.resolveActivity(getPackageManager())!=null){
                        startActivity(intent);
                    }
                    else {
                        Log.d("Intent","ActivityNotFound");
                    }

                }

            }


        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                if (grantResults.length > 0 && grantResults[0] == getPackageManager().PERMISSION_GRANTED) {
                    callByNumber();
                } else {
                    finish();
                }
            }
        }
    }
}