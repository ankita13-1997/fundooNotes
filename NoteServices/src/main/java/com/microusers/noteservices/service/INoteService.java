package com.microusers.noteservices.service;

import com.microusers.noteservices.dto.NoteDetailsDto;
import com.microusers.noteservices.model.NoteDetailsModel;

public interface INoteService {

    NoteDetailsModel addNewNotes(String token, NoteDetailsDto noteDetailsDto);
}
