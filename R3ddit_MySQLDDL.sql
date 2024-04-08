-- -- 创建 user_database 数据库
-- CREATE DATABASE IF NOT EXISTS user_database;

-- -- 使用 user_database 数据库
-- USE user_database;

-- -- 创建 user 表
-- CREATE TABLE IF NOT EXISTS user (
--     user_id INT PRIMARY KEY AUTO_INCREMENT,
--     first_name VARCHAR(50) NOT NULL,
--     last_name VARCHAR(50) NOT NULL,
--     email VARCHAR(100) UNIQUE NOT NULL ,
--     password VARCHAR(255) NOT NULL,
--     active BOOLEAN NOT NULL,
--     date_joined DATETIME NOT NULL ,
--     type VARCHAR(20), -- can also use INT if there are only a few types and enum
--     profile_image_url VARCHAR(255) DEFAULT 'default.jpg', -- later we use the actual image url for default profile image
--     email_verified BOOLEAN NOT NULL DEFAULT FALSE,
--     verification_token VARCHAR(255),
--     token_expiry DATETIME
-- );


-- INSERT INTO user (first_name, last_name, email, password, active, date_joined, type, email_verified, verification_token, token_expiry)
-- VALUES
-- ('admin', 'admin', 'admin@admin.com', '123', true, '2022-01-01 12:00:00', 'admin', true, 'token', '2023-02-06 12:15:00'),
-- ('Jane', 'Doe', 'jane@example.com', '123', true, '2022-01-02 14:30:00', 'user', true, 'token', '2023-02-06 12:15:00'),
-- ('Alice', 'Smith', 'alice@example.com', '123', true, '2022-01-03 10:15:00', 'user', false, 'token', '2023-02-06 12:15:00');

-- -- 创建 message_database 数据库
-- CREATE DATABASE IF NOT EXISTS message_database;

-- -- 使用 message_database 数据库
-- USE message_database;

-- -- 创建 message 表
-- CREATE TABLE IF NOT EXISTS message (
--     message_id INT PRIMARY KEY AUTO_INCREMENT,
--     user_id INT NOT NULL ,
--     email VARCHAR(100) NOT NULL ,
--     message_text TEXT NOT NULL ,
--     date_created DATETIME NOT NULL ,
--     status VARCHAR(20),
--     subject      varchar(50)  not null
-- );
-- create index user_id
--     on message (user_id);

-- -- message 表插入假数据
-- INSERT INTO message (user_id, email, message_text, date_created, status, subject)
-- VALUES
-- (1, 'john@example.com', 'Hello, this is a message from John.', '2022-01-04 09:45:00', 'Open','m1'),
-- (2, 'jane@example.com', 'Hi there! This is Jane.', '2022-01-05 11:00:00', 'Open','m2'),
-- (3, 'alice@example.com', 'Hi there, This is Alice', '2022-01-06 13:20:00', 'Close','m3');

-- -- 创建 history_database 数据库
-- CREATE DATABASE IF NOT EXISTS history_database;

-- -- 使用 history_database 数据库
-- USE history_database;

-- -- 创建 history 表
-- CREATE TABLE IF NOT EXISTS history (
--     user_id   int         not null,
--     post_id   varchar(24) not null,
--     view_date datetime    null,
--     primary key (post_id, user_id)
-- );

-- -- history 表插入假数据
-- INSERT INTO history (user_id, post_id, view_date)
-- VALUES
-- (1, 1, '2022-01-07 15:30:00'),
-- (2, 2, '2022-01-08 16:45:00'),
-- (3, 3, '2022-01-09 18:00:00');

