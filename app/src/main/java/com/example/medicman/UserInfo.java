package com.example.medicman;

public class UserInfo {
    String email;
    String userName;
    String gender;
    String profileUrl = "";
    String providerPhNo;
    String dob;


    public UserInfo(String email, String userName, String gender, String profileUrl, String providerPhNo, String dob,int totalMedicines) {
        this.email = email;

        this.userName = userName;
        this.gender = gender;
        this.profileUrl = profileUrl;
        this.providerPhNo = providerPhNo;
        this.dob = dob;
    }

    public UserInfo() {
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public void setProviderPhNo(String providerPhNo) {
        this.providerPhNo = providerPhNo;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public String getGender() {
        return gender;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public String getProviderPhNo() {
        return providerPhNo;
    }

    public String getDob() {
        return dob;
    }
}
