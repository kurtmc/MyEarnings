package com.mcalpinedevelopment.calculatepay.employee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.mcalpinedevelopment.calculatepay.Calculator;
import com.mcalpinedevelopment.calculatepay.R;
import com.mcalpinedevelopment.calculatepay.database.EmployeeDatabase;
import com.mcalpinedevelopment.calculatepay.database.EmployeePreferences;
import com.mcalpinedevelopment.calculatepay.tax.TaxCalculator;

import android.app.Activity;
import android.content.Context;

public class Employee {
	private Context _context;
	
	private double _hours;
	private double _rate;
	private String _paytype;	
	private TaxCalculator _taxCalculator;
//	private double[] _taxData;
//	private String[] _pay;
	
	public Employee(String message, Context context) {
		_context = context;
		Calculator calculator = new Calculator(message, _context);
        _hours = calculator.hours();
        _rate = calculator.rate();
        _paytype = calculator.paytype();
        
        _taxCalculator = new TaxCalculator(_rate, _hours, _context);
        
        
        
//        _taxData = computePayslip();
//        _pay = new String[] {_context.getString(R.string.gross) + String.format("%.2f",_taxData[0]),_context.getString(R.string.paye) + String.format("%.2f",_taxData[1]),_context.getString(R.string.studentLoadCalc) + String.format("%.2f",_taxData[2]),_context.getString(R.string.kiwi_saver_calc) + String.format("%.2f",_taxData[3]),_context.getString(R.string.nett) + String.format("%.2f",_taxData[4])};

	}
	
//	public String name() {
//		return null;
//	}
//	
//	public String payRate() {
//		return "";
//	}
//	
//	public String hours() {
//		return "";		
//	}
	
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
	
//	private double[] computePayslip() {
//		// Get the employee preferences
//		EmployeePreferences preferences = EmployeeDatabase.getDatabase(_context).getEmployeeDetails();
//		
//        double gross = _rate*_hours;
//        String[] taxData = readTaxData().split(",");
//        double paye = 0;
//        if (preferences.get_taxCode().equals("M")) {
//            paye = Double.parseDouble(taxData[1]);
//        } else if (preferences.get_taxCode().equals("ME")) {
//            paye = Double.parseDouble(taxData[2]);
//        } else if (preferences.get_taxCode() .equals("ML")) {
//            paye = Double.parseDouble(taxData[3]);
//        }
//        double sL = 0;
//        if (preferences.get_studentLoan().equals("true")) {
//            sL = Double.parseDouble(taxData[4]);
//        }
//        double kS = 0;
//        if (preferences.get_kiwiSaver().equals("None")) {
//        } else if (preferences.get_kiwiSaver().equals("1")){
//            kS = Double.parseDouble(taxData[5])/2.0;
//        } else if (preferences.get_kiwiSaver().equals("2")) {
//            kS = Double.parseDouble(taxData[5]);
//        } else if (preferences.get_kiwiSaver().equals("3")){
//            kS = Double.parseDouble(taxData[5])*(3.0/2.0);
//        } else if (preferences.get_kiwiSaver().equals("4")) {
//            kS = Double.parseDouble(taxData[6]);
//        } else if (preferences.get_kiwiSaver().equals("5")){
//            kS = Double.parseDouble(taxData[6])*(5.0/4.0);
//        } else if (preferences.get_kiwiSaver().equals("6")){
//            kS = Double.parseDouble(taxData[6])*(6.0/4.0);
//        } else if (preferences.get_kiwiSaver().equals("7")){
//            kS = Double.parseDouble(taxData[6])*(7.0/4.0);
//        } else if (preferences.get_kiwiSaver().equals("8")) {
//            kS = Double.parseDouble(taxData[7]);
//        }
//        double nett = gross - paye - sL - kS;
//        double[] outputTaxData = {gross,paye,sL,kS,nett};
//
//        return outputTaxData;
//    }
//	
//	private String readTaxData() {
//        double grossIncome = _rate*_hours;
//        int readFactor;
//        InputStream is;
//        if (_paytype.equals("Weekly")) {
//            is = _context.getResources().openRawResource(R.raw.weeklypaye);
//            readFactor = 1;
//        } else {
//            is = _context.getResources().openRawResource(R.raw.fortnighlypaye);
//            readFactor = 2;
//        }
//
//        String line = "";
//        InputStreamReader isr = new InputStreamReader(is);
//        BufferedReader br = new BufferedReader(isr);
//        String prevLine;
//        if (grossIncome == 0) {
//            line = "0,0.0,0.0,0.0,0,0.0,0.0,0.0,0.0,0,0.0,0,0.0,0,0.0,0";
//
//        } else {
//            for (int i = 0; i < (int)grossIncome/readFactor; i++)
//            try {
//                prevLine = line;
//                line = br.readLine();
//                if (line == null){
//                    line = prevLine;
//                    break;
//                }
//            } catch (IOException e) {
//
//            }
//        }
//        return line;
//    }	
}
