package com.mcalpinedevelopment.calculatepay.database;

public class EmployeePreferences {
	private String _name;
	private EmployeeDetails.PayPeriod _payPeriod;
	private EmployeeDetails.TaxCode _taxCode;
	private EmployeeDetails.KiwiSaver _kiwiSaver;
	private EmployeeDetails.StudentLoan _studentLoan;
	private double _hourlyPay;
	
	public void set_name(String _name) {
		this._name = _name;
	}
	public void set_payPeriod(EmployeeDetails.PayPeriod _payPeriod) {
		this._payPeriod = _payPeriod;
	}
	public void set_taxCode(EmployeeDetails.TaxCode _taxCode) {
		this._taxCode = _taxCode;
	}
	public void set_kiwiSaver(EmployeeDetails.KiwiSaver _kiwiSaver) {
		this._kiwiSaver = _kiwiSaver;
	}
	public void set_studentLoan(EmployeeDetails.StudentLoan _studentLoan) {
		this._studentLoan = _studentLoan;
	}
	public void set_hourlyPay(double _hourlyPay) {
		this._hourlyPay = _hourlyPay;
	}
	public String get_name() {
		return _name;
	}
	public EmployeeDetails.PayPeriod get_payPeriod() {
		return _payPeriod;
	}
	public EmployeeDetails.TaxCode get_taxCode() {
		return _taxCode;
	}
	public EmployeeDetails.KiwiSaver get_kiwiSaver() {
		return _kiwiSaver;
	}
	public EmployeeDetails.StudentLoan get_studentLoan() {
		return _studentLoan;
	}
	public double get_hourlyPay() {
		return _hourlyPay;
	}
	@Override
	public boolean equals(Object obj) {
		EmployeePreferences dM = (EmployeePreferences) obj;
		if (	   this._hourlyPay == dM._hourlyPay
				&& this._name.equals(dM._name)
				&& this._payPeriod.equals(dM._payPeriod)
				&& this._taxCode.equals(dM._taxCode)
				&& this._kiwiSaver.equals(dM._kiwiSaver)
				&& this._studentLoan.equals(dM._studentLoan)) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return 	"Name: "			+ this._name 		+ ", " 
		+ 		"Hourly pay: "		+ this._hourlyPay 	+ ", " 
		+ 		"Pay period: "		+ this._payPeriod 	+ ", " 
		+ 		"Tax code: "		+ this._taxCode 	+ ", " 
		+ 		"Kiwi Saver: "		+ this._kiwiSaver 	+ ", " 
		+ 		"Student loan: "	+ this._studentLoan;
	}
	
	/**
	 * @return a DetailMessage with default values
	 */
	public static EmployeePreferences defaultValue() {
		EmployeePreferences dM = new EmployeePreferences();
    	dM.set_name("");
    	dM.set_payPeriod(EmployeeDetails.PayPeriod.WEEKLY);
    	dM.set_taxCode(EmployeeDetails.TaxCode.M);
    	dM.set_kiwiSaver(EmployeeDetails.KiwiSaver.ZERO);
    	dM.set_studentLoan(EmployeeDetails.StudentLoan.FALSE);
    	dM.set_hourlyPay(0.0);
    	return dM;
	}
	
	
	
	
}
