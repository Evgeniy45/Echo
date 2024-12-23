package com.echoteam.app.integration.controllers;

import com.echoteam.app.controllers.UserAvatarController;
import com.echoteam.app.controllers.UserController;
import com.echoteam.app.entities.dto.nativeDTO.AvatarDTO;
import com.echoteam.app.entities.dto.nativeDTO.UserDTO;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.echoteam.app.utils.PathUtilities.getClasspath;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserAvatarControllerTest {
    private static final String IMAGE_PATH_SOURCE_ROOT = "imagesToPost/astronaut.jpeg";
    @Autowired
    TestRestTemplate template;

    @BeforeEach
    void createAvatarForTest() {
        long userId = 1;

        ResponseEntity<UserDTO> userResponse = template.getForEntity(
                UserController.userUri + "/" + userId,
                UserDTO.class
        );

        AvatarDTO avatar = userResponse.getBody().getAvatar();
        createAvatarInPath(avatar.getFilePath(), IMAGE_PATH_SOURCE_ROOT);
    }

    @SneakyThrows
    private void createAvatarInPath(String filePath, String imageSourceRoot) {
        Path path = getClasspath().resolve(filePath);
        Path imageAbsolute = getClasspath().resolve(imageSourceRoot);

        if (!Files.exists(path)) {

            byte[] image = Files.readAllBytes(imageAbsolute);
            Files.write(path, image);
        }
    }

    @Test
    void getAvatarByUserId_shouldReturn200_whenItIsOk() {
        // given
        long userId = 1;

        // when
        ResponseEntity<byte[]> response = template.getForEntity(
                UserAvatarController.avatarUri + "/" + userId,
                byte[].class
        );

        // then
        assertThat(response.getStatusCode())
                .as("Expected HTTP 200 OK for userId %d, but received %s", userId, response.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        assertThat(response.getBody())
                .as("Response body for userId %d is null. Ensure avatar data exists and is returned properly.", userId)
                .isNotNull();
    }

    @Test
    void getAvatarById_shouldReturn404_whenFileNotExists() {
        // given
        long userId = 2;

        // when
        ResponseEntity<String> response = template.getForEntity(
                UserAvatarController.avatarUri + "/" + userId,
                String.class
        );

        ResponseEntity<UserDTO> responseForUser = template.getForEntity(
                UserController.userUri + "/" + userId,
                UserDTO.class
        );
        UserDTO user = responseForUser.getBody();

        // then
        assertThat(response.getStatusCode())
                .as("Expected HTTP 404 NOT_FOUND for userId %d, but got %s", userId, response.getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);

        DocumentContext context = JsonPath.parse(response.getBody());

        String responseErrorTitle = context.read("$.body.title");
        String expectedErrorTitle = "Not Found";
        assertThat(responseErrorTitle)
                .as("Expected error title '%s' in response body, but got '%s'", expectedErrorTitle, responseErrorTitle)
                .isEqualTo(expectedErrorTitle);

        String responseErrorDetails = context.read("$.body.detail");
        String expectedErrorDetail = "User %s dont have any avatar.".formatted(user.getNickname());
        assertThat(responseErrorDetails)
                .as("Expected error detail '%s' in response body, but got '%s'", expectedErrorDetail, responseErrorDetails)
                .isEqualTo(expectedErrorDetail);
    }

    @SneakyThrows
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void addAvatars_shouldReturn201_whenAvatarIsCreated() {
        // given
        long userId = 2;
        HttpEntity<MultiValueMap<String, Object>> httpEntity = getMultiPartFileHttpEntity(userId);

        // when
        ResponseEntity<String> postResponse = template.postForEntity(
                UserAvatarController.avatarUri,
                httpEntity,
                String.class
        );

        // then
        assertThat(postResponse.getStatusCode())
                .as("Expected response status to be 204 No Content, but was %s", postResponse.getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);

        URI location = postResponse.getHeaders().getLocation();
        assertThat(location)
                .as("Location header should not be null after avatar creation.")
                .isNotNull();

        ResponseEntity<byte[]> getResponse = template.getForEntity(
                location,
                byte[].class
        );

        assertThat(getResponse.getStatusCode())
                .as("Expected response status to be 200 OK, but was %s", getResponse.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        assertThat(getResponse.getBody())
                .as("Response body should not be null when fetching the created avatar.")
                .isNotNull();
    }

    private HttpEntity<MultiValueMap<String,Object>> getMultiPartFileHttpEntity(long userId) throws IOException {
        MultiValueMap<String,Object> body = getMultiValueMap(userId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        return new HttpEntity<>(body, headers);
    }

    private MultiValueMap<String,Object> getMultiValueMap(long userId) throws IOException {
        Path classPath = getClasspath();
        Path resourcePath = Path.of(IMAGE_PATH_SOURCE_ROOT);
        Path absolute = classPath.resolve(resourcePath);

        if (!Files.exists(absolute)) throw new IOException("Resource with such name is not found.");

        byte[] resource = Files.readAllBytes(absolute);
        ByteArrayResource fileResource = new ByteArrayResource(resource) {
            @Override
            public String getFilename() {
                return resourcePath.getFileName().toString();
            }
        };

        MultiValueMap<String,Object> body = new LinkedMultiValueMap<>();
        body.add("userId", userId);
        body.add("avatar", fileResource);
        return body;
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void deleteAvatar_shouldReturn204_whenIsOk() {
        // given
        long userId = 1;

        // when
        ResponseEntity<Void> response = template.exchange(
                UserAvatarController.avatarUri + "/" + userId,
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Void.class
        );

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).isNull();
    }

}
