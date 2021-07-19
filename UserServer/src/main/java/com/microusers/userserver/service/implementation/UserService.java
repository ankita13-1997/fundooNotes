package com.microusers.userserver.service.implementation;

import com.microusers.userserver.dto.ReddishDto;
import com.microusers.userserver.dto.userLoginDto;
import com.microusers.userserver.dto.userRegistrationDto;
import com.microusers.userserver.exception.FudooGlobalException;
import com.microusers.userserver.exception.UserException;
//import com.microusers.userserver.model.RabbitMQBody;
import com.microusers.userserver.model.RabbitMQBody;
import com.microusers.userserver.model.UserDetailsModel;
import com.microusers.userserver.repository.UserRepository;
import com.microusers.userserver.service.IUserService;
//import com.microusers.userserver.utils.MailService;
import com.microusers.userserver.utils.Token;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userDetailsRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    Token jwtToken ;

//    @Autowired
//    MailService mailService;

    @Autowired
    RedisService redisService;

    @Autowired
    private RabbitTemplate rabbitTemplate;



    @Override
    public UserDetailsModel addUser(userRegistrationDto userDetails) {
        Optional<UserDetailsModel> byEmailId = userDetailsRepository.findByEmailID(userDetails.getEmailID());
        if(byEmailId.isPresent()){
            throw new UserException(UserException.ExceptionType.USER_ALREADY_PRESENT);
        }
        String password = bCryptPasswordEncoder.encode(userDetails.getPassword());
        UserDetailsModel userDetailsModel = new UserDetailsModel(userDetails.getFullName(),
                userDetails.getPhoneNumber(),
                userDetails.getEmailID(),
                password);
        UserDetailsModel saveDetails = userDetailsRepository.save(userDetailsModel);
        String tokenId = jwtToken.generateVerificationToken(userDetailsModel);
        String requestUrl ="http://localhost:8081/user/verify/email/"+tokenId;
        System.out.println("token from registration is "+tokenId);
//        try {
//            mailService.sendMail(requestUrl,"the verification link is ",userDetailsModel.getEmailID());
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }

        RabbitMQBody rabbitMQBody = new RabbitMQBody();
        rabbitMQBody.setEmail(userDetails.getEmailID());
        rabbitMQBody.setSubject("the verification link for registration");
        rabbitMQBody.setBody(requestUrl);
        rabbitTemplate.convertAndSend("user_service", rabbitMQBody);



        return saveDetails;
    }

    @Override
    public void verifyEmail(String tokenId) {
        System.out.println("your token id is "+tokenId);
        UUID tokenjwt = jwtToken.decodeJWT(tokenId);
        System.out.println(tokenjwt);
        Optional<UserDetailsModel> userId = userDetailsRepository.findById(tokenjwt);
        System.out.println("userid" +userId);
        if(!userId.isPresent()) {
            throw  new UserException(UserException.ExceptionType.USER_NOT_FOUND);
        }
        userId.get().isVerified=true;
        userId.get().updatedAt= LocalDateTime.now();
        userDetailsRepository.save(userId.get());


    }

    @Override
    public UserDetailsModel userLogin(userLoginDto userLoginDTO) {
        System.out.println(userLoginDTO.emailID);
        Optional<UserDetailsModel> userDetailsByEmail = userDetailsRepository.findByEmailID(userLoginDTO.getEmailID());
        System.out.println("the optional message is "+userDetailsByEmail);

        if (!userDetailsByEmail.isPresent()) {
            throw new UserException(UserException.ExceptionType.EMAIL_NOT_FOUND);
        }
        if(userDetailsByEmail.get().isVerified){
            boolean password = bCryptPasswordEncoder.matches(userLoginDTO.password,
                    userDetailsByEmail.get().password);
            if (!password) {
                throw new UserException(UserException.ExceptionType.PASSWORD_INVALID);
            }
            String tokenString = jwtToken.generateLoginToken(userDetailsByEmail.get());
            ReddishDto reddishDto= new ReddishDto(userDetailsByEmail.get().getUserId(),tokenString);
            redisService.saveData(reddishDto);
            System.out.println("the user model  "+userDetailsByEmail.get());
            return userDetailsByEmail.get();
        }
        throw new UserException(UserException.ExceptionType.EMAIL_NOT_FOUND);
    }

    @Override
    public String resetPasswordLink(String emailID) throws MessagingException {
        UserDetailsModel user = userDetailsRepository.findByEmailID(emailID).orElseThrow(() -> new UserException(UserException.ExceptionType.EMAIL_NOT_FOUND));
        String tokenGenerate = jwtToken.generateVerificationToken(user);
        String urlToken = "Click on below link to Reset your Password \n"
                + "http://localhost:8081/user/reset/password/" +tokenGenerate;
//        mailService.sendMail(urlToken, "Reset Password", user.emailID);
        return "Reset Password Link Has Been Sent To Your Email Address";
    }

    @Override
    public String resetPassword(String password, String urlToken) {
        System.out.println(urlToken);
        UUID userId = jwtToken.decodeJWT(urlToken);
        System.out.println(userId);
        UserDetailsModel userDetails = userDetailsRepository.findById(userId).orElseThrow(() -> new UserException(UserException.ExceptionType.INVALID_DATA));
        String encodePassword = bCryptPasswordEncoder.encode(password);
        userDetails.password = encodePassword;
        userDetailsRepository.save(userDetails);
        return "Password Has Been Reset";
    }

    @Override
    public List<UserDetailsModel> getUserInformation(String token) {
        UUID userId = jwtToken.decodeJWT(token);
        UserDetailsModel findTheExistedUser = userDetailsRepository.findById(userId).
                orElseThrow(() ->new UserException(UserException.ExceptionType.USER_NOT_FOUND));
        List<UserDetailsModel> userDetailsModelList = new ArrayList<>();
        userDetailsModelList.add(findTheExistedUser);

        return userDetailsModelList;
    }

    @Override
    public UserDetailsModel setUserDetails(String token) {
        UUID userId = jwtToken.decodeJWT(token);

        UserDetailsModel findTheExistedUser = userDetailsRepository.findById(userId).
                orElseThrow(() ->new UserException(UserException.ExceptionType.USER_NOT_FOUND));


        return findTheExistedUser;
    }

    @Override
    public UserDetailsModel getUser(String token) {
        String user = redisService.getData(token);

        Optional<UserDetailsModel> userSearch = userDetailsRepository.findById(UUID.fromString(user));
        System.out.println(userSearch);
        return userSearch.get();
    }
}
