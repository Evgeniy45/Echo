package com.echoteam.app.services;

import com.echoteam.app.dao.UserAvatarRepository;
import com.echoteam.app.entities.Avatar;
import com.echoteam.app.entities.User;
import com.echoteam.app.entities.dto.nativeDTO.AvatarDTO;
import com.echoteam.app.exceptions.ParameterIsNotValidException;
import com.echoteam.app.exceptions.SomethingWrongException;
import com.echoteam.app.utils.Utils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.echoteam.app.entities.mappers.UserAvatarMapper.INSTANCE;

@Service
@RequiredArgsConstructor
public class UserAvatarServiceImpl implements UserAvatarService {
    public static final long MAX_FILE_SIZE_RESTRICTION = 10 * 1024 * 1024;
    public static final Path AVATARS_ROOT_DIRECTORY = Path.of("app/avatars/");

    private final UserService userService;
    private final FileManagerService fileManager;
    private final UserAvatarRepository avatarRepository;

    @Override
    public AvatarDTO getAvatarById(Long userId) {
        User user = userService.getById(userId);
        if (user.getAvatar() == null) {
            throw new EntityNotFoundException("User %s dont have any avatar.".formatted(user.getNickname()));
        }

        Optional<Avatar> avatarById = avatarRepository.findById(user.getAvatar().getId());
        try {
            if (avatarById.isPresent()) {
                AvatarDTO dto = INSTANCE.toDTOFromAvatar(avatarById.get());
                try {
                    dto.setContent(fileManager.download(dto.getFilePath()));
                } catch (EntityNotFoundException e) {
                    throw new EntityNotFoundException("File with name: %s not found.".formatted(dto.getFileName()));
                }
                return dto;
            } else {
                throw new EntityNotFoundException("Avatar with id:%s could not be processed, please try again.".formatted(userId));
            }
        } catch (IOException e) {
            throw new SomethingWrongException("The file could not be read, please try again.");
        }
    }

    @Transactional
    @Override
    public void saveAvatar(Long userId, MultipartFile avatar) {
        User user = userService.getById(userId);
        Avatar userAvatar = user.getAvatar() == null
                ? createAvatar(avatar, user)
                : editAvatar(avatar, user);

        avatarRepository.save(userAvatar);
        try {
            fileManager.upload(userAvatar.getFilePath(), avatar);
        } catch (IOException e) {
            throw new SomethingWrongException("File wasn't added, try again please." + e);
        }
    }

    private void validateMultipartFile(MultipartFile avatar) {
        if (avatar.isEmpty() || avatar == null)
            throw new ParameterIsNotValidException("Avatar is null.");

        String contentType = avatar.getContentType();
        if (!contentType.equals(MediaType.IMAGE_JPEG_VALUE) &&
            !contentType.equals(MediaType.IMAGE_PNG_VALUE))
            throw new ParameterIsNotValidException("Avatar must be JPEG or PNG type.");

        if (avatar.getSize() > MAX_FILE_SIZE_RESTRICTION)
            throw new ParameterIsNotValidException("Avatar size must be less 10 MB.");
    }

    private Avatar createAvatar(MultipartFile avatar, User user) {
        String extension = resolveExtension(avatar.getContentType());
        String filePath = createPathForAvatar(user.getId(), extension).toString();
        return Avatar.builder()
                .user(user)
                .fileName(avatar.getOriginalFilename())
                .filePath(filePath)
                .fileSize(avatar.getSize())
                .contentType(avatar.getContentType())
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();
    }

    private Avatar editAvatar(MultipartFile avatar, User user) {
        Avatar userAvatar = user.getAvatar();
        userAvatar.setFileName(avatar.getOriginalFilename());
        userAvatar.setFileSize(avatar.getSize());
        userAvatar.setContentType(avatar.getContentType());
        userAvatar.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        return userAvatar;
    }

    private String resolveExtension(String contentType) {
        String result;
        switch (contentType) {
            case MediaType.IMAGE_PNG_VALUE -> result = ".png";
            case MediaType.IMAGE_JPEG_VALUE -> result = ".jpg";
            default -> throw new SomethingWrongException("resolveExtension: there can't resolve extension.");
        }

        return result;
    }

    private Path createPathForAvatar(Long userId, String extension) {
        String hashedTimeMillis = Utils.generateHash(String.valueOf(System.currentTimeMillis()));
        String hashedUserId = Utils.generateHash(String.valueOf(userId));

        if (hashedTimeMillis == null || hashedUserId == null)
            throw new SomethingWrongException("Something was wrong. Try again please.");

        String fileName = String.format("%s_%s", hashedTimeMillis, hashedUserId);
        String catalog = fileName.substring(0,2);

        return AVATARS_ROOT_DIRECTORY.resolve(catalog + File.separator + fileName + extension);
    }

    @Transactional
    @Override
    public void deleteAvatar(Long userId) {
        if (userId == null)
            throw new ParameterIsNotValidException("Id is require.");

        User user = userService.getById(userId);

        if (user.getAvatar() != null) {
            Avatar avatar = user.getAvatar();
            try {
                fileManager.delete(avatar.getFilePath());
            } catch (IOException e) {
                throw new SomethingWrongException("The avatar for '%s' wasn't deleted. Try again please.".formatted(user.getNickname()));
            }

            user.setAvatar(null);
            avatarRepository.deleteById(avatar.getId());
        }
    }

}
