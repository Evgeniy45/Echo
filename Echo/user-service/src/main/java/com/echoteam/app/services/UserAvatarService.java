package com.echoteam.app.services;

import com.echoteam.app.entities.dto.nativeDTO.AvatarDTO;
import org.springframework.web.multipart.MultipartFile;

public interface UserAvatarService {

    AvatarDTO getAvatarById(Long id);
    void saveAvatar(Long userId, MultipartFile avatar);
    void deleteAvatar(Long userId);

}
