package com.microusers.noteservices.service;

import com.microusers.noteservices.dto.EditNote;
import com.microusers.noteservices.dto.LabelToNoteDto;
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

    NoteDetailsModel archievNote(String userIdToken, UUID noteId);

    NoteDetailsModel archievNoteToPin(String userIdToken, UUID noteId);

    NoteDetailsModel trashNote(String userIdToken, UUID noteId);

    String deleteNotePerManently(String userIdToken, UUID noteId);

    NoteDetailsModel UnPinNote(String userIdToken, UUID noteId);

    NoteDetailsModel UnArchievNote(String userIdToken, UUID noteId);

    NoteDetailsModel UnTrashNote(String userIdToken, UUID noteId);

    List<NoteDetailsModel> getNotesByPin(String token);

    List<NoteDetailsModel> getNotesByTrash(String token);

    List<NoteDetailsModel> getNotesByArchieve(String token);



}


