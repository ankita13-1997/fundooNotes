package com.microusers.noteservices.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class EditNote extends NoteDetailsDto{
    UUID noteId;



    public EditNote(String title, String description, Date reminder, String color) {
        super(title, description, reminder, color);

    }
}
