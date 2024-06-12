package com.project.Ewallet.User.service.resource;

import com.project.Ewallet.User.domain.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    @NotBlank(message = "username cannot be empty")
    private String username;
    @NotBlank(message = "password cannot be blank")
    private String password;
    @Email(message = "email cannot be empty")
    private String email;

    public User toUser(){
        return User.builder().username(username).password(password)
                .email(email).build();
    }

}
