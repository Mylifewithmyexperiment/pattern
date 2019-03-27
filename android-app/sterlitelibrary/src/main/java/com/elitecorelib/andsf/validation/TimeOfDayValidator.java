package com.elitecorelib.andsf.validation;

import com.elitecorelib.andsf.exception.InvalidDataException;
import com.elitecorelib.andsf.pojo.ANDSFPolicies;
import com.elitecorelib.andsf.pojo.ANDSFTimeOfDay;
import com.elitecorelib.andsf.utility.CustomConstant;
import com.elitecorelib.core.EliteSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeOfDayValidator implements IValidationHandler {

    private final static String MODULE = "TimeOfDayValidator";
    private IValidationHandler nextValidator;

    public void setNextValidator(IValidationHandler nextValidationHandler) {
        this.nextValidator = nextValidationHandler;
    }

    public int validate(ANDSFPolicies policy) {

        int validationStatus = CustomConstant.NOT_MATCHED;

        EliteSession.eLog.d(MODULE, "Policy Time of Day validation started");

        if (policy.timeOfDay == null || policy.timeOfDay.isEmpty()) {
            EliteSession.eLog.d(MODULE, "Policy Name - " + policy.policyName);
            EliteSession.eLog.d(MODULE, "Time of Day is not Found into Policy.");
            validationStatus = CustomConstant.NOT_FOUND_IN_POLICY;
            return validationStatus;
        } else {
            EliteSession.eLog.d(MODULE, "Policy Name - " + policy.policyName);
            EliteSession.eLog.d(MODULE, "Time of Day is Found into Policy.");
        }

        for (ANDSFTimeOfDay timeOfDay : policy.timeOfDay) {

            boolean isTimeStart = true;
            boolean isDateStart = true;
            boolean isTimeStop = true;
            boolean isDateStop = true;

            if (timeOfDay.timeStart == null || "".equals(timeOfDay.timeStart)) {
                isTimeStart = false;
            }

            if (timeOfDay.timeStop == null || "".equals(timeOfDay.timeStop)) {
                isTimeStop = false;
            }

            if (timeOfDay.dateStart == null || "".equals(timeOfDay.dateStart)) {
                isDateStart = false;
            }

            if (timeOfDay.dateStop == null || "".equals(timeOfDay.dateStop)) {
                isDateStop = false;
            }

            try {
                timeOfDay.validate(isTimeStart, isTimeStop, isDateStart, isDateStop);
            } catch (InvalidDataException ide) {
                EliteSession.eLog.e(MODULE, "Time of day is not proper into Policy " + timeOfDay + ide.getMessage());
                continue;
            }

            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

            Date startDate = null;
            Date stopDate = null;
            Date startTime = null;
            Date stopTime = null;
            Date currentTime = null;

            Calendar currentCal = new GregorianCalendar();
            Calendar tempCal = new GregorianCalendar();

            try {

                if (isTimeStart)
                    startTime = timeFormat.parse(timeOfDay.timeStart);

                if (isTimeStop)
                    stopTime = timeFormat.parse(timeOfDay.timeStop);

                currentTime = timeFormat.parse(timeFormat.format(currentCal.getTime()));

                if (isDateStart && isTimeStart) {
                    startDate = dateTimeFormat.parse(timeOfDay.dateStart + " " + timeOfDay.timeStart);
                } else if (isDateStart && !isTimeStart) {
                    startDate = dateFormat.parse(timeOfDay.dateStart);
                } else if (!isDateStart && isTimeStart) {
                    startDate = timeFormat.parse(timeOfDay.timeStart);
                    tempCal.setTime(startDate);
                    tempCal.set(Calendar.YEAR, currentCal.get(Calendar.YEAR));
                    tempCal.set(Calendar.MONTH, currentCal.get(Calendar.MONTH));
                    tempCal.set(Calendar.DAY_OF_MONTH, currentCal.get(Calendar.DAY_OF_MONTH));
                    startDate = tempCal.getTime();
                } else {
                    startDate = null;
                }

                if (isDateStop && isTimeStop) {
                    stopDate = dateTimeFormat.parse(timeOfDay.dateStop + " " + timeOfDay.timeStop);
                } else if (isDateStop && !isTimeStop) {
                    stopDate = dateFormat.parse(timeOfDay.dateStop);
                    tempCal.clear();
                    tempCal.setTime(stopDate);
                    tempCal.set(Calendar.HOUR, 23);
                    tempCal.set(Calendar.MINUTE, 59);
                    tempCal.set(Calendar.SECOND, 59);
                    stopDate = tempCal.getTime();
                } else if (!isDateStop && isTimeStop) {
                    stopDate = timeFormat.parse(timeOfDay.timeStop);
                    tempCal.clear();
                    tempCal.setTime(stopDate);
                    tempCal.set(Calendar.YEAR, currentCal.get(Calendar.YEAR));
                    tempCal.set(Calendar.MONTH, currentCal.get(Calendar.MONTH));
                    tempCal.set(Calendar.DAY_OF_MONTH, currentCal.get(Calendar.DAY_OF_MONTH));
                    stopDate = tempCal.getTime();
                } else {
                    stopDate = null;
                }

                EliteSession.eLog.d(MODULE, "After parsing Startdate : " + startDate + " and stop date:" + stopDate);

            } catch (ParseException e) {
                EliteSession.eLog.e(MODULE, "Exceptiion While parsing Dates" + e.getMessage());
                continue;
            }

            boolean timeStopBeforeTimeStart = false;

            if (startDate != null && stopDate != null) {
                timeStopBeforeTimeStart = stopDate.before(startDate);
                EliteSession.eLog.d(MODULE, "Stop Date is before time start");
            }
            Date currDate = currentCal.getTime();

            EliteSession.eLog.d(MODULE, "Current Date : " + currDate);
            EliteSession.eLog.d(MODULE, "Start Date : " + startDate);
            EliteSession.eLog.d(MODULE, "Stop Date : " + stopDate);

            //Implementation is start from Here
            if (startDate != null && stopDate != null && currDate.after(startDate) && currDate.before(stopDate)) {

                if (startTime != null && stopTime != null & currentTime.after(startTime) && currentTime.before(stopTime)) {
                    EliteSession.eLog.i(MODULE, "Time validate");
                    validationStatus = CustomConstant.MATCHED;
                    break;
                } else {
                    EliteSession.eLog.e(MODULE, "Time not validate");
                    continue;
                }

            } else if (startDate != null && stopDate == null && currDate.after(startDate)) {
                validationStatus = CustomConstant.MATCHED;
                break;
            } else if (startDate == null && stopDate != null && currDate.before(stopDate)) {
                validationStatus = CustomConstant.MATCHED;
                break;
            }
        }
        return validationStatus;
    }
}
