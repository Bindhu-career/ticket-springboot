package com.bigbang.api.validator;

import java.util.Arrays;

import org.passay.DigitCharacterRule;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.SpecialCharacterRule;
import org.passay.UppercaseCharacterRule;

public class PasswordConstraintValidator {
	public static boolean isValid(String password) {
		PasswordValidator validator = new PasswordValidator(
				Arrays.asList(new LengthRule(8, 30), new UppercaseCharacterRule(1), new DigitCharacterRule(1),
						new SpecialCharacterRule(1)));

		RuleResult result = validator.validate(new PasswordData(password));
		if (result.isValid()) {
			return true;
		}
		return false;
	}

}
