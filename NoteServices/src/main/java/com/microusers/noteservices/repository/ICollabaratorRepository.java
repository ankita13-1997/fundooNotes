package com.microusers.noteservices.repository;

import com.microusers.noteservices.model.CollaboratorDetailsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ICollabaratorRepository extends JpaRepository<CollaboratorDetailsModel, UUID> {

    List<CollaboratorDetailsModel> findByUserId(UUID userId);
    Optional<CollaboratorDetailsModel> findByCollabEmailAndUserId(String emailId,UUID userId);


}
