/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.userservice.dto;

import com.app.userservice.entity.Users;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 *
 * @author user
 */
public class PutShipAddressRequestDTO {

    private Long id;
    private Long refUserId;
    private Users currUser;
    @NotNull
    @NotBlank
    @Size(max = 500)
    private String addressLine1;
    @Size(max = 500)
    private String addressLine2;
    @NotNull
    @NotBlank
    @Size(max = 255)
    private String city;
    @NotNull
    @NotBlank
    @Size(max = 255)
    private String country;
    @NotNull
    @NotBlank
    @Size(max = 10)
    private String phoneNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRefUserId() {
        return refUserId;
    }

    public void setRefUserId(Long refUserId) {
        this.refUserId = refUserId;
    }

    public Users getCurrUser() {
        return currUser;
    }

    public void setCurrUser(Users currUser) {
        this.currUser = currUser;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
