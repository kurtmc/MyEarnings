package com.mcalpinedevelopment.calculatepay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

    public final static String EXTRA_MESSAGE = "com.mcalpinedevelopment.calculatepay.MESSAGE";

    //Declare fields for the GUI
    Button bCalculate;
    EditText eTHoursWorked;
    private ToggleButton tbAdvanced;
    private EditText etTimeAndAHalf;
    private EditText etDoubleTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main); //Set the view to activity_main

        //Initialise fields, accessing GUI components
        bCalculate = (Button)findViewById(R.id.buttonCalculate);
        eTHoursWorked = (EditText)findViewById(R.id.editTextHoursWorked);
        tbAdvanced = (ToggleButton)findViewById(R.id.tbAdvanced);
        etTimeAndAHalf = (EditText)findViewById(R.id.etTimeAndAHalf);
        etDoubleTime = (EditText)findViewById(R.id.etDoubleTime);
        

        //Set on click listener for bCalculate, and only execute if something is typed in the EditText eTHoursWorked
        bCalculate.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (!(eTHoursWorked.getText().length() == 0)) {
                    sendMessageToCalculate(v);
                }
            }


        });
        
        tbAdvanced.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					etTimeAndAHalf.setVisibility(View.VISIBLE);
					etDoubleTime.setVisibility(View.VISIBLE);
				} else {
					etTimeAndAHalf.setVisibility(View.GONE);
					etDoubleTime.setVisibility(View.GONE);
				}
				
			}});



        // Enter on the EditText eTHoursWorked will start CalculateActivity
        // Need to sort this out
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
                        	if (!tbAdvanced.isChecked()) {
                        		sendMessageToCalculate(v);
                        	}                            
                            return true;
                        default:
                            break;
                    }
                }
                return false;
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
        double hoursWorked = Double.parseDouble(eTHoursWorked.getText().toString());
        if (tbAdvanced.isChecked()) {
        	String timeAndAHalf = etTimeAndAHalf.getText().toString();
        	String doubleTime = etDoubleTime.getText().toString();
        	hoursWorked += 1.5*Double.parseDouble(timeAndAHalf.equals("") ? "0.0" : timeAndAHalf);
            hoursWorked += 2.0*Double.parseDouble(doubleTime.equals("") ? "0.0" : doubleTime);
        }        
        String message = String.valueOf(hoursWorked);
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
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
            case R.id.about:
            	startAboutActivity();
            	break;
        }
        return super.onOptionsItemSelected(item);
    }


    
}
