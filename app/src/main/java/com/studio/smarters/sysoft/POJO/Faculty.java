package com.studio.smarters.sysoft.POJO;

public class Faculty {

    String name,image,desc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Faculty(String name, String image, String desc) {

        this.name = name;
        this.image = image;
        this.desc = desc;
    }

    public Faculty() {

    }
}
