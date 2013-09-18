package com.mcalpinedevelopment.calculatepay;

import android.app.Activity;


/**
 * Created by kurt on 20/05/13.
 */
public class Calculator{
	
	// Fields to store values
	private double _hours;
	private double _rate;
	private String _paytype;
	
	private FileReader _fileReader;
	
	/**
	 * @param message
	 * message is the String passed from MainActivity it contains a String
	 * that represents how many hours were worked
	 * @param activity
	 * activity is needed for context so that FileReader can be instantiated
	 */
	public Calculator(String message, Activity activity) {
		// Instantiate FileReader for later use
		_fileReader = new FileReader(activity);
		
		String payInfo = message;
        try {
            _hours = Double.parseDouble(payInfo);
        } catch (NumberFormatException e) { // catch if there is wrong formatting, set to 0.0
            _hours = 0.0;
        } catch (NullPointerException e) { // catch if there is empty string, set to 0.0
        	_hours = 0.0;
        }

        String[] preferencesArray = _fileReader.readPreferences().split(",");
        
        // Catch exceptions where the String is either incorrectly formatted,
        // or it is empty
        try {
        	_rate = Double.parseDouble(preferencesArray[5]);
        } catch (NumberFormatException e) {
        	_rate = 0.0;
        } catch (NullPointerException e) {
        	_rate = 0.0;
        }        
        
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
