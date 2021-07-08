package com.microusers.noteservices.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditLabelDto {

    @NotNull(message ="The label Id field should not be NULL")
//    @NotEmpty(message = "The label Id field should not be empty")
    private UUID labelId;

    @NotNull(message ="The label name field should not be NULL")
    @NotEmpty(message = "The label name field should not be empty")
    private String labelName;
}
