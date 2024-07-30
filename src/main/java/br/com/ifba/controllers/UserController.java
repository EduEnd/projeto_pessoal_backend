package br.com.ifba.controllers;


import br.com.ifba.dto.UserGetResponseDto;
import br.com.ifba.dto.UserPostRequestDto;
import br.com.ifba.entity.User;
import br.com.ifba.infrastructure.mapper.ObjectMapperUtil;
import br.com.ifba.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ObjectMapperUtil objectMapperUtil;

    @GetMapping(path = "/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?>findAll(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(objectMapperUtil.mapAll(
                        this.userService.findAll(),
                        UserGetResponseDto.class));
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<UserGetResponseDto> findById(@PathVariable Long id) {
        User user = userService.findById(id);
            UserGetResponseDto responseDto = objectMapperUtil.map(user, UserGetResponseDto.class);
            return ResponseEntity.ok(responseDto);
        }


    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody @Valid UserPostRequestDto userPostRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(objectMapperUtil.map(userService.save(
                        (objectMapperUtil.map(userPostRequestDto,User.class))),
                        UserGetResponseDto.class));
    }

    @PutMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateUser(@RequestBody @Valid UserPostRequestDto userPostRequestDto) {
        User user = objectMapperUtil.map(userPostRequestDto, User.class);
        userService.updateUser(user);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.deleteUser(id));
    }
}
