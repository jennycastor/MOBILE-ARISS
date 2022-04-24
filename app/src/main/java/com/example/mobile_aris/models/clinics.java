package com.example.mobile_aris.models;

public class clinics {
    String _id,name_clinic;

    public clinics(String _id, String name_clinic) {
        this._id = _id;
        this.name_clinic = name_clinic;

    }

    public clinics() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName_clinic() {
        return name_clinic;
    }

    public void setName_clinic(String name_clinic) {
        this.name_clinic = name_clinic;
    }

    @Override
    public String toString() {
        return "id:"+_id+" Name: "+name_clinic;
    }
}
