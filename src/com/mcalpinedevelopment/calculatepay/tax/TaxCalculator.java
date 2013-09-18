package com.mcalpinedevelopment.calculatepay.tax;

public class TaxCalculator {
	
	
	public double gross(double hours, double rate) {
		return hours*rate;
	}
//	public double paye() {
//		
//	}
//	private static double[] computePayslip() {
//        String[] preferencesArray = readPreferences().split(",");
//        double gross = _rate*_hours;
//        String[] taxData = readTaxData().split(",");
//        double paye = 0;
//        if (preferencesArray[3].equals("M")) {
//            paye = Double.parseDouble(taxData[1]);
//        } else if (preferencesArray[3].equals("ME")) {
//            paye = Double.parseDouble(taxData[2]);
//        } else if (preferencesArray[3] .equals("ML")) {
//            paye = Double.parseDouble(taxData[3]);
//        }
//        double sL = 0;
//        if (preferencesArray[2].equals("true")) {
//            sL = Double.parseDouble(taxData[4]);
//        }
//        double kS = 0;
//        if (preferencesArray[4].equals("None")) {
//        } else if (preferencesArray[4].equals("1")){
//            kS = Double.parseDouble(taxData[5])/2.0;
//        } else if (preferencesArray[4].equals("2")) {
//            kS = Double.parseDouble(taxData[5]);
//        } else if (preferencesArray[4].equals("3")){
//            kS = Double.parseDouble(taxData[5])*(3.0/2.0);
//        } else if (preferencesArray[4].equals("4")) {
//            kS = Double.parseDouble(taxData[6]);
//        } else if (preferencesArray[4].equals("5")){
//            kS = Double.parseDouble(taxData[6])*(5.0/4.0);
//        } else if (preferencesArray[4].equals("6")){
//            kS = Double.parseDouble(taxData[6])*(6.0/4.0);
//        } else if (preferencesArray[4].equals("7")){
//            kS = Double.parseDouble(taxData[6])*(7.0/4.0);
//        } else if (preferencesArray[4].equals("8")) {
//            kS = Double.parseDouble(taxData[7]);
//        }
//        double nett = gross - paye - sL - kS;
//        double[] outputTaxData = {gross,paye,sL,kS,nett};
//
//        return outputTaxData;
//    }

}
