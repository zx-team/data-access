package com.ismes.data_access.checkvalue;

import com.ismes.data_access.controller.PropertyField;
import com.ismes.data_access.util.RegExpValidatorUtils;

public class CheckInt extends CheckUtil {

	@Override
	public boolean checkData(PropertyField pf, String jsonstr) {
		if (RegExpValidatorUtils.isNull(pf.getMinValue())) {
			return true;
		} else {
			if (Integer.parseInt(jsonstr) >= Integer.parseInt(pf.getMinValue())) {
				if (RegExpValidatorUtils.isNull(pf.getMaxValue())) {
					return true;
				} else {
					if (Integer.parseInt(jsonstr) <= Integer.parseInt(pf.getMinValue())) {
						return true;
					} else {
						return false;
					}
				}
			} else {
				return false;
			}
		}
	}

}
