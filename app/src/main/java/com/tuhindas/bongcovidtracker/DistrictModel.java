package com.tuhindas.bongcovidtracker;

public class DistrictModel {

    private String district, active, confirmed, recovered, deceased;

    public DistrictModel(String district, String active, String confirmed, String recovered, String deceased) {
        this.district = district;
        this.active = active;
        this.confirmed = confirmed;
        this.recovered = recovered;
        this.deceased = deceased;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getDeceased() {
        return deceased;
    }

    public void setDeceased(String deceased) {
        this.deceased = deceased;
    }
}