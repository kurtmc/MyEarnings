package com.mcalpinedevelopment.calculatepay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;

public class Employee {
	private Activity _activity;
	
	private double _hours;
	private double _rate;
	private String _paytype;	
	private double[] _taxData;
	private String[] _pay;
	
	public Employee(String message, Activity activity) {
		_activity = activity;
		Calculator calculator = new Calculator(message, _activity);
        _hours = calculator.hours();
        _rate = calculator.rate();
        _paytype = calculator.paytype();
        
        _taxData = computePayslip();
        _pay = new String[] {_activity.getString(R.string.gross) + String.format("%.2f",_taxData[0]),_activity.getString(R.string.paye) + String.format("%.2f",_taxData[1]),_activity.getString(R.string.studentLoadCalc) + String.format("%.2f",_taxData[2]),_activity.getString(R.string.kiwi_saver_calc) + String.format("%.2f",_taxData[3]),_activity.getString(R.string.nett) + String.format("%.2f",_taxData[4])};

	}
	
	public String name() {
		return null;
	}
	
	public String payRate() {
		return "";
	}
	
	public String hours() {
		return "";		
	}
	
	public String gross() {
		return "" + _pay[0];
		
	}
	
	public String paye() {
		return "" + _pay[1];
		
	}
	
	public String studentLoan() {
		return "" + _pay[2];
		
	}
	
	public String kiwiSaver() {
		return "" + _pay[3];
		
	}
	
	public String nett() {
		return "" + _pay[4];
		
	}
	
	private double[] computePayslip() {
		// Instantiate FileReader
		FileReader fileReader = new FileReader(_activity);
		
        String[] preferencesArray = fileReader.readPreferences().split(",");
        double gross = _rate*_hours;
        String[] taxData = readTaxData().split(",");
        double paye = 0;
        if (preferencesArray[3].equals("M")) {
            paye = Double.parseDouble(taxData[1]);
        } else if (preferencesArray[3].equals("ME")) {
            paye = Double.parseDouble(taxData[2]);
        } else if (preferencesArray[3] .equals("ML")) {
            paye = Double.parseDouble(taxData[3]);
        }
        double sL = 0;
        if (preferencesArray[2].equals("true")) {
            sL = Double.parseDouble(taxData[4]);
        }
        double kS = 0;
        if (preferencesArray[4].equals("None")) {
        } else if (preferencesArray[4].equals("1")){
            kS = Double.parseDouble(taxData[5])/2.0;
        } else if (preferencesArray[4].equals("2")) {
            kS = Double.parseDouble(taxData[5]);
        } else if (preferencesArray[4].equals("3")){
            kS = Double.parseDouble(taxData[5])*(3.0/2.0);
        } else if (preferencesArray[4].equals("4")) {
            kS = Double.parseDouble(taxData[6]);
        } else if (preferencesArray[4].equals("5")){
            kS = Double.parseDouble(taxData[6])*(5.0/4.0);
        } else if (preferencesArray[4].equals("6")){
            kS = Double.parseDouble(taxData[6])*(6.0/4.0);
        } else if (preferencesArray[4].equals("7")){
            kS = Double.parseDouble(taxData[6])*(7.0/4.0);
        } else if (preferencesArray[4].equals("8")) {
            kS = Double.parseDouble(taxData[7]);
        }
        double nett = gross - paye - sL - kS;
        double[] outputTaxData = {gross,paye,sL,kS,nett};

        return outputTaxData;
    }
	
	private String readTaxData() {
        double grossIncome = _rate*_hours;
        int readFactor;
        InputStream is;
        if (_paytype.equals("Weekly")) {
            is = _activity.getResources().openRawResource(R.raw.weeklypaye);
            readFactor = 1;
        } else {
            is = _activity.getResources().openRawResource(R.raw.fortnighlypaye);
            readFactor = 2;
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
        return line;
    }	
}
