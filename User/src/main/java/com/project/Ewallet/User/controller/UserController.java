package com.project.Ewallet.User.controller;

import com.project.Ewallet.User.domain.User;
import com.project.Ewallet.User.service.UserService;
import com.project.Ewallet.User.service.resource.TransactionRequest;
import com.project.Ewallet.User.service.resource.UserRequest;
import com.project.Ewallet.User.service.resource.UserResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("user")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserRequest userRequest){
        userService.createUser(userRequest.toUser());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String userId){
        UserResponse userResponse = userService.getUser(userId);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @DeleteMapping("user/{userId}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable  String userId){
        User user = userService.deleteUser(userId);
        return new ResponseEntity<>(new UserResponse(user), HttpStatus.OK);
    }

    @PutMapping("user/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable("userId") String userId, @RequestBody UserRequest userRequest){
        userService.updateUser(userRequest, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("user/{userId}/transfer")
    public ResponseEntity<?> performTransaction (@PathVariable("userId") String userId,
                                                 @RequestBody TransactionRequest transactionRequest){
        boolean tx = userService.transfer(Long.valueOf(userId), transactionRequest);
        if(tx){
            return  new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
