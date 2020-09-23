package com.roborosx.covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);



        TextView textView=findViewById(R.id.head);
        TextView textView1=findViewById(R.id.text);
        TextView roborosx=findViewById(R.id.roborosx);
        textView1.setAlpha(0);
        roborosx.setAlpha(0);
        roborosx.animate().alpha(2).setStartDelay(1000);
        textView1.animate().setStartDelay(2000);
        textView1.animate().alpha(2).setDuration(1500);
        textView.setAlpha(0);
        textView.animate().alpha(0.5f).setDuration(1000);
        textView.animate().alpha(1).setDuration(1000);

        new CountDownTimer(4000,1000){

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Intent intent=new Intent(MainActivity.this,NavigationActivity.class);
                startActivity(intent);
                finish();
            }
        }.start();

    }
}