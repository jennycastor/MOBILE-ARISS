package com.example.mobile_aris.models;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PetVaxxModel {
    String Id, vacname, vacdate, revac;

    public PetVaxxModel(String id, String vaccine_name, String date_of_vaccination, String revaccination_schedule) {
        this.Id = id;
        this.vacname = vaccine_name;
        this.vacdate = date_of_vaccination;
        this.revac = revaccination_schedule;
    }

    public String getVId() {
        return Id;
    }

    public void setVid(String Id) {
        this.Id = Id;
    }

    public String getVname() {
        return vacname;
    }

    public void setVname(String vacname) {
        this.vacname = vacname;
    }

    public String getVdate() throws ParseException {
        DateFormat outputFormat = new SimpleDateFormat("MMM. dd, yyy", Locale.US);
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.US);

        String inputText = vacdate;
        Date date = inputFormat.parse(inputText);
        String outputText = outputFormat.format(date);
        return outputText;
    }

    public void setVdate(String vacdate) {
        this.vacdate = vacdate;
    }

    public String getVrevac() throws ParseException {
        DateFormat outputFormat = new SimpleDateFormat("MMM. dd, yyy", Locale.US);
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.US);

        String inputText = revac;
        Date date = inputFormat.parse(inputText);
        String outputText = outputFormat.format(date);
        return outputText;
    }

    public void setVrevac(String revac) {
        this.revac = revac;
    }
}
