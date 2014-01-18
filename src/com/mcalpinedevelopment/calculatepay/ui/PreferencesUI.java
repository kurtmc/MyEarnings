package com.mcalpinedevelopment.calculatepay.ui;

import java.util.ArrayList;
import java.util.List;

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
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.ToggleButton;

public class PreferencesUI {	
	
	// Name	
	private EditText eTName;
	
	// Pay period buttons
	private ToggleButton tbWeekly;
	private ToggleButton tbFortnightly;
	private ToggleButton tbMonthly;
	private ImageButton ibPayPeriodHelp;
	
	// Tax code buttons
	private ToggleButton tbTaxCodeM;
	private ToggleButton tbTaxCodeME;
	private ImageButton ibTaxCodeHelp;
	
	
	// Kiwi Saver widgets
	private NumberPicker npKiwiSaver;
	private ImageButton ibKiwiSaverHelp;
	
	//Student Loan buttons
	private ToggleButton tbStudentLoan;
	private ImageButton ibStudentLoanHelp;
	
	// Pay rate widgets
	private EditText eTPayRate;
	private ImageButton ibPayRateHelp;
	
	//Save button
	private Button bSave;
	
	// Manage pay period buttons list
	private List<ToggleButton> _listOfPayPeriodButtons;
	
	// Manage Tax code buttons list
	private List<ToggleButton> _listOfTaxCodeButtons;
	
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
        
        initialiseWidgets();
        manageWidgets();
        
        npKiwiSaver = (NumberPicker)_activity.findViewById(R.id.npKiwiSaver);
        npKiwiSaver.setMaxValue(8);
        npKiwiSaver.setMinValue(0);
        
        bSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                writePreferences();
                goBackToMainView(v);
            }
        });
        

        setActivityValues();
	}
    
    private void manageWidgets() {
    	// Pay period
		tbWeekly.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				updateToggleButtons(buttonView, isChecked, _listOfPayPeriodButtons);
			}});
		
		tbFortnightly.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				updateToggleButtons(buttonView, isChecked, _listOfPayPeriodButtons);
			}});
		
		tbMonthly.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				updateToggleButtons(buttonView, isChecked, _listOfPayPeriodButtons);
			}});
		
		// Tax code
		tbTaxCodeM.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				updateToggleButtons(buttonView, isChecked, _listOfTaxCodeButtons);
			}});
		tbTaxCodeME.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				updateToggleButtons(buttonView, isChecked, _listOfTaxCodeButtons);
			}});
		
	}
    
    private void updateToggleButtons(CompoundButton buttonView, boolean isChecked, List<ToggleButton> listOfManagedButtons) {
    	if (isChecked) { // If it is checked set the others to false
			for (ToggleButton b : listOfManagedButtons ) {					
					if (buttonView.equals(b)) {
						
					} else {
						b.setChecked(false);
					}
				} 
			} else { // Else check that atleast another is checked else set to checked
				boolean anotherChecked = false;
				for (ToggleButton b : listOfManagedButtons ) {
					if (b.isChecked()) {
						anotherChecked = true;
					}
				}
				if (!anotherChecked) {
					buttonView.setChecked(true);
				}
			}
    }

	private void initialiseWidgets() {
    	
    	// Name
    	eTName = (EditText)_activity.findViewById(R.id.editTextName);
    	
    	// Pay period buttons
    	tbWeekly = (ToggleButton)_activity.findViewById(R.id.tbWeekly);
    	tbFortnightly = (ToggleButton)_activity.findViewById(R.id.tbFortnightly);
    	tbMonthly = (ToggleButton)_activity.findViewById(R.id.tbMonthly);
    	ibPayPeriodHelp = (ImageButton)_activity.findViewById(R.id.bHelpPayPeriod);
    	
    	// Manage pay period list
    	_listOfPayPeriodButtons = new ArrayList<ToggleButton>();
    	_listOfPayPeriodButtons.add(tbWeekly);
    	_listOfPayPeriodButtons.add(tbFortnightly);
    	_listOfPayPeriodButtons.add(tbMonthly);
    	
    	// Tax code buttons
    	tbTaxCodeM = (ToggleButton)_activity.findViewById(R.id.tbTaxCodeM);
    	tbTaxCodeME = (ToggleButton)_activity.findViewById(R.id.tbTaxCodeME);
    	ibTaxCodeHelp = (ImageButton)_activity.findViewById(R.id.bHelpTaxCode);
    	
    	_listOfTaxCodeButtons = new ArrayList<ToggleButton>();
    	_listOfTaxCodeButtons.add(tbTaxCodeM);
    	_listOfTaxCodeButtons.add(tbTaxCodeME);
    	
    	
    	
    	
    	// Kiwi Saver widgets
    	npKiwiSaver = (NumberPicker)_activity.findViewById(R.id.npKiwiSaver);
    	ibKiwiSaverHelp = (ImageButton)_activity.findViewById(R.id.bHelpKiwiSaver);
    	
    	//Student Loan buttons
    	tbStudentLoan = (ToggleButton)_activity.findViewById(R.id.tbStudentLoan);
    	ibStudentLoanHelp = (ImageButton)_activity.findViewById(R.id.bHelpStudentLoan);
    	
    	// Pay rate widgets
    	eTPayRate = (EditText)_activity.findViewById(R.id.editTextPayRate);
    	ibPayRateHelp = (ImageButton)_activity.findViewById(R.id.bHelpPayRate);
    	
    	// Save button
    	bSave = (Button)_activity.findViewById(R.id.buttonSave);
    }
    
    private void setActivityValues() {
    	//Setup preferences from previous input
        EmployeePreferences dM = readEmployeeInfo();
        eTName.setText(dM.get_name());
        if (dM.get_payPeriod().equals(EmployeeDetails.PayPeriod.WEEKLY)) {
            tbWeekly.setChecked(true);
        } else if (dM.get_payPeriod().equals(EmployeeDetails.PayPeriod.FORTNIGHTLY)) {
            tbFortnightly.setChecked(true);
        } else if (dM.get_payPeriod().equals(EmployeeDetails.PayPeriod.MONTHLY)) {
            tbMonthly.setChecked(true);
        }
        if (dM.get_studentLoan().equals(EmployeeDetails.StudentLoan.TRUE)) {
            tbStudentLoan.setChecked(true);
        } else {
        	tbStudentLoan.setChecked(false);
        }
        if (dM.get_taxCode().equals(EmployeeDetails.TaxCode.M)) {
            tbTaxCodeM.setChecked(true);
        } else if (dM.get_taxCode().equals(EmployeeDetails.TaxCode.ME)) {
        	tbTaxCodeME.setChecked(true);
        }
        npKiwiSaver.setValue((int)dM.get_kiwiSaver().getValue());
    }
    
    /**
     * @return String int the format
     * Name, pay period, student loan, tax code, kiwi saver, hourly pay
     */
    private EmployeePreferences readEmployeeInfo() {   
    	
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
		if (tbWeekly.isChecked()) {
        	payPeriod = EmployeeDetails.PayPeriod.WEEKLY;
        } else if (tbFortnightly.isChecked()) {
        	payPeriod = EmployeeDetails.PayPeriod.FORTNIGHTLY;
        } else if (tbMonthly.isChecked()) {
        	payPeriod = EmployeeDetails.PayPeriod.MONTHLY;
        }
		
		EmployeeDetails.StudentLoan studentLoan = null;
        if (tbStudentLoan.isChecked()) {
        	studentLoan = EmployeeDetails.StudentLoan.TRUE;
        } else {
        	studentLoan = EmployeeDetails.StudentLoan.FALSE;
        }
      
        EmployeeDetails.TaxCode taxCode = null;
        if (tbTaxCodeM.isChecked()) {
        	taxCode = EmployeeDetails.TaxCode.M;
        } else if (tbTaxCodeME.isChecked()) {
        	taxCode = EmployeeDetails.TaxCode.ME;
        }
        
        EmployeeDetails.KiwiSaver kiwiSaver = null;
        try {
            kiwiSaver = EmployeeDetails.parseKiwiSaver(npKiwiSaver.getValue());
        } catch (DetailParseException e) {
        	kiwiSaver = EmployeeDetails.KiwiSaver.ZERO;
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
