package com.mcalpinedevelopment.calculatepay;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.content.Context;

public class FileReader {
	private Context _context;
	
	// Filename constant
	private final String FILENAME = "preferences.txt";
	
	public FileReader(Context context) {
		_context = context;
	}
	
	public String readPreferences() {
		try {

            StringBuilder fileContent = new StringBuilder();
            FileInputStream fis = _context.openFileInput(FILENAME);

            byte[] buffer = new byte[1024];
            @SuppressWarnings("unused")
			int length;
            while ((length = fis.read(buffer)) != -1) {
                fileContent.append(new String(buffer));
            }
            fis.close();

            return fileContent.toString();
            
         // Return default values if there is a problem
        } catch (FileNotFoundException e) {
            return "Enter Name,Weekly,false,M,None,0.0"; 
        } catch (IOException e) {
            return "Enter Name,Weekly,false,M,None,0.0";
        }
    }
}
