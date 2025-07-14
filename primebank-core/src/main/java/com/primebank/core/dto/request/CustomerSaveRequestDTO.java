package com.primebank.core.dto.request;

import com.primebank.core.entity.enums.Gender;

import java.io.Serializable;
import java.time.LocalDate;

public class CustomerSaveRequestDTO implements Serializable {
    private String fullName;
    private String email;
    private String address;
    private String nic;
    private String birthCertificateNo;
    private String contactNo;
    private LocalDate dateOfBirth;
    private Gender gender;
    private byte[] photo;

    public CustomerSaveRequestDTO() {
    }

    public CustomerSaveRequestDTO(String fullName, String email, String address, String nic, String birthCertificateNo, String contactNo, LocalDate dateOfBirth, Gender gender, byte[] photo) {
        this.fullName = fullName;
        this.email = email;
        this.address = address;
        this.nic = nic;
        this.birthCertificateNo = birthCertificateNo;
        this.contactNo = contactNo;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.photo = photo;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getBirthCertificateNo() {
        return birthCertificateNo;
    }

    public void setBirthCertificateNo(String birthCertificateNo) {
        this.birthCertificateNo = birthCertificateNo;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
