package com.mcalpinedevelopment.calculatepay;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.mcalpinedevelopment.calculatepay.ui.PreferencesUI;

/**
 * Created by kurt on 26/05/13.
 */
public class PreferencesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        
        // Instantiate PreferencesUI to handle the UI
        @SuppressWarnings("unused")
		PreferencesUI preferencesUI = new PreferencesUI(this);
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

