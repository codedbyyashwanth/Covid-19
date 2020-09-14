package com.roborosx.covid19;

public class HospitalInfo {
    public int UHospital,UBed,RHospital,RBed;
    public String State;

    public HospitalInfo(int UHospital, int UBed, int RHospital, int RBed, String state) {
        this.UHospital = UHospital;
        this.UBed = UBed;
        this.RHospital = RHospital;
        this.RBed = RBed;
        State = state;
    }

    public HospitalInfo() {
    }

    public int getUHospital() {
        return UHospital;
    }

    public int getUBed() {
        return UBed;
    }

    public int getRHospital() {
        return RHospital;
    }

    public int getRBed() {
        return RBed;
    }

    public String getState() {
        return State;
    }
}
