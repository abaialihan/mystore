package com.abai.mystore.controller;

import com.abai.mystore.dto.AdminDto;
import com.abai.mystore.dto.UserDto;
import com.abai.mystore.entity.User;
import com.abai.mystore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/admin/")
public class AdminRestControllerV1 {

    private UserService userService;

    @Autowired
    public AdminRestControllerV1(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "users/{id}")
    public ResponseEntity<AdminDto> getUserById(@PathVariable(name = "id") Long id){
        User user = userService.findById(id);

        if(user == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        AdminDto result = AdminDto.fromUser(user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
