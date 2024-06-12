package com.project.Ewallet.User.service;

import com.project.Ewallet.User.domain.User;
import com.project.Ewallet.User.exception.UserException;
import com.project.Ewallet.User.service.resource.TransactionRequest;
import com.project.Ewallet.User.service.resource.UserRequest;
import com.project.Ewallet.User.service.resource.UserResponse;

public interface UserService {

    public void createUser(User user) throws UserException;
    public UserResponse getUser(String id);
    public User deleteUser(String id);
    public User updateUser(UserRequest userRequest, String id);
    public boolean transfer(Long userId, TransactionRequest transactionRequest);

}
