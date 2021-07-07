package com.microusers.noteservices.service.implementation;

import com.microusers.noteservices.dto.NoteDetailsDto;
import com.microusers.noteservices.exception.FudooGlobalException;
import com.microusers.noteservices.exception.NoteException;
import com.microusers.noteservices.model.NoteDetailsModel;
import com.microusers.noteservices.model.UserDetailsModel;
import com.microusers.noteservices.repository.INoteRepository;
import com.microusers.noteservices.service.INoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class NoteService implements INoteService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    INoteRepository noteRepository;

    @Override
    public NoteDetailsModel addNewNotes(String token, NoteDetailsDto noteDetailsDto) {
        UserDetailsModel userDetailsModel =findUser(token);
        Optional<NoteDetailsModel> findNoteDetailsModel = noteRepository.findByTitle(noteDetailsDto.getTitle());

        if(findNoteDetailsModel.isPresent()){
            throw new NoteException(NoteException.ExceptionType.NOTE_ALREADY_PRESENT);
        }
        NoteDetailsModel noteDetailsModel = new NoteDetailsModel(noteDetailsDto.getTitle(),
                                                                 noteDetailsDto.getColor(),
                                                                 noteDetailsDto.getReminder(),
                                                                 noteDetailsDto.getDescription());

        noteDetailsModel.setUserId(userDetailsModel.getUserId());
        noteDetailsModel.setUserEmail(userDetailsModel.getEmailID());
        noteDetailsModel.setUserFullName(userDetailsModel.getFullName());

        NoteDetailsModel saveNoteDetails = noteRepository.save(noteDetailsModel);

        return saveNoteDetails;
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
