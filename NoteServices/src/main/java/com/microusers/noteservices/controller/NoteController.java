package com.microusers.noteservices.controller;

import com.microusers.noteservices.dto.EditNote;
import com.microusers.noteservices.dto.LabelToNoteDto;
import com.microusers.noteservices.dto.NoteDetailsDto;
import com.microusers.noteservices.dto.ResponseDto;
import com.microusers.noteservices.model.NoteDetailsModel;
import com.microusers.noteservices.service.INoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/notes_api")
@CrossOrigin
public class NoteController {

    @Autowired
    INoteService noteService;

    @PostMapping("/add_notes")
    public ResponseEntity addNotes(@RequestHeader(value = "UserToken")String Token,
                                   @RequestBody @Valid NoteDetailsDto noteDetailsDto, BindingResult bindingResult){

       if (bindingResult.hasErrors()){
           return new ResponseEntity<ResponseDto>(new ResponseDto(bindingResult.getAllErrors().get(0).
                   getDefaultMessage(),"100",null),
                   HttpStatus.BAD_REQUEST);
       }

        NoteDetailsModel noteDetailsModel=noteService.addNewNotes(Token,noteDetailsDto);

        return  new ResponseEntity
                (new ResponseDto("THE NOTE ADDED SUCCESSFULLY ","200", noteDetailsModel),
                        HttpStatus.OK);
    }

    @GetMapping("/get_all_notes")
    public ResponseEntity getNotesList(@RequestHeader(value = "UserToken")String Token){
        List<NoteDetailsModel> noteDetailsModelList = noteService.getListNotes(Token);

        return  new ResponseEntity
                (new ResponseDto("THE LIST OF NOTES ARE ","200", noteDetailsModelList),
                        HttpStatus.OK);
    }

    @GetMapping("/get_notes_byId")
    public ResponseEntity getNotesById(@RequestHeader(value = "UserToken")String Token,
                                       @RequestParam(name = "noteId") UUID noteId){
        NoteDetailsModel noteDetailsModel=noteService.getNoteById(Token,noteId);

        return  new ResponseEntity
                (new ResponseDto("THE NOTE BY YOUR SELECTION IS ","200", noteDetailsModel),
                        HttpStatus.OK);

    }


    @GetMapping("/get_notes_byDate")
    public ResponseEntity getNotesByTime(@RequestHeader(value = "UserToken")String Token){
        List<NoteDetailsModel> noteDetailsModelList=noteService.getNotesByDate(Token);
        return  new ResponseEntity
                (new ResponseDto("THE NOTE BY YOUR TIME IS ","200", noteDetailsModelList),
                        HttpStatus.OK);

    }

    @GetMapping("/get_notes_byTitle")
    public ResponseEntity getNotesByTitle(@RequestHeader(value = "UserToken")String Token){
        List<NoteDetailsModel> noteDetailsModelList=noteService.getNotesByTitle(Token);
        return  new ResponseEntity
                (new ResponseDto("THE NOTE BY YOUR TITLE IS ","200", noteDetailsModelList),
                        HttpStatus.OK);

    }

    @GetMapping("/get_notes_byPin")
    public ResponseEntity getNotesByPin(@RequestHeader(value = "UserToken")String Token){
        List<NoteDetailsModel> noteDetailsModelList=noteService.getNotesByPin(Token);
        return  new ResponseEntity
                (new ResponseDto("THE NOTE BY YOUR Pin IS ","200", noteDetailsModelList),
                        HttpStatus.OK);

    }

    @GetMapping("/get_notes_bytrash")
    public ResponseEntity getNotesByTrash(@RequestHeader(value = "UserToken")String Token){
        List<NoteDetailsModel> noteDetailsModelList=noteService.getNotesByTrash(Token);
        return  new ResponseEntity
                (new ResponseDto("THE NOTE BY YOUR TRASH IS ","200", noteDetailsModelList),
                        HttpStatus.OK);

    }

    @GetMapping("/get_notes_byArchieve")
    public ResponseEntity getNotesByArchieve(@RequestHeader(value = "UserToken")String Token){
        List<NoteDetailsModel> noteDetailsModelList=noteService.getNotesByArchieve(Token);
        return  new ResponseEntity
                (new ResponseDto("THE NOTE BY YOUR ARCHIEVE IS ","200", noteDetailsModelList),
                        HttpStatus.OK);

    }

    @GetMapping("/get_notes_byFilter")
    public ResponseEntity getNotesByFilter(@RequestHeader(value = "UserToken")String Token,
                                           @RequestParam(name = "pin") boolean pin,
                                           @RequestParam(name = "archive") boolean archive,
                                           @RequestParam(name = "trash") boolean trash){
        List<NoteDetailsModel> noteDetailsModelList=noteService.getNotesByFilter(Token,pin,archive,trash);
        return  new ResponseEntity
                (new ResponseDto("THE NOTE BY YOUR FILTER IS ","200", noteDetailsModelList),
                        HttpStatus.OK);

    }





