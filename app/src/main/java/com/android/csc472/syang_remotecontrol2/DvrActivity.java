package com.android.csc472.syang_remotecontrol2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class DvrActivity extends AppCompatActivity {

    boolean isPlay;
    boolean isRecord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dvr);

//Switch Back to TV button behavior:
        Button bToTV = (Button) findViewById(R.id.backToTV);
        bToTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();//pop activity on stack
            }
        });
//POWER Switch behavior:
        Switch dvrSwitch = (Switch) findViewById(R.id.dvrPowerSwitch);
        final TextView dvrPowerStatus = (TextView) findViewById(R.id.dvrPowerStatus);
        final TextView dvrState = (TextView) findViewById(R.id.dvrState_Status);

//PLAY button behavior:
        final Button bPlay = (Button)findViewById(R.id.bPlay);
        bPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPlay = true;
                TextView dvrPowerStatus = (TextView) findViewById(R.id.dvrPowerStatus);
                if (dvrPowerStatus.getText() == "On"){
                    if (!isRecord) {
                        dvrState.setText("Playing");
                    }
                    else {
                        warnMessageShow();
                    }
                }
            }
        });
//STOP button behavior:
        final Button bStop = (Button)findViewById(R.id.bStop);
        bStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView dvrPowerStatus = (TextView) findViewById(R.id.dvrPowerStatus);
                if (dvrPowerStatus.getText() == "On") {
                    dvrState.setText("Stopped");
                    isPlay = false;
                    isRecord = false;
                }
            }
        });
//PAUSE button behavior:
        final Button bPause = (Button)findViewById(R.id.bPause);
        bPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView dvrPowerStatus = (TextView) findViewById(R.id.dvrPowerStatus);
                if (dvrPowerStatus.getText() == "On"){
                    if (isPlay && !isRecord) {
                        dvrState.setText("Paused");
                        isPlay = false;
                    }
                    else {
                        warnMessageShow();
                    }
                }
            }
        });
//FAST FORWARD button behavior:
        final Button bForward = (Button)findViewById(R.id.bForward);
        bForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView dvrPowerStatus = (TextView) findViewById(R.id.dvrPowerStatus);
                if (dvrPowerStatus.getText() == "On"){
                    if (isPlay && !isRecord) {
                        dvrState.setText("Fast Forward");
                        isPlay = false;
                    }
                    else {
                        warnMessageShow();
                    }
                }
            }
        });
//FAST REWIND button behavior:
        final Button bRewind = (Button)findViewById(R.id.bRewind);
        bRewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView dvrPowerStatus = (TextView) findViewById(R.id.dvrPowerStatus);
                if (dvrPowerStatus.getText() == "On"){
                    if (isPlay && !isRecord) {
                        dvrState.setText("Fast Rewind");
                        isPlay = false;
                    }
                    else {
                        warnMessageShow();
                    }
                }
            }
        });
//Record button behavior:
        final Button bRecord = (Button)findViewById(R.id.bRecord);
        bRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView dvrPowerStatus = (TextView) findViewById(R.id.dvrPowerStatus);
                //wrong toast not show
                if (dvrPowerStatus.getText() == "On") {
                    if (dvrState.getText().toString() == "Stopped"){
                        dvrState.setText("Recording");
                        Toast.makeText(getApplicationContext(),
                                "Recording", Toast.LENGTH_SHORT).show();
                        isRecord = true;
                    }
                    else {
                        warnMessageShow();
                    }
                }
            }
        });
        //initial button to false
        bPlay.setEnabled(false);
        bForward.setEnabled(false);
        bPause.setEnabled(false);
        bRewind.setEnabled(false);
        bStop.setEnabled(false);
        bRecord.setEnabled(false);
        dvrSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                dvrPowerStatus.setText(isChecked ? "On" : "Off");
                Toast.makeText(getApplicationContext(),
                        "DVR Power is " + dvrPowerStatus.getText().toString(),
                        Toast.LENGTH_SHORT).show();
                if(isChecked) {
                    bPlay.setEnabled(true);
                    bForward.setEnabled(true);
                    bPause.setEnabled(true);
                    bRewind.setEnabled(true);
                    bStop.setEnabled(true);
                    bRecord.setEnabled(true);
                }
                else {
                    bPlay.setEnabled(false);
                    bForward.setEnabled(false);
                    bPause.setEnabled(false);
                    bRewind.setEnabled(false);
                    bStop.setEnabled(false);
                    bRecord.setEnabled(false);
                }
                dvrState.setText("Stopped");
                isPlay = false;
                isRecord = false;
            }
        });
    }
    public void warnMessageShow() {
        Toast.makeText(getApplicationContext(),
                "unavailable under current status", Toast.LENGTH_SHORT).show();
    }
}

