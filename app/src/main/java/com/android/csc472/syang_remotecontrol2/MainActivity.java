package com.android.csc472.syang_remotecontrol2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity implements CompoundButton.OnCheckedChangeListener {
    StringBuilder channel = new StringBuilder();
    View.OnClickListener bListener;

    boolean isOn;
    private static final String TAG = "MainActivity";
    private static final int toDVR = 100; // request code
// initial favorite button channel for ABC, NBC, CBS
    String leftFavChannel = "007";
    String midFavChannel = "123";
    String rightFavChannel = "888";
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView powerStatus = findViewById(R.id.powerStatus);
        final TextView volumeStatus = findViewById(R.id.volumeStatus);
        final Button b0 = findViewById(R.id.zero);
        final Button b1 = findViewById(R.id.one);
        final Button b2 = findViewById(R.id.two);
        final Button b3 = findViewById(R.id.three);
        final Button b4 = findViewById(R.id.four);
        final Button b5 = findViewById(R.id.five);
        final Button b6 = findViewById(R.id.six);
        final Button b7 = findViewById(R.id.seven);
        final Button b8 = findViewById(R.id.eight);
        final Button b9 = findViewById(R.id.nine);
        final Button bPlus = findViewById(R.id.ChPlus);
        final Button bMinus = findViewById(R.id.ChMinus);
        final Button bABC = findViewById(R.id.ABC);
        final Button bNBC = findViewById(R.id.NBC);
        final Button bCBS = findViewById(R.id.CBS);

        Switch powerSwitch = findViewById(R.id.powerSwitch);
        final SeekBar volumeSeek = findViewById(R.id.volumeSeek);
        final ArrayList<Button> arrayButton = new ArrayList<Button>();
        arrayButton.add(b0);
        arrayButton.add(b1);
        arrayButton.add(b2);
        arrayButton.add(b3);
        arrayButton.add(b4);
        arrayButton.add(b5);
        arrayButton.add(b6);
        arrayButton.add(b7);
        arrayButton.add(b8);
        arrayButton.add(b9);
        arrayButton.add(bPlus);
        arrayButton.add(bMinus);
        arrayButton.add(bABC);
        arrayButton.add(bNBC);
        arrayButton.add(bCBS);
        volumeSeek.setEnabled(false);
        for (int i = 0; i < arrayButton.size(); i++) {
            arrayButton.get(i).setEnabled(false);
        }
//TO DVR button behavior:
        Button btoDvr = (Button) findViewById(R.id.toDVR);
        btoDvr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DvrActivity.class);
                startActivity(intent);
            }
        });
//TO to Configure button behavior:
        Button btoCon = (Button) findViewById(R.id.toConfigure);
        btoCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ConfigureActivity.class);
                startActivityForResult(intent,100);
            }
        });

//When power switch changed
        powerSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                powerStatus.setText(isChecked?"On" : "Off");
                Toast.makeText(getApplicationContext(),
                        "TV Power is " + powerStatus.getText().toString(),
                        Toast.LENGTH_SHORT).show();
                if(isChecked) {
                    for (int i = 0; i < arrayButton.size(); i++) {
                        arrayButton.get(i).setEnabled(true);
                    }
                    volumeSeek.setEnabled(true);
                    bListener = new View.OnClickListener() {
                        public void onClick (View v){
                            Button b = (Button) v;
                            if (channel.length() < 3) {
                                channel.append(b.getText());
                            }
                            validateChannel();
                        }
                    };
                }
                else {
                    volumeSeek.setEnabled(false);
                    for (int i = 0; i < arrayButton.size(); i++) {
                        arrayButton.get(i).setEnabled(false);
                    }
                    bListener = null;
                }
                for (int i = 0; i < 10; i++) {
                    arrayButton.get(i).setOnClickListener(bListener);}
            }

        });
//When volume seek changed
        volumeSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                    Log.d(TAG, "onProgressChanged");
                TextView powerStatus = findViewById(R.id.powerStatus);
                if (powerStatus.getText() == "On") {
                    volumeStatus.setText(i + "");
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
//                    Log.d(TAG, "onStartTrackingTouch");
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
//                    Log.d(TAG, "onStopTrackingTouch");
            }
        });
//When CH+ button pressed
        bPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView channelStatus = findViewById(R.id.channelStatus);
                TextView powerStatus = findViewById(R.id.powerStatus);
                int intChannel = Integer.parseInt(channelStatus.getText().toString());
                if (powerStatus.getText() == "On") {
                    if (intChannel < 999) {
                        intChannel++;
                        channelStatus.setText(intChannel + "");
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"End of channel range",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
//When CH- button pressed
        bMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView channelStatus = findViewById(R.id.channelStatus);
                TextView powerStatus = findViewById(R.id.powerStatus);
                int intChannel = Integer.parseInt(channelStatus.getText().toString());
                if (powerStatus.getText() == "On") {
                    if (intChannel > 1) {
                        intChannel--;
                        channelStatus.setText(intChannel + "");
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"End of channel range",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
//When ABC button pressed
        bABC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView channelStatus = findViewById(R.id.channelStatus);
                TextView powerStatus = findViewById(R.id.powerStatus);
                if (powerStatus.getText() == "On") {
                    channelStatus.setText(leftFavChannel);
                    channel.setLength(0);
                }
            }
        });
//When NBC button pressed
        bNBC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView channelStatus = findViewById(R.id.channelStatus);
                TextView powerStatus = findViewById(R.id.powerStatus);
                if (powerStatus.getText() == "On") {
                    channelStatus.setText(midFavChannel);
                    channel.setLength(0);
                }
            }
        });
//When CBS button pressed
        bCBS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView channelStatus = findViewById(R.id.channelStatus);
                TextView powerStatus = findViewById(R.id.powerStatus);
                if (powerStatus.getText() == "On") {
                    channelStatus.setText(rightFavChannel);
                    channel.setLength(0);
                }
            }
        });
    }//end of onCreate
// help methods
    public void validateChannel(){

        if (channel.length() == 3) {
            int channelValue = Integer.parseInt(channel.toString());

            if (channelValue > 0 && channelValue <= 999) {
                final TextView channelStatus = findViewById(R.id.channelStatus);
                channelStatus.setText(channel);
            }
            else {
                Toast.makeText(getApplicationContext(),"invalid channel",Toast.LENGTH_SHORT).show();
            }
            channel.setLength(0);
        }
    }
    @Override
//Connection to other activities
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == toDVR) {
            if (resultCode == RESULT_OK) {
                String label = data.getStringExtra("Label");
                String num =  data.getStringExtra("Num");
                String location = data.getStringExtra("Location").toString();

                if(location.equals("Left")) {
                    Button bLeft = findViewById(R.id.ABC);
                    bLeft.setText(label);
                    leftFavChannel = num;
                }
                else if(location.equals("Middle")) {
                    Button bMid = findViewById(R.id.NBC);
                    bMid.setText(label);
                    midFavChannel = num;
                }
                else if(location.equals("Right")) {
                    Button bRight = findViewById(R.id.CBS);
                    bRight.setText(label);
                    rightFavChannel = num;
                }
                Toast.makeText(this, "Favorite channel on " + location +
                        " set to: " + label, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "No update for the favorite channel", Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    public void onCheckedChanged(CompoundButton button, boolean isChecked) {

    }
    public void onSwitchClicked(View view) {
    }

}

