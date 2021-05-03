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
    private static final int MY_PERMISSIONS_REQUEST_SMS = 12;
    EditText textCall;
    EditText textSMS;
    Button btnCall;
    Button btnSMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        callByNumber();
        sendSmsText();
    }

    public void callByNumber() {
        btnCall = findViewById(R.id.btnCall);
        textCall = findViewById(R.id.editTextCall);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission
                        (MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions
                            (MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
                } else {
                    String number = textCall.getText().toString();
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
    public void sendSmsText(){
        btnSMS = findViewById(R.id.buttonSMS);
        textSMS = findViewById(R.id.editTextSMS);
        textCall = findViewById(R.id.editTextCall);
        btnSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission
                        (MainActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions
                            (MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SMS);
                } else {
                    String number = textCall.getText().toString();

                    String text = textSMS.getText().toString();

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("smsto:" + number)) ;
                    intent.setType("vnd.android-dir/mms-sms");
                    intent.putExtra("address", number);
                    intent.putExtra("sms_body", text);




//                    intent.setData(Uri.parse("tel: "+ number));
                    if (intent.resolveActivity(getPackageManager())!=null){
                        startActivity(intent.createChooser(intent,"Send SMS with "));
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