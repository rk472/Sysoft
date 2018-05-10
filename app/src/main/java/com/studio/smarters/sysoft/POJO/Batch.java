package com.studio.smarters.sysoft.POJO;

import com.google.firebase.database.PropertyName;

public class Batch {
    String batch_name,batch_start,batch_timing,image;
    boolean trending;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isTrending() {
        return trending;
    }

    public void setTrending(boolean trending) {
        this.trending = trending;
    }

    public Batch(String batch_name, String batch_start, String batch_timing, String image, boolean trending) {
        this.batch_name = batch_name;
        this.batch_start = batch_start;
        this.batch_timing = batch_timing;
        this.image = image;
        this.trending = trending;
    }

    public String getBatch_name() {
        return batch_name;
    }

    public void setBatch_name(String batch_name) {
        this.batch_name = batch_name;
    }

    public String getBatch_start() {
        return batch_start;
    }

    public void setBatch_start(String batch_start) {
        this.batch_start = batch_start;
    }

    public String getBatch_timing() {
        return batch_timing;
    }

    public void setBatch_timing(String batch_timing) {
        this.batch_timing = batch_timing;
    }

    public Batch() {

    }


}
