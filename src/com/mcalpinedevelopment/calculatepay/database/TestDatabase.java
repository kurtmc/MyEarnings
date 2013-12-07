package com.mcalpinedevelopment.calculatepay.database;


import android.test.AndroidTestCase;

public class TestDatabase extends AndroidTestCase {	

    @Override
	protected void setUp() throws Exception {
    	EmployeeDatabase db = EmployeeDatabase.getDatabase(getContext());
    	db.deleteDatabase();
    	EmployeeDatabase.destroyInstance();
		super.setUp();
	}

	/**
     * Test to see if the database can be correctly deleted, inferring that it had been correctly created
     */
    public void testNewDatabase() {
    	EmployeeDatabase db = EmployeeDatabase.getDatabase(getContext());
    	assertTrue(db.deleteDatabase());
    }
    
    /**
     * Test to see if after adding an employee the database can return the name on the getName() call
     * @throws DetailParseException 
     */
    public void testAddingEmployeeGetName() throws DetailParseException {
    	EmployeeDatabase db = EmployeeDatabase.getDatabase(getContext());
    	EmployeePreferences employeePreferences = new EmployeePreferences();
    	employeePreferences.set_name("Kurt McAlpine");    	
		employeePreferences.set_payPeriod(EmployeeDetails.parsePayPeriod("weekly"));
    	employeePreferences.set_taxCode(EmployeeDetails.parseTaxCode("M"));
    	employeePreferences.set_kiwiSaver(EmployeeDetails.parseKiwiSaver("zero"));
    	employeePreferences.set_studentLoan(EmployeeDetails.parseStudentLoan("false"));
    	employeePreferences.set_hourlyPay(Double.parseDouble("25.0"));
    	db.updateData(employeePreferences); 
    	assertEquals("Kurt McAlpine", db.getName());
    }
    
    public void testAddingEmployeeGetEmployeeDetails() throws DetailParseException {
    	EmployeeDatabase db = EmployeeDatabase.getDatabase(getContext());
    	
    	EmployeePreferences employeePreferences = new EmployeePreferences();
    	employeePreferences.set_name("Kurt McAlpine");    	
		employeePreferences.set_payPeriod(EmployeeDetails.parsePayPeriod("weekly"));
    	employeePreferences.set_taxCode(EmployeeDetails.parseTaxCode("M"));
    	employeePreferences.set_kiwiSaver(EmployeeDetails.parseKiwiSaver("zero"));
    	employeePreferences.set_studentLoan(EmployeeDetails.parseStudentLoan("false"));
    	employeePreferences.set_hourlyPay(Double.parseDouble("25.0"));
    	
    	db.updateData(employeePreferences);
    	EmployeePreferences expectedDetails = employeePreferences;
    	EmployeePreferences result = db.getEmployeeDetails();
    	

    	assertEquals(expectedDetails.toString(), result.toString());
    	
    }
    
    /**
     * Test to see if after adding an employee the database can return the name on the getName() call
     * multiple times
     * @throws DetailParseException 
     */
    public void testAddingEmployeeGetNameMultipleTimes() throws DetailParseException {
    	EmployeeDatabase db = EmployeeDatabase.getDatabase(getContext());
    	EmployeePreferences employeePreferences = null; 
    	for (int i = 0; i<10; i++) {
    		employeePreferences = new EmployeePreferences();
        	employeePreferences.set_name("Kurt McAlpine");    	
    		employeePreferences.set_payPeriod(EmployeeDetails.parsePayPeriod("weekly"));
        	employeePreferences.set_taxCode(EmployeeDetails.parseTaxCode("M"));
        	employeePreferences.set_kiwiSaver(EmployeeDetails.parseKiwiSaver("zero"));
        	employeePreferences.set_studentLoan(EmployeeDetails.parseStudentLoan("false"));
        	employeePreferences.set_hourlyPay(Double.parseDouble("25.0"));
    		db.updateData(employeePreferences);
        	assertEquals("Kurt McAlpine", db.getName());
    	}
    	
    }
    
    
}