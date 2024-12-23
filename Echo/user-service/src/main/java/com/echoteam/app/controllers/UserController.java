package com.echoteam.app.controllers;

import com.echoteam.app.entities.User;
import com.echoteam.app.entities.dto.changedDTO.ChangedUser;
import com.echoteam.app.entities.dto.createdDTO.CreatedUser;
import com.echoteam.app.entities.dto.nativeDTO.UserDTO;
import com.echoteam.app.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

import static com.echoteam.app.entities.mappers.UserMapper.INSTANCE;

@RestController
@RequestMapping("${application.endpoint.user}")
@RequiredArgsConstructor
public class UserController {
    public static String userUri;
    private final UserService userService;

    @Value("${application.endpoint.user}")
    public void setUserUri(String uri) {
        UserController.userUri = uri;
    }

    @GetMapping
    public ResponseEntity<PagedModel<UserDTO>> getUsers(@RequestParam(name ="page",defaultValue = "0") int page,
                                                        @RequestParam(name ="size",defaultValue = "10") int size,
                                                        @RequestParam(name ="sortBy",defaultValue = "id") String sortBy,
                                                        @RequestParam(name ="direction",defaultValue = "ask") String direction) {
        Sort orderBy = createSort(sortBy, direction);
        PageRequest pageRequest = PageRequest.of(page, size, orderBy);
        Page<User> pageResponse = userService.getAll(pageRequest);

        PagedModel<UserDTO> pagedModel = createPagedModel(pageResponse);
        return ResponseEntity.ok(pagedModel);
    }

    private Sort createSort(String sortBy, String direction) {
        return direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
    }

    private PagedModel<UserDTO> createPagedModel(Page<User> page) {
        Page<UserDTO> userDTOS = page.map(INSTANCE::toDTOFromUser);
        userDTOS.forEach(UserDTO::doRoutine);
        return new PagedModel<>(userDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id) {
        var dto = INSTANCE.toDTOFromUser(userService.getById(id));
        dto.doRoutine();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/userByNickname")
    public ResponseEntity<UserDTO> getUserByNickname(@RequestParam(name = "nickname") String nickname) {
        var dto = INSTANCE.toDTOFromUser(userService.getByNickname(nickname));
        dto.doRoutine();

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@Valid @RequestBody CreatedUser user,
                                            UriComponentsBuilder uriBuilder) {
        var createdUser = userService.createUser(INSTANCE.toDTOFromCreatedUser(user));

        URI location = uriBuilder
                .replacePath(userUri + "/{id}")
                .buildAndExpand(Map.of("id", createdUser.getId()))
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(location)
                .build();
    }


    @PutMapping
    public ResponseEntity<Void> updateUser(@Valid @RequestBody ChangedUser changedUser,
                                        UriComponentsBuilder uriBuilder) {
        var user = userService.updateUser(INSTANCE.toDTOFromChangedUser(changedUser));
        URI location = uriBuilder
                .replacePath(userUri + "/{id}")
                .buildAndExpand(Map.of("id", user.getId()))
                .toUri();
        return ResponseEntity.status(HttpStatus.OK)
                .location(location)
                .build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
