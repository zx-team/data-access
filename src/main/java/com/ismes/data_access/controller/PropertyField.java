package com.ismes.data_access.controller;

public class PropertyField {
	String type;
	String minValue;
	String maxValue;
	String multiValue;
	String sqlField;
	String dateformat;
	String regex;

	public String getDateformat() {
		return dateformat;
	}

	public void setDateformat(String dateformat) {
		this.dateformat = dateformat;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMinValue() {
		return minValue;
	}

	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}

	public String getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}

	public String getSqlField() {
		return sqlField;
	}

	public void setSqlField(String sqlField) {
		this.sqlField = sqlField;
	}

	public String getMultiValue() {
		return multiValue;
	}

	public void setMultiValue(String multiValue) {
		this.multiValue = multiValue;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

}
