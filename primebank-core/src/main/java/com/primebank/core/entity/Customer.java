package com.primebank.core.entity;

import com.primebank.core.entity.enums.UserStatus;
import com.primebank.core.entity.enums.Gender;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
@NamedQueries({
        @NamedQuery(name = "Customer.findAll", query = "SELECT c from Customer c"),
        @NamedQuery(name = "Customer.getCustomerCountByNic", query = "SELECT COUNT(c) FROM Customer c WHERE c.nic = :nic"),
        @NamedQuery(name = "Customer.getCustomerCountByBirthCert", query = "SELECT COUNT(c) FROM Customer c WHERE c.birthCertificateNo = :bcNo"),
        @NamedQuery(name = "Customer.findByNic", query = "SELECT c from Customer c where c.nic = :nic"),
        @NamedQuery(name = "Customer.findByName", query = "SELECT c from Customer c WHERE LOWER(c.fullName) LIKE LOWER(CONCAT('%', :name, '%'))"),
        @NamedQuery(name = "Customer.existsByEmailOrNic", query = "SELECT COUNT(c) from Customer c WHERE c.email = :email OR c.nic = :nic"),
})
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    private String address;

    private String nic; // Optional – only for adults

    @Column(name = "birth_certificate_no")
    private String birthCertificateNo; // Optional – only for minors

    @Column(name = "contact_number", nullable = false)
    private String contactNo;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.ACTIVE;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] photo;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    private void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    private void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Customer() {
    }

    public Customer(String fullName, String email, String address, String nic, String birthCertificateNo, String contactNo, LocalDate dateOfBirth, Gender gender, byte[] photo) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

}
