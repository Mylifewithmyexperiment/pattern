package com.elitecorelib.andsf.validation;

import com.elitecorelib.andsf.exception.InvalidDataException;
import com.elitecorelib.andsf.pojo.ANDSFPolicies;

public interface IValidationHandler {
	
	public void setNextValidator(IValidationHandler nextValidationHandler);
	
	public int validate(ANDSFPolicies policy) throws InvalidDataException;

}
