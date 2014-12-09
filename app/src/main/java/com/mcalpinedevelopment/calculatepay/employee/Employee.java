package com.mcalpinedevelopment.calculatepay.employee;

import com.mcalpinedevelopment.calculatepay.Calculator;
import com.mcalpinedevelopment.calculatepay.R;
import com.mcalpinedevelopment.calculatepay.tax.TaxCalculator;

import android.content.Context;

public class Employee {
	private Context _context;
	
	private double _hours;
	private double _rate;	
	private TaxCalculator _taxCalculator;
	
	public Employee(String message, Context context) {
		_context = context;
		Calculator calculator = new Calculator(message, _context);
        _hours = calculator.hours();
        _rate = calculator.rate();
        
        _taxCalculator = new TaxCalculator(_rate, _hours, _context);
	}
	
	public String gross() {		
		return _context.getString(R.string.gross) + String.format("%.2f",_taxCalculator.get_grossIncome());
	}
	
	public String paye() {
		return _context.getString(R.string.paye) + String.format("%.2f", _taxCalculator.get_paye());
	}
	
	public String studentLoan() {		
		return _context.getString(R.string.studentLoadCalc) + String.format("%.2f",_taxCalculator.get_studentLoan());		
	}
	
	public String kiwiSaver() {
		return _context.getString(R.string.kiwi_saver_calc) + String.format("%.2f",_taxCalculator.get_kiwiSaver());		
	}
	
	public String nett() {		
		return _context.getString(R.string.nett) + String.format("%.2f",_taxCalculator.get_nett());		
	}
	
	public double grossDouble() {		
		return _taxCalculator.get_grossIncome();
	}
	
	public double payeDouble() {
		return _taxCalculator.get_paye();
	}
	
	public double studentLoanDouble() {		
		return _taxCalculator.get_studentLoan();		
	}
	
	public double kiwiSaverDouble() {
		return _taxCalculator.get_kiwiSaver();		
	}
	
	public double nettDouble() {		
		return _taxCalculator.get_nett();		
	}
	
	
}
