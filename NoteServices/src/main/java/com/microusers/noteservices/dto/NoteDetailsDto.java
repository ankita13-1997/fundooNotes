package com.microusers.noteservices.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NoteDetailsDto {
    private String title;
    private String description;
    private Date reminder;
    private String color;

}
