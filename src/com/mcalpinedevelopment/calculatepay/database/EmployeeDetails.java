package com.mcalpinedevelopment.calculatepay.database;

import java.util.Locale;

public class EmployeeDetails {
	public enum PayPeriod {
		WEEKLY, FORTNIGHTLY, MONTHLY;
	}
	public enum TaxCode {
		M, ME, ML;
	}
	public enum KiwiSaver {
		ZERO(0), ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8);
		
		private final double _value;
		private KiwiSaver(double value) {
			this._value = value;
		}
		
		public double getValue() {
			return this._value;
		}
	}
	public enum StudentLoan {
		TRUE, FALSE;
	}

	private static String stripString(String string) {
		return string.trim().toLowerCase(new Locale("en"));
	}
	
	public static PayPeriod parsePayPeriod(String detail) throws DetailParseException {
		String string = stripString(detail);
		if (string.equals("weekly")) {
			return PayPeriod.WEEKLY;
		} else if (string.equals("fortnightly")) {
			return PayPeriod.FORTNIGHTLY;
		} else if (string.equals("monthly")) {
			return PayPeriod.MONTHLY;
		}
		throw new DetailParseException("Error parsing pay period");
	}
	public static TaxCode parseTaxCode(String detail) throws DetailParseException {
		String string = stripString(detail);
		if (string.equals("m")) {
			return TaxCode.M;
		} else if (string.equals("me")) {
			return TaxCode.ME;
		} else if (string.equals("ml")) {
			return TaxCode.ML;
		}
		throw new DetailParseException("Error parsing tax code");
			
	}
	public static KiwiSaver parseKiwiSaver(String detail) throws DetailParseException {
		String string = stripString(detail);
		
		if (string.equals("zero") || string.equals("0")) {
			return KiwiSaver.ZERO;
		} else if (string.equals("one") || string.equals("1")) {
			return KiwiSaver.ONE;
		} else if (string.equals("two") || string.equals("2")) {
			return KiwiSaver.TWO;
		} else if (string.equals("three") || string.equals("3")) {
			return KiwiSaver.THREE;
		} else if (string.equals("four") || string.equals("4")) {
			return KiwiSaver.FOUR;
		} else if (string.equals("five") || string.equals("5")) {
			return KiwiSaver.FIVE;
		} else if (string.equals("six") || string.equals("6")) {
			return KiwiSaver.SIX;
		} else if (string.equals("seven") || string.equals("7")) {
			return KiwiSaver.SEVEN;
		} else if (string.equals("eight") || string.equals("8")) {
			return KiwiSaver.EIGHT;
		}		
		throw new DetailParseException("Error parsing Kiwi Saver");
		
	}
	public static StudentLoan parseStudentLoan(String detail) throws DetailParseException {
		String string = stripString(detail);
		
		if (string.equals("true")) {
			return StudentLoan.TRUE;
		} else if (string.equals("false")) {
			return StudentLoan.FALSE;
		}
		
		throw new DetailParseException("Error parsing student loan");
		
		
	}
}
