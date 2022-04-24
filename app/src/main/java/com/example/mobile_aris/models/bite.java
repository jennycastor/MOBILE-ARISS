package com.example.mobile_aris.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

public class bite {
    String location, createdAt, status_of_vaccination, patient_status, vaccine, classification, _id ,clinic_id,user_id, cliID;

    Integer exposure_category, bite_case_no;

    public bite(String location, String createdAt, String status_of_vaccination, String patient_status, String vaccine, String classification, String _id, Integer exposure_category, Integer bite_case_no, String clinic_id, String user_id, String cliId) {
        this.location = location;
        this.createdAt = createdAt;
        this.status_of_vaccination = status_of_vaccination;
        this.patient_status = patient_status;
        this.vaccine = vaccine;
        this.classification = classification;
        this._id = _id;
        this.exposure_category = exposure_category;
        this.bite_case_no = bite_case_no;
        this.clinic_id = clinic_id;
        this.user_id = user_id;
        this.cliID = cliId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getCreatedAt() {
//        LocalDate new_date = LocalDate.parse(createdAt, DateTimeFormatter.ofPattern("MM-dd-yyyy"));
//        DateTimeFormatter date = DateTimeFormatter.ofPattern("MM-dd-yyyy");
//        createdAt.format(String.valueOf(date));
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus_of_vaccination() {
        return status_of_vaccination;
    }

    public void setStatus_of_vaccination(String status_of_vaccination) {
        this.status_of_vaccination = status_of_vaccination;
    }

    public String getPatient_status() {
        return patient_status;
    }

    public void setPatient_status(String patient_status) {
        this.patient_status = patient_status;
    }

    public String getVaccine() {
        return vaccine;
    }

    public void setVaccine(String vaccine) {
        this.vaccine = vaccine;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Integer getExposure_category() {
        return exposure_category;
    }

    public void setExposure_category(Integer exposure_category) {
        this.exposure_category = exposure_category;
    }

    public Integer getBite_case_no() {
        return bite_case_no;
    }

    public void setBite_case_no(Integer bite_case_no) {
        this.bite_case_no = bite_case_no;
    }

    public String getCliID() {
        return cliID;
    }

    public void setClinic_id(String clinic_id) {
        this.clinic_id = clinic_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}

