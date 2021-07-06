package com.microusers.userserver.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@NoArgsConstructor
@Getter
@Setter
public class userLoginDto {
    @Pattern(regexp = "^[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",message = "please do enter the valid email id" )
    @NotNull(message = "Please Do Enter email id!")
    @NotEmpty(message = "Please Do Enter email id!")
    public String emailID;



    @Pattern(regexp = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$", message = "please enter correct password")
    @NotNull(message = "Please Do Enter Password!")
    @NotEmpty(message = "Please Do  Enter Password!")
    public String password;

    public userLoginDto(String emailID, String password) {
        this.emailID = emailID;
        this.password = password;
    }
}
