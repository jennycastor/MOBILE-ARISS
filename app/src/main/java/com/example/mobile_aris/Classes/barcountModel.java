package com.example.mobile_aris.Classes;

public class barcountModel {
    String barname, barcount;
    public barcountModel(String bname, String bcount) {
        this.barname = bname;
        this.barcount = bcount;
    }

    public String getBname() {
        return barname;
    }

    public void setBname(String barname) {
        this.barname = barname;
    }

    public String getBcount() {
        return barcount;
    }

    public void setBcount(String barcount) {
        this.barcount = barcount;
    }
}
