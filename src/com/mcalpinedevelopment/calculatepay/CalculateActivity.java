package com.mcalpinedevelopment.calculatepay;

import android.annotation.SuppressLint;
import android.app.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.*;
import com.google.ads.*;


/**
 * Created by kurt on 17/05/13.
 */
public class CalculateActivity extends Activity{
    private AdView adView;
    final private String MY_AD_UNIT_ID = "a151adb2d8447b7";

    double _hours = 0;
    double _rate = 0;
    String _paytype = "";

    /**
     * Constants
     */
    final double TAXRATE1 = 0.105;
    final double TAXRATE2 = 0.175;
    final double TAXRATE3 = 0.30;
    final double TAXRATE4 = 0.33;
    final double TAXRATE5 = 0.45;
    final double TAXBRACKET1 = 14000;
    final double TAXBRACKET2 = 48000;
    final double TAXBRACKET3 = 70000;

    TextView tvDataToDisplay0;
    TextView tvDataToDisplay1;
    TextView tvDataToDisplay2;
    TextView tvDataToDisplay3;
    TextView tvDataToDisplay4;


    //################### refactoring
    String FILENAME = "preferences.txt";




    //Code inside of this method is executed when it is instantiated
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        // Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Get the message from the intent
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        
        
        
        // ############## Refactoring
//        String payInfo = message; //.split(" ");
//        try {
//            _hours = Double.parseDouble(payInfo);
//        } catch (NumberFormatException e) {
//            _hours = 0.0;
//        }
//        //_rate = Double.parseDouble(payInfo[1]);
//        //_paytype = payInfo[2];
//
//        String[] preferencesArray = readPreferences().split(",");
//        _rate = Double.parseDouble(preferencesArray[5]);
//        _paytype = preferencesArray[1];
        // ############## Refactoring
        // REPLACEMENT
        Calculator calculator = new Calculator(message, this);
        _hours = calculator.hours();
        _rate = calculator.rate();
        _paytype = calculator.paytype();
        // REPLACEMENT


        // Set the text view as the activity layout
        setContentView(R.layout.calculated_view);
        tvDataToDisplay0 = (TextView)findViewById(R.id.dataToDisplay0);
        tvDataToDisplay1 = (TextView)findViewById(R.id.dataToDisplay1);
        tvDataToDisplay2 = (TextView)findViewById(R.id.dataToDisplay2);
        tvDataToDisplay3 = (TextView)findViewById(R.id.dataToDisplay3);
        tvDataToDisplay4 = (TextView)findViewById(R.id.dataToDisplay4);
        double[] taxData = computePayslip();
        String[] pay = {getString(R.string.gross) + String.format("%.2f",taxData[0]),getString(R.string.paye) + String.format("%.2f",taxData[1]),getString(R.string.studentLoadCalc) + String.format("%.2f",taxData[2]),getString(R.string.kiwi_saver_calc) + String.format("%.2f",taxData[3]),getString(R.string.nett) + String.format("%.2f",taxData[4])};
        tvDataToDisplay0.setText(pay[0]);
        tvDataToDisplay1.setText(pay[1]);
        tvDataToDisplay2.setText(pay[2]);
        tvDataToDisplay3.setText(pay[3]);
        tvDataToDisplay4.setText(pay[4]);



        
        // === ADVERTISEMENT ========================================================
        // Create the adView
        adView = new AdView(this, AdSize.BANNER, MY_AD_UNIT_ID);
        // Lookup your LinearLayout assuming it's been given
        // the attribute android:id="@+id/mainLayout"
        LinearLayout layout = (LinearLayout)findViewById(R.id.linearLayoutAd);
        // Add the adView to it
        layout.addView(adView);
        // Initiate a generic request to load it with an ad
        adView.loadAd(new AdRequest());
        // === ADVERTISEMENT =======================================================*/


    }

    private String readTaxData() {
        double grossIncome = _rate*_hours;
        int readFactor;
        InputStream is;
        if (_paytype.equals("Weekly")) {
            is = getResources().openRawResource(R.raw.weeklypaye);
            readFactor = 1;
        } else {
            is = getResources().openRawResource(R.raw.fortnighlypaye);
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
    
    // ########### refactoring
    private String readPreferences() {
        try {

            StringBuilder fileContent = new StringBuilder();
            FileInputStream fis = openFileInput(FILENAME);
            
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) != -1) {
                fileContent.append(new String(buffer));
            }
            fis.close();

            return fileContent.toString();

        } catch (FileNotFoundException e) {
            return "Enter Name,Weekly,false,M,None,0.0";
        } catch (IOException e) {
            return "Enter Name,Weekly,false,M,None,0.0";
        }
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }




    /*
    private String GetValues() {
        _hours = hoursWorked;
        _rate = payRate;
        _paytype = inputpayType;

    }
    */

    private double calcPaye() {

        // Declare some values
        double total = 0;
        double annualGross = 0;

        //Estimate annual income for hourly paid employees, and set annual income for salaried employees
        if (_paytype.equals("Monthly")) {
            annualGross = _rate*_hours*12;
        } else if (_paytype.equals("Fortnightly")) {
            annualGross = _rate*_hours*52.0/2.0;
        } else if (_paytype.equals("Weekly")) {
            annualGross = _rate*_hours*52;
        }

        //Go through each tax bracket and calculate the tax and add to total
        while (annualGross != 0){
            if (annualGross <= TAXBRACKET1) {
                total += annualGross*TAXRATE1;
                annualGross = 0;
            } else if ((annualGross > TAXBRACKET1) &&  (annualGross <= TAXBRACKET2)) {
                total += (annualGross - TAXBRACKET1)*TAXRATE2;
                annualGross = TAXBRACKET1;
            } else if ((annualGross > TAXBRACKET2) &&  (annualGross <= TAXBRACKET3)) {
                total += (annualGross - TAXBRACKET2)*TAXRATE3;
                annualGross = TAXBRACKET2;
            } else if (annualGross > TAXBRACKET3) {
                total += (annualGross - TAXBRACKET3)*TAXRATE4;
                annualGross = TAXBRACKET3;
            }

        }

        //Calculate PAYE correctly
        double earnings = _hours*_rate;
        int earningsInt = (int)earnings;




        // Return the tax one week
        return total*1.0/52.0;

    }


    private double[] computePayslip() {
        String[] preferencesArray = readPreferences().split(",");
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

    //Overriding back button
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent = new Intent(this, MainActivity.class);
            String message = "";
            intent.putExtra("", message);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }

}
