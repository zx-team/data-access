package com.ismes.data_access.checkvalue;

import com.ismes.data_access.controller.PropertyField;
import com.ismes.data_access.util.RegExpValidatorUtils;

public class CheckString extends CheckUtil {

	@Override
	public boolean checkData(PropertyField pf, String jsonstr) {
		// TODO Auto-generated method stub
		if (RegExpValidatorUtils.isNull(pf.getDateformat())) {
			return true;
		} else {
			if (RegExpValidatorUtils.isDateFormat(pf.getDateformat(), jsonstr)) {
				return true;
			} else {
				return false;
			}
		}
	}

}
