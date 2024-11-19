CREATE TABLE users
(
    id                 UUID         NOT NULL,
    email              VARCHAR(255) NOT NULL,
    username           VARCHAR(255) NOT NULL,
    password_hash      VARCHAR(255) NOT NULL,
    created_at         TIMESTAMP WITHOUT TIME ZONE,
    is_banned          BOOLEAN,
    user_possibilities BYTEA,
    updated_at         TIMESTAMP WITHOUT TIME ZONE,
    record_status      VARCHAR(30) DEFAULT 'ACTIVE',
    user_type          VARCHAR(255) NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

ALTER TABLE users
    ADD CONSTRAINT uc_users_username UNIQUE (username);