package edu.uic.cs478.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;

public class SecondActivity extends AppCompatActivity {
    //private static final String TAG = "MyActivity-2";
    static String var;
    private EditText phoneNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        phoneNum = findViewById(R.id.PhoneNumber);


    }

    @Override
    public void onBackPressed() {
        //Log.d(TAG, "On HERE ");
        Intent intent = new Intent();
        intent.putExtra("result", phoneNum.getText().toString());
        setResult(RESULT_OK, intent);

        SecondActivity.super.onBackPressed();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
                Intent intent = new Intent();
                var = phoneNum.getText().toString();
                //intent.putExtra("result", var);
                setResult(RESULT_OK, intent);

                finish();
            default:
                return super.onKeyUp(keyCode, event);
        }

    }
}