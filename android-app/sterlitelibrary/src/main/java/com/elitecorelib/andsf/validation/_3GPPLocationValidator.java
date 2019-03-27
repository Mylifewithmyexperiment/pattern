package com.elitecorelib.andsf.validation;

import com.elitecorelib.andsf.api.DeviceDetail;
import com.elitecorelib.andsf.exception.InvalidDataException;
import com.elitecorelib.andsf.pojo.ANDSFCircular;
import com.elitecorelib.andsf.pojo.ANDSFGeoLocation;
import com.elitecorelib.andsf.pojo.ANDSFLocation3GPP;
import com.elitecorelib.andsf.pojo.ANDSFPolicies;
import com.elitecorelib.andsf.utility.CustomConstant;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.wifi.utility.WifiUtility;

public class _3GPPLocationValidator implements IValidationHandler {

    private final static String MODULE = "_3GPPLocationValidator";
    private IValidationHandler nextValidator;
    private Boolean isLACMatch = false;
    private Boolean isCellIdMatch = false;
    private boolean isTACMatch = false;
    private boolean isEU_TRAN = false;


    public void setNextValidator(IValidationHandler nextValidationHandler) {
        this.nextValidator = nextValidationHandler;

    }


    public int validate(ANDSFPolicies policy) throws InvalidDataException {
        int validationStatus = CustomConstant.NOT_MATCHED;

        EliteSession.eLog.d(MODULE, "3GPP validation started for Policy " + policy.policyName);

        if (policy.validityArea == null || policy.validityArea.location_3GPP == null || policy.validityArea.location_3GPP.isEmpty()) {
            EliteSession.eLog.d(MODULE, "Validity Area or 3GPP Location is empty,so no need to validate 3GPP area.");

            if (policy.validityArea.geo_Location_ == null || policy.validityArea.geo_Location_.isEmpty()) {
                validationStatus = CustomConstant.NOT_MATCHED;
                return validationStatus;
            }
        }

        DeviceDetail devicelocation = DeviceDetail.getInstance();
        if (devicelocation != null) {
            if (devicelocation.getUe3GPPLocation() != null) {

                if (policy.validityArea.location_3GPP != null && !policy.validityArea.location_3GPP.isEmpty()) {
                    for (ANDSFLocation3GPP location3gpp : policy.validityArea.location_3GPP) {
                        location3gpp.validate();
                        /**
                         * EliteSession.eLogic for 3GPP location validation
                         * Match PLMN ,If PLMN is matched then check for TAC,LAC,GERAN,EUTRAN or EUTRA,
                         * If any one of match then skip next check and return true.
                         *
                         */
                        if (location3gpp.PLMN != null && location3gpp.PLMN.equals(devicelocation.getUe3GPPLocation().PLMN)) {

                            EliteSession.eLog.d(MODULE, " 3GPP policy PLMN= " + location3gpp.PLMN + " device PLMN=" + devicelocation.getUe3GPPLocation().PLMN + " matched");

                            if(devicelocation.getIsLTE_DataNetwork()) {

                                if (location3gpp.TAC != null && location3gpp.TAC.equals(devicelocation.getUe3GPPLocation().TAC)) {
                                    isTACMatch = true;
                                    EliteSession.eLog.d(MODULE, " Policy TAC= " + location3gpp.TAC + " and Device TAC=" + devicelocation.getUe3GPPLocation().TAC + " matched");
                                }

                                EliteSession.eLog.d(MODULE, " Policy EUTRA_CI= " + location3gpp.EUTRA_CI + " and Device EUTRA_CI=" + devicelocation.getUe3GPPLocation().EUTRA_CI);

                                if (location3gpp.EUTRA_CI != null && location3gpp.EUTRA_CI.equals(devicelocation.getUe3GPPLocation().EUTRA_CI)) {
                                    isEU_TRAN = true;
                                    EliteSession.eLog.d(MODULE, " Policy EUTRA_CI= " + location3gpp.EUTRA_CI + " and Device EUTRA_CI=" + devicelocation.getUe3GPPLocation().EUTRA_CI + " matched");
                                    validationStatus = CustomConstant.MATCHED;
                                    break;
                                }

                            } else {

                                if (location3gpp.LAC != null && location3gpp.LAC.equals(devicelocation.getUe3GPPLocation().LAC)) {
                                    isLACMatch = true;
                                    EliteSession.eLog.d(MODULE, " Policy LAC= " + location3gpp.LAC + " and Device LAc=" + devicelocation.getUe3GPPLocation().LAC + " matched");
                                }

                                if (location3gpp.GERAN_CI != null && location3gpp.GERAN_CI.equals(devicelocation.getUe3GPPLocation().GERAN_CI)) {
                                    isCellIdMatch = true;
                                    EliteSession.eLog.d(MODULE, " Policy cellid= " + location3gpp.GERAN_CI + " and Device cellid=" + devicelocation.getUe3GPPLocation().GERAN_CI + " matched");
                                    validationStatus = CustomConstant.MATCHED;
                                    break;
                                }
                            }

                            // Comment for jio demo purpose
							/*if (location3gpp.UTRAN_CI != null && location3gpp.UTRAN_CI.equals(devicelocation.getUe3GPPLocation().UTRAN_CI)) {
								is3GPPLocationMatch = true;
								EliteSession.eLog.d(MODULE, " UTRAN_CI is matched " + location3gpp.UTRAN_CI);
								validationStatus = CustomConstant.MATCHED;
								break;

							}
							*/

                        } else {
                            EliteSession.eLog.d(MODULE, " PLMN not match or null");
                            continue;
                        }
                    }
                }

            } else {
                EliteSession.eLog.d(MODULE, "Current Device 3GPP Location is  not found");
                validationStatus = CustomConstant.NOT_MATCHED;
            }

            if ((isLACMatch == true && isCellIdMatch == true) || (isTACMatch == true && isEU_TRAN == true) ) {
                validationStatus = CustomConstant.MATCHED;
                return validationStatus;
            }

            if (devicelocation.getUeGeo_Location() != null) {

                if (policy.validityArea.geo_Location_ != null && !policy.validityArea.geo_Location_.isEmpty()) {

                    outerLoop:
                    for (ANDSFGeoLocation locationGeo : policy.validityArea.geo_Location_) {

                        if (locationGeo.circular != null && !locationGeo.circular.isEmpty()) {

                            for (ANDSFCircular GeoCurcular : locationGeo.circular) {
                                if (GeoCurcular.latitude == null && GeoCurcular.latitude.equals("")) {
                                    EliteSession.eLog.d(MODULE, " Geo latitude null or blank ");
                                    validationStatus = CustomConstant.NOT_MATCHED;
                                    break;
                                }

                                if (GeoCurcular.longitude == null && GeoCurcular.longitude.equals("")) {
                                    EliteSession.eLog.d(MODULE, " Geo longitude  null or blank");
                                    validationStatus = CustomConstant.NOT_MATCHED;
                                    break;
                                }

                                EliteSession.eLog.d(MODULE, " Geo Policy Latitute: " + GeoCurcular.latitude + " Longitude : " + GeoCurcular.longitude);
                                if (devicelocation.getUeGeo_Location().latitude != null && !devicelocation.getUeGeo_Location().latitude.equals("") && devicelocation.getUeGeo_Location().longitude != null && !devicelocation.getUeGeo_Location().longitude.equals("")) {
                                    EliteSession.eLog.d(MODULE, " Device Latitute: " + devicelocation.getUeGeo_Location().latitude + " Longitude : " + devicelocation.getUeGeo_Location().longitude);
                                    double distance = WifiUtility.getDistanceBetweenLocations(Double.parseDouble(GeoCurcular.latitude), Double.parseDouble(GeoCurcular.longitude), Double.parseDouble(devicelocation.getUeGeo_Location().latitude), Double.parseDouble(devicelocation.getUeGeo_Location().longitude), 'M');
                                    if (distance < Double.parseDouble(GeoCurcular.radius)) {
                                        EliteSession.eLog.d(MODULE, " Distance= " + distance + " and ANDSF Redious=" + Double.parseDouble(GeoCurcular.radius) + " condition match");
                                        validationStatus = CustomConstant.MATCHED;
                                        break outerLoop;
                                    } else {
                                        EliteSession.eLog.d(MODULE, " Distance= " + distance + " and ANDSF Redious=" + Double.parseDouble(GeoCurcular.radius) + " condition not match");
                                        validationStatus = CustomConstant.NOT_MATCHED;
                                    }
                                } else {
                                    EliteSession.eLog.d(MODULE, " Device Latitute Longitude not found");
                                    validationStatus = CustomConstant.NOT_MATCHED;
                                    break;
                                }
                            }
                        }
                    }
                }
            } else {
                EliteSession.eLog.d(MODULE, "Current Device Geo location is  not found");
                validationStatus = CustomConstant.NOT_MATCHED;
            }
        } else {
            EliteSession.eLog.d(MODULE, "Current Device detail null");
            validationStatus = CustomConstant.NOT_MATCHED;
        }
        return validationStatus;
    }
}
