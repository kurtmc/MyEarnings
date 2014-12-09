package com.mcalpinedevelopment.calculatepay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;

public class AboutActivity extends Activity {
	
	// Strings used in list
	private final static String TITLE = "title";
	private final static String CONTENT = "content";
	
	private final static String DEV = "Developer";
	private final static String DEV_C = "Kurt McAlpine";
	private final static String EMAIL = "Email the developer";
	private final static String EMAIL_C = "kurt@mcalpinedevelopment.com";
	private final static String FEEDBACK = "Feedback";
	private final static String FEEDBACK_C = "Give feedback on your experience using this application";
	
	
	private ListView aboutList;
	private List<Map<String, String>> _data;
	
	
	// Integer to count how many times the Developer item is clicked so the user can see an easter egg
	private int developerClicks = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		aboutList = (ListView) findViewById(R.id.aboutList);
		
		_data = new ArrayList<Map<String, String>>();
		
	    Map<String, String> datum = new HashMap<String, String>(2);
	    
	    // Developer name
	    datum.put(TITLE, DEV);
	    datum.put(CONTENT, DEV_C);
	    _data.add(datum);
	    
	    // Email the developer
	    datum = new HashMap<String, String>(2);
	    datum.put(TITLE, EMAIL);
	    datum.put(CONTENT, EMAIL_C);
	    _data.add(datum);
	    
	    // Feedback
	    datum = new HashMap<String, String>(2);
	    datum.put(TITLE, FEEDBACK);
	    datum.put(CONTENT, FEEDBACK_C);
	    _data.add(datum);
		
		SimpleAdapter adapter = new SimpleAdapter(this, _data, android.R.layout.simple_list_item_2, new String[] {TITLE, CONTENT}, new int[] {android.R.id.text1, android.R.id.text2});
		
		aboutList.setAdapter(adapter);
		

		aboutList.setOnItemClickListener(new OnItemClickListener() {
			@Override
		    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// Depending on which item in the list that is clicked do different things
				String clicked = _data.get(position).get(TITLE);
				
				// DEV clicked
				if (clicked.equals(DEV)) {
					developerClicks++;
					if (developerClicks > 10) {
						Toast.makeText(getApplicationContext(), "Well done you clicked that "+developerClicks+" times...", Toast.LENGTH_SHORT).show();
					}
					
				// EMAIL clicked
				} else if (clicked.equals(EMAIL)) {
					Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","kurt@mcalpinedevelopment.com", null));
					emailIntent.putExtra(Intent.EXTRA_SUBJECT, "My Earnings");
					startActivity(Intent.createChooser(emailIntent, ""));
				
				// FEEDBACK clicked
				} else if (clicked.equals(FEEDBACK)) {
					Uri uri = Uri.parse("market://details?id=com.mcalpinedevelopment.calculatepay");
					Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
					try {
					  startActivity(goToMarket);
					} catch (ActivityNotFoundException e) {
					  Toast.makeText(getBaseContext(), "Couldn't launch the market", Toast.LENGTH_LONG).show();
					}					
				}
			}
		});
	}
}