   @PutMapping("update_note")
   public ResponseEntity updateNotes(@RequestHeader(value = "UserToken")String Token,
                                      @RequestBody @Valid EditNote editNote,BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return new ResponseEntity<ResponseDto>(new ResponseDto(bindingResult.getAllErrors().get(0).
                    getDefaultMessage(),"100",null),
                    HttpStatus.BAD_REQUEST);
        }
        NoteDetailsModel noteDetailsModel=noteService.updateNotes(Token,editNote);


        return  new ResponseEntity
                (new ResponseDto("UPDATED NOTE ","200",noteDetailsModel),
                        HttpStatus.OK);

    }

    @PutMapping("/pin_note")
    public ResponseEntity pin(@RequestHeader(name = "userId") String userIdToken,
                                        @RequestParam(name = "noteId") UUID noteId) {

        NoteDetailsModel pinNoteDetailsModel=noteService.pinNote(userIdToken,noteId);
        return  new ResponseEntity
                (new ResponseDto("UPDATED NOTE PINNED ","200",pinNoteDetailsModel),
                        HttpStatus.OK);
    }

    @PutMapping("/unpin_note")
    public ResponseEntity unPin(@RequestHeader(name = "userId") String userIdToken,
                              @RequestParam(name = "noteId") UUID noteId) {

        NoteDetailsModel pinNoteDetailsModel=noteService.UnPinNote(userIdToken,noteId);
        return  new ResponseEntity
                (new ResponseDto("UPDATED NOTE UN-PINNED ","200",pinNoteDetailsModel),
                        HttpStatus.OK);
    }

    @PutMapping("/archieve_note")
    public ResponseEntity archieve(@RequestHeader(name = "userId") String userIdToken,
                              @RequestParam(name = "noteId") UUID noteId) {
        NoteDetailsModel pinNoteDetailsModel=noteService.archievNote(userIdToken,noteId);
        return  new ResponseEntity
                (new ResponseDto("NOTE ARCHIEVED ","200",pinNoteDetailsModel),
                        HttpStatus.OK);

    }

    @PutMapping("/un-archieve_note")
    public ResponseEntity UnArchieve(@RequestHeader(name = "userId") String userIdToken,
                                   @RequestParam(name = "noteId") UUID noteId) {
        NoteDetailsModel pinNoteDetailsModel=noteService.UnArchievNote(userIdToken,noteId);
        return  new ResponseEntity
                (new ResponseDto("NOTE UN_ARCHIEVED ","200",pinNoteDetailsModel),
                        HttpStatus.OK);

    }


    @PutMapping("/archieve_note_pin")
    public ResponseEntity archieveNoteToPin(@RequestHeader(name = "userId") String userIdToken,
                                   @RequestParam(name = "noteId") UUID noteId) {
        NoteDetailsModel pinNoteDetailsModel=noteService.archievNoteToPin(userIdToken,noteId);
        return  new ResponseEntity
                (new ResponseDto("NOTE ARCHIEVED TP PIN ","200",pinNoteDetailsModel),
                        HttpStatus.OK);

    }

    @PutMapping("/trash_note")
    public ResponseEntity trashNote(@RequestHeader(name = "userId") String userIdToken,
                                            @RequestParam(name = "noteId") UUID noteId) {
        NoteDetailsModel pinNoteDetailsModel=noteService.trashNote(userIdToken,noteId);
        return  new ResponseEntity
                (new ResponseDto("NOTE IN TRASH ","200",pinNoteDetailsModel),
                        HttpStatus.OK);

    }

    @PutMapping("/UnTrash_note")
    public ResponseEntity unTrashNote(@RequestHeader(name = "userId") String userIdToken,
                                    @RequestParam(name = "noteId") UUID noteId) {
        NoteDetailsModel pinNoteDetailsModel=noteService.UnTrashNote(userIdToken,noteId);
        return  new ResponseEntity
                (new ResponseDto("NOTE UN_TRASHED","200",pinNoteDetailsModel),
                        HttpStatus.OK);

    }

    @DeleteMapping("/delete_note")
    public ResponseEntity deleteNote(@RequestHeader(name = "userId") String userIdToken,
                                    @RequestParam(name = "noteId") UUID noteId) {
        String pinNoteDetailsModel=noteService.deleteNotePerManently(userIdToken,noteId);
        return  new ResponseEntity
                (new ResponseDto("NOTE DELETED ","200",pinNoteDetailsModel),
                        HttpStatus.OK);

    }








    }
