package com.microusers.noteservices.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NoteDetailsDto {
    @NotNull(message = "The Note title field should not be empty")
    @NotEmpty(message = "The Note title field should not be null")
    private String title;

    @NotNull(message = "The Note description field should not be empty")
    @NotEmpty(message = "The Note description field should not be null")
    private String description;
    private Date reminder;
    private String color;

}
