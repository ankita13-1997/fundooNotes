package com.microusers.userserver.repository;

import com.microusers.userserver.model.UserDetailsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserDetailsModel, UUID> {

    Optional<UserDetailsModel> findByEmailID(String emailID);
}
