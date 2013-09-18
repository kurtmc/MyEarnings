package com.mcalpinedevelopment.calculatepay;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class MainActivity extends Activity {

    public final static String EXTRA_MESSAGE = "com.mcalpinedevelopment.calculatepay.MESSAGE";

    //Declare fields for the GUI
    Button bCalculate;
    Button bPreferences;
    EditText eTHoursWorked;
    EditText eTPayRate;
    RadioButton rBWeekly;
    RadioButton rBFortnightly;
    RadioButton rBMonthly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main); //Set the view to activity_main

        //Initialise fields, accessing GUI components
        bCalculate = (Button)findViewById(R.id.buttonCalculate);
        bPreferences = (Button)findViewById(R.id.buttonPreferences);
        eTHoursWorked = (EditText)findViewById(R.id.editTextHoursWorked);
        eTPayRate = (EditText)findViewById(R.id.editTextPayRate);
        rBWeekly = (RadioButton)findViewById(R.id.radioButtonWeekly);
        rBFortnightly = (RadioButton)findViewById(R.id.radioButtonFortnightly);
        //rBMonthly = (RadioButton)findViewById(R.id.radioButtonMonthly);

        //Set on click listener for bCalculate, and only execute if something is typed in the EditText eTHoursWorked
        bCalculate.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (!(eTHoursWorked.getText().length() == 0)) {
                    sendMessageToCalculate(v);
                }
            }


        });



        // Enter on the EditText eTHoursWorked will start CalculateActivity
        eTHoursWorked.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            sendMessageToCalculate(v);
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });


        // On click listener for bPreferences, launchers PreferencesActivity
        bPreferences.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startPreferencesActivity();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /** Called when the user clicks the Calculate button
     * Takes the string for eTHoursWorked and sends it to CalculateActivity */
    public void sendMessageToCalculate(View view) {
        Intent intent = new Intent(this, CalculateActivity.class);
        String message = eTHoursWorked.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    //Starts PreferencesActivity
    private void startPreferencesActivity() {
        Intent intent = new Intent(this, PreferencesActivity.class);
        startActivity(intent);
    }

    //Overriding back button to exit app when click inside this activity
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }



    //Settings button take you to PreferencesActivity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startPreferencesActivity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    
}
