package com.microusers.userserver.service;

import com.microusers.userserver.dto.userLoginDto;
import com.microusers.userserver.dto.userRegistrationDto;
import com.microusers.userserver.model.UserDetailsModel;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;


public interface IUserService {
    UserDetailsModel addUser(userRegistrationDto userDetails);

    void verifyEmail(String tokenId);

    UserDetailsModel userLogin(userLoginDto userLoginDTO);

    String resetPasswordLink(String emailID) throws MessagingException;

    String resetPassword(String password, String urlToken);

    List<UserDetailsModel> getUserInformation(String token);

    UserDetailsModel setUserDetails(String token);
}
