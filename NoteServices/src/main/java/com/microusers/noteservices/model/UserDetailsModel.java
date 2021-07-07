package com.microusers.noteservices.model;



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
public class UserDetailsModel {


    public UUID userId;
    public String fullName;
    private String phoneNumber;
    public String emailID;
    public String password;
    public boolean isVerified;
    public LocalDateTime createdAt = LocalDateTime.now();
    public LocalDateTime updatedAt;




}
