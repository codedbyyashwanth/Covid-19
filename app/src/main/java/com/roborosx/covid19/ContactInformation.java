package com.roborosx.covid19;

public class ContactInformation {
    public String state,number;

    public ContactInformation(String state, String number) {
        this.state = state;
        this.number = number;
    }

    public ContactInformation() {
    }

    public String getState() {
        return state;
    }

    public String getNumber() {
        return number;
    }
}
