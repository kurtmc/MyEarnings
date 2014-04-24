package com.mcalpinedevelopment.calculatepay.tax;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;

import com.mcalpinedevelopment.calculatepay.R;
import com.mcalpinedevelopment.calculatepay.database.EmployeeDatabase;
import com.mcalpinedevelopment.calculatepay.database.EmployeeDetails;
import com.mcalpinedevelopment.calculatepay.database.EmployeePreferences;

public class TaxCalculator {

	private EmployeeDetails.PayPeriod _payperiod;
	private Context _context;
	
	private String _taxTableInfo;
	
	private double _grossIncome;
	private double _paye;
	private double _studentLoan;
	private double _kiwiSaver;
	private double _nett;
	
	
	public TaxCalculator(double payRate, double hours, Context context) {
		_context = context;
		_grossIncome = payRate * hours;
		_payperiod = EmployeeDatabase.getDatabase(_context).getEmployeeDetails().get_payPeriod();
		
		readTaxTable();
		computePayslip();
	}
	
	/**
	 * @return Reads the line in the table containing the tax information
	 * up to the line closest to the _grossIncome value
	 */
	private void readTaxTable() {
        double grossIncome = _grossIncome;
        int readFactor;
        InputStream is;
        if (_payperiod.equals(EmployeeDetails.PayPeriod.WEEKLY)) {
            is = _context.getResources().openRawResource(R.raw.weeklypaye);
            readFactor = 1;
        } else if (_payperiod.equals(EmployeeDetails.PayPeriod.FORTNIGHTLY)) {
            is = _context.getResources().openRawResource(R.raw.fortnightlypaye);
            readFactor = 2;
        } else {
        	is = _context.getResources().openRawResource(R.raw.monthlypaye);
            readFactor = 5;
        }

        String line = "";
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String prevLine;
        if (grossIncome == 0) {
            line = "0,0.0,0.0,0.0,0,0.0,0.0,0.0,0.0,0,0.0,0,0.0,0,0.0,0";

        } else {
            for (int i = 0; i < (int)grossIncome/readFactor; i++)
            try {
                prevLine = line;
                line = br.readLine();
                if (line == null){
                    line = prevLine;
                    break;
                }
            } catch (IOException e) {

            }
        }
        _taxTableInfo = line;
    }
	
	/**
	 * Assigns the fields the values in accordence to the employees preferences and their gross income
	 * 
	 * At the moment the two files are formated as follows:
	 * 
	 * Notes:
	 * ML does not exist anymore
	 * M, ME and ML are tax codes
	 * SL stands for student loan
	 * 3%, 4% and 8% are Kiwi Saver amounts
	 * 
	 * weeklypaye.csv:
	 * 
	 * Earnings,M,ME,ML,SL,3%,4%,8%
	 * 1,0.12,0.12,0.12,0,0.03,0.04,0.08
	 * 2,0.24,0.24,0.24,0,0.06,0.08,0.16
	 * 3,0.36,0.36,0.36,0,0.09,0.12,0.24
	 * 4,0.48,0.48,0.48,0,0.12,0.16,0.32
	 * 5,0.61,0.61,0.61,0,0.15,0.2,0.4
	 * 
	 * fortnightly.csv:
	 * Earnings,M,ME,ML,SL,3%,4%,8%
	 * 2,0.24,0.24,0.24,0,0.06,0.08,0.16
	 * 4,0.48,0.48,0.48,0,0.12,0.16,0.32
	 * 6,0.72,0.72,0.72,0,0.18,0.24,0.48
	 * 8,0.96,0.96,0.96,0,0.24,0.32,0.64
	 * 10,1.22,1.22,1.22,0,0.3,0.4,0.8
	 */
	private void computePayslip() {
		// Get the employee preferences
		EmployeePreferences preferences = EmployeeDatabase.getDatabase(_context).getEmployeeDetails();
		
        String[] taxData = _taxTableInfo.split(",");
        _paye = 0;
        if (preferences.get_taxCode().equals(EmployeeDetails.TaxCode.M)) {
        	_paye = Double.parseDouble(taxData[1]);
        } else if (preferences.get_taxCode().equals(EmployeeDetails.TaxCode.ME)) {
        	_paye = Double.parseDouble(taxData[2]);
        } else if (preferences.get_taxCode() .equals(EmployeeDetails.TaxCode.ML)) {
        	_paye = Double.parseDouble(taxData[3]);
        }
        _studentLoan = 0;
        if (preferences.get_studentLoan().equals(EmployeeDetails.StudentLoan.TRUE)) {
        	_studentLoan = Double.parseDouble(taxData[4]);
        }
        _kiwiSaver = 0;
        if (preferences.get_kiwiSaver().equals(EmployeeDetails.KiwiSaver.ZERO)) {
        } else if (preferences.get_kiwiSaver().equals(EmployeeDetails.KiwiSaver.ONE)){
        	_kiwiSaver = Double.parseDouble(taxData[5])/3.0;
        } else if (preferences.get_kiwiSaver().equals(EmployeeDetails.KiwiSaver.TWO)) {
        	_kiwiSaver = Double.parseDouble(taxData[5])*(2.0/3.0);
        } else if (preferences.get_kiwiSaver().equals(EmployeeDetails.KiwiSaver.THREE)){
        	_kiwiSaver = Double.parseDouble(taxData[5]);
        } else if (preferences.get_kiwiSaver().equals(EmployeeDetails.KiwiSaver.FOUR)) {
        	_kiwiSaver = Double.parseDouble(taxData[6]);
        } else if (preferences.get_kiwiSaver().equals(EmployeeDetails.KiwiSaver.FIVE)){
        	_kiwiSaver = Double.parseDouble(taxData[6])*(5.0/4.0);
        } else if (preferences.get_kiwiSaver().equals(EmployeeDetails.KiwiSaver.SIX)){
        	_kiwiSaver = Double.parseDouble(taxData[6])*(6.0/4.0);
        } else if (preferences.get_kiwiSaver().equals(EmployeeDetails.KiwiSaver.SEVEN)){
        	_kiwiSaver = Double.parseDouble(taxData[6])*(7.0/4.0);
        } else if (preferences.get_kiwiSaver().equals(EmployeeDetails.KiwiSaver.EIGHT)) {
        	_kiwiSaver = Double.parseDouble(taxData[7]);
        }
        _nett = _grossIncome - _paye - _studentLoan - _kiwiSaver;
    }
	
	public double get_grossIncome() {
		return _grossIncome;
	}

	public double get_paye() {
		return _paye;
	}

	public double get_studentLoan() {
		return _studentLoan;
	}

	public double get_kiwiSaver() {
		return _kiwiSaver;
	}

	public double get_nett() {
		return _nett;
	}
}
