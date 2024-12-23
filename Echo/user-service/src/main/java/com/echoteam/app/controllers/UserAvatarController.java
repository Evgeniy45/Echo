package com.echoteam.app.controllers;

import com.echoteam.app.entities.dto.nativeDTO.AvatarDTO;
import com.echoteam.app.services.UserAvatarService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("${application.endpoint.avatar}")
@RequiredArgsConstructor
public class UserAvatarController {
    public static String avatarUri;
    private final UserAvatarService avatarService;

    @Value("${application.endpoint.avatar}")
    public void setAvatarUri(String uri) {
        UserAvatarController.avatarUri = uri;
    }

    @Deprecated
    @GetMapping
    public ModelAndView getForm() {
        return new ModelAndView("image-form");
    }

    @Deprecated
    @GetMapping("/get-image")
    public ModelAndView getImage() {
        return new ModelAndView("image-get-form");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<byte[]> getAvatarByUserId(@PathVariable(value = "userId") Long userId) {
        AvatarDTO avatarById = avatarService.getAvatarById(userId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatarById.getContentType()));
        headers.setContentLength(avatarById.getFileSize());
        headers.setCacheControl(CacheControl.noCache());

        return ResponseEntity.ok()
                .headers(headers)
                .body(avatarById.getContent());
    }

    @PostMapping
    public ResponseEntity<Void> addAvatar(@RequestParam("userId") Long userId,
                                          @RequestParam("avatar") MultipartFile avatar,
                                          UriComponentsBuilder uriBuilder) {
        avatarService.saveAvatar(userId, avatar);

        URI location = uriBuilder
                .replacePath(avatarUri + "/{userId}")
                .buildAndExpand(Map.of("userId", userId))
                .toUri();
        return ResponseEntity.noContent()
                .location(location)
                .build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteAvatar(@PathVariable(value = "userId") Long userId) {
        avatarService.deleteAvatar(userId);

        return ResponseEntity.noContent().build();
    }

}
