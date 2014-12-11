package com.mcalpinedevelopment.calculatepay;

import android.test.AndroidTestCase;

import com.mcalpinedevelopment.calculatepay.tax.TaxCalculations;

public class TaxCalculationsTest extends AndroidTestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testSimpleCases() {
        assertEquals("27220.00", TaxCalculations.taxOnAnnualIncomeNZString(110000));
        assertEquals("27220.33", TaxCalculations.taxOnAnnualIncomeNZString(110001));
        assertEquals("27220.66", TaxCalculations.taxOnAnnualIncomeNZString(110002));
        assertEquals("27220.99", TaxCalculations.taxOnAnnualIncomeNZString(110003));
        assertEquals("27221.32", TaxCalculations.taxOnAnnualIncomeNZString(110004));
        assertEquals("27221.65", TaxCalculations.taxOnAnnualIncomeNZString(110005));
    }

    public void test0to14000() {
        assertEquals("0.00", TaxCalculations.taxOnAnnualIncomeNZString(0));
        assertEquals("218.40", TaxCalculations.taxOnAnnualIncomeNZString(2080));
        assertEquals("436.80", TaxCalculations.taxOnAnnualIncomeNZString(4160));
        assertEquals("1365.00", TaxCalculations.taxOnAnnualIncomeNZString(13000));
        assertEquals("1417.50", TaxCalculations.taxOnAnnualIncomeNZString(13500));
        assertEquals("1459.50", TaxCalculations.taxOnAnnualIncomeNZString(13900));
        assertEquals("1468.95", TaxCalculations.taxOnAnnualIncomeNZString(13990));
        assertEquals("1470.00", TaxCalculations.taxOnAnnualIncomeNZString(14000));
    }

    public void test14000to48000() {
        assertEquals("1470.02", TaxCalculations.taxOnAnnualIncomeNZString(14000.1));
        assertEquals("1470.09", TaxCalculations.taxOnAnnualIncomeNZString(14000.5));
        assertEquals("1486.80", TaxCalculations.taxOnAnnualIncomeNZString(14096));
        assertEquals("4445.00", TaxCalculations.taxOnAnnualIncomeNZString(31000));
        assertEquals("6299.42", TaxCalculations.taxOnAnnualIncomeNZString(41596.66));
        assertEquals("7245.06", TaxCalculations.taxOnAnnualIncomeNZString(47000.35));
        assertEquals("7246.05", TaxCalculations.taxOnAnnualIncomeNZString(47006));
        assertEquals("7420.00", TaxCalculations.taxOnAnnualIncomeNZString(48000));
    }

    public void test48000to70000() {
        //TODO
//        assertEquals("", TaxCalculations.taxOnAnnualIncomeNZString());
//        assertEquals("", TaxCalculations.taxOnAnnualIncomeNZString());
//        assertEquals("", TaxCalculations.taxOnAnnualIncomeNZString());
//        assertEquals("", TaxCalculations.taxOnAnnualIncomeNZString());
//        assertEquals("", TaxCalculations.taxOnAnnualIncomeNZString());
//        assertEquals("", TaxCalculations.taxOnAnnualIncomeNZString());
//        assertEquals("", TaxCalculations.taxOnAnnualIncomeNZString());
//        assertEquals("", TaxCalculations.taxOnAnnualIncomeNZString());
    }

    public void test70000up() {
        //TODO
//        assertEquals("", TaxCalculations.taxOnAnnualIncomeNZString());
//        assertEquals("", TaxCalculations.taxOnAnnualIncomeNZString());
//        assertEquals("", TaxCalculations.taxOnAnnualIncomeNZString());
//        assertEquals("", TaxCalculations.taxOnAnnualIncomeNZString());
//        assertEquals("", TaxCalculations.taxOnAnnualIncomeNZString());
//        assertEquals("", TaxCalculations.taxOnAnnualIncomeNZString());
//        assertEquals("", TaxCalculations.taxOnAnnualIncomeNZString());
//        assertEquals("", TaxCalculations.taxOnAnnualIncomeNZString());
    }

}
