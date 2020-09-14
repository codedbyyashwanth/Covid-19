package com.roborosx.covid19;

public class CollegesInfo {
    public String State,College,City,Type;
    public int Capacity,Beds;

    public CollegesInfo(String state, String college, String city, String type, int capacity, int beds) {
        State = state;
        College = college;
        City = city;
        Type = type;
        Capacity = capacity;
        Beds = beds;
    }

    public CollegesInfo() {
    }

    public String getState() {
        return State;
    }

    public String getCollege() {
        return College;
    }

    public String getCity() {
        return City;
    }

    public String getType() {
        return Type;
    }

    public int getCapacity() {
        return Capacity;
    }

    public int getBeds() {
        return Beds;
    }
}
