package com.mcalpinedevelopment.calculatepay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.app.Activity;

public class AboutActivity extends Activity {
	
	private ListView aboutList;
	private List<Map<String, String>> _data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		aboutList = (ListView) findViewById(R.id.aboutList);
		
		_data = new ArrayList<Map<String, String>>();
		
	    Map<String, String> datum = new HashMap<String, String>(2);
	    
	    // Developer name
	    datum.put("title", "Developer");
	    datum.put("content", "Kurt McAlpine");
	    _data.add(datum);
	    
	    // Email the developer
	    datum = new HashMap<String, String>(2);
		
		SimpleAdapter adapter = new SimpleAdapter(this, _data, android.R.layout.simple_list_item_2, new String[] {"title", "content"}, new int[] {android.R.id.text1, android.R.id.text2});
		
		aboutList.setAdapter(adapter);
		

		aboutList.setOnItemClickListener(new OnItemClickListener() {
			@Override
		    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// When clicked, show a toast with the TextView text
				
				// Depending on which item in the list that is clicked do different things
				String clicked = _data.get(position).get("title");
				if (clicked.equals("Developer")) {
					Toast.makeText(getApplicationContext(), "Good job", Toast.LENGTH_SHORT).show();
				}
				
		    }

			
		  });
	}

}
