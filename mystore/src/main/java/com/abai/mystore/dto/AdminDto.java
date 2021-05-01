package com.abai.mystore.dto;

import com.abai.mystore.entity.Status;
import com.abai.mystore.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminDto {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String status;

    public User toUser(){
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setFirstname(firstName);
        user.setLastname(lastName);
        user.setEmail(email);
        user.setStatus(Status.valueOf(status));

        return user;
    }

    public static AdminDto fromUser(User user){
        AdminDto adminDto = new AdminDto();
        adminDto.setId(user.getId());
        adminDto.setUsername(user.getUsername());
        adminDto.setFirstName(user.getFirstname());
        adminDto.setLastName(user.getLastname());
        adminDto.setEmail(user.getEmail());
        adminDto.setStatus(user.getStatus().name());

        return adminDto;
    }
}
