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
        
        Calculator calculator = new Calculator(message, this);
        _hours = calculator.hours();
        _rate = calculator.rate();
        _paytype = calculator.paytype();

        // Set the text view as the activity layout
        setContentView(R.layout.calculated_view);
        tvDataToDisplay0 = (TextView)findViewById(R.id.dataToDisplay0);
        tvDataToDisplay1 = (TextView)findViewById(R.id.dataToDisplay1);
        tvDataToDisplay2 = (TextView)findViewById(R.id.dataToDisplay2);
        tvDataToDisplay3 = (TextView)findViewById(R.id.dataToDisplay3);
        tvDataToDisplay4 = (TextView)findViewById(R.id.dataToDisplay4);
        
        // Instantiate Employee object
        Employee employee = new Employee(message, this);
        
        tvDataToDisplay0.setText(employee.gross());
        tvDataToDisplay1.setText(employee.paye());
        tvDataToDisplay2.setText(employee.studentLoan());
        tvDataToDisplay3.setText(employee.kiwiSaver());
        tvDataToDisplay4.setText(employee.nett());


        
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
    
    // ########### refactoring
    private String readPreferences() {
        try {

            StringBuilder fileContent = new StringBuilder();
            FileInputStream fis = openFileInput(FILENAME);
            
            byte[] buffer = new byte[1024];
            @SuppressWarnings("unused")
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

    /** ##################### Not technically used, perhaps implement later
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
    }*/

}
