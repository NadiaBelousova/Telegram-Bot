-- liquibase formatted sql

-- changeset nbelousova:1
create table notification_task (
id BIGSERIAL PRIMARY KEY,
chat_id BIGINT,
text_message TEXT,
    date_time Timestamp
)