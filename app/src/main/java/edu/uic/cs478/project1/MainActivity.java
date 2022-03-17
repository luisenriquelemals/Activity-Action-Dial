package edu.uic.cs478.project1;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";

    private Button button1;
    private Button button2;
    private String validNumber;
    private boolean goDialer = false;

    /**Create an Activity to get the information of the second activity */
    ActivityResultLauncher<Intent> phoneNumberActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    /** Create message */
                    Context context = getApplicationContext();
                    CharSequence text = "Valid Phone Number";
                    CharSequence text2 = "Invalid Phone Number";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    Toast toast2 = Toast.makeText(context, text2, duration);
                    /** Create message */

                    /** Check the Result code and getting the intent*/
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        if (intent != null) {
                            String data =  intent.getStringExtra("result");
                            //Log.d(TAG, " On Data: "+ num);
                            /** Check the number, if it is true show message or it is not show message 2*/
                            boolean var = checkPhoneNumber();
                            if (var) {
                                toast.show();
                                validNumber = data;
                                goDialer = true;
                            }else {
                                goDialer = false;
                                toast2.show();
                            }


                        }
                    }

                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.button1P);
        button2 = findViewById(R.id.button2P);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenSecondActivity();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validNumber = SecondActivity.var;
                if (goDialer) {
                    Intent intent2 = new Intent(Intent.ACTION_DIAL);
                    intent2.setData(Uri.parse("tel:"+validNumber));
                    startActivity(intent2);
                }else {
                    /** Create message */
                    Context context = getApplicationContext();
                    CharSequence text = "Invalid Number, enter a valid Number";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

            }
        });


    }

    public void OpenSecondActivity () {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        phoneNumberActivity.launch(intent);

    }

    public boolean checkPhoneNumber () {

        String data = SecondActivity.var;

        /** One special case of the parenthesis example: (123 856 55 --> no valid*/
        if ( (data.contains("(") && !data.contains(")")) || (data.contains(")") && !data.contains("(") )) {
                return false;
        }
        /** validating the phone number with pattern */
        Pattern p = Pattern.compile("^\\(?\\d{3}\\)?\\s?-?\\d{3}\\s?-?\\d{3}$");
        Matcher m = p.matcher(data);
        return m.matches();
    }


}