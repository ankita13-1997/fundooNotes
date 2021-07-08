package com.microusers.noteservices.controller;

import com.microusers.noteservices.dto.EditLabelDto;
import com.microusers.noteservices.dto.LabelDto;
import com.microusers.noteservices.dto.ResponseDto;
import com.microusers.noteservices.model.LabelDetailsModel;
import com.microusers.noteservices.model.NoteDetailsModel;
import com.microusers.noteservices.service.ILabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/label_api")
@CrossOrigin
public class LabelController {

    @Autowired
    ILabelService labelService;

    @PostMapping("/adding_label")
    public ResponseEntity addLabel(@RequestHeader(name = "userId") String userIdToken,
                                   @RequestBody @Valid LabelDto addLabelDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<ResponseDto>(new ResponseDto(bindingResult.getAllErrors().get(0).
                    getDefaultMessage(), "100", null),
                    HttpStatus.BAD_REQUEST);
        }
        LabelDetailsModel labelDetailsModel = labelService.addLabel(userIdToken, addLabelDto);

        return new ResponseEntity
                (new ResponseDto("THE LABEL ADDED SUCCESSFULLY ", "200", labelDetailsModel),
                        HttpStatus.OK);


    }

    @GetMapping("/getting_all_label")
    public ResponseEntity getLabel(@RequestHeader(name = "userId") String userIdToken) {
        List<LabelDetailsModel> labelList = labelService.setListOfLabel(userIdToken);

        return new ResponseEntity
                (new ResponseDto("THE LABEL LIST ARE ", "200", labelList),
                        HttpStatus.OK);
    }

    @PutMapping("/update_label")
    public ResponseEntity updateLabel(@RequestHeader(name = "userId") String userIdToken,
                                      @RequestBody @Valid EditLabelDto addLabelDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<ResponseDto>(new ResponseDto(bindingResult.getAllErrors().get(0).
                    getDefaultMessage(), "100", null),
                    HttpStatus.BAD_REQUEST);
        }

        LabelDetailsModel labelDetailsModel = labelService.updateLabel(userIdToken, addLabelDto);

        return new ResponseEntity
                (new ResponseDto("THE LABEL UPDATED SUCCESSFULLY ", "200", labelDetailsModel),
                        HttpStatus.OK);

    }

    @DeleteMapping
    public ResponseEntity deleteLabel(@RequestHeader(name = "userId") String userIdToken,
                                      @RequestParam(name = "labelId") UUID labelId) {


        String labelDetailsModel = labelService.deleteLabelDetails(userIdToken, labelId);

        return new ResponseEntity
                (new ResponseDto("THE LABEL DELETED SUCCESSFULLY ", "200", labelDetailsModel),
                        HttpStatus.OK);

    }


    @GetMapping("/labels")
    public ResponseEntity addLabeltoNote(@RequestHeader String token,
                                         @RequestParam UUID labelId,
                                         @RequestParam UUID noteId) {
        NoteDetailsModel noteDetailsModel = labelService.addLabelNote(token, labelId, noteId);

        return new ResponseEntity
                (new ResponseDto("THE LABEL ADDED TO NOTE SUCCESSFULLY ",
                        "200",
                        noteDetailsModel),
                        HttpStatus.OK);

    }
}


