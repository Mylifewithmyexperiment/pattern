package com.elitecorelib.andsf.api;

import com.elitecorelib.andsf.exception.InvalidDataException;
import com.elitecorelib.andsf.pojo.ANDSFDiscoveryInformations;
import com.elitecorelib.andsf.pojo.ANDSFPolicies;
import com.elitecorelib.andsf.pojo.ANDSFPolicyResponse;
import com.elitecorelib.andsf.pojo.ANDSFPrioritizedAccess;
import com.elitecorelib.andsf.utility.ANDSFConstant;
import com.elitecorelib.andsf.utility.CustomConstant;
import com.elitecorelib.andsf.validation.IValidationHandler;
import com.elitecorelib.andsf.validation.TimeOfDayValidator;
import com.elitecorelib.andsf.validation.WLanLocationValidator;
import com.elitecorelib.andsf.validation._3GPPLocationValidator;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.realm.RealmOperation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PolicyImplementation {

    private final static String MODULE = "PolicyImplementation";
    private static ANDSFPolicyResponse policyResponse;
    private static ANDSFPolicies currentValidPolicy;
    private static int currentPolicyRulePriority;
    private static int currentValidPolicyId;
    private static String currentAccessId;
    private static ArrayList<ANDSFDiscoveryInformations> currentDiscoveryInformation;
    private static List<Integer> noAccessPolicyIdList = new ArrayList<Integer>();
    private static ANDSFPrioritizedAccess currentPrioritizedAccess;
    private static List<String> noAccessIdList = new ArrayList<String>();
    private static int isSwitchOnDatabyPolicy = 0;

    public static void setPolicyResponse(ANDSFPolicyResponse polResponse) {
        resetALL();
        policyResponse = polResponse;
//		evaluate();
    }


    public static void evaluate() {
        List<ANDSFPrioritizedAccess> accessIdList = null;

        EliteSession.eLog.d(MODULE, "--------------Policy Validation started Here----------------------");
        findValidPolicy();

        if (currentValidPolicy != null) {
            currentDiscoveryInformation = new ArrayList<>();

            accessIdList = currentValidPolicy.prioritizedAccess;
            if (accessIdList != null && !accessIdList.isEmpty()) {
                EliteSession.eLog.d(MODULE,"---------------------Before Sorting -----------------");
                for (ANDSFPrioritizedAccess access : accessIdList) {
                    EliteSession.eLog.d(MODULE,"SSID Name -"+access.getAccessId() +" Priority  - "+access.getAccessNetworkPriority());
                }

                RealmOperation realmOperation = RealmOperation.with(LibraryApplication.getLibraryApplication().getLibraryContext());
                realmOperation.sortDescoveryInformation(accessIdList);

                EliteSession.eLog.d(MODULE,"---------------------after Sorting -----------------");
                for (ANDSFPrioritizedAccess access : accessIdList) {
                    EliteSession.eLog.d(MODULE,"SSID Name -"+access.getAccessId() +" Priority  - "+access.getAccessNetworkPriority());
                }

                for (ANDSFPrioritizedAccess prioritizedAccess : accessIdList) {

                    EliteSession.eLog.d(MODULE, "policy Name " + currentValidPolicy.policyName + " Priritized Access " + prioritizedAccess.accessId);
                    if (prioritizedAccess != null) {

                        if (noAccessIdList != null && !noAccessIdList.isEmpty() && noAccessIdList.contains(currentValidPolicyId + "_" + prioritizedAccess.accessId)) {
                            EliteSession.eLog.d(MODULE, "As per last attempt to connect it is not available ,so skipping this prioritized access");
                            continue;
                        }

                        if (prioritizedAccess.accessNetworkPriority > 0 && prioritizedAccess.accessNetworkPriority < 250) {
                            if (prioritizedAccess.accessTechnology == ANDSFConstant.AccessTechnologyWLAN) {
                                currentAccessId = prioritizedAccess.accessId;
                                findDiscoveryInformation();

                                if (currentDiscoveryInformation != null) {
                                    EliteSession.eLog.d(MODULE, "Current WLAN Connection attributes Size = " + currentDiscoveryInformation.size());
                                }

                            } else if (prioritizedAccess.accessTechnology == ANDSFConstant.AccessTechnology3GPP) {
                                isSwitchOnDatabyPolicy = 1;
                                //Swith on Data Networks.

                            }
                        } else if (prioritizedAccess.accessNetworkPriority == 254 && prioritizedAccess.accessNetworkPriority == 255) {
                            if (prioritizedAccess.accessTechnology == ANDSFConstant.AccessTechnologyWLAN) {
                                findDiscoveryInformation();

                                return;
                                //Try to disconnect particular Connections

                            } else if (prioritizedAccess.accessTechnology == ANDSFConstant.AccessTechnology3GPP) {
                                //Swith on Data Networks.
                                //Switch of Data networks configured into devices
                                isSwitchOnDatabyPolicy = 2;
                                return;
                            }
                        }
                    }

                }
                if (currentDiscoveryInformation == null) {
                    EliteSession.eLog.d(MODULE, "Policy Donot have any valid Data network Connection and not found SSID also ");
                    EliteSession.eLog.d(MODULE, "Put it into Black List policy ");
                    noAccessPolicyIdList.add(currentValidPolicyId);
                    resetCurrentPolicy();
                    evaluate();
                }

            } else {

            }

        } else {
            EliteSession.eLog.d(MODULE, "None of Valid ANDSF policy found into validity areas.");
        }
    }

    private static void findDiscoveryInformation() {
        try {
            EliteSession.eLog.d(MODULE, "Find Discovery Information called for:" + currentAccessId);
            List<ANDSFDiscoveryInformations> discoveryInformationList = policyResponse.discoveryInformations;

            if (discoveryInformationList != null && !discoveryInformationList.isEmpty() && currentAccessId != null && !"".equals(currentAccessId)) {

                for (ANDSFDiscoveryInformations discoveryInformation : discoveryInformationList) {
                    if (discoveryInformation.accessNetworkInformationWLAN != null && discoveryInformation.accessNetworkInformationWLAN.SSIDName != null && discoveryInformation.accessNetworkInformationWLAN.SSIDName.equalsIgnoreCase(currentAccessId)) {
                        currentDiscoveryInformation.add(discoveryInformation);
                    }
                }
            }
        }catch (Exception e) {
            EliteSession.eLog.d(MODULE, "Error in add Discovery Information - "+e.getLocalizedMessage());
        }
    }

    public static void findValidPolicy() {
        //Sort Policy based on Priority
        List<ANDSFPolicies> policyList = policyResponse.policies;
        if (policyList != null && !policyList.isEmpty()) {

            if (noAccessPolicyIdList != null && noAccessPolicyIdList.isEmpty() && noAccessPolicyIdList.size() == policyList.size()) {
                EliteSession.eLog.d(MODULE, "There is no valid Policy is found ,so Skipping this check.");
                resetCurrentPolicy();
                return;
            }

            Collections.sort(policyList);
            //Now iterate Policy once it is sorted
            EliteSession.eLog.d(MODULE, "No of Policy received." + policyList.size());

            for (ANDSFPolicies policy : policyList) {

                if (!policy.enable) {
                    EliteSession.eLog.i(MODULE, policy.policyName+" Policies is enable " + policy.enable);
                    continue;
                }


                if (noAccessPolicyIdList != null && !noAccessPolicyIdList.isEmpty() && policy != null && noAccessPolicyIdList.contains(policy.policyId)) {
                    EliteSession.eLog.d(MODULE, "This policy do not have valid data connections " + policy.policyName);
                    continue;
                }

                EliteSession.eLog.d(MODULE, "Policy " + policy.policyName + " ,Id= " + policy.policyId + " ,Priority= " + policy.rulePriority);
//                EliteSession.eLog.d(MODULE, "Policy Id is " + policy.policyId);
//                EliteSession.eLog.d(MODULE, "Policy Rule Priority is " + policy.rulePriority);

                //3GPP validation started
                IValidationHandler _3ggpValidationHandler = new _3GPPLocationValidator();
                int _3gppValidationStatus = CustomConstant.UNKNOWN;

                try {

                    _3gppValidationStatus = _3ggpValidationHandler.validate(policy);

                } catch (InvalidDataException e) {
                    EliteSession.eLog.e(MODULE, "Exception while validating 3GPP Validation " + e.getMessage());
                    continue;
                }

                EliteSession.eLog.d(MODULE, policy.policyName + " is validation Status for 3gpp :" + _3gppValidationStatus);

                if (_3gppValidationStatus == CustomConstant.NOT_MATCHED) {
                    EliteSession.eLog.d(MODULE, policy.policyName + " is not valid policy due to Invalid 3gpp ");
                    continue;
                }

                //Now checking for Timebase valid Policies.
                IValidationHandler timeOfdayValidator = new TimeOfDayValidator();
                int timeOfDayValidationStatus = CustomConstant.UNKNOWN;

                try {

                    timeOfDayValidationStatus = timeOfdayValidator.validate(policy);

                } catch (InvalidDataException e) {
                    EliteSession.eLog.e(MODULE, "Exception while validating TimeOfDay Validator" + e.getMessage());
                    continue;
                }

                EliteSession.eLog.d(MODULE, policy.policyName + " is validation Status :" + timeOfDayValidationStatus);

                if (timeOfDayValidationStatus == CustomConstant.NOT_MATCHED) {
                    EliteSession.eLog.d(MODULE, policy.policyName + " is not valid policy due to Invalid data into Time of day.");
                    continue;
                }


                //WLAN validation started
               /* IValidationHandler wlanValidationHandler = new WLanLocationValidator();
                int wlanValidationStatus = CustomConstant.UNKNOWN;

                try {

                    wlanValidationStatus = wlanValidationHandler.validate(policy);

                } catch (InvalidDataException e) {
                    EliteSession.eLog.e(MODULE, "Exception while validating Wlan Location validation " + e.getMessage());
                    continue;
                }

                if (wlanValidationStatus == CustomConstant.NOT_MATCHED) {
                    EliteSession.eLog.d(MODULE, policy.policyName + " is not valid policy due to Invalid data into Wlan.");
                    continue;
                }


                //WLAN validation started
                IValidationHandler geoValidationHandler = new WLanLocationValidator();
                int geoValidationStatus = CustomConstant.UNKNOWN;

                try {

                    geoValidationStatus = geoValidationHandler.validate(policy);

                } catch (InvalidDataException e) {
                    EliteSession.eLog.e(MODULE, "Exception while validating geo Location validation " + e.getMessage());
                    continue;
                }

                if (geoValidationStatus == CustomConstant.NOT_MATCHED) {
                    EliteSession.eLog.d(MODULE, policy.policyName + " is not valid policy due to Invalid data into geo.");
                    continue;
                }*/

                EliteSession.eLog.d(MODULE, policy.policyName + " is valid Policy after validtion.");

                currentValidPolicy = policy;
                currentValidPolicyId = policy.policyId;
                currentPolicyRulePriority = policy.rulePriority;
                break;
            }
        }
    }

    public static ANDSFDiscoveryInformations getCurrentValidDiscoveryInformation() {
        return null;
    }

    public static ArrayList<ANDSFDiscoveryInformations> getCurrentValidDiscoveryInformationList() {
        return currentDiscoveryInformation;
    }



    public static ANDSFPolicies getCurrentValidPolicy() {
        return currentValidPolicy;
    }

    public static void setPolicyStatus(int policyid, boolean status) {
        if (!status) {
            noAccessPolicyIdList.add(policyid);
        }
        resetCurrentPolicy();
    }

    public static void resetALL() {
        currentValidPolicy = null;
        ;
        currentValidPolicyId = 0;
        currentPolicyRulePriority = 0;
        isSwitchOnDatabyPolicy = 0;
        currentDiscoveryInformation = null;
        noAccessIdList.clear();
        noAccessPolicyIdList.clear();
    }

    public static void resetCurrentPolicy() {
        currentValidPolicy = null;
        ;
        currentValidPolicyId = 0;
        currentPolicyRulePriority = 0;
        currentDiscoveryInformation = null;
        isSwitchOnDatabyPolicy = 0;
    }


    public static void addUnRechableSSID(String ssid) {
        noAccessIdList.add(currentValidPolicyId + "_" + ssid);
    }

    public static boolean findValidPolicy(ANDSFPolicies policy) {
        boolean ValidationStatus = false;

        EliteSession.eLog.d(MODULE, "Policy Name is " + policy.policyName);
        EliteSession.eLog.d(MODULE, "Policy Id is " + policy.policyId);
        EliteSession.eLog.d(MODULE, "Policy Rule Priority is " + policy.rulePriority);

        //Now checking for valid Policies.
        IValidationHandler timeOfdayValidator = new TimeOfDayValidator();
        int timeOfDayValidationStatus = CustomConstant.UNKNOWN;

        try {

            timeOfDayValidationStatus = timeOfdayValidator.validate(policy);

        } catch (InvalidDataException e) {
            EliteSession.eLog.e(MODULE, "Exception while validating TimeOfDay Validator" + e.getMessage());
            EliteSession.eLog.e(MODULE, policy.policyName + " is not valid policy due to Invalid data into Time of day.");
            EliteSession.eLog.e(MODULE, "Evaluating Next policy");
            ValidationStatus = false;
            return ValidationStatus;
        }

        EliteSession.eLog.d(MODULE, policy.policyName + " is validation Status :" + timeOfDayValidationStatus);

        if (timeOfDayValidationStatus == CustomConstant.NOT_MATCHED) {
            EliteSession.eLog.d(MODULE, policy.policyName + " is not valid policy due to Invalid data into Time of day.");
            EliteSession.eLog.d(MODULE, "Evaluating Next policy");
            ValidationStatus = false;
            return ValidationStatus;

        }


        //3GPP validation started
        IValidationHandler _3ggpValidationHandler = new _3GPPLocationValidator();
        int _3gppValidationStatus = CustomConstant.UNKNOWN;

        try {

            _3gppValidationStatus = _3ggpValidationHandler.validate(policy);

        } catch (InvalidDataException e) {
            EliteSession.eLog.e(MODULE, "Exception while validating 3GPP Validation " + e.getMessage());
            EliteSession.eLog.e(MODULE, policy.policyName + " is not valid policy due to Invalid data into 3GPP Locations .");
            EliteSession.eLog.e(MODULE, "Evaluating Next policy");
            ValidationStatus = false;
            return ValidationStatus;
        }

        EliteSession.eLog.d(MODULE, policy.policyName + " is validation Status for 3gpp :" + _3gppValidationStatus);

        if (_3gppValidationStatus == CustomConstant.NOT_MATCHED) {
            EliteSession.eLog.d(MODULE, policy.policyName + " is not valid policy due to Invalid data into Time of day.");
            EliteSession.eLog.d(MODULE, "Evaluating Next policy");
            ValidationStatus = false;
            return ValidationStatus;

        }


        //WLAN validation started
        IValidationHandler wlanValidationHandler = new WLanLocationValidator();
        int wlanValidationStatus = CustomConstant.UNKNOWN;

        try {

            wlanValidationStatus = wlanValidationHandler.validate(policy);

        } catch (InvalidDataException e) {
            EliteSession.eLog.e(MODULE, "Exception while validating Wlan Location validation " + e.getMessage());
            EliteSession.eLog.e(MODULE, policy.policyName + " is not valid policy due to Invalid data into 3GPP Locations .");
            EliteSession.eLog.e(MODULE, "Evaluating Next policy");
            ValidationStatus = false;
            return ValidationStatus;

        }

        EliteSession.eLog.d(MODULE, policy.policyName + " is validation Status for wlan :" + wlanValidationStatus);

        if (wlanValidationStatus == CustomConstant.NOT_MATCHED) {
            EliteSession.eLog.d(MODULE, policy.policyName + " is not valid policy due to Invalid data into Wlan.");
            EliteSession.eLog.d(MODULE, "Evaluating Next policy");
            ValidationStatus = false;
            return ValidationStatus;

        }


        //WLAN validation started
        IValidationHandler geoValidationHandler = new WLanLocationValidator();
        int geoValidationStatus = CustomConstant.UNKNOWN;

        try {

            geoValidationStatus = geoValidationHandler.validate(policy);

        } catch (InvalidDataException e) {
            EliteSession.eLog.e(MODULE, "Exception while validating geo Location validation " + e.getMessage());
            EliteSession.eLog.e(MODULE, policy.policyName + " is not valid policy due to Invalid data into geo Location .");
            EliteSession.eLog.e(MODULE, "Evaluating Next policy");
            ValidationStatus = false;
            return ValidationStatus;
        }

        EliteSession.eLog.d(MODULE, policy.policyName + " is validation Status for Geo :" + geoValidationStatus);

        if (geoValidationStatus == CustomConstant.NOT_MATCHED) {
            EliteSession.eLog.d(MODULE, policy.policyName + " is not valid policy due to Invalid data into geo.");
            EliteSession.eLog.d(MODULE, "Evaluating Next policy");
            ValidationStatus = false;
            return ValidationStatus;
        }

        EliteSession.eLog.d(MODULE, policy.policyName + " is valid Policy after validtion.");
        EliteSession.eLog.d(MODULE, policy.policyName + " now processing prioritized access.");


        EliteSession.eLog.d(MODULE, policy.policyName + " NO Further Policy Validation.");
        ValidationStatus = true;
        return ValidationStatus;

    }


}
