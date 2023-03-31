CREATE TABLE IF NOT EXISTS member_base_info (
    id bigint NOT NULL UNIQUE,
    uuid int NOT NULL UNIQUE,
    country varchar(16) DEFAULT '86',
    phone varchar(32) NOT NULL UNIQUE,
    username varchar(255) DEFAULT NULL,
    head_img varchar(128) DEFAULT NULL,
    password char(32) DEFAULT NULL,
    register_time timestamp NULL DEFAULT NULL,
    state tinyint DEFAULT 0,
    remark varchar(255) DEFAULT NULL,
    PRIMARY KEY (id),
    KEY phone_index (phone),
    KEY uuid_index (uuid)
);