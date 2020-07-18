package com.robertx22.mine_and_slash.database.data.stats;

public class ConversionMethod {

	public ConversionMethod(Stat conv, Stat thatBenefits) {
		this.converted = conv;
		this.statThatBenefits = thatBenefits;
	}

	public Stat converted;
	public Stat statThatBenefits;
}
