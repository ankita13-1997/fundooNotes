package com.microusers.noteservices.elasticsearch;

import com.microusers.noteservices.model.NoteDetailsModel;

import java.util.List;
import java.util.UUID;

public interface IElasticSearch {

    public NoteDetailsModel addNewNotes(NoteDetailsModel noteDetailsModel);

    public NoteDetailsModel updateNote(NoteDetailsModel noteDetailsModel);

    public void deleteNote(UUID NoteId);

    public List<NoteDetailsModel> searchData();

    public List<NoteDetailsModel> searchall(String query, String token);
}
