package com.ismes.data_access.checkvalue;

import com.ismes.data_access.controller.PropertyField;
import com.ismes.data_access.util.RegExpValidatorUtils;

public abstract class CheckUtil {
	public boolean publicCheck(PropertyField pf, String jsonstr) {
		if (match(pf, jsonstr) && isIndexof(jsonstr, pf) && checkData(pf, jsonstr)) {
			return true;
		}
		return false;
	}

	// 判断是否符合正则
	public boolean match(PropertyField pf, String str) {
		if (RegExpValidatorUtils.isNull(pf.getRegex())) {
			return true;
		} else {
			if (RegExpValidatorUtils.match(pf.getRegex(), str)) {
				return true;
			} else {
				return false;
			}
		}
	}

	// 判断是否存在
	public boolean isIndexof(String completestr, PropertyField pf) {
		if (RegExpValidatorUtils.isNull(pf.getMultiValue())) {
			return true;
		} else {
			if (RegExpValidatorUtils.isIndexof(pf.getMultiValue(), completestr)) {
				return true;
			} else {
				return false;
			}
		}
	}

	public abstract boolean checkData(PropertyField pf, String jsonstr);

}
