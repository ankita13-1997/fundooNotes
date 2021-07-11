package com.microusers.noteservices.service.implementation;

import com.microusers.noteservices.exception.CollabaretorException;
import com.microusers.noteservices.exception.NoteException;
import com.microusers.noteservices.model.CollaboratorDetailsModel;
import com.microusers.noteservices.model.LabelDetailsModel;
import com.microusers.noteservices.model.NoteDetailsModel;
import com.microusers.noteservices.model.UserDetailsModel;
import com.microusers.noteservices.repository.ICollabaratorRepository;
import com.microusers.noteservices.repository.INoteRepository;
import com.microusers.noteservices.service.ICollabaretorService;
import com.microusers.noteservices.utils.MailService;
import com.microusers.noteservices.utils.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CollabaretorService implements ICollabaretorService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ICollabaratorRepository collabaratorRepository;

    @Autowired
    Token jwtToken;

    @Autowired
    MailService mailService;

    @Autowired
    INoteRepository noteRepository;


    @Override
    public CollaboratorDetailsModel addCollabaretor(String userIdToken, String collabEmail) {
        UserDetailsModel user = findUser(userIdToken);
        if(user.getEmailID().equals(collabEmail)){
            throw new CollabaretorException(CollabaretorException.ExceptionType.USER_CANNOT_BE_COLLABATED);
        }
        Optional<CollaboratorDetailsModel> collaborator = collabaratorRepository.findByCollabEmailAndUserId(collabEmail,user.getUserId());
        if (collaborator.isPresent()){
            throw new CollabaretorException(CollabaretorException.ExceptionType.COLLABARETOR_ALREADY_PRESENT);
        }

        CollaboratorDetailsModel collaboratorDetailsModel=new CollaboratorDetailsModel(collabEmail);
        collaboratorDetailsModel.setUserId(user.getUserId());
        collaboratorDetailsModel.setUserEmail(user.getEmailID());
        collaboratorDetailsModel.setUserFullName(user.getFullName());
        CollaboratorDetailsModel saveCollab = collabaratorRepository.save(collaboratorDetailsModel);

        String tokenId = jwtToken.generateVerificationToken(collaboratorDetailsModel);
        String requestUrl ="http://localhost:8082/collabaretor/verify/email_collab/"+tokenId;
        System.out.println("token from registration is "+tokenId);
        try {
            mailService.sendMail(requestUrl,"the verification link is ",collaboratorDetailsModel.getCollabEmail());
        } catch (MessagingException e) {
            e.printStackTrace();
        }


        return saveCollab;
    }

    @Override
    public String verifyEmail(String tokenId) {

        System.out.println("your token id is "+tokenId);
        UUID tokenjwt = jwtToken.decodeJWT(tokenId);
        System.out.println(tokenjwt);
        Optional<CollaboratorDetailsModel> collabById = collabaratorRepository.findById(tokenjwt);
          if(!collabById.isPresent()){
              throw new CollabaretorException(CollabaretorException.
                      ExceptionType.COLLABARETOR_NOT_PRESENT);
          }
          collabById.get().setAdded(true);
          collabById.get().setUpdatedDate(LocalDateTime.now());

        CollaboratorDetailsModel save = collabaratorRepository.save(collabById.get());

        return "YOU GOT COLLAB NOTIFICATION BY "+save.getUserEmail();

    }

    @Override
    public List<CollaboratorDetailsModel> getListCollaberator(String userIdToken) {
        UserDetailsModel user = findUser(userIdToken);
        System.out.println(user.getUserId());
        List<CollaboratorDetailsModel> searchCollabByUser=collabaratorRepository.findByUserId(user.getUserId());



        return searchCollabByUser;
    }

    @Override
    public String deleteCollabaretor(String userIdToken,String email) {
        UserDetailsModel user = findUser(userIdToken);
        Optional<CollaboratorDetailsModel> collabByCollabEmailAAndUserId = collabaratorRepository.
                findByCollabEmailAndUserId(email, user.getUserId());

        if(!collabByCollabEmailAAndUserId.isPresent()){
            throw new CollabaretorException(CollabaretorException.
                    ExceptionType.COLLABARETOR_NOT_PRESENT);
        }


        collabaratorRepository.delete(collabByCollabEmailAAndUserId.get());


        String tokenId = jwtToken.generateVerificationToken(collabByCollabEmailAAndUserId.get());

        String message = "the deletion message";
        System.out.println("token from registration is "+tokenId);
        try {
            mailService.sendMail(message,"You are removed collab by ",collabByCollabEmailAAndUserId.get().getCollabEmail());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return   collabByCollabEmailAAndUserId.get().getCollabEmail()+"DELETED FROM COLLAB";
    }

    @Override
    public CollaboratorDetailsModel addCollabaretorToNote(String userIdToken,
                                                          String collabEmail,
                                                          UUID noteId) {

        UserDetailsModel user = findUser(userIdToken);
        Optional<NoteDetailsModel> noteSearch = noteRepository.
                findByNoteIdAndUserId(noteId, user.getUserId());

        if (!noteSearch.isPresent()){
            throw new NoteException(NoteException.ExceptionType.NOTE_NOT_PRESENT);
        }
        Optional<CollaboratorDetailsModel> collabSearch = collabaratorRepository.
                findByCollabEmailAndUserId(collabEmail, user.getUserId());

        if(!collabSearch.isPresent()){
            throw new CollabaretorException(CollabaretorException.
                    ExceptionType.COLLABARETOR_NOT_PRESENT);
        }
        noteSearch.get().getCollaborators().add(collabSearch.get());
        collabSearch.get().getNotes().add(noteSearch.get());
        NoteDetailsModel saveNote = noteRepository.save(noteSearch.get());
        CollaboratorDetailsModel saveCollab = collabaratorRepository.save(collabSearch.get());


        return saveCollab;
    }

    @Override
    public CollaboratorDetailsModel addNewCollabaretorToNote(String userIdToken, String collabEmail, UUID noteId) {

        UserDetailsModel user = findUser(userIdToken);
        if(user.getEmailID().equals(collabEmail)){
            throw new CollabaretorException(CollabaretorException.ExceptionType.USER_CANNOT_BE_COLLABATED);
        }

        NoteDetailsModel noteSearch = noteRepository.
                findByNoteIdAndUserId(noteId, user.getUserId())
                .orElseThrow(() -> new NoteException(NoteException.ExceptionType.NOTE_NOT_PRESENT));

        Optional<CollaboratorDetailsModel> collabSearch = collabaratorRepository.
                findByCollabEmailAndUserId(collabEmail, user.getUserId());

        if(collabSearch.isPresent()) {
            if (collabSearch.stream().anyMatch(collab -> collab.getNotes().contains(noteSearch)) == true) {
                throw new CollabaretorException(CollabaretorException.ExceptionType.
                        COLLABARETOR_ALREADY_PRESENT_FOR_NOTE);
            } else {
                noteSearch.getCollaborators().add(collabSearch.get());
                collabSearch.get().getNotes().add(noteSearch);
                NoteDetailsModel saveNote = noteRepository.save(noteSearch);
                CollaboratorDetailsModel saveCollab = collabaratorRepository.save(collabSearch.get());
            }
        }


          CollaboratorDetailsModel collabModel = new CollaboratorDetailsModel(collabEmail);
          collabModel.setUserId(user.getUserId());
          collabModel.setUserFullName(user.getFullName());
          collabModel.setUserEmail(user.getEmailID());
          collabModel.setCollabEmail(collabEmail);
//          collabModel.getNotes().add(noteSearch);
          CollaboratorDetailsModel saveCollab = collabaratorRepository.save(collabModel);

        String tokenId = jwtToken.generateVerificationToken(collabModel);
        String requestUrl ="http://localhost:8082/collabaretor/verify/email_collab/"+tokenId;
        System.out.println("token from registration is "+tokenId);

        try {
            mailService.sendMail(requestUrl,"the verification link is ",saveCollab.getCollabEmail());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
          noteSearch.getCollaborators().add(saveCollab);
          NoteDetailsModel noteSave=noteRepository.save(noteSearch);



        return saveCollab;

    }

    @Override
    public List<CollaboratorDetailsModel> getCollabListByNotes(String userTokenId, UUID noteId) {
        UserDetailsModel user = findUser(userTokenId);

        NoteDetailsModel noteSearch = noteRepository.
                findByNoteIdAndUserId(noteId, user.getUserId()).
                orElseThrow(() -> new NoteException(NoteException.ExceptionType.NOTE_NOT_PRESENT));;

        List<NoteDetailsModel> noteDetailsModelList = new ArrayList<>();
        noteDetailsModelList.add(noteSearch);

        List<CollaboratorDetailsModel> collabListOfNotes= noteSearch.getCollaborators();



        return collabListOfNotes;
    }

    @Override
    public String deleteCollabOfNotes(String userTokenId, String collabEmail, UUID noteId) {

        UserDetailsModel user = findUser(userTokenId);

        NoteDetailsModel noteSearch = noteRepository.
                findByNoteIdAndUserId(noteId, user.getUserId()).
                orElseThrow(() -> new NoteException(NoteException.ExceptionType.NOTE_NOT_PRESENT));

        CollaboratorDetailsModel collabSearch = collabaratorRepository.
                findByCollabEmailAndUserId(collabEmail, user.getUserId()).
                orElseThrow(() -> new CollabaretorException
                        (CollabaretorException.ExceptionType.COLLABARETOR_NOT_PRESENT));

        if (noteSearch.getLabels().contains(collabSearch)) {
            throw new CollabaretorException(CollabaretorException.ExceptionType.
                    COLLABARETOR_NOT_PRESENT_FOR_NOTE);
        }

        noteSearch.getLabels().remove(collabSearch);
        collabSearch.getNotes().remove(noteSearch);
        noteRepository.save(noteSearch);
        collabaratorRepository.save(collabSearch);


        return "COLLAB DELETED FROM NOTE SUCCESSFULLY";

    }


    private UserDetailsModel findUser(String token) {

        UserDetailsModel userDetailsModel = restTemplate.
                getForObject("http://localhost:8081/user/getuser?userEmailToken= "+token,
                        UserDetailsModel.class);


        if(userDetailsModel == null){
            throw new NoteException(NoteException.ExceptionType.USER_NOT_PRESENT);
        }

        return userDetailsModel;
    }
}
