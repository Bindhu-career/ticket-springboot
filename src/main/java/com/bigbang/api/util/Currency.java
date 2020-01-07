package com.bigbang.api.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;


public class Currency {
	
 public static String convertCentToDollar(String payment) {
	 System.out.println("in currency are -----  "+ payment);
	 String s = null;
	 NumberFormat n = NumberFormat.getCurrencyInstance(Locale.US); 
	try {
		s = n.format(Long.parseLong(payment));
	} catch (NumberFormatException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 System.out.println("converted dollars are ----- "+ s);
	 return s;
 }

}
