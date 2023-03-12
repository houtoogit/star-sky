CREATE TABLE IF NOT EXISTS member_base_info (
    id bigint NOT NULL UNIQUE,
    base_uuid int NOT NULL UNIQUE,
    base_country int DEFAULT NULL,
    base_phone varchar(32) NOT NULL UNIQUE,
    base_name varchar(255) DEFAULT NULL,
    base_head_img varchar(128) DEFAULT NULL,
    base_password char(32) DEFAULT NULL,
    base_register_time timestamp NULL DEFAULT NULL,
    base_state tinyint DEFAULT NULL,
    remark varchar(255) DEFAULT NULL,
    PRIMARY KEY (id),
    KEY base_phone_index (base_phone),
    KEY base_uuid_index (base_uuid)
);