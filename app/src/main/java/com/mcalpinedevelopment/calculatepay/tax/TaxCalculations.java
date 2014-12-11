package com.mcalpinedevelopment.calculatepay.tax;

import java.text.DecimalFormat;

public class TaxCalculations {


    private static double getTaxableIncome(double income, TaxBracket taxBracket) {
        if (income >= taxBracket.getUpper()) {
            return taxBracket.getRange();
        } else if (income >= taxBracket.getLower()) {
            return income - taxBracket.getLower();
        } else {
            return 0;
        }
    }

    public static double taxOnAnnualIncome(double annualIncome, TaxBrackets taxBrackets) {
        double incomeTax = 0;
        for (TaxBracket taxBracket : taxBrackets) {
            incomeTax += getTaxableIncome(annualIncome, taxBracket) * taxBracket.getPercentage();
        }

        return incomeTax;
    }

    public static double taxOnAnnualIncomeNZ(double annualIncome) {

        TaxBrackets taxBrackets = new TaxBrackets();
        // Tax brackets and percentages excluding ACC earners levy
        taxBrackets.addBracket(new TaxBracket(0, 14000, 10.5 * 0.01));
        taxBrackets.addBracket(new TaxBracket(14000, 48000, 17.5 * 0.01));
        taxBrackets.addBracket(new TaxBracket(48000, 70000, 30 * 0.01));
        taxBrackets.addBracket(new TaxBracket(70000, Double.MAX_VALUE, 33 * 0.01));

        return taxOnAnnualIncome(annualIncome, taxBrackets);
    }

    public static String taxOnAnnualIncomeNZString(double annualIncome) {
        double tax = taxOnAnnualIncomeNZ(annualIncome);

        if (tax <= 0) {
            return "0.00";
        }

        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(tax);
    }
}
