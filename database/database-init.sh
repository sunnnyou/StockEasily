#!/bin/bash

# Create database
mysql -u root -p -e "CREATE DATABASE stockeasily;"

# Create user and grant privileges
mysql -u root -p -e "CREATE USER 'stockeasily'@'localhost' IDENTIFIED BY 'develop';
GRANT ALL PRIVILEGES ON stockeasily.* TO 'stockeasily'@'localhost';"

# Create tables
mysql -u root -p stockeasily < db_structure.sql