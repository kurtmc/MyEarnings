package com.mcalpinedevelopment.calculatepay.database;

public class DetailMessage {
	private String _name;
	private String _payPeriod;
	private String _taxCode;
	private String _kiwiSaver;
	private String _studentLoan;
	private String _hourlyPay;
	
	public void set_name(String _name) {
		this._name = _name;
	}
	public void set_payPeriod(String _payPeriod) {
		this._payPeriod = _payPeriod;
	}
	public void set_taxCode(String _taxCode) {
		this._taxCode = _taxCode;
	}
	public void set_kiwiSaver(String _kiwiSaver) {
		this._kiwiSaver = _kiwiSaver;
	}
	public void set_studentLoan(String _studentLoan) {
		this._studentLoan = _studentLoan;
	}
	public void set_hourlyPay(String _hourlyPay) {
		this._hourlyPay = _hourlyPay;
	}
	public String get_name() {
		return _name;
	}
	public String get_payPeriod() {
		return _payPeriod;
	}
	public String get_taxCode() {
		return _taxCode;
	}
	public String get_kiwiSaver() {
		return _kiwiSaver;
	}
	public String get_studentLoan() {
		return _studentLoan;
	}
	public String get_hourlyPay() {
		return _hourlyPay;
	}
	@Override
	public boolean equals(Object obj) {
		DetailMessage dM = (DetailMessage) obj;
		if (	   this._hourlyPay.equals(dM._hourlyPay)
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
	
	
	
	
}
