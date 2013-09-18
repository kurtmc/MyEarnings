package com.mcalpinedevelopment.calculatepay;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;


/**
 * Created by kurt on 20/05/13.
 */
public class Calculator{
	
	// Filename constant
	private final String FILENAME = "preferences.txt";
	
	// Fields to store values
	private double _hours;
	private double _rate;
	private String _paytype;
	
	private FileReader _fileReader;
	
	public Calculator(String message, Activity activity) {
		// Instantiate FileReader for later use
		_fileReader = new FileReader(activity);
		
		String payInfo = message; //.split(" ");
        try {
            _hours = Double.parseDouble(payInfo);
        } catch (NumberFormatException e) {
            _hours = 0.0;
        }

        String[] preferencesArray = _fileReader.readPreferences().split(",");
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
}
