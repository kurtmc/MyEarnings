package com.mcalpinedevelopment.calculatepay;

import android.annotation.SuppressLint;
import android.app.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.ads.*;
import com.mcalpinedevelopment.calculatepay.employee.Employee;


/**
 * Created by kurt on 17/05/13.
 */
public class CalculateActivity extends Activity{
    private AdView adView;
    final private String MY_AD_UNIT_ID = "a151adb2d8447b7";

    TextView tvDataToDisplay0;
    TextView tvDataToDisplay1;
    TextView tvDataToDisplay2;
    TextView tvDataToDisplay3;
    TextView tvDataToDisplay4;

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

        

        // Set the text view as the activity layout
        setContentView(R.layout.calculated_view);
        tvDataToDisplay0 = (TextView)findViewById(R.id.dataToDisplay0);
        tvDataToDisplay1 = (TextView)findViewById(R.id.dataToDisplay1);
        tvDataToDisplay2 = (TextView)findViewById(R.id.dataToDisplay2);
        tvDataToDisplay3 = (TextView)findViewById(R.id.dataToDisplay3);
        tvDataToDisplay4 = (TextView)findViewById(R.id.dataToDisplay4);
        
        // Get the message from the intent
        Intent intent = getIntent();
        String hoursWorked = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        
        // Instantiate Employee object
        Employee employee = new Employee(hoursWorked, this);
        
        tvDataToDisplay0.setText(employee.gross());
        tvDataToDisplay1.setText(employee.paye());
        tvDataToDisplay2.setText(employee.studentLoan());
        tvDataToDisplay3.setText(employee.kiwiSaver());
        tvDataToDisplay4.setText(employee.nett());

    }
    

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
            case R.id.action_settings:
                startPreferencesActivity();
                break;
            case R.id.about:
            	startAboutActivity();
            	break;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    //Starts PreferencesActivity
    private void startPreferencesActivity() {
        Intent intent = new Intent(this, PreferencesActivity.class);
        startActivity(intent);
    }
    
    // Start About
    private void startAboutActivity() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }
    
}
