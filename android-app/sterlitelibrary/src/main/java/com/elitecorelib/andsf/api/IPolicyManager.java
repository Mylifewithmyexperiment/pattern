/**
 * 
 */
package com.elitecorelib.andsf.api;

import com.elitecorelib.andsf.pojo.ResponseObject;

/**
 *
 * Method for Pulling Policies.
 * Call PullPolicyRequest from Device when device neeed new Policies.
 *
 */
public interface IPolicyManager {
	 ResponseObject pullPolicy();

}
