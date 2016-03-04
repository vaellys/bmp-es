package com.ist.ioc.service.common.elasticsearch.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Formats {

	public static BigDecimal formatMoney(BigDecimal bigDecimal) {
		return bigDecimal.setScale(2, RoundingMode.DOWN);
	}
	
	public static void main(String[] args) {
		System.out.println(formatDigital(0.11111111221211111111111111111111111111111));
	}
	
	public static double formatDigital(double digital){
	    BigDecimal bigDecimal = new BigDecimal(digital);
	    BigDecimal bigDecimal2 = new BigDecimal(100);
	    BigDecimal multiply = bigDecimal.multiply(bigDecimal2);
	    BigDecimal formatDigital = formatMoney(multiply);
	    return formatDigital.doubleValue();
	}

}
