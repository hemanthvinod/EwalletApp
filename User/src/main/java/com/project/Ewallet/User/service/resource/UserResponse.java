package com.project.Ewallet.User.service.resource;


import com.project.Ewallet.User.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private String id;
    private String username;
    private String email;

    public UserResponse(User user){
        this.email = user.getEmail();
        this.id = String.valueOf(user.getId());
        this.username = user.getUsername();
    }


}
