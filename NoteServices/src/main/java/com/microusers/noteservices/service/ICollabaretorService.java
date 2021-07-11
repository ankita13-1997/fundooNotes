package com.microusers.noteservices.service;

import com.microusers.noteservices.model.CollaboratorDetailsModel;

import java.util.List;
import java.util.UUID;

public interface ICollabaretorService {
    CollaboratorDetailsModel addCollabaretor(String userIdToken, String collabEmail);

    String verifyEmail(String tokenId);

    List<CollaboratorDetailsModel> getListCollaberator(String userIdToken);

    String deleteCollabaretor(String userIdToken,String email);

    CollaboratorDetailsModel addCollabaretorToNote(String userIdToken, String collabEmail, UUID noteId);

    CollaboratorDetailsModel addNewCollabaretorToNote(String userIdToken, String collabEmail, UUID noteId);

    List<CollaboratorDetailsModel> getCollabListByNotes(String userTokenId, UUID noteId);

    String deleteCollabOfNotes(String userTokenId, String collabEmail, UUID noteId);
}
