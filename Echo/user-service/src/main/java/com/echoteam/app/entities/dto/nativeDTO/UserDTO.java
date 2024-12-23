package com.echoteam.app.entities.dto.nativeDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDTO {
    private Long id;
    private String nickname;
    private Timestamp created;
    private Timestamp changed;
    private Boolean isDeleted;
    private AvatarDTO avatar;
    private UserDetailDTO userDetail;
    private UserAuthDTO userAuth;
    private List<UserRoleDTO> roles;


    public static UserDTO getValidInstance() {
        return UserDTO.builder()
                .id(1L)
                .nickname("nickname")
                .created(Timestamp.valueOf(LocalDateTime.of(2000,1,1,3,1)))
                .changed(Timestamp.valueOf(LocalDateTime.of(2000,1,1,3,2)))
                .isDeleted(false)
                .avatar(AvatarDTO.getValidInstance())
                .userDetail(UserDetailDTO.getValidInstance())
                .userAuth(UserAuthDTO.getValidInstance())
                .roles(UserRoleDTO.getValidInstanceList())
                .build();
    }

    public void doRoutine() {
        hidePassword();
        makeReference();
    }

    public void hidePassword() {
        if (this.userAuth != null)
            this.userAuth.hidePassword();
    }

    public void makeReference() {
        if (Objects.nonNull(this.userDetail))
            this.userDetail.setUserId(this.id);

        if (Objects.nonNull(this.userAuth))
            this.userAuth.setUserId(this.id);
    }

}
