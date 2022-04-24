package com.example.mobile_aris.models;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class pendingModel {
    private String mId;
    private String mDate;
    private String mTime;
    private String mPurpose;
    private String mStatus;
    private String mClinic;

    public pendingModel(String p_id, String date, String time_slot, String purpose, String status, String clinic) {
        mId = p_id;
        mDate = date;
        mTime = time_slot;
        mPurpose = purpose;
        mStatus = status;
        mClinic = clinic;

    }

    public String getPId() {
        return mId;
    }

    public String getPDate() throws ParseException {
        DateFormat outputFormat = new SimpleDateFormat("MMM. dd, yyy", Locale.US);
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.US);

        String inputText = mDate;
        Date date = inputFormat.parse(inputText);
        String outputText = outputFormat.format(date);
        return outputText;
//        return mDate;
    }

    public String getPTime() {
        return mTime;
    }

    public String getPPurpose() {
        return mPurpose;
    }

    public String getPStatus() {
        return mStatus;
    }

    public String getPClinic() {
        return mClinic;
    }

}
