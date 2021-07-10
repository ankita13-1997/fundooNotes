package com.microusers.noteservices.service;

import com.microusers.noteservices.dto.EditLabelDto;
import com.microusers.noteservices.dto.LabelDto;
import com.microusers.noteservices.model.LabelDetailsModel;
import com.microusers.noteservices.model.NoteDetailsModel;

import java.util.List;
import java.util.UUID;

public interface ILabelService {
    LabelDetailsModel addLabel(String userIdToken, LabelDto addLabelDto);

    List<LabelDetailsModel> setListOfLabel(String userIdToken);

    LabelDetailsModel updateLabel(String userIdToken, EditLabelDto addLabelDto);

    String deleteLabelDetails(String userIdToken, UUID labelId);

    NoteDetailsModel addLabelNote(String token, UUID labelId, UUID noteId);


    NoteDetailsModel addNewLabelToExistingNote(String token, UUID noteId,LabelDto addLabelDto);

    List<LabelDetailsModel> getLabelByNotes(String userIdToken, UUID noteId);

    String deleteLabelFromNote(String token, UUID labelId, UUID noteId);
}
