package com.mcalpinedevelopment.calculatepay.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class EmployeeDatabase {
		// Activity reference
		Context _context;
		
		private static EmployeeDatabase _instance;
		
		private final String DATABASE_NAME = "EmployeeData.db";
		private final String TABLE_NAME = "employee";
		
		// Table columns
		private final String NAME = "name";
		private final String PAY_PERIOD = "pay_period";
		private final String KIWI_SAVER = "kiwi_saver";
		private final String TAX_CODE = "tax_code";
		private final String STUDENT_LOAN = "student_loan";
		private final String HOURLY_PAY = "hourly_pay";

		
		private final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" 
				+ NAME 			+ " TEXT,"	
				+ PAY_PERIOD 	+ " TEXT,"
				+ STUDENT_LOAN 	+ " TEXT,"
				+ TAX_CODE 		+ " TEXT,"
				+ KIWI_SAVER 	+ " TEXT,"
				+ HOURLY_PAY 	+ " TEXT"
				+ ");";
		
		
		private EmployeeDatabase(Context context) {
			_context = context;
			createDatabase();	
		}
		
		public static EmployeeDatabase getDatabase(Context context) {
			if (_instance == null) {
				_instance = new EmployeeDatabase(context);
			}
			return _instance;
		}
		
		/**
		 * Sets the _instance field to null so that a new instance can be created
		 */
		public static void destroyInstance() {
			_instance = null;
		}
		
		private void createDatabase() {
			SQLiteDatabase db;
			try {
				db = openDatabase();
				db.execSQL(DATABASE_CREATE);
				db.close();
			} catch (Exception e){ 
				Log.e("myDatabase","ERROR: Creating the database");
				e.printStackTrace();
			}
		}
		
		private void insertData(EmployeePreferences dM) {
			Log.d("myDatabase","Insert data");
			SQLiteDatabase db;
			db =  openDatabase();
			
			ContentValues values = putData(dM); //new ContentValues();
//			values.put(NAME, dM.get_name());
//			values.put(PAY_PERIOD, dM.get_payPeriod());
//			values.put(KIWI_SAVER, dM.get_kiwiSaver());
//			values.put(TAX_CODE, dM.get_taxCode());			
//			values.put(STUDENT_LOAN, dM.get_studentLoan());
//			values.put(HOURLY_PAY, dM.get_hourlyPay());

			db.insert(TABLE_NAME, null, values);
			
			db.close(); // Always close the database
		}
		
		private ContentValues putData(EmployeePreferences dM) {
			ContentValues values = new ContentValues();
			values.put(NAME, dM.get_name());
			values.put(PAY_PERIOD, dM.get_payPeriod().toString());
			values.put(KIWI_SAVER, dM.get_kiwiSaver().toString());
			values.put(TAX_CODE, dM.get_taxCode().toString());			
			values.put(STUDENT_LOAN, dM.get_studentLoan().toString());
			values.put(HOURLY_PAY, Double.toString(dM.get_hourlyPay()));			
			return values;
		}
		
		public void updateData(EmployeePreferences dM) {
			if (getName() == null) {
				insertData(dM);
				return;
			}
			Log.d("myDatabase","Update data");
			SQLiteDatabase db;
			db =  openDatabase();
			
			ContentValues values = putData(dM); //new ContentValues();
//			values.put(NAME, dM.get_name());
//			values.put(PAY_PERIOD, dM.get_payPeriod().toString());
//			values.put(KIWI_SAVER, dM.get_kiwiSaver().toString());
//			values.put(TAX_CODE, dM.get_taxCode().toString());			
//			values.put(STUDENT_LOAN, dM.get_studentLoan().toString());
			values.put(HOURLY_PAY, Double.toString(dM.get_hourlyPay()));
			
			db.update(TABLE_NAME, values,NAME + "=" + "\'"+getName()+"\'", null);
			
			db.close();
			
		}
		
		public EmployeePreferences getEmployeeDetails() {
			SQLiteDatabase db;
			db =  openDatabase();		
			Cursor rows = null;
			
			try {
				rows = db.rawQuery("SELECT * FROM "+ TABLE_NAME, null); //db.rawQuery("SELECT * FROM "+ TABLE_NAME + " WHERE " + NAME + " == " + "\'"+getName()+"\'", null);
			} catch (SQLiteException e) {
				return null;
			}
			if (rows.moveToFirst() == false) {
				Log.d("myDatabase", "First row empty");
			} else {
				Log.d("myDatabase", "Not empty");
			}
			
			EmployeePreferences dM = new EmployeePreferences();
			
			try {
				dM.set_name(rows.getString(0));
				dM.set_payPeriod(EmployeeDetails.parsePayPeriod(rows.getString(1)));
				dM.set_studentLoan(EmployeeDetails.parseStudentLoan(rows.getString(2)));
				dM.set_taxCode(EmployeeDetails.parseTaxCode(rows.getString(3)));
				dM.set_kiwiSaver(EmployeeDetails.parseKiwiSaver(rows.getString(4)));
				dM.set_hourlyPay(Double.parseDouble(rows.getString(5)));
			} catch (CursorIndexOutOfBoundsException e) {
			} catch (DetailParseException e) {
				Log.e("DetailParseException", e.getMessage());
			}
			
			db.close();
			
			return dM;
		}
		
		
		/**
		 * @return The name at the top of the database
		 * null if no table
		 */
		public String getName() {
			SQLiteDatabase db;
			db =  openDatabase();	
			Cursor rows = null;
			try {
				rows = db.rawQuery("SELECT * FROM "+ TABLE_NAME, null);
			} catch (SQLiteException e) {
				return null;
			}
			String name = null;
			if (rows.moveToFirst() == false) {
				Log.e("myDatabase", "First row empty");
			} else {
				Log.d("myDatabase", "Not empty");
			}
			try {
				name = rows.getString(0);
			} catch (CursorIndexOutOfBoundsException e) {
				Log.e("myDatabase", "There is no value in row 0");
			}
			db.close(); // Always close the database
			return name;		
		}
		
		
		
		private SQLiteDatabase openDatabase() {
			return _context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
		}
		
		/**
		 * @return true if the database was successfully deleted
		 */
		public boolean deleteDatabase() {
			return _context.deleteDatabase(DATABASE_NAME);
		}

		
}
