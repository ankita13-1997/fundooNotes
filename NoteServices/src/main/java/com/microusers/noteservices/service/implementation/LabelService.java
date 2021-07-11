package com.microusers.noteservices.service.implementation;

import com.microusers.noteservices.dto.EditLabelDto;
import com.microusers.noteservices.dto.LabelDto;
import com.microusers.noteservices.exception.LabelException;
import com.microusers.noteservices.exception.NoteException;
import com.microusers.noteservices.model.LabelDetailsModel;
import com.microusers.noteservices.model.NoteDetailsModel;
import com.microusers.noteservices.model.UserDetailsModel;
import com.microusers.noteservices.repository.ILabelRepository;
import com.microusers.noteservices.repository.INoteRepository;
import com.microusers.noteservices.service.ILabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LabelService implements ILabelService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ILabelRepository labelRepository;

    @Autowired
    INoteRepository noteRepository;

    @Override
    public LabelDetailsModel addLabel(String userIdToken, LabelDto addLabelDto) {
        UserDetailsModel user = findUser(userIdToken);
        List<LabelDetailsModel> LabelSearchByUserId = labelRepository.findByUserId(user.getUserId());
        if(LabelSearchByUserId.stream().
                anyMatch(labelDetailsModel -> labelDetailsModel.getLabelName().
                        equals(addLabelDto.getLabelName()))) {
            throw new LabelException(LabelException.ExceptionType.LABEL_ALREADY_PRESENT);
        }

        LabelDetailsModel labelDetailsModel = new LabelDetailsModel(addLabelDto.getLabelName());
        labelDetailsModel.setUserId(user.getUserId());
        LabelDetailsModel saveLabel = labelRepository.save(labelDetailsModel);

        return saveLabel;
    }

    @Override
    public List<LabelDetailsModel> setListOfLabel(String userIdToken) {
        UserDetailsModel user = findUser(userIdToken);
        List<LabelDetailsModel> labelSearchByUserId = labelRepository.findByUserId(user.getUserId());
        return labelSearchByUserId;
    }

    @Override
    public LabelDetailsModel updateLabel(String userIdToken, EditLabelDto addLabelDto) {
        UserDetailsModel user = findUser(userIdToken);
        LabelDetailsModel labelSearch = labelRepository.
                findByUserIdAndLabelId(user.getUserId(), addLabelDto.getLabelId()).
                orElseThrow(() -> new LabelException(LabelException.ExceptionType.LABEL_NOT_PRESENT));

        labelSearch.setLabelName(addLabelDto.getLabelName());
        labelSearch.setUpdatedDate(LocalDateTime.now());
        LabelDetailsModel saveLabel = labelRepository.save(labelSearch);

        return saveLabel;
    }

    @Override
    public String deleteLabelDetails(String userIdToken, UUID labelId) {
        UserDetailsModel user = findUser(userIdToken);
        LabelDetailsModel labelSearch = labelRepository.
                findByUserIdAndLabelId(user.getUserId(), labelId).
                orElseThrow(() -> new LabelException(LabelException.ExceptionType.LABEL_NOT_PRESENT));

        labelRepository.delete(labelSearch);

        return "THE LABEL IS DELETED SUCCESSFULLY";
    }

    @Override
    public NoteDetailsModel addLabelNote(String token, UUID labelId, UUID noteId) {
        UserDetailsModel user = findUser(token);
        NoteDetailsModel searchNote = noteRepository.
                findByNoteIdAndUserId(noteId, user.getUserId())
                .orElseThrow(() -> new NoteException(NoteException.ExceptionType.NOTE_NOT_PRESENT));

        LabelDetailsModel labelSearch = labelRepository.
                findByUserIdAndLabelId(user.getUserId(), labelId).
                orElseThrow(() -> new LabelException(LabelException.ExceptionType.LABEL_NOT_PRESENT));

        if (searchNote.getLabels().contains(labelSearch)==true && user==null){
            throw new LabelException(LabelException.ExceptionType.LABEL_ALREADY_PRESENT);


        }
        labelSearch.getNotes().add(searchNote);
        searchNote.getLabels().add(labelSearch);
        labelRepository.save(labelSearch);
        NoteDetailsModel saveNote = noteRepository.save(searchNote);

        return saveNote;
    }

    @Override
    public NoteDetailsModel addNewLabelToExistingNote(String token,
                                                      UUID noteId,LabelDto addLabelDto) {

        UserDetailsModel user = findUser(token);
        NoteDetailsModel noteSearch = noteRepository.
                findByNoteIdAndUserId(noteId, user.getUserId())
                .orElseThrow(() -> new NoteException(NoteException.ExceptionType.NOTE_NOT_PRESENT));

        if(noteSearch.getLabels().contains(addLabelDto.getLabelName()))
        {
          throw new LabelException(LabelException.ExceptionType.LABEL_ALREADY_PRESENT_FOR_NOTE);
        }

        Optional<LabelDetailsModel> labelSearch = labelRepository.
                findByLabelNameAndUserId(addLabelDto.getLabelName(), user.getUserId());
        if (labelSearch.isPresent()){
            throw new LabelException(LabelException.ExceptionType.LABEL_ALREADY_PRESENT);
        }


        LabelDetailsModel labelDetailsModel = new LabelDetailsModel(addLabelDto.getLabelName());
        labelDetailsModel.setUserId(user.getUserId());

        LabelDetailsModel saveLabel = labelRepository.save(labelDetailsModel);
        //saveLabel.getNotes().add(noteSearch);
        labelRepository.save(saveLabel);
        noteSearch.getLabels().add(saveLabel);

        NoteDetailsModel saveNote = noteRepository.save(noteSearch);
        saveNote.getLabels().add(saveLabel);
        NoteDetailsModel saveNoteWithLabel = noteRepository.save(saveNote);

        return saveNoteWithLabel;
    }

    @Override
    public List<LabelDetailsModel> getLabelByNotes(String userIdToken, UUID noteId) {

        UserDetailsModel user = findUser(userIdToken);
        NoteDetailsModel searchNote = noteRepository.
                findByNoteIdAndUserId(noteId, user.getUserId()).
                orElseThrow(() -> new NoteException(NoteException.ExceptionType.NOTE_NOT_PRESENT));
        List<NoteDetailsModel> noteDetailsModelList = new ArrayList<>();
        noteDetailsModelList.add(searchNote);
        List<LabelDetailsModel> listLabel = searchNote.getLabels();

        return listLabel;
    }

    @Override
    public String deleteLabelFromNote(String token,UUID labelId, UUID noteId) {
        UserDetailsModel user = findUser(token);

        NoteDetailsModel searchNote = noteRepository.
                findByNoteIdAndUserId(noteId, user.getUserId())
                .orElseThrow(() -> new NoteException(NoteException.ExceptionType.NOTE_NOT_PRESENT));

        LabelDetailsModel labelSearch = labelRepository.
                findByUserIdAndLabelId(user.getUserId(), labelId).
                orElseThrow(() -> new LabelException(LabelException.ExceptionType.LABEL_NOT_PRESENT));

        if (searchNote.getLabels().contains(labelSearch)==false && user==null){
            throw new LabelException(LabelException.ExceptionType.LABEL_NOT_PRESENT);


        }

        labelSearch.getNotes().remove(searchNote);
        searchNote.getLabels().remove(labelSearch);
        labelRepository.save(labelSearch);
        noteRepository.save(searchNote);

        return "DELETED THE LABEL FROM NOTE";
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