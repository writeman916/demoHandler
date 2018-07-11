package com.example.kuroshiro.demohandler;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    Button btCount;
    TextView txtNum;
    ProgressBar proG;
    Handler mHander;
    static int MESSAGE_COUNT_DOWN = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        btCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Count();
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private void init()
    {
        btCount = findViewById(R.id.btCount);
        txtNum = findViewById(R.id.txtNum);
        proG = findViewById(R.id.progressBar);
        mHander = new Handler(){
            @Override
            public void handleMessage(Message msg) {
//                if(msg.what == 0)
//                {
//                    txtNum.setText(String.valueOf(msg.arg1));
//                }

                txtNum.setText(msg.obj.toString());
            }
        };
    }

    private void Count()
    {
        Thread mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int time = Integer.parseInt(txtNum.getText().toString());
                do {
                    time--;

                    Message msg = new Message();
//                    msg.what = MESSAGE_COUNT_DOWN;
//                    msg.arg1 = time;

                    msg.obj = time;
                    mHander.sendMessage(msg);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }while(time>0);
            }
        });
        mThread.start();
        Thread mThread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<101;i++)
                {
                    final int value = i;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mHander.post(new Runnable() {
                        @Override
                        public void run() {
                            proG.setProgress(value);
                        }
                    });
                }
            }
        });
        mThread2.start();
    }
}
