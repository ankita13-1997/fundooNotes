package com.microusers.userserver.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class userRegistrationDto {
    @Pattern(regexp = "^[A-Z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", message = "please enter valid credentials")
    @NotNull(message = "Please Do Enter valid name!")
    @NotEmpty(message = "Please Enter Valid name!")
    public String fullName;


    @Pattern(regexp = "^[9]{1}[1]{1}[7896]{1}[0-9]{9}$", message = "Please Do Enter Valid Mobile Number!")
    @NotNull(message = "Please Do Enter Valid Mobile Number!")
    @NotEmpty(message = "Please Do Enter Valid Mobile Number!")
    public String phoneNumber;

    @Pattern(regexp = "^[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "please do enter the valid email id")
    @NotNull(message = "Please Do Enter email id!")
    @NotEmpty(message = "Please Do Enter email id!")
    public String emailID;


    @Pattern(regexp = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",
            message = "please enter correct password")
    @NotNull(message = "Please Do Enter Password!")
    @NotEmpty(message = "Please Do  Enter Password!")
    public String password;


    public userRegistrationDto(String fullName, String phoneNumber, String emailID, String password) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.emailID = emailID;
        this.password = password;
    }

}

