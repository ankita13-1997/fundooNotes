package com.microusers.noteservices.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteDetailsModel {

    @Id
    @GeneratedValue(generator = "uuid2",strategy = GenerationType.AUTO)
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    public UUID noteId;

    @Column(name = "note_name")
    private String title;

    @Column(name = "note_description")
    private String description;

    @Column(name = "note_reminder")
    private Date reminder;

    @Column(name = "note_color")
    @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})|([0]{0})$", message = "Invalid color")
    private String color;

    @Column(name = "note_pin", columnDefinition = "boolean default false")
    private boolean isPin;

    @Column(name = "note_archive", columnDefinition = "boolean default false")
    private boolean isArchive;

    @Column(name = "note_trash", columnDefinition = "boolean default false")
    private boolean isTrash;

    @Column(name = "note_created_date")
    private LocalDateTime createdDate= LocalDateTime.now();

    @Column(name = "note_updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "user_id")
    @NotNull
    @Type(type = "uuid-char")
    private UUID userId;


    @Column(name = "user_Firstname")
    private String userFullName;



    @Column(name = "user_email")
    private String userEmail;

    public NoteDetailsModel(String title, String color, Date reminder, String description) {
        this.title=title;
        this.color=color;
        this.reminder=reminder;
        this.description=description;
    }


}
