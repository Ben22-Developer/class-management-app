CREATE USER IF NOT EXISTS 'teacherUser'@'localHost' IDENTIFIED BY '12345';

GRANT INSERT, SELECT, UPDATE, DELETE ON api_key_confirm TO 'teacherUser'@'localHost'; 

GRANT INSERT, SELECT, DELETE ON archive_schedule TO 'teacherUser'@'localHost'; 

GRANT INSERT, SELECT, UPDATE, DELETE ON paid_api_key_confirm TO 'teacherUser'@'localHost'; 

GRANT INSERT, SELECT, UPDATE, DELETE ON paid_token_cash_amount TO 'teacherUser'@'localHost'; 

GRANT INSERT, SELECT, UPDATE, DELETE ON paid_token_validitiy TO 'teacherUser'@'localHost'; 

GRANT INSERT, SELECT, UPDATE, DELETE ON role TO 'teacherUser'@'localHost'; 

GRANT INSERT, SELECT, UPDATE, DELETE ON token_type TO 'teacherUser'@'localHost';

GRANT INSERT, SELECT, UPDATE, DELETE ON user TO 'teacherUser'@'localHost';

GRANT INSERT, SELECT, UPDATE, DELETE ON user_role TO 'teacherUser'@'localHost';

GRANT INSERT, SELECT, UPDATE, DELETE ON user_schedule TO 'teacherUser'@'localHost';

GRANT INSERT, SELECT, UPDATE, DELETE ON user_token_amount TO 'teacherUser'@'localHost';

GRANT INSERT, SELECT, UPDATE, DELETE ON user_token_type TO 'teacherUser'@'localHost'; 

GRANT INSERT, SELECT, UPDATE, DELETE ON password_reset_code TO 'teacherUser'@'localHost'; 

GRANT CREATE, ALTER, INSERT, SELECT, UPDATE, DELETE ON SPRING_AI_CHAT_MEMORY TO 'teacherUser'@'localHost';

GRANT INSERT, SELECT, UPDATE, DELETE ON chat_rating TO 'teacherUser'@'localHost';