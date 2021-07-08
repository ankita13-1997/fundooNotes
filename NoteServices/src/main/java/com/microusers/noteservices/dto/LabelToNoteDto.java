package com.microusers.noteservices.dto;

import com.microusers.noteservices.model.LabelDetailsModel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
public class LabelToNoteDto {

    @NotNull(message = "The noteId field should not be empty")
    private UUID noteId;

    private List<LabelDetailsModel> labels;
}
