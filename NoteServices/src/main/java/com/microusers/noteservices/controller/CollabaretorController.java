package com.microusers.noteservices.controller;

import com.microusers.noteservices.dto.ResponseDto;
import com.microusers.noteservices.model.CollaboratorDetailsModel;
import com.microusers.noteservices.service.ICollabaretorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/collabaretor")
@CrossOrigin
public class CollabaretorController {

    @Autowired
    ICollabaretorService collabaretorService;


    @PostMapping("send_collab_mail")
    public ResponseEntity addCollaborator(@RequestHeader(name="userIdToken") String userIdToken,
                                          @RequestParam(name = "collabEmail") String collabEmail){

        CollaboratorDetailsModel collaboratorDetailsModel =collabaretorService.
                addCollabaretor(userIdToken,collabEmail);

        return new ResponseEntity
                (new ResponseDto("EMAIL IS SENT TO THE COLLABERATOR SUCCESSFULLY ", "200",
                        collaboratorDetailsModel),
                        HttpStatus.OK);

    }
    @GetMapping("/verify/email_collab/{tokenId}")
    public ResponseEntity verifyEmail(@PathVariable String  tokenId ){
        System.out.println("the token id from responseEntity is : "+tokenId);
        String message =collabaretorService.verifyEmail(tokenId);
        return new ResponseEntity
                (new ResponseDto("COLLABED NOTES ", "200",
                        message),
                        HttpStatus.OK);
    }


    @GetMapping("/get_collaborator_by_user")
    public ResponseEntity getCollaboratorOfUser(@RequestHeader(name="userIdToken") String userIdToken){

        List<CollaboratorDetailsModel> collabaretorList = collabaretorService.
                                                           getListCollaberator(userIdToken);


        return new ResponseEntity
                (new ResponseDto("COLLABED NOTES LIST ARE  ", "200",
                        collabaretorList),
                        HttpStatus.OK);
    }

    @DeleteMapping("/delete_collab")
    public ResponseEntity deleteCollaboratorOfUser(@RequestHeader(name="userIdToken") String userIdToken,
                                                   @RequestParam(name = "collabEmail") String collabEmail){

        String message = collabaretorService.deleteCollabaretor(userIdToken,collabEmail);

        return new ResponseEntity
                (new ResponseDto("COLLABED NOTES DELETED  ", "200",
                        message),
                        HttpStatus.OK);

    }



    @PutMapping("/addcollabtonote")
    public ResponseEntity addCollabToNote(@RequestHeader(name = "userIdToken") String userIdToken,
                                          @RequestParam(name = "collabEmail") String collabEmail,
                                          @RequestParam(name = "noteId") UUID noteId) {

        CollaboratorDetailsModel collaboratorDetailsModel =collabaretorService.
                addCollabaretorToNote(userIdToken,collabEmail,noteId);


        return new ResponseEntity
                (new ResponseDto("AADED COLLAB SUCCESSFULLY ", "200",
                        collaboratorDetailsModel),
                        HttpStatus.OK);

    }

    @PutMapping("/add_new_collabtonote")
    public ResponseEntity addNewCollabToNote(@RequestHeader(name = "userIdToken") String userIdToken,
                                          @RequestParam(name = "collabEmail") String collabEmail,
                                          @RequestParam(name = "noteId") UUID noteId) {

        CollaboratorDetailsModel collaboratorDetailsModel =collabaretorService.
                addNewCollabaretorToNote(userIdToken,collabEmail,noteId);


        return new ResponseEntity
                (new ResponseDto("AADED COLLAB SUCCESSFULLY ", "200",
                        collaboratorDetailsModel),
                        HttpStatus.OK);

    }

    @GetMapping("get_collab_by_notes_list")
    public ResponseEntity getCollabNotes(@RequestHeader(name="userToken")String userTokenId,
                                         @RequestParam(name="noteId")UUID noteId){


        List<CollaboratorDetailsModel> collabListByNotes = collabaretorService.
                getCollabListByNotes(userTokenId,noteId);


        return new ResponseEntity
                (new ResponseDto("LIsT OF COLLABERATOR IN ANOTE IS  ", "200",
                        collabListByNotes),
                        HttpStatus.OK);

    }

    @PutMapping("delete_collabaretor_from_note")
    public ResponseEntity deleteCollabretorFromNote(@RequestHeader(name="userToken")String userTokenId,
                                                    @RequestParam(name = "collabEmail") String collabEmail,
                                                    @RequestParam(name = "noteId") UUID noteId){




                  String message=collabaretorService.
                          deleteCollabOfNotes(userTokenId,collabEmail,noteId);

        return new ResponseEntity
                (new ResponseDto("DELETION STATUS   ", "200",
                        message),
                        HttpStatus.OK);

    }



}
