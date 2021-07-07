package com.microusers.noteservices.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class NoteDetailsDto {
    private String title;
    private String description;
    private Date reminder;
    private String color;

}
