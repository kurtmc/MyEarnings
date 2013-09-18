package com.mcalpinedevelopment.calculatepay;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;

public class FileReader {
	private Activity _activity;
	
	// Filename constant
	private final String FILENAME = "preferences.txt";
	
	public FileReader(Activity activity) {
		_activity = activity;
	}
	
	public String readPreferences() {
        try {

            StringBuilder fileContent = new StringBuilder();
            FileInputStream fis = _activity.openFileInput(FILENAME);

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
}
