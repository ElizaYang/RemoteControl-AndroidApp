package com.android.csc472.syang_remotecontrol2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class ConfigureActivity extends AppCompatActivity implements
        CompoundButton.OnCheckedChangeListener{
    private static final String TAG = "ConfigureActivity";

    StringBuilder channel = new StringBuilder();
    String bLocation = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure);
        RadioButton radioLeft = (RadioButton)findViewById(R.id.radioLeft);
        RadioButton radioMid = (RadioButton)findViewById(R.id.radioMiddle);
        RadioButton radioRight = (RadioButton)findViewById(R.id.radioRight);
        radioLeft.setOnCheckedChangeListener(this);
        radioMid.setOnCheckedChangeListener(this);
        radioRight.setOnCheckedChangeListener(this);

        final Button b0 = (Button)findViewById(R.id.b0);
        final Button b1 = (Button)findViewById(R.id.b1);
        final Button b2 = (Button)findViewById(R.id.b2);
        final Button b3 = (Button)findViewById(R.id.b3);
        final Button b4 = (Button)findViewById(R.id.b4);
        final Button b5 = (Button)findViewById(R.id.b5);
        final Button b6 = (Button)findViewById(R.id.b6);
        final Button b7 = (Button)findViewById(R.id.b7);
        final Button b8 = (Button)findViewById(R.id.b8);
        final Button b9 = (Button)findViewById(R.id.b9);
        final Button bPlus = (Button)findViewById(R.id.bChPlus);
        final Button bMinus = (Button)findViewById(R.id.bChMinus);
        View.OnClickListener bListener = new View.OnClickListener() {
            public void onClick (View v){
                Button b = (Button) v;
                if (channel.length() < 3) {
                    channel.append(b.getText());
                }
                validateChannel();
            }
        };
        b0.setOnClickListener(bListener);
        b1.setOnClickListener(bListener);
        b2.setOnClickListener(bListener);
        b3.setOnClickListener(bListener);
        b4.setOnClickListener(bListener);
        b5.setOnClickListener(bListener);
        b6.setOnClickListener(bListener);
        b7.setOnClickListener(bListener);
        b8.setOnClickListener(bListener);
        b9.setOnClickListener(bListener);
//When CH+ button pressed
        bPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView newChannelNum = (TextView) findViewById(R.id.customChannelNum);
                if (newChannelNum.length() == 0 || newChannelNum == null) {// when not channel value input
                    Toast.makeText(getApplicationContext(),"Enter channel value first",Toast.LENGTH_SHORT).show();
                }
                else {
                    int intChannel = Integer.parseInt(newChannelNum.getText().toString());
                    if (intChannel < 999) {
                        intChannel++;
                        newChannelNum.setText(intChannel + "");
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
                TextView newChannelNum = (TextView) findViewById(R.id.customChannelNum);
                if (newChannelNum.length() == 0 || newChannelNum == null) {// when not channel value input
                    Toast.makeText(getApplicationContext(),"Enter channel value first",Toast.LENGTH_SHORT).show();
                }
                else {
                    int intChannel = Integer.parseInt(newChannelNum.getText().toString());
                    if (intChannel > 1) {
                        intChannel--;
                        newChannelNum.setText(intChannel + "");
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"End of channel range",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
//SAVE button:
        Button save = (Button)findViewById(R.id.bSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText newChannelLabel = (EditText)findViewById(R.id.customLabelInput);
                TextView newChannelNum = (TextView) findViewById(R.id.customChannelNum);
                if (bLocation == null || bLocation.equals("")) {
                    Toast.makeText(getApplicationContext(),"Please select favorite button location",Toast.LENGTH_SHORT).show();
                }
                else if (newChannelLabel.length()<2 || newChannelLabel.length()>4){
                    Toast.makeText(getApplicationContext(),"Label length must between 2-4",Toast.LENGTH_SHORT).show();
                }
                else if (newChannelNum.length()== 0 || newChannelNum == null){
                    Toast.makeText(getApplicationContext(),"Channel number can't be empty",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("Label",newChannelLabel.getText().toString());
                    returnIntent.putExtra("Num",newChannelNum.getText().toString());
                    returnIntent.putExtra("Location",bLocation);
                    setResult(RESULT_OK,returnIntent);
                    finish();//pop activity on stack
                }
            }
        });

//CANCEL button: pop current activity
        Button bCancel = (Button) findViewById(R.id.bCancel);
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();//pop activity on stack
            }
        });
    }
    public void validateChannel(){
        if (channel.length() == 3) {
            int channelValue = Integer.parseInt(channel.toString());

            if (channelValue > 0 && channelValue <= 999) {
                TextView customChannelNum = (TextView) findViewById(R.id.customChannelNum);
                customChannelNum.setText(channel);
            }
            else {
                Toast.makeText(getApplicationContext(),"invalid channel",Toast.LENGTH_SHORT).show();
            }
            channel.setLength(0);
        }
    }
    public void onRadioButtonClicked(View view) {
        RadioButton radioButton = (RadioButton) view;
//        Log.d(TAG, "onRadioButtonClicked() " + radioButton.getText() + " " + (radioButton.isChecked() ? "selected" : "unselected"));
    }
    @Override
    public void onCheckedChanged(CompoundButton button, boolean isChecked) {
        Toast.makeText(this, button.getText() + " is " + (isChecked ? "checked" : "unchecked"),
                Toast.LENGTH_SHORT).show();
        bLocation = button.getText().toString();
    }
}
