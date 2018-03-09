package com.sai_jayant.doctorprescription;

/**
 * Created by macbookpro on 08/03/18.
 */

public class Patient {

    String patient_name;
    String gender;
    String age;
    String weight;
    String medicines;
    String adress;
    String number;
    String date;
    int id;




    public Patient(String patient_name, String gender, String age, String weight, String number, String adress, String date, String medicines,int id) {
        this.patient_name = patient_name;
        this.gender = gender;
        this.age = age;
        this.weight = weight;
        this.number = number;
        this.adress = adress;
        this.date = date;
        this.medicines = medicines;
        this.id=id;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMedicines() {
        return medicines;
    }

    public void setMedicines(String medicines) {
        this.medicines = medicines;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
