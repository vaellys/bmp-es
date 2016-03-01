package com.ist.ioc.service.common.elasticsearch.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Formats {

	public static BigDecimal formatMoney(BigDecimal bigDecimal) {
		return bigDecimal.setScale(2, RoundingMode.DOWN);
	}
	
	public static void main(String[] args) {
		System.out.println(formatMoney(new BigDecimal("0.42105263157894735").multiply(new BigDecimal(100))));
	}

}
