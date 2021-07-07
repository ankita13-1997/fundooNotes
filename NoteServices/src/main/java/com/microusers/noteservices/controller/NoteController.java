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






}
