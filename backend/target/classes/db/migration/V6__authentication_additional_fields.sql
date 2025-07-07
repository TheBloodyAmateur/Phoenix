-- Drop existing tables if they exist
ALTER TABLE users
ADD firstname VARCHAR(255);

ALTER TABLE users
ADD lastname VARCHAR(255);