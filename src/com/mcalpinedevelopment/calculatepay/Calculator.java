package com.mcalpinedevelopment.calculatepay;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;


/**
 * Created by kurt on 20/05/13.
 */
public class Calculator{
	
	// Activity that created Calculator
	Activity _activity;
	
	// Filename constant
	private final String FILENAME = "preferences.txt";
	
	// Fields to store values
	private double _hours;
	private double _rate;
	private String _paytype;
	
	public Calculator(String message, Activity activity) {
		// Get the activity so a file can be opened
		_activity = activity;
		
		String payInfo = message; //.split(" ");
        try {
            _hours = Double.parseDouble(payInfo);
        } catch (NumberFormatException e) {
            _hours = 0.0;
        }
        //_rate = Double.parseDouble(payInfo[1]);
        //_paytype = payInfo[2];

        String[] preferencesArray = readPreferences().split(",");
        _rate = Double.parseDouble(preferencesArray[5]);
        _paytype = preferencesArray[1];
	}
	
	public double rate() {
		return _rate;
	}
	public double hours() {
		return _hours;
	}
	public String paytype() {
		return _paytype;
	}
	
	private String readPreferences() {
        try {

            StringBuilder fileContent = new StringBuilder();
            FileInputStream fis = _activity.openFileInput(FILENAME);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) != -1) {
                fileContent.append(new String(buffer));
            }
            fis.close();

            return fileContent.toString();

        } catch (FileNotFoundException e) {
            return "Enter Name,Weekly,false,M,None,0.0";
        } catch (IOException e) {
            return "Enter Name,Weekly,false,M,None,0.0";
        }
    }
}
