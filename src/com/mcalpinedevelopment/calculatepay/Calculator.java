package com.mcalpinedevelopment.calculatepay;

/**
 * Created by kurt on 20/05/13.
 */
public class Calculator {
	
	// Fields to store values
	double _hours;
	double _rate;
	String _paytype;
	
	public Calculator(String message) {
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
		return _rate;
	}
	public double paytype() {
		return _rate;
	}
}
