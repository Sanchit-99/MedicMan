package com.example.medicman;

/**
 * details from here
 * https://www.drugs.com/amoxicillin.html
 * https://www.medindia.net/doctors/drug_information/abarelix.htm
 */


import android.os.Parcel;
import android.os.Parcelable;

public class MedicineUserGuideInfo implements Parcelable {
    String medName;
    public static final Creator<MedicineUserGuideInfo> CREATOR = new Creator<MedicineUserGuideInfo>() {
        @Override
        public MedicineUserGuideInfo createFromParcel(Parcel in) {
            return new MedicineUserGuideInfo(in);
        }

        @Override
        public MedicineUserGuideInfo[] newArray(int size) {
            return new MedicineUserGuideInfo[size];
        }
    };
    String medSideEffects;
    String medUsage;

    public MedicineUserGuideInfo() {
    }

    public MedicineUserGuideInfo(String medName, String medSideEffects, String medUsage) {
        this.medName = medName;
        this.medSideEffects = medSideEffects;
        this.medUsage = medUsage;
    }

    protected MedicineUserGuideInfo(Parcel in) {
        medName = in.readString();
        medSideEffects = in.readString();
        medUsage = in.readString();
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public String getMedSideEffects() {
        return medSideEffects;
    }

    public void setMedSideEffects(String medSideEffects) {
        this.medSideEffects = medSideEffects;
    }

    public String getMedUsage() {
        return medUsage;
    }

    public void setMedUsage(String medUsage) {
        this.medUsage = medUsage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(medName);
        parcel.writeString(medSideEffects);
        parcel.writeString(medUsage);
    }
}
