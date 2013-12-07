package com.mcalpinedevelopment.calculatepay;

import com.implementation.EmployeePreferences;
import com.implementation.EmployeeDatabase;

import android.content.Context;


/**
 * Created by kurt on 20/05/13.
 */
public class Calculator {
	
	// Fields to store values
	private double _hours;
	private double _rate;
	
	/**
	 * @param hoursWorked
	 * message is the String passed from MainActivity it contains a String
	 * that represents how many hours were worked
	 * @param activity
	 * activity is needed for context so that FileReader can be instantiated
	 */
	public Calculator(String hoursWorked, Context activity) {
		
		
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
        	_rate = dM.get_hourlyPay();
        } catch (NumberFormatException e) {
        	_rate = 0.0;
        } catch (NullPointerException e) {
        	_rate = 0.0;
        }        

	}
	
	public double rate() {
		return _rate;
	}
	public double hours() {
		return _hours;
	}
	
}
