package com.microusers.userserver.dto;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class ReddishDto {
    public UUID userID;
    public String token;

}
