package com.studio.smarters.sysoft.POJO;

public class Batch {
    String batch_name,batch_start,batch_timing;

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

    public Batch(String batch_name, String batch_start, String batch_timing) {

        this.batch_name = batch_name;
        this.batch_start = batch_start;
        this.batch_timing = batch_timing;
    }
}
