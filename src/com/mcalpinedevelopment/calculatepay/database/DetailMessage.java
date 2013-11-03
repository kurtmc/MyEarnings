package com.mcalpinedevelopment.calculatepay.database;

public class DetailMessage {
	private String _name;
	private String _payPeriod;
	private String _taxCode;
	private String _kiwiSaver;
	private String _studentLoan;
	private String _hourlyPay;
	public DetailMessage(String name, String payPeriod, String taxCode, String kiwiSaver, String studentLoan, String hourlyPay){
		_name = name;
		_payPeriod = payPeriod;
		_taxCode = taxCode;
		_kiwiSaver = kiwiSaver;
		_studentLoan = studentLoan;
		_hourlyPay = hourlyPay;
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
}
