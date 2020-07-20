package com.example.medicman;

/**
 * details from here
 * https://www.drugs.com/amoxicillin.html
 * https://www.medindia.net/doctors/drug_information/abarelix.htm
 */


import android.os.Parcel;
import android.os.Parcelable;

public class MedicineUserGuideInfo implements Parcelable {
    String medSideEffects;
    String medUsage;
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
    String medName;

    public MedicineUserGuideInfo() {
    }

    String medPrecaution;

    public MedicineUserGuideInfo(String medSideEffects, String medUsage, String medName, String medPrecaution) {
        this.medSideEffects = medSideEffects;
        this.medUsage = medUsage;
        this.medName = medName;
        this.medPrecaution = medPrecaution;
    }

    protected MedicineUserGuideInfo(Parcel in) {
        medSideEffects = in.readString();
        medUsage = in.readString();
        medName = in.readString();
        medPrecaution = in.readString();
    }

    public static Creator<MedicineUserGuideInfo> getCREATOR() {
        return CREATOR;
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

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public String getMedPrecaution() {
        return medPrecaution;
    }

    public void setMedPrecaution(String medPrecaution) {
        this.medPrecaution = medPrecaution;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(medSideEffects);
        parcel.writeString(medUsage);
        parcel.writeString(medName);
        parcel.writeString(medPrecaution);
    }
}
