package com.mcalpinedevelopment.calculatepay;

import java.util.List;

import android.test.ActivityTestCase;

public class CalculateActivityTest extends ActivityTestCase {
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testCentre() {	   
	   Pie pieChart = new Pie(100);
	   assertEquals(50, pieChart.centre()[0]);
	   assertEquals(50, pieChart.centre()[1]);
	 }
	
	public void testSlice() {
		Pie pieChart = new Pie(100);
		List<int[]> slice = pieChart.slice(100/5);
		
		assertEquals(50, slice.get(0)[0]);
		assertEquals(50, slice.get(0)[1]);
		assertEquals(50, slice.get(1)[0]);
		assertEquals(0, slice.get(1)[1]);
		assertEquals(98, slice.get(2)[0]);
		assertEquals(35, slice.get(2)[1]);
		assertEquals(50, slice.get(3)[0]);
		assertEquals(50, slice.get(3)[1]);
	}
}
