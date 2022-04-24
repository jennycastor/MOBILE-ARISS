package com.example.mobile_aris.models;

public class petModel {
    private String mId;
    private String mImageUrl;
    private String mpId;
    private String mName;
    private String mGender;
    private String mdate;
    private String mspecie;
    private String mbreed;
    private String mcolor;
    private int mAge;
    private String mvacname;
    private String mvacdate;
    private String mrevac, mvId;

    public petModel(String petId, String imageUrl, String pubId, String name, int age, String date, String gender, String specie, String breed, String color) {
        mId = petId;
        mImageUrl = imageUrl;
        mpId = pubId;
        mName = name;
        mAge = age;
        mGender = gender;
        mdate = date;
        mspecie = specie;
        mbreed = breed;
        mcolor = color;
    }

    public petModel(String vacname, String vacdate, String revac) {
        mvacname = vacname;
        mvacdate = vacdate;
        mrevac = revac;
    }

    public String getId() {
        return mId;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getpId() {
        return mpId;
    }

    public String getGender() {
        return mGender;
    }

    public String getPname() {
        return mName;
    }

    public int getPAge() {
        return mAge;
    }

    public String getCreatedAt() {return mdate;}

    public String getSpecie() {return mspecie;}

    public String getBreed() {return mbreed;}

    public String getPColor() {return mcolor;}

    public String getVname() {return mvacname;}

    public String getVdate() {return mvacdate;}

    public String getVrevac() {return mrevac;}

}
