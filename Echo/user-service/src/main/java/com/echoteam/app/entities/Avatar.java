package com.echoteam.app.entities;

import jakarta.persistence.*;
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
@Entity
@Table(name = "user_avatar")
public class Avatar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_avatar_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @Column(name = "createdAt", nullable = false)
    private Timestamp createdAt;

    public static Avatar getValidInstance() {
        return Avatar.builder()
                .id(1L)
                .fileName("cat-avatar")
                .filePath("app/avatars/nf/987ba42df342ff36013a3afc3987ba42df342ff36013a3afc3.jpg")
                .fileSize(13000L)
                .contentType(MediaType.IMAGE_JPEG_VALUE)
                .createdAt(Timestamp.valueOf(LocalDateTime.of(2000,1,1,3,1)))
                .build();
    }
}
