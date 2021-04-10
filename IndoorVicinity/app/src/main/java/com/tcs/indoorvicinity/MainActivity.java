package com.tcs.indoorvicinity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private float x1,x2;
    static final int MIN_DISTANCE = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;

                if (Math.abs(deltaX) > MIN_DISTANCE)
                {
                    // Left to Right swipe action
                    if (x2 > x1)
                    {
                      //  System.out.println("Left to right");
                        Intent i =new Intent(MainActivity.this,com.tcs.indoorvicinity.Login.class);
                        startActivity(i);
                    }

                    // Right to left swipe action
                    else
                    {
                        //System.out.println("Right to left");
                        Intent i =new Intent(MainActivity.this,com.tcs.indoorvicinity.UserHome.class);
                        startActivity(i);

                    }

                }
                else
                {
                    // consider as something else - a screen tap for example
                    System.out.println("Something else");
                    Toast.makeText(this, "Please swipe to right for vendor login", Toast.LENGTH_LONG).show();
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}

