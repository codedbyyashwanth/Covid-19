package com.roborosx.covid19;

public class StatsInfo {
    public String SState;
    public int confirmed,discharged,death;

    public StatsInfo(String SState, int confirmed, int discharged, int death) {
        this.SState = SState;
        this.confirmed = confirmed;
        this.discharged = discharged;
        this.death = death;
    }

    public StatsInfo() {
    }

    public String getSState() {
        return SState;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public int getDischarged() {
        return discharged;
    }

    public int getDeath() {
        return death;
    }
}
