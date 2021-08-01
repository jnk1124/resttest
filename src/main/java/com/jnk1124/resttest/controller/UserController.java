package com.jnk1124.resttest.controller;

import com.jnk1124.resttest.exception.UserNotFoundException;
import com.jnk1124.resttest.repository.User;
import com.jnk1124.resttest.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getUserList(){
        return userService.getUserList();
    }

    @GetMapping("/users/{userId}")
    public User getUsers( @PathVariable long userId){

        User selectUser = userService.getUser(userId);

        if(selectUser == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", userId));
        }

        return selectUser;
    }

    @PostMapping("/users")
    public ResponseEntity insertUser(@Valid @RequestBody User user){
        long id = userService.insertUser(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable long id){

        User user = userService.deleteById(id);

        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
    }

    @PutMapping("/users")
    public User updateUser(@Valid @RequestBody User user){
        long userId = user.getId();

        logger.debug("userId : " + userId);

        if(userId != 0){
            User selectUser = userService.getUser(userId);

            if(selectUser == null){
                throw new UserNotFoundException(String.format("ID[%s] not found", userId));
            }
            // update user
            return userService.updateUser(user);
        }else{
            throw new UserNotFoundException(String.format("ID[%s] not found", userId));
        }
    }

}
