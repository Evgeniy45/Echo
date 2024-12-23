package com.echoteam.app.entities.mappers;

import com.echoteam.app.entities.Avatar;
import com.echoteam.app.entities.dto.nativeDTO.AvatarDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserAvatarMapper {
    UserAvatarMapper INSTANCE = Mappers.getMapper(UserAvatarMapper.class);

    AvatarDTO toDTOFromAvatar(Avatar avatar);
    Avatar toAvatarFromDTO(AvatarDTO dto);
}
