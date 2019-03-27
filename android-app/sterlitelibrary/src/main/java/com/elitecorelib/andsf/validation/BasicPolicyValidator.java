/**
 * 
 */
package com.elitecorelib.andsf.validation;

import com.elitecorelib.andsf.exception.InvalidDataException;
import com.elitecorelib.andsf.pojo.ANDSFPolicies;
import com.elitecorelib.andsf.pojo.ANDSFPrioritizedAccess;
import com.elitecorelib.andsf.utility.CustomConstant;
import com.elitecorelib.andsf.utility.CustomMessage;
import com.elitecorelib.core.EliteSession;

/**
 * @author brijesh.mistry
 *
 */
public class BasicPolicyValidator implements IValidationHandler {

	private IValidationHandler nextHandler; 
	
	public void setNextValidator(IValidationHandler nextValidationHandler) {
		this.nextHandler = nextValidationHandler; 
	}

	
	
	public int validate(ANDSFPolicies policy) throws InvalidDataException {
		
		int validationStatus = CustomConstant.NOT_PROVIDED;
		
		if(policy==null){
			validationStatus = CustomConstant.NOT_FOUND_IN_POLICY;
			EliteSession.eLog.d(CustomConstant.ApplicationTag,"Policy is Null So,Skipping Validation Part.");
			return validationStatus;
		}
		
		//Rule Priorrity Validation
		if(policy.rulePriority < 0){
			validationStatus = CustomConstant.WRONG_VALUE;
			throw new InvalidDataException(CustomMessage.InvalidRulePriority);
		}
		
		
		//prioritizedAccess Exception
		if(policy.prioritizedAccess == null || policy.prioritizedAccess.isEmpty()){
			throw new InvalidDataException(CustomMessage.missingPrioritizedAccess);
		}else{
			//Validationg Individual prioritized Access
			for(ANDSFPrioritizedAccess prioritizedAccess:policy.prioritizedAccess){
				if(prioritizedAccess!=null){
					prioritizedAccess.validate();
				}
			}
		}								
		
		
		return validationStatus ;
	}

}
