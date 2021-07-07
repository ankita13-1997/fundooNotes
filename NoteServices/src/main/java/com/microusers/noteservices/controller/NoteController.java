package com.microusers.noteservices.controller;

import com.microusers.noteservices.dto.NoteDetailsDto;
import com.microusers.noteservices.dto.ResponseDto;
import com.microusers.noteservices.model.NoteDetailsModel;
import com.microusers.noteservices.service.INoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
                                                     @RequestBody NoteDetailsDto noteDetailsDto, BindingResult bindingResult){

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

}
