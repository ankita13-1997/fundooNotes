package com.microusers.noteservices.service.implementation;

import com.microusers.noteservices.dto.EditNote;
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

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Override
    public List<NoteDetailsModel> getListNotes(String token) {
        UserDetailsModel userDetailsModel =findUser(token);
        UUID userNumber = userDetailsModel.getUserId();
        System.out.println(userNumber);
//        List<NoteDetailsModel> noteDetailsList = noteRepository.findAll().stream().
//                    filter(note -> note.getUserId()==userNumber).
//                     collect(Collectors.toList());
        List<NoteDetailsModel> noteDetailsList = noteRepository.findByUserId(userNumber);
        System.out.println(noteDetailsList);
        return noteDetailsList;
    }

    @Override
    public NoteDetailsModel getNoteById(String token, UUID noteId) {
        UserDetailsModel userDetailsModel = findUser(token);
        Optional<NoteDetailsModel> noteDetailsModel = noteRepository.findById(noteId);
        if (!noteDetailsModel.isPresent()){
            throw new NoteException(NoteException.ExceptionType.NOTE_ALREADY_PRESENT);

        }
        return noteDetailsModel.get();
    }

    @Override
    public List<NoteDetailsModel> getNotesByDate(String token) {
        UserDetailsModel userDetails = findUser(token);
        UUID userNumber = userDetails.getUserId();
        List<NoteDetailsModel> noteDetailsList = noteRepository.findByUserId(userNumber);
        List<NoteDetailsModel> notelist=noteDetailsList.stream().
                sorted(Comparator.comparing(NoteDetailsModel::getCreatedDate).reversed()).parallel().
                collect(Collectors.toList());


        return notelist;
    }

    @Override
    public List<NoteDetailsModel> getNotesByTitle(String token) {
        UserDetailsModel userDetails = findUser(token);
        UUID userNumber = userDetails.getUserId();
        List<NoteDetailsModel> noteDetailsList = noteRepository.findByUserId(userNumber);
        List<NoteDetailsModel> notelist=noteDetailsList.stream().
                sorted(Comparator.comparing(NoteDetailsModel::getTitle).reversed()).parallel().
                collect(Collectors.toList());
        return notelist;
    }

    @Override
    public List<NoteDetailsModel> getNotesByFilter(String token,boolean pin,boolean archive,boolean trash) {
        UserDetailsModel userDetails = findUser(token);
        UUID userNumber = userDetails.getUserId();
        List<NoteDetailsModel> noteDetailsList = noteRepository.findByUserId(userNumber);
        List<NoteDetailsModel> notelist=noteDetailsList.stream().
                filter(noteDetails -> noteDetails.isArchive()==archive &&
                        noteDetails.isPin()==pin && noteDetails.isTrash()==trash).
                        sorted(Comparator.comparing(NoteDetailsModel::getTitle).reversed()).parallel().
                        collect(Collectors.toList());
        return notelist;
    }

    @Override
    public NoteDetailsModel updateNotes(String token, EditNote editNote) {
        UserDetailsModel userDetailsModel = findUser(token);
        Optional<NoteDetailsModel> noteById = noteRepository.findById(editNote.getNoteId());
        if(!noteById.isPresent()){
            throw new NoteException(NoteException.ExceptionType.NOTE_NOT_PRESENT);
        }
        noteById.get().setTitle(editNote.getTitle());
        noteById.get().setDescription(editNote.getDescription());
        noteById.get().setColor(editNote.getColor());
        noteById.get().setUpdatedDate(LocalDateTime.now());

        NoteDetailsModel saveNote = noteRepository.save(noteById.get());

        return saveNote;
    }

    @Override
    public NoteDetailsModel pinNote(String userIdToken, UUID noteId) {
        UserDetailsModel user = findUser(userIdToken);
        UUID userNumber = user.getUserId();
        noteRepository
        return null;
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
