package com.studio.smarters.sysoft.POJO;

public class Course {
    String doc,suration,fee,image,name;

    public Course(String doc, String suration, String fee, String image, String name) {
        this.doc = doc;
        this.suration = suration;
        this.fee = fee;
        this.image = image;
        this.name = name;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getSuration() {
        return suration;
    }

    public void setSuration(String suration) {
        this.suration = suration;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Course() {

    }
}
