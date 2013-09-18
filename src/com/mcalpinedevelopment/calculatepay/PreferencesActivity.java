package com.mcalpinedevelopment.calculatepay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by kurt on 26/05/13.
 */
public class PreferencesActivity extends Activity {
    EditText eTName;
    RadioButton rBWeekly;
    RadioButton rBFortnightly;
    RadioButton rBMonthly;
    CheckBox cBSL;
    RadioButton rBM;
    RadioButton rBME;
    RadioButton rBML;
    RadioButton rBKSNone;

    Button bSave;
    EditText eTPayRate;

    SeekBar sBKiwiSaver;
    RadioButton rBVarKiwiSaver;

    String FILENAME = "preferences.txt";

    //FileReader to read preferences.txt
    FileReader _fileReader;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        // Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setContentView(R.layout.preferences);

        //Instantiate FileReader
        _fileReader = new FileReader(this);

        //Widget assignments
        eTName = (EditText)findViewById(R.id.editTextName);
        rBWeekly = (RadioButton)findViewById(R.id.radioButtonWeekly);
        rBFortnightly = (RadioButton)findViewById(R.id.radioButtonFortnightly);
        rBMonthly = (RadioButton)findViewById(R.id.radioButtonMonthly);
        cBSL = (CheckBox)findViewById(R.id.checkBoxSL);
        rBM = (RadioButton)findViewById(R.id.radioButtonM);
        rBME = (RadioButton)findViewById(R.id.radioButtonME);
        rBML = (RadioButton)findViewById(R.id.radioButtonML);
        rBKSNone = (RadioButton)findViewById(R.id.radioButtonKSNone);
        bSave = (Button)findViewById(R.id.buttonSave);
        eTPayRate = (EditText)findViewById(R.id.editTextPayRate);

        sBKiwiSaver = (SeekBar)findViewById(R.id.seekBarKiwiSaver);
        rBVarKiwiSaver = (RadioButton)findViewById(R.id.radioButtonVaribaleKS);

        //Set up sBKiwiSaver (SeekBar) to change the percentage of kiwisaver to be paid
        sBKiwiSaver.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                double segment = 100/7;
                if (progress <= segment/2) {
                    rBVarKiwiSaver.setText("1 %");
                } else if (progress > segment/2 && progress <= 3*segment/2) {
                    rBVarKiwiSaver.setText("2 %");
                } else if (progress > 3*segment/2 && progress <= 5*segment/2) {
                    rBVarKiwiSaver.setText("3 %");
                } else if (progress > 5*segment/2 && progress <= 7*segment/2) {
                    rBVarKiwiSaver.setText("4 %");
                } else if (progress > 7*segment/2 && progress <= 9*segment/2) {
                    rBVarKiwiSaver.setText("5 %");
                }else if (progress > 9*segment/2 && progress <= 11*segment/2) {
                    rBVarKiwiSaver.setText("6 %");
                }else if (progress > 11*segment/2 && progress <= 13*segment/2) {
                    rBVarKiwiSaver.setText("7 %");
                }else if (progress > 13*segment/2 && progress <= 14*segment/2) {
                    rBVarKiwiSaver.setText("8 %");
                }
            }

            public void onStartTrackingTouch(SeekBar arg0) {
                // TODO Auto-generated method stub

            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

        });

        //Setup preferences from previous input
        String[] preferences = readText().split(",");
        eTName.setText(preferences[0]);
        if (preferences[1].equals("Weekly")) {
            rBWeekly.setChecked(true);
        } else if (preferences[1].equals("Fortnightly")) {
            rBFortnightly.setChecked(true);
        } else if (preferences[1].equals("Monthly")) {
            rBMonthly.setChecked(true);
        }
        if (preferences[2].equals("true")) {
            cBSL.setChecked(true);
        } else {
            cBSL.setChecked(false);
        }
        if (preferences[3].equals("M")) {
            rBM.setChecked(true);
        } else if (preferences[3].equals("ME")) {
            rBME.setChecked(true);
        } else if (preferences[3].equals("ML")) {
            rBML.setChecked(true);
        }
        if (preferences[4].equals("None")) {
            rBKSNone.setChecked(true);
        } else {

            rBVarKiwiSaver.setChecked(true);
            rBVarKiwiSaver.setText(preferences[4]+" %");
            sBKiwiSaver.setProgress((int)((Double.parseDouble(preferences[4])-1)*100.0/7.0));
        }
        eTPayRate.setText(preferences[5]);

        bSave.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                writePreferences();
                goBackToMainView(v);
            }


        });

    }

    private String readText() {   
    	return _fileReader.readPreferences();
//        try {
//
//            StringBuilder fileContent = new StringBuilder();
//            FileInputStream fis = openFileInput(FILENAME);
//
//            byte[] buffer = new byte[1024];
//            int length;
//            while ((length = fis.read(buffer)) != -1) {
//                fileContent.append(new String(buffer));
//            }
//            fis.close();
//
//            return fileContent.toString();
//
//        } catch (FileNotFoundException e) {
//            return "Enter Name,Weekly,false,M,None,0.0";
//        } catch (IOException e) {
//            return "Enter Name,Weekly,false,M,None,0.0";
//        }

        /*
        String line = "";
        InputStream is = getResources().openRawResource(R.raw.preferences);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
            try {
                line = br.readLine();
            } catch (IOException e) {

            }
        return line;
        */
    }

    private void writePreferences(){
        StringBuilder preferencesToSave = new StringBuilder();

        preferencesToSave.append(eTName.getText()+",");

        if (rBWeekly.isChecked()) {
            preferencesToSave.append("Weekly,");
        } else if (rBFortnightly.isChecked()) {
            preferencesToSave.append("Fortnightly,");
        } else if (rBMonthly.isChecked()) {
            preferencesToSave.append("Monthly,");
        } else {
            preferencesToSave.append(",");
        }

        if (cBSL.isChecked()) {
            preferencesToSave.append("true,");
        } else {
            preferencesToSave.append("false,");
        }
        if (rBM.isChecked()) {
            preferencesToSave.append("M,");
        } else if (rBME.isChecked()) {
            preferencesToSave.append("ME,");
        } else if (rBML.isChecked()) {
            preferencesToSave.append("ML,");
        } else {
            preferencesToSave.append(",");
        }
        if (rBKSNone.isChecked()) {
            preferencesToSave.append("None,");
        } else {
            //This is really a quick fix
            //So you should probably go and fix the problem where you can potentially save a 0 length string
            //as your kiwisaver value
            try {
                String kiwiSaver = rBVarKiwiSaver.getText().charAt(0)+"";
                preferencesToSave.append(kiwiSaver+",");
            } catch (StringIndexOutOfBoundsException e) {
                preferencesToSave.append("None,");
            }
        }

        preferencesToSave.append(eTPayRate.getText() + ",");



        //Save preferences to local storage
        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(preferencesToSave.toString().getBytes());
            fos.close();


        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (IOException e) {
            System.out.println("IOException");
        }

    }
    public void goBackToMainView(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //Overriding back button
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent = new Intent(this, MainActivity.class);
            String message = "";
            intent.putExtra("", message);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }



}

