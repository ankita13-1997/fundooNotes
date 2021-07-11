package com.microusers.noteservices.repository;

import com.microusers.noteservices.model.LabelDetailsModel;
import com.microusers.noteservices.model.NoteDetailsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ILabelRepository extends JpaRepository<LabelDetailsModel, UUID> {

    List<LabelDetailsModel> findByUserId(UUID userId);
    Optional<LabelDetailsModel> findByUserIdAndLabelId(UUID userId, UUID labelId);
    LabelDetailsModel findByLabelName(String LabelName);
    Optional<LabelDetailsModel> findByLabelNameAndUserId(String labelName,UUID userId);



}
