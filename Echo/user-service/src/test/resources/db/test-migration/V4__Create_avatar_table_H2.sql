DROP TABLE IF EXISTS user_avatar;

CREATE TABLE user_avatar
(
    user_avatar_id BIGINT AUTO_INCREMENT,
    user_id        INTEGER      NOT NULL UNIQUE,
    file_name      VARCHAR(255) NOT NULL,
    file_path      VARCHAR(255) NOT NULL UNIQUE,
    file_size      INTEGER      NOT NULL,
    content_type   VARCHAR(255) NOT NULL,
    created_at      TIMESTAMP    NOT NULL,
    PRIMARY KEY (user_avatar_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE
);

INSERT INTO user_avatar(user_avatar_id, user_id, file_name, file_path, file_size, content_type, created_at)
VALUES (
        50,
        1,
        'volcano.jpeg',
        'app/avatars/87/873d1b4b75dfb727348edafcc7493f8e98e5ca88e0b52fdcd105434c19a14a0f_6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b.jpg',
        447772,
        'image/jpeg',
        '2024-12-01 22:51:04.477747'
       );