CREATE DATABASE auth_db;
CREATE USER 'auth_user'@'%' IDENTIFIED BY 's3cr3t_auth';
GRANT ALL PRIVILEGES ON auth_db.* TO 'auth_user'@'%';
FLUSH PRIVILEGES;

CREATE DATABASE notification_db;
CREATE USER 'notification_user'@'%' IDENTIFIED BY 's3cr3t_notification';
GRANT ALL PRIVILEGES ON notification_db.* TO 'notification_user'@'%';
FLUSH PRIVILEGES;

CREATE DATABASE order_db;
CREATE USER 'order_user'@'%' IDENTIFIED BY 's3cr3t_order';
GRANT ALL PRIVILEGES ON order_db.* TO 'order_user'@'%';
FLUSH PRIVILEGES;

CREATE DATABASE payment_db;
CREATE USER 'payment_user'@'%' IDENTIFIED BY 's3cr3t_payment';
GRANT ALL PRIVILEGES ON payment_db.* TO 'payment_user'@'%';
FLUSH PRIVILEGES;
