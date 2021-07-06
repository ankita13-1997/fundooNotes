package com.microusers.userserver.model;


import com.microusers.userserver.dto.userLoginDto;
import com.microusers.userserver.dto.userRegistrationDto;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class UserDetailsModel {

    @Id
    @GeneratedValue(generator = "uuid2",strategy = GenerationType.AUTO)
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    public UUID userId;


    public String fullName;
    private String phoneNumber;
    public String emailID;
    public String password;
    public boolean isVerified;
    public LocalDateTime createdAt = LocalDateTime.now();
    public LocalDateTime updatedAt;


    public UserDetailsModel(String fullName, String phoneNumber, String emailID, String password) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.emailID = emailID;
        this.password = password;
    }

    public UserDetailsModel(UserDetailsModel userDetailsModel){
        this.userId=userDetailsModel.getUserId();
        this.fullName=userDetailsModel.getFullName();
        this.phoneNumber=userDetailsModel.getPhoneNumber();
        this.emailID=userDetailsModel.getEmailID();
        this.password=userDetailsModel.getPassword();
        this.isVerified=userDetailsModel.isVerified();
        this.createdAt=userDetailsModel.getCreatedAt();
        this.updatedAt=userDetailsModel.getUpdatedAt();
    }


    public UserDetailsModel(userRegistrationDto userDetailsDto) {
        this.fullName = userDetailsDto.getFullName();
        this.phoneNumber = userDetailsDto.getPhoneNumber();
        this.emailID = userDetailsDto.getEmailID();
        this.password = userDetailsDto.getPassword();
    }

    public UserDetailsModel(userLoginDto loginDto) {
        this.emailID = loginDto.emailID;
        this.password = loginDto.password;

    }
}
