package com.example.medicman;

public class MedicineInfo {

    String name;
    String dosage;
    String time;
    String image_url;
    int id; //for notification
    String key; //for recyclerview

    public MedicineInfo() {
    }

    public MedicineInfo(String name, String dosage, String time, String image_url,int id, String key) {
        this.name = name;
        this.dosage = dosage;
        this.time = time;
        this.image_url=image_url;
        this.id=id;
        this.key=key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
