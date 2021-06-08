package io.procq.osukeypad;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.Buffer;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    String ip = "192.168.0.101";


    private void DoSend(int keyid) { // this does not work properly yet
        //add 2 to keyid depending which button is pressed
        Thread t = new Thread(new SendDataRunnable(keyid, ip));
        t.start();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String FILENAME = "osu!keypad.config";

        View mainView = findViewById(R.id.mainScreen);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        final Button button = (Button) findViewById(R.id.btnConnect);
        final Button buttonSave = (Button) findViewById(R.id.btnSave);
        final Button buttonLoad = (Button) findViewById(R.id.btnLoad);
        final EditText editText = (EditText) findViewById(R.id.editTextIp);

        final Button buttonA = (Button) findViewById(R.id.buttonA);
        final Button buttonB = (Button) findViewById(R.id.buttonB);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
                    ip = editText.getText().toString();
                    fos.write(ip.getBytes());

                    Toast.makeText(getApplicationContext(), ip, Toast.LENGTH_SHORT).show();

                }catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Something went wrong with saving config.", Toast.LENGTH_SHORT).show();
                }


            }
        });
        buttonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileInputStream fis = openFileInput(FILENAME);
                    int ch;
                    String ip = "";
                    while ((ch = fis.read() )!= -1) {
                        ip += (char)ch;
                    }
                    editText.setText(ip);

                }catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Something went wrong with reading config.", Toast.LENGTH_SHORT).show();
                }


            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ip = editText.getText().toString();


                try {
                    InetAddress.getByName(ip);
                    Toast.makeText(getApplicationContext(), "Ip correctly saved.", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Ip you entered is wrong.", Toast.LENGTH_SHORT).show();
                }

            }
        });



        buttonA.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_POINTER_DOWN) {
                    DoSend(0);
                    return true;
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_POINTER_UP) {
                    DoSend(1);
                    return true;
                }
                return true;
            }

        });


        buttonB.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_POINTER_DOWN) {
                    DoSend(2);
                    return true;
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_POINTER_UP) {
                    DoSend(3);
                    return true;
                }
                return true;
            }
        });





    }

}