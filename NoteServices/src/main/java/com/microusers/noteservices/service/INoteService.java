package com.microusers.noteservices.service;

import com.microusers.noteservices.dto.EditNote;
import com.microusers.noteservices.dto.NoteDetailsDto;
import com.microusers.noteservices.model.NoteDetailsModel;

import java.util.List;
import java.util.UUID;

public interface INoteService {

    NoteDetailsModel addNewNotes(String token, NoteDetailsDto noteDetailsDto);

    List<NoteDetailsModel> getListNotes(String token);


    NoteDetailsModel getNoteById(String token, UUID noteId);

    List<NoteDetailsModel> getNotesByDate(String token);

    List<NoteDetailsModel> getNotesByTitle(String token);

    List<NoteDetailsModel> getNotesByFilter(String token,boolean pin,boolean archieve,boolean trash);

    NoteDetailsModel updateNotes(String token, EditNote editNote);

    NoteDetailsModel pinNote(String userIdToken, UUID noteId);
}


