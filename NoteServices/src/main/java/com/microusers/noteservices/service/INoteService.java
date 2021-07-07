package com.microusers.noteservices.service;

import com.microusers.noteservices.dto.NoteDetailsDto;
import com.microusers.noteservices.model.NoteDetailsModel;

import java.util.List;

public interface INoteService {

    NoteDetailsModel addNewNotes(String token, NoteDetailsDto noteDetailsDto);

    List<NoteDetailsModel> getListNotes(String token);
}
