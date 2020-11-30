package org.demo.project.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ClubClient {

    private int id;
    private int clubCardNumber;
    private String lastName;
    private String firstName;
    private String middleName;
    private String phoneNumber;
    private String mail;

    public ClubClient() {
    }

    public ClubClient(int id, int clubCardNumber, String lastName, String firstName, String middleName, String phoneNumber, String mail) {
        this.id = id;
        this.clubCardNumber = clubCardNumber;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
    }

    //trash?


    public ClubClient(int clubCardNumber, String lastName, String firstName, String middleName, String phoneNumber, String mail) {
        this.clubCardNumber = clubCardNumber;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
    }

    public ClubClient(String lastName, String firstName, String middleName, String phoneNumber, String mail) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
    }

    //getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClubCardNumber() {
        return clubCardNumber;
    }

    public void setClubCardNumber(int clubCardNumber) {
        this.clubCardNumber = clubCardNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

}
