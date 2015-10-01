create user potato with password 'potato';
create database potato_db with owner potato;
\connect potato_db;
CREATE SCHEMA potato_schema;
GRANT ALL PRIVILEGES ON SCHEMA potato_schema TO potato;
GRANT ALL PRIVILEGES ON DATABASE potato_db TO potato;
