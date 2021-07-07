package com.microusers.noteservices.repository;

import com.microusers.noteservices.model.NoteDetailsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface  INoteRepository extends JpaRepository<NoteDetailsModel, UUID> {
          Optional<NoteDetailsModel> findByTitle(String title);
          List<NoteDetailsModel> findByUserId(UUID userId);

}
