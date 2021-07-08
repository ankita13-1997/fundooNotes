package com.microusers.noteservices.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LabelDto {

    @NotEmpty(message = "The label name field should not be empty")
    @NotNull(message = "The label name field should not be null")
    private String labelName;
}
