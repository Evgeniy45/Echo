package com.echoteam.app.unit.entities.mappers;

import com.echoteam.app.entities.Avatar;
import com.echoteam.app.entities.dto.nativeDTO.AvatarDTO;
import com.echoteam.app.entities.mappers.UserAvatarMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserAvatarMapperTest {
    private final UserAvatarMapper mapper = UserAvatarMapper.INSTANCE;

    @Test
    void mapToDTO_fromUserAvatarTest() {
        Avatar avatar = Avatar.getValidInstance();
        AvatarDTO dto = mapper.toDTOFromAvatar(avatar);

        assertThat(dto.getId())
                .as("The ID in DTO must match the ID in the Avatar entity")
                .isEqualTo(avatar.getId());
        assertThat(dto.getFileName())
                .as("The fileName in DTO must match the fileName in the Avatar entity")
                .isEqualTo(avatar.getFileName());
        assertThat(dto.getFilePath())
                .as("The filePath in DTO must match the filePath in the Avatar entity")
                .isEqualTo(avatar.getFilePath());
        assertThat(dto.getFileSize())
                .as("The fileSize in DTO must match the fileSize in the Avatar entity")
                .isEqualTo(avatar.getFileSize());
        assertThat(dto.getContentType())
                .as("The contentType in DTO must match the contentType in the Avatar entity")
                .isEqualTo(avatar.getContentType());
        assertThat(dto.getCreatedAt())
                .as("The createdAt in DTO must match the createdAt timestamp in the Avatar entity")
                .isEqualTo(avatar.getCreatedAt());

        assertThat(dto.getContent())
                .as("The content in DTO must be null as it is not included in the mapping")
                .isNull();
    }

    @Test
    void mapToAvatar_fromAvatarDTO() {
        AvatarDTO dto = AvatarDTO.getValidInstance();
        Avatar avatar = mapper.toAvatarFromDTO(dto);

        assertThat(avatar.getId())
                .as("The ID in Avatar must match the ID in the AvatarDTO")
                .isEqualTo(dto.getId());
        assertThat(avatar.getFileName())
                .as("The fileName in Avatar must match the fileName in the AvatarDTO")
                .isEqualTo(dto.getFileName());
        assertThat(avatar.getFilePath())
                .as("The filePath in Avatar must match the filePath in the AvatarDTO")
                .isEqualTo(dto.getFilePath());
        assertThat(avatar.getFileSize())
                .as("The fileSize in Avatar must match the fileSize in the AvatarDTO")
                .isEqualTo(dto.getFileSize());
        assertThat(avatar.getContentType())
                .as("The contentType in Avatar must match the contentType in the AvatarDTO")
                .isEqualTo(dto.getContentType());
        assertThat(avatar.getCreatedAt())
                .as("The createdAt in Avatar must match the createdAt timestamp in the AvatarDTO")
                .isEqualTo(dto.getCreatedAt());
    }

}
