package com.jnk1124.resttest.controller;

import com.jnk1124.resttest.exception.UserNotFoundException;
import com.jnk1124.resttest.repository.User;
import com.jnk1124.resttest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    @ResponseBody
    public List<User> getUserList(){
        return userService.getUserList();
    }

    @GetMapping("/users/{userId}")
    @ResponseBody
    public User getUsers( @PathVariable long userId){

        User selectUser = userService.getUser(userId);

        if(selectUser == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", userId));
        }

        return selectUser;
    }

    @PostMapping("/users")
    @ResponseBody
    public ResponseEntity insertUser(@RequestBody User user){
        long id = userService.insertUser(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    @ResponseBody
    public void deleteUser(@PathVariable long id){

        User user = userService.deleteById(id);

        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

    }

}
