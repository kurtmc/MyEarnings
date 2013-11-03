package com.mcalpinedevelopment.calculatepay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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

import com.mcalpinedevelopment.calculatepay.database.DetailMessage;
import com.mcalpinedevelopment.calculatepay.database.EmployeeDatabase;

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
        //rBMonthly = (RadioButton)findViewById(R.id.radioButtonMonthly);
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
//        String[] preferences = readEmployeeInfo().split(",");
        DetailMessage dM = readEmployeeInfo();
        eTName.setText(dM.get_name());
        if (dM.get_payPeriod().equals("Weekly")) {
            rBWeekly.setChecked(true);
        } else if (dM.get_payPeriod().equals("Fortnightly")) {
            rBFortnightly.setChecked(true);
        } else if (dM.get_payPeriod().equals("Monthly")) {
            rBMonthly.setChecked(true);
        }
        if (dM.get_studentLoan().equals("true")) {
            cBSL.setChecked(true);
        } else {
            cBSL.setChecked(false);
        }
        if (dM.get_taxCode().equals("M")) {
            rBM.setChecked(true);
        } else if (dM.get_taxCode().equals("ME")) {
            rBME.setChecked(true);
        } else if (dM.get_taxCode().equals("ML")) {
            rBML.setChecked(true);
        }
        if (dM.get_kiwiSaver().equals("None")) {
            rBKSNone.setChecked(true);            
        } else {
            rBVarKiwiSaver.setChecked(true);
            rBVarKiwiSaver.setText(dM.get_kiwiSaver()+" %");
            sBKiwiSaver.setProgress((int)((Double.parseDouble(dM.get_kiwiSaver())-1)*100.0/7.0));
        }
        
        try {
        	Double.parseDouble(dM.get_hourlyPay());
        	eTPayRate.setText(dM.get_hourlyPay());
        } catch (NumberFormatException e) {
        	eTPayRate.setText("0.0");
        } catch (NullPointerException e) {
        	eTPayRate.setText("0.0");
        }
        

        bSave.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                writePreferences();
                goBackToMainView(v);
            }


        });

    }

    /**
     * @return String int the format
     * Name, pay period, student loan, tax code, kiwi saver, hourly pay
     */
    private DetailMessage readEmployeeInfo() {   
    	Log.d("myDatabase", "Reading database");
    	EmployeeDatabase db = EmployeeDatabase.getDatabase(this);
    	DetailMessage dM = db.getEmployeeDetails();
    	if (dM != null && dM.get_payPeriod() != null) {
    		return dM;
    	}
    	dM = new DetailMessage();
    	dM.set_name("Enter name");
    	dM.set_payPeriod("Weekly");
    	dM.set_taxCode("M");
    	dM.set_kiwiSaver("None");
    	dM.set_studentLoan("false");
    	dM.set_hourlyPay("0.0");
    	return dM;
    }

    private void writePreferences(){
//        StringBuilder preferencesToSave = new StringBuilder();
    	
    	
        String name = eTName.getText().toString();
//        preferencesToSave.append(eTName.getText()+",");

        String payPeriod = null;
        if (rBWeekly.isChecked()) {
        	payPeriod = "Weekly";
//            preferencesToSave.append("Weekly,");
        } else if (rBFortnightly.isChecked()) {
        	payPeriod = "Fortnightly";
//            preferencesToSave.append("Fortnightly,");
        } else if (rBMonthly.isChecked()) {
        	payPeriod = "Monthly";
//            preferencesToSave.append("Monthly,");
        } //else {
//            preferencesToSave.append(",");
//        }

        String studentLoan = null;
        if (cBSL.isChecked()) {
        	studentLoan = "true";
//            preferencesToSave.append("true,");
        } else {
        	studentLoan = "false";
//            preferencesToSave.append("false,");
        }
        
        String taxCode = null;
        if (rBM.isChecked()) {
        	taxCode = "M";
//            preferencesToSave.append("M,");
        } else if (rBME.isChecked()) {
        	taxCode = "ME";
//            preferencesToSave.append("ME,");
        } else if (rBML.isChecked()) {
        	taxCode = "ML";
//            preferencesToSave.append("ML,");
        } //else {
//            preferencesToSave.append(",");
//        }
        
        String kiwiSaver = null;
        if (rBKSNone.isChecked()) {
        	kiwiSaver = "None";
//            preferencesToSave.append("None,");
        } else {
            //This is really a quick fix
            //So you should probably go and fix the problem where you can potentially save a 0 length string
            //as your kiwisaver value
            try {
                kiwiSaver = rBVarKiwiSaver.getText().charAt(0)+"";
//                preferencesToSave.append(kiwiSaver+",");
            } catch (StringIndexOutOfBoundsException e) {
            	kiwiSaver = "None";
//                preferencesToSave.append("None,");
            }
        }

        String payRate = eTPayRate.getText().toString();
//        preferencesToSave.append(eTPayRate.getText() + ",");

        
        // Save preferences to database
//        String[] employeeDetails = preferencesToSave.toString().split(",");
        EmployeeDatabase db = EmployeeDatabase.getDatabase(this);
        Log.d("myDatabase", "Writing database");
        
        DetailMessage dM = new DetailMessage();
    	dM.set_name(name);
    	dM.set_payPeriod(payPeriod);
    	dM.set_taxCode(taxCode);
    	dM.set_kiwiSaver(kiwiSaver);
    	dM.set_studentLoan(studentLoan);
    	dM.set_hourlyPay(payRate);
        
        db.updateData(dM);

        //Save preferences to local storage
//        try {
//            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
//            fos.write(preferencesToSave.toString().getBytes());
//            fos.close();
//
//
//        } catch (FileNotFoundException e) {
//            System.out.println("File not found.");
//        } catch (IOException e) {
//            System.out.println("IOException");
//        }

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

