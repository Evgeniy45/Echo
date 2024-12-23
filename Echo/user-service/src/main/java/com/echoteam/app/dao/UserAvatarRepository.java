package com.echoteam.app.dao;

import com.echoteam.app.entities.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAvatarRepository extends JpaRepository<Avatar, Long> {

}
