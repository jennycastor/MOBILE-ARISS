package com.example.mobile_aris.models;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UserVaxxModel {
    String day, date_injected, vaccine, lot, remarks;
    public UserVaxxModel(String day, String date_injected, String vaccine, String lot, String remarks) {
            this.day = day;
            this.date_injected = date_injected;
            this.vaccine = vaccine;
            this.lot = lot;
            this.remarks = remarks;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDti() throws ParseException {
        DateFormat outputFormat = new SimpleDateFormat("MMM. dd, yyy", Locale.US);
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.US);

        String inputT = date_injected;
        Date date = inputFormat.parse(inputT);
        String outputT = outputFormat.format(date);
        return outputT;
    }

    public void setDti(String date_injected) {
        this.date_injected = date_injected;
    }

    public String getVac() { return vaccine; }

    public void setVac(String vaccine) {
        this.vaccine = vaccine;
    }

    public String getLot() { return lot; }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getRem() { return remarks; }

    public void setRem(String remarks) {
        this.remarks = remarks;
    }
}
