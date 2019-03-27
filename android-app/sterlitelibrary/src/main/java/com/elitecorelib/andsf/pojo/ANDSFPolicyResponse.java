package com.elitecorelib.andsf.pojo;

import java.io.Serializable;
import java.util.ArrayList;

import io.realm.RealmList;

/**
 * Created by harshesh.soni on 9/22/2016.
 */
public class ANDSFPolicyResponse implements Serializable {

    public ArrayList<String> availablePolicyNames;
    public ArrayList<String> availableDiscoveryInformationNames;
    public ArrayList<ANDSFDiscoveryInformations> discoveryInformations;
    public ArrayList<ANDSFPolicies> policies;

    public ArrayList<String> getAvailablePolicyNames() {
        return availablePolicyNames;
    }

    public void setAvailablePolicyNames(ArrayList<String> availablePolicyNames) {
        this.availablePolicyNames = availablePolicyNames;
    }

    public ArrayList<String> getAvailableDiscoveryInformationNames() {
        return availableDiscoveryInformationNames;
    }

    public void setAvailableDiscoveryInformationNames(ArrayList<String> availableDiscoveryInformationNames) {
        this.availableDiscoveryInformationNames = availableDiscoveryInformationNames;
    }

    public ArrayList<ANDSFDiscoveryInformations> getDiscoveryInformations() {
        return discoveryInformations;
    }

    public void setDiscoveryInformations(ArrayList<ANDSFDiscoveryInformations> discoveryInformations) {
        this.discoveryInformations = discoveryInformations;
    }

    public ArrayList<ANDSFPolicies> getPolicies() {
        return policies;
    }

    public void setPolicies(ArrayList<ANDSFPolicies> policies) {
        this.policies = policies;
    }
}
