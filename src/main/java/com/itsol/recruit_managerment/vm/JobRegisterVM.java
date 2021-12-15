package com.itsol.recruit_managerment.vm;

public class JobRegisterVM {
    String id;
    String profilestatus;
    String dateregister;
    String dateinterview;
    String methodinterview;
    String cv;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfilestatus() {
        return profilestatus;
    }

    public void setProfilestatus(String profilestatus) {
        this.profilestatus = profilestatus;
    }

    public String getDateregister() {
        return dateregister;
    }

    public void setDateregister(String dateregister) {
        this.dateregister = dateregister;
    }

    public String getDateinterview() {
        return dateinterview;
    }

    public void setDateinterview(String dateinterview) {
        this.dateinterview = dateinterview;
    }

    public String getMethodinterview() {
        return methodinterview;
    }

    public void setMethodinterview(String methodinterview) {
        this.methodinterview = methodinterview;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }
}
