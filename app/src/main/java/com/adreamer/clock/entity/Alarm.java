package com.adreamer.clock.entity;

/**
 * Created by A Dreamer on 2016/7/31.
 */
public class Alarm {

    private long id;
    private int time;
    private boolean isRepeated;
    private boolean isEnable;

    public Alarm() {
    }

    public Alarm(int time, boolean isRepeated, boolean isEnable) {
        this(-1, time, isRepeated, isEnable);
    }
    public Alarm(long id, int time, boolean isRepeated, boolean isEnable) {
        setId(id);
        setTime(time);
        setRepeated(isRepeated);
        setEnable(isEnable);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isRepeated() {
        return isRepeated;
    }

    public void setRepeated(boolean repeated) {
        isRepeated = repeated;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }
}
