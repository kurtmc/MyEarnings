package com.mcalpinedevelopment.calculatepay.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.mcalpinedevelopment.calculatepay.MainActivity;
import com.mcalpinedevelopment.calculatepay.R;
import com.mcalpinedevelopment.calculatepay.database.DetailParseException;
import com.mcalpinedevelopment.calculatepay.database.EmployeeDatabase;
import com.mcalpinedevelopment.calculatepay.database.EmployeeDetails;
import com.mcalpinedevelopment.calculatepay.database.EmployeePreferences;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;

public class PreferencesUI {
	private EditText eTName;
	private RadioButton rBWeekly;
	private RadioButton rBFortnightly;
	private RadioButton rBMonthly;
	private CheckBox cBSL;
	private RadioButton rBM;
	private RadioButton rBME;
	private RadioButton rBML;
	private RadioButton rBKSNone;

	private Button bSave;
	private EditText eTPayRate;

	private SeekBar sBKiwiSaver;
	private RadioButton rBVarKiwiSaver;
	
    private Activity _activity;
    
    @SuppressLint("NewApi")
	public PreferencesUI(Activity activity) {
    	_activity = activity;
    	
		// Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
        	_activity.getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        _activity.setContentView(R.layout.preferences);

        //Widget assignments
        eTName = (EditText)_activity.findViewById(R.id.editTextName);
        rBWeekly = (RadioButton)_activity.findViewById(R.id.radioButtonWeekly);
        rBFortnightly = (RadioButton)_activity.findViewById(R.id.radioButtonFortnightly);
        //rBMonthly = (RadioButton)_activity.findViewById(R.id.radioButtonMonthly);
        cBSL = (CheckBox)_activity.findViewById(R.id.checkBoxSL);
        rBM = (RadioButton)_activity.findViewById(R.id.radioButtonM);
        rBME = (RadioButton)_activity.findViewById(R.id.radioButtonME);
        rBML = (RadioButton)_activity.findViewById(R.id.radioButtonML);
        rBKSNone = (RadioButton)_activity.findViewById(R.id.radioButtonKSNone);
        bSave = (Button)_activity.findViewById(R.id.buttonSave);
        eTPayRate = (EditText)_activity.findViewById(R.id.editTextPayRate);

        sBKiwiSaver = (SeekBar)_activity.findViewById(R.id.seekBarKiwiSaver);
        rBVarKiwiSaver = (RadioButton)_activity.findViewById(R.id.radioButtonVaribaleKS);

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

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
        });

        //Setup preferences from previous input
        EmployeePreferences dM = readEmployeeInfo();
        eTName.setText(dM.get_name());
        if (dM.get_payPeriod().equals(EmployeeDetails.PayPeriod.WEEKLY)) {
            rBWeekly.setChecked(true);
        } else if (dM.get_payPeriod().equals(EmployeeDetails.PayPeriod.FORTNIGHTLY)) {
            rBFortnightly.setChecked(true);
        } else if (dM.get_payPeriod().equals(EmployeeDetails.PayPeriod.MONTHLY)) {
            rBMonthly.setChecked(true);
        }
        if (dM.get_studentLoan().equals(EmployeeDetails.StudentLoan.TRUE)) {
            cBSL.setChecked(true);
        } else {
            cBSL.setChecked(false);
        }
        if (dM.get_taxCode().equals(EmployeeDetails.TaxCode.M)) {
            rBM.setChecked(true);
        } else if (dM.get_taxCode().equals(EmployeeDetails.TaxCode.ME)) {
            rBME.setChecked(true);
        } else if (dM.get_taxCode().equals(EmployeeDetails.TaxCode.ML)) {
            rBML.setChecked(true);
        }
        if (dM.get_kiwiSaver().equals(EmployeeDetails.KiwiSaver.ZERO)) {
            rBKSNone.setChecked(true);            
        } else {
            rBVarKiwiSaver.setChecked(true);
            rBVarKiwiSaver.setText(dM.get_kiwiSaver()+" %");
//            sBKiwiSaver.setProgress((int)((Double.parseDouble(dM.get_kiwiSaver())-1)*100.0/7.0));
            sBKiwiSaver.setProgress((int)(((dM.get_kiwiSaver().getValue())-1)*100.0/7.0));
        }
        
        // #############################################
        // Need to re-evalute this block
        try {
//        	Double.parseDouble(dM.get_hourlyPay());
        	eTPayRate.setText(Double.toString(dM.get_hourlyPay()));
        } catch (NumberFormatException e) {
        	eTPayRate.setText("0.0");
        } catch (NullPointerException e) {
        	eTPayRate.setText("0.0");
        }    
     // #############################################

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
    private EmployeePreferences readEmployeeInfo() {   
    	// If the old preferences file exists read it and convert to EmployeePreferences object then delete the file
    	String FILENAME = "preferences.txt";
    	
    	// Determine if the old preferences file exits
    	boolean fileExists = false;
    	try {
    		FileInputStream theFile = _activity.openFileInput(FILENAME);
    		fileExists = true;
    		try {
				theFile.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	} catch (FileNotFoundException e) {
    	}
    	
    	if (fileExists) {
    		// Read old file  
    		String oldPreferences = "";
			try {
	            StringBuilder fileContent = new StringBuilder();
	            FileInputStream fis = _activity.openFileInput(FILENAME);

	            byte[] buffer = new byte[1024];
	            @SuppressWarnings("unused")
				int length;
	            while ((length = fis.read(buffer)) != -1) {
	                fileContent.append(new String(buffer));
	            }
	            fis.close();

	            oldPreferences = fileContent.toString();
	            Log.d("Old preferences", oldPreferences);
	         // Return default values if there is a problem
	        } catch (FileNotFoundException e) {
	        	Log.d("Old preferences", "FileNotFoundException");
	        } catch (IOException e) {
	        	Log.d("Old preferences", "IOException");
	        }
			
			// Create EmployeePreferences object
			String[] prefs = oldPreferences.split(",");
			EmployeePreferences employeePrefs = new EmployeePreferences();
			employeePrefs.set_name(prefs[0]);
			try {
				employeePrefs.set_payPeriod(EmployeeDetails.parsePayPeriod(prefs[1]));
			} catch (DetailParseException e) {
				employeePrefs.set_payPeriod(EmployeeDetails.PayPeriod.WEEKLY);
			}
			try {
				employeePrefs.set_studentLoan(EmployeeDetails.parseStudentLoan(prefs[2]));
			} catch (DetailParseException e) {
				employeePrefs.set_studentLoan(EmployeeDetails.StudentLoan.FALSE);
			}
			try {
				employeePrefs.set_taxCode(EmployeeDetails.parseTaxCode(prefs[3]));
			} catch (DetailParseException e) {
				employeePrefs.set_taxCode(EmployeeDetails.TaxCode.M);
			}
			try {
				employeePrefs.set_kiwiSaver(EmployeeDetails.parseKiwiSaver(prefs[4]));
			} catch (DetailParseException e) {
				employeePrefs.set_kiwiSaver(EmployeeDetails.KiwiSaver.ZERO);
			}
			try {
				employeePrefs.set_hourlyPay(Double.parseDouble(prefs[5]));
			} catch (NumberFormatException e) {
				employeePrefs.set_hourlyPay(0.0);
			}
			
			// Delete the file
			File file = new File(FILENAME);
            boolean deleted = file.delete();
            if (deleted)
            	Log.d("Old preferences","The file was successfully deleted!!!!!!!!");
    	}
    	
    	Log.d("myDatabase", "Reading database");
    	EmployeeDatabase db = EmployeeDatabase.getDatabase(_activity);
    	EmployeePreferences dM = db.getEmployeeDetails();
    	if (dM != null && dM.get_payPeriod() != null) {
    		return dM;
    	}
    	return EmployeePreferences.defaultValue();
    }
    
    private void writePreferences() {    	
		String name = eTName.getText().toString();
		EmployeeDetails.PayPeriod payPeriod = null;
		if (rBWeekly.isChecked()) {
        	payPeriod = EmployeeDetails.PayPeriod.WEEKLY;
        } else if (rBFortnightly.isChecked()) {
        	payPeriod = EmployeeDetails.PayPeriod.FORTNIGHTLY;
        } else if (rBMonthly.isChecked()) {
        	payPeriod = EmployeeDetails.PayPeriod.MONTHLY;
        }

		EmployeeDetails.StudentLoan studentLoan = null;
        if (cBSL.isChecked()) {
        	studentLoan = EmployeeDetails.StudentLoan.TRUE;
        } else {
        	studentLoan = EmployeeDetails.StudentLoan.FALSE;
        }
        
        EmployeeDetails.TaxCode taxCode = null;
        if (rBM.isChecked()) {
        	taxCode = EmployeeDetails.TaxCode.M;
        } else if (rBME.isChecked()) {
        	taxCode = EmployeeDetails.TaxCode.ME;
        } else if (rBML.isChecked()) {
        	taxCode = EmployeeDetails.TaxCode.ML;
        } 
        
        EmployeeDetails.KiwiSaver kiwiSaver = null;
        if (rBKSNone.isChecked()) {
        	kiwiSaver = EmployeeDetails.KiwiSaver.ZERO;
        } else {
            //This is really a quick fix
            //So you should probably go and fix the problem where you can potentially save a 0 length string
            //as your kiwisaver value
            try {
                kiwiSaver = EmployeeDetails.parseKiwiSaver(rBVarKiwiSaver.getText().charAt(0)+"");
            } catch (StringIndexOutOfBoundsException e) {
            	kiwiSaver = EmployeeDetails.KiwiSaver.ZERO;
            } catch (DetailParseException e) {
            	kiwiSaver = EmployeeDetails.KiwiSaver.ZERO;
            }
        }

        String payRate = eTPayRate.getText().toString();
        
        // Save preferences to database
        EmployeeDatabase db = EmployeeDatabase.getDatabase(_activity);
        Log.d("myDatabase", "Writing database");
        
        EmployeePreferences dM = new EmployeePreferences();
    	dM.set_name(name);
    	dM.set_payPeriod(payPeriod);
    	dM.set_taxCode(taxCode);
    	dM.set_kiwiSaver(kiwiSaver);
    	dM.set_studentLoan(studentLoan);
    	dM.set_hourlyPay(Double.parseDouble(payRate));
        
        db.updateData(dM);
    }
    public void goBackToMainView(View view) {
        Intent intent = new Intent(_activity, MainActivity.class);
        _activity.startActivity(intent);
    }
}
