DROP TABLE IF EXISTS user_avatar;

CREATE TABLE user_avatar
(
    user_avatar_id BIGSERIAL,
    user_id        INTEGER      NOT NULL UNIQUE,
    file_name      VARCHAR(255) NOT NULL,
    file_path      VARCHAR(255) NOT NULL UNIQUE,
    file_size      INTEGER      NOT NULL,
    content_type   VARCHAR(255) NOT NULL,
    created_at      TIMESTAMP    NOT NULL,
    PRIMARY KEY (user_avatar_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE
);
