package com.example.medicman;

public class MedicineUserGuideInfo {
    String medName;
    String medDescription;
    String medImgUrl;

    public MedicineUserGuideInfo() {
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public String getMedDescription() {
        return medDescription;
    }

    public void setMedDescription(String medDescription) {
        this.medDescription = medDescription;
    }

    public String getMedImgUrl() {
        return medImgUrl;
    }

    public void setMedImgUrl(String medImgUrl) {
        this.medImgUrl = medImgUrl;
    }

    public MedicineUserGuideInfo(String medName, String medDescription, String medImgUrl) {
        this.medName = medName;
        this.medDescription = medDescription;
        this.medImgUrl = medImgUrl;
    }
}
