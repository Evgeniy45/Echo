package com.echoteam.app.unit.entities.mappers;

import com.echoteam.app.entities.User;
import com.echoteam.app.entities.dto.changedDTO.ChangedUser;
import com.echoteam.app.entities.dto.createdDTO.CreatedUser;
import com.echoteam.app.entities.dto.nativeDTO.UserDTO;
import com.echoteam.app.entities.mappers.UserMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserMapperTest {
    private final UserMapper mapper = UserMapper.INSTANCE;

    @Test
    void mapToDTO_fromCreatedUser() {
        CreatedUser createdUser = CreatedUser.getValidInstance();
        UserDTO dto = mapper.toDTOFromCreatedUser(createdUser);

        assertThat(dto.getNickname()).isEqualTo(createdUser.getNickname());
        assertThat(dto.getRoles()).isNotEmpty();

        assertThat(dto.getId()).isNull();
// todo: refactor test with new changes (avatar)
//        assertThat(dto.getAvatar()).isNull();
        assertThat(dto.getCreated()).isNull();
        assertThat(dto.getChanged()).isNull();
        assertThat(dto.getIsDeleted()).isNull();
        assertThat(dto.getUserDetail()).isNull();
        assertThat(dto.getUserAuth()).isNull();
    }

    @Test
    void mapToDTO_fromChangedUser() {
        ChangedUser changedUser = ChangedUser.getValidInstance();
        UserDTO dto = mapper.toDTOFromChangedUser(changedUser);

        assertThat(dto.getId()).isEqualTo(changedUser.getId());
        assertThat(dto.getNickname()).isEqualTo(changedUser.getNickname());
        assertThat(dto.getRoles()).isNotEmpty();
// todo: refactor test with new changes (avatar)
//        assertThat(dto.getAvatar()).isNull();
        assertThat(dto.getCreated()).isNull();
        assertThat(dto.getChanged()).isNull();
        assertThat(dto.getIsDeleted()).isNull();
        assertThat(dto.getUserDetail()).isNull();
        assertThat(dto.getUserAuth()).isNull();
    }

    @Test
    void mapToUser_fromDTO() {
        UserDTO dto = UserDTO.getValidInstance();
        User user = mapper.toUserFromDTO(dto);

        assertThat(user.getId()).isEqualTo(dto.getId());
        assertThat(user.getNickname()).isEqualTo(dto.getNickname());
// todo: refactor test with new changes (avatar)
//        assertThat(user.getAvatar()).isEqualTo(dto.getAvatar());
        assertThat(user.getCreated()).isEqualTo(dto.getCreated());
        assertThat(user.getChanged()).isEqualTo(dto.getChanged());
        assertThat(user.getIsDeleted()).isEqualTo(dto.getIsDeleted());
        assertThat(user.getUserDetail()).isNotNull();
        assertThat(user.getUserAuth()).isNotNull();
        assertThat(user.getRoles()).isNotEmpty();
    }

    @Test
    void mapToDTO_fromUser() {
        User user = User.getValidInstance();
        UserDTO dto = mapper.toDTOFromUser(user);

        assertThat(dto.getId()).isEqualTo(user.getId());
        assertThat(dto.getNickname()).isEqualTo(user.getNickname());
// todo: refactor test with new changes (avatar)
//        assertThat(dto.getAvatar()).isEqualTo(user.getAvatar());
        assertThat(dto.getCreated()).isEqualTo(user.getCreated());
        assertThat(dto.getChanged()).isEqualTo(user.getChanged());
        assertThat(dto.getIsDeleted()).isEqualTo(user.getIsDeleted());
        assertThat(dto.getUserDetail()).isNotNull();
        assertThat(dto.getUserAuth()).isNotNull();
        assertThat(dto.getRoles()).isNotEmpty();
    }

}
