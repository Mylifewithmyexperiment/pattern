package com.elitecorelib.andsf.pojo;

import com.elitecorelib.andsf.exception.InvalidDataException;
import com.elitecorelib.andsf.utility.CustomMessage;

import java.io.Serializable;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

/**
 * Created by harshesh.soni on 9/23/2016.
 */
@RealmClass
public class ANDSFTimeOfDay implements Serializable, RealmModel {
    public String dateStop = "";
    public String timeStart = "";
    public String dateStart = "";
    public String timeStop = "";
    public int dayOfWeek;

    public String getDateStop() {
        return dateStop;
    }

    public void setDateStop(String dateStop) {
        this.dateStop = dateStop;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getTimeStop() {
        return timeStop;
    }

    public void setTimeStop(String timeStop) {
        this.timeStop = timeStop;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void validate(boolean isTimeStart, boolean isTimeStop, boolean isDateStart, boolean isDateStop) throws InvalidDataException {

        if( !isTimeStart && isTimeStop && !isDateStart && !isDateStop){
            throw new InvalidDataException(CustomMessage.invalidTimeOfDayMessage);
        }


        if( isTimeStart && !isTimeStop && !isDateStart && !isDateStop){
            throw new InvalidDataException(CustomMessage.invalidTimeOfDayMessage);
        }

        if( !isTimeStart && isTimeStop && isDateStart && !isDateStop){
            throw new InvalidDataException(CustomMessage.invalidTimeOfDayMessage);
        }

        if( isTimeStart && !isTimeStop && !isDateStart && isDateStop){
            throw new InvalidDataException(CustomMessage.invalidTimeOfDayMessage);
        }

    }
}
