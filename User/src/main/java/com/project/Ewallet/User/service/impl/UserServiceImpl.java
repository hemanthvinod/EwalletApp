package com.project.Ewallet.User.service.impl;

import com.project.Ewallet.User.domain.User;
import com.project.Ewallet.User.exception.UserException;
import com.project.Ewallet.User.repository.UserRepository;
import com.project.Ewallet.User.service.UserService;
import com.project.Ewallet.User.service.resource.TransactionRequest;
import com.project.Ewallet.User.service.resource.UserRequest;
import com.project.Ewallet.User.service.resource.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Value("${kafka.topic.user-created}")
    private String USER_CREATED_TOPIC;

    @Value("${kafka.topic.user-deleted}")
    private String USER_DELETED_TOPIC;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    RestTemplate restTemplate;


    @Override
    public void createUser(User user) throws UserException{
        Optional<User> userOptional = userRepository.findByUsername(user.getUsername());
        if(userOptional.isPresent()){
            throw new UserException("EWALLET_USER_EXISTS_EXCEPTION", "user already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        kafkaTemplate.send(USER_CREATED_TOPIC, String.valueOf(user.getId()));
    }

    @Override
    public UserResponse getUser(String id) {
        Optional<User> optionalUser = userRepository.findById(Long.valueOf(id));
        User user = optionalUser.orElseThrow(() ->
                new UserException("EWALLET_USER_NOT_FOUND_EXCEPTION", "user does not exist"));
        return new UserResponse(user);
    }

    @Override
    public User deleteUser(String id) {
        Optional<User> optionalUser = userRepository.findById(Long.valueOf(id));
        if(optionalUser.isEmpty()){
            throw new UserException("EWALLET_USER_NOT_FOUND_EXCEPTION", "user does not exist");
        }
        userRepository.deleteById(Long.valueOf(id));
        kafkaTemplate.send(USER_DELETED_TOPIC, String.valueOf(id));
        return optionalUser.get();
    }

    @Override
    public User updateUser(UserRequest userRequest, String id) {
        Optional<User> userOptional = userRepository.findById(Long.valueOf(id));
        if(userOptional.isEmpty()){
            throw new UserException("USER_NOT_FOUND_EXCEPTION","User not found");
        }
        validateChange(userOptional.get(), userRequest);
        User user = userOptional.get();
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setEmail(userRequest.getEmail());
        return userRepository.save(user);
    }

    private void validateChange(User user, UserRequest userRequest) {
        if(user.getUsername().equals(userRequest.getUsername()) && user.getPassword().equals(userRequest.getPassword())
        && user.getEmail().equals(userRequest.getEmail())){
            throw new UserException("EWALLET_USER_NOT_CHANGED_EXCEPTION", "no change in user details");
        }
    }

    @Override
    public boolean transfer(Long userId, TransactionRequest transactionRequest) {
        Optional<User>  optionalSender = userRepository.findById(userId);
        if(optionalSender.isEmpty()){
            throw new UserException("EWALLET_SENDER_NOT_FOUND_EXCEPTION","user does not exist");
        }
        Optional<User>  optionalReceiver= userRepository.findById(userId);
        if(optionalReceiver.isEmpty()){
            throw new UserException("RECEIVER_NOT_FOUND_EXCEPTION","user does not exist");
        }
        ResponseEntity<Boolean> response = restTemplate.postForEntity("http://localhost:8083/transaction/"+userId,transactionRequest,Boolean.class);
        return  response.getBody();
    }
}
