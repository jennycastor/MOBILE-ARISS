package com.example.mobile_aris.bites;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class hreport {
    String username,photo,type,description, r_createdAt;
    String admin,text,createdAt, role, btype;
    String day, date_injected, vaccine, lot, admin_name, remarks;

    public hreport(String name, String photo, String type, String description, String r_createdAt, String admin, String text, String createdAt, String role, String btype, String day, String date_injected, String vaccine, String lot, String remarks) {
        this.username = name;
        this.type = type;
        this.description = description;
        this.r_createdAt = r_createdAt;
        this.admin = admin;
        this.text = text;
        this.createdAt = createdAt;
        this.photo = photo;
        this.role = role;
        this.btype = btype;
        this.day = day;
        this.date_injected = date_injected;
        this.vaccine = vaccine;
        this.lot = lot;
        this.remarks = remarks;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getR_createdAt() throws ParseException {
        DateFormat outputFormat = new SimpleDateFormat("MMM. dd, yyy", Locale.US);
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.US);

        String inputText = r_createdAt;
        Date date = inputFormat.parse(inputText);
        String outputText = outputFormat.format(date);
        return outputText;
//        return r_createdAt;
    }

    public void setR_createdAt(String r_createdAt) {
        this.r_createdAt = r_createdAt;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreatedAt() throws ParseException {
        DateFormat outputFormat = new SimpleDateFormat("MMM. dd, yyy", Locale.US);
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.US);

        String inputT = createdAt;
        Date date = inputFormat.parse(inputT);
        String outputT = outputFormat.format(date);
        return outputT;
//        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.text = role;
    }

    public String getBType() {
        return btype;
    }

    public void setBtype(String btype) {
        this.text = btype;
    }

    public String getVday() {
        return day;
    }

    public void setVady(String day) {
        this.day = day;
    }

    public String getVcdate() throws ParseException {
        DateFormat outputFormat = new SimpleDateFormat("MMM. dd, yyy", Locale.US);
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.US);

        String inputT = date_injected;
        Date date = inputFormat.parse(inputT);
        String outputT = outputFormat.format(date);
        return outputT;
    }

    public void setVcdate(String date_injected) {
        this.date_injected = date_injected;
    }

    public String getVacc() {
        return vaccine;
    }

    public void setVacc(String vaccine) {
        this.vaccine = vaccine;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getUadm() {
        return admin_name;
    }

    public void setUadm(String admin_name) {
        this.admin_name = admin_name;
    }

    public String getRem() {
        return remarks;
    }

    public void setRem(String remarks) {
        this.remarks = remarks;
    }
}
