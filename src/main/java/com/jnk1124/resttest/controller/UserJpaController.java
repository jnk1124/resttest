package com.jnk1124.resttest.controller;

import com.jnk1124.resttest.exception.UserNotFoundException;
import com.jnk1124.resttest.repository.Post;
import com.jnk1124.resttest.repository.PostRepository;
import com.jnk1124.resttest.repository.User;
import com.jnk1124.resttest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/jpa")
public class UserJpaController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable  Long id){

        Optional<User> user = userRepository.findById(id);

        if(!user.isPresent()){
            throw new UserNotFoundException(String.format("ID{%s} not found", id));
        }

        EntityModel<User> model = EntityModel.of(user.get());
        WebMvcLinkBuilder webMvcLinkBuilder = linkTo(methodOn(this.getClass()).retrieveAllUsers());

        model.add(webMvcLinkBuilder.withRel("all-users"));

        return model;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id){


        userRepository.deleteById(id);

    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){

        User save = userRepository.save(user);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(save.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> retrieveAllPostByUser(@PathVariable Long id){

        Optional<User> user = userRepository.findById(id);

        if(!user.isPresent()){
            throw new UserNotFoundException(String.format("ID{%s} not found", id));
        }

        return user.get().getPostList();
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<User> createPost(@PathVariable Long id,  @RequestBody Post post){

        Optional<User> user = userRepository.findById(id);

        if(!user.isPresent()){
            throw new UserNotFoundException(String.format("ID{%s} not found", id));
        }

        post.setUser(user.get());

        Post savePost = postRepository.save(post);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savePost.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

}
