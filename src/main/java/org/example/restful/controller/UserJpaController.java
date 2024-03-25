package org.example.restful.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.restful.bean.Post;
import org.example.restful.bean.User;
import org.example.restful.exception.UserNotFoundException;
import org.example.restful.repository.PostRepository;
import org.example.restful.repository.UserRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/jpa")
@AllArgsConstructor
public class UserJpaController {
    private UserRepository userRepository;
    private PostRepository postRepository;


    // /jpa/users
    @GetMapping("/users")
    public Map retrieveAllusers(){

        int size = userRepository.findAll().size();
        return   Map.of("users",userRepository.findAll(),"size",userRepository.findAll().size());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity retrieveUsersById(@PathVariable int id ){
        Optional<User> user =  userRepository.findById(id);


        if(!user.isPresent()){
            throw new UserNotFoundException("id : " +id);
        }

        EntityModel entityModel =  EntityModel.of(user.get());

        WebMvcLinkBuilder linTo =  linkTo(methodOn(this.getClass()).retrieveAllusers());
        entityModel.add(linTo.withRel("all-users"));

        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable int id ){
        userRepository.deleteById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User savedUser = userRepository.save(user);

        //201번 나옴
        //Lcation에 생성된 User URI 전달
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> retrieveAllPostsByUser(@PathVariable int id){
        Optional<User> user  = userRepository.findById(id);
        if(!user.isPresent()){
            throw new UserNotFoundException("id - " + id);
        }

        return user.get().getPosts();
    }


    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Post> createPost(@PathVariable int id, @RequestBody Post post) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("id-" + id);
        }

        User user = userOptional.get();

        post.setUser(user);

        postRepository.save(post);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}