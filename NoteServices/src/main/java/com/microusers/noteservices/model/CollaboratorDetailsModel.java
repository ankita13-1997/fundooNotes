package com.microusers.noteservices.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollaboratorDetailsModel {


    @Id
    @GeneratedValue(generator = "uuid2",strategy = GenerationType.AUTO)
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    public UUID CollaboratorId;

    @Column(name = "Collaborator_email")
    private String collabEmail;

    @Column(name = "Collaborator_Addec")
    private boolean isAdded;

    @Column(name = "Collaborator_created_date")
    private LocalDateTime createdDate= LocalDateTime.now();


    @Column(name = "Collaborator_updated_date")
    private LocalDateTime updatedDate;


    @Column(name = "Collaborator_user_id")
    @NotNull
    @Type(type = "uuid-char")
    private UUID userId;


    @Column(name = "Collaborator_user_Firstname")
    private String userFullName;

    @Column(name = "user_email")
    private String userEmail;

    @JsonIgnoreProperties(value = "collaborators")
    @ManyToMany(mappedBy = "collaborators", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<NoteDetailsModel> notes;


    public CollaboratorDetailsModel(String collabEmail) {
        this.collabEmail=collabEmail;
    }
}
