package com.nikan.clickpaz.nikantest.DatabaseModel;

/**
 * Created by slim shady on 09/10/2018.
 */

public class InformationModel {

    private int id;
    private String firstName;
    private String lastName;
    private String landlinePhone;
    private String address;


    public InformationModel() {
    }

    public InformationModel(int id, String firstName, String lastName, String landlinePhone, String address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.landlinePhone = landlinePhone;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLandlinePhone() {
        return landlinePhone;
    }

    public void setLandlinePhone(String landlinePhone) {
        this.landlinePhone = landlinePhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "InformationModel{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", landlinePhone='" + landlinePhone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
