package com.echoteam.app.entities.dto.nativeDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AvatarDTO {
    private Long id;
    private String fileName;
    private String filePath;
    private Long fileSize;
    private String contentType;
    private Timestamp createdAt;
    private byte[] content;

    public static AvatarDTO getValidInstance() {
        return AvatarDTO.builder()
                .id(1L)
                .fileName("cat-avatar")
                .filePath("app/avatars/nf/987ba42df342ff36013a3afc3987ba42df342ff36013a3afc3.jpg")
                .fileSize(13000L)
                .contentType(MediaType.IMAGE_JPEG_VALUE)
                .createdAt(Timestamp.valueOf(LocalDateTime.of(2000,1,1,3,1)))
                .content(null)
                .build();
    }
}
