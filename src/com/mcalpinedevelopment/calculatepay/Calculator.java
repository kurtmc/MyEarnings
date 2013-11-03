package com.mcalpinedevelopment.calculatepay;

import com.mcalpinedevelopment.calculatepay.database.EmployeePreferences;
import com.mcalpinedevelopment.calculatepay.database.EmployeeDatabase;

import android.app.Activity;


/**
 * Created by kurt on 20/05/13.
 */
public class Calculator {
	
	// Fields to store values
	private double _hours;
	private double _rate;
	private String _payPeriod;

	
	/**
	 * @param hoursWorked
	 * message is the String passed from MainActivity it contains a String
	 * that represents how many hours were worked
	 * @param activity
	 * activity is needed for context so that FileReader can be instantiated
	 */
	public Calculator(String hoursWorked, Activity activity) {
		
		
		try {
			_hours = Double.parseDouble(hoursWorked);
		} catch (NumberFormatException e) { // catch if there is wrong formatting, set to 0.0
            _hours = 0.0;
        } catch (NullPointerException e) { // catch if there is empty string, set to 0.0
        	_hours = 0.0;
        }

		EmployeeDatabase db = EmployeeDatabase.getDatabase(activity);
		EmployeePreferences dM = db.getEmployeeDetails();
		
        try {
        	_rate = Double.parseDouble(dM.get_hourlyPay());
        } catch (NumberFormatException e) {
        	_rate = 0.0;
        } catch (NullPointerException e) {
        	_rate = 0.0;
        }        
        
        _payPeriod = dM.get_payPeriod();
	}
	
	public double rate() {
		return _rate;
	}
	public double hours() {
		return _hours;
	}
	public String paytype() {
		return _payPeriod;
	}
}
