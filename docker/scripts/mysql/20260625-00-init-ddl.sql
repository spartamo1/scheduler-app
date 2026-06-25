CREATE TABLE schedule
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    title      VARCHAR(100) NOT NULL,
    content    TEXT         NOT NULL,
    created_by VARCHAR(100) NOT NULL,
    password   VARCHAR(100) NOT NULL,
    created_at DATETIME     NOT NULL,
    updated_at DATETIME     NOT NULL
);

CREATE TABLE comment
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    schedule_id INT          NOT NULL,
    content     TEXT         NOT NULL,
    created_by  VARCHAR(100) NOT NULL,
    password    VARCHAR(100) NOT NULL,
    created_at  DATETIME     NOT NULL,
    updated_at  DATETIME     NOT NULL,

    CONSTRAINT fk_comment_schedule
        FOREIGN KEY (schedule_id)
            REFERENCES schedule (id)
            ON DELETE CASCADE
);