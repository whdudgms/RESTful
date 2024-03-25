package org.example.restful.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.restful.bean.User;
import org.example.restful.dao.UserDaoService;
import org.example.restful.exception.UserNotFoundException;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@Tag(name ="user-controller", description = "일반 사용자 서비스를 위한 컨트럴러")
public class UserController {
    private UserDaoService service;

    public UserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return service.findAll();
    }

    @Operation(summary = "사용자 정보 조회",description = "사용자 ID를 이용해서 사용자 상세 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class)))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR !!"),
    })
    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(
            @Parameter(description = "사용자",required = true, example="1") @PathVariable int id){
        User user = service.findOne(id); // ID를 기반으로 사용자를 조회합니다.

        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id)); // 사용자를 찾을 수 없는 경우 예외를 발생시킵니다.
        }

        EntityModel<User> entityModel = EntityModel.of(user); // 조회된 사용자를 EntityModel로 감싸 하이퍼미디어 정보를 추가합니다.

        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers()); // '모든 사용자 조회' API로의 링크를 생성합니다.
        entityModel.add(linkTo.withRel("all-users")); // 생성된 링크를 현재 EntityModel에 추가합니다. 'all-users'라는 관계명(rel)을 가지게 됩니다.

        return entityModel; // 하이퍼미디어 정보가 포함된 EntityModel을 반환합니다.
    }

    //CREATE
    //input - details of user
    // output -  CREATED & Return the created URI
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User savedUser = service.save(user);

        //201번 나옴
        //Lcation에 생성된 User URI 전달
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }


    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable int id){
        User deleteuser =  service.deleteById(id);

        if (deleteuser == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
        return ResponseEntity.noContent().build();
    }



}
