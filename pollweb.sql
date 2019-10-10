DROP SCHEMA IF EXISTS pollwebWithHibernate;
CREATE SCHEMA pollwebWithHibernate;
CREATE USER IF NOT EXISTS 'pollweb'@'localhost' IDENTIFIED BY 'pollweb';
GRANT ALL ON pollwebWithHibernate.* TO pollweb@localhost;