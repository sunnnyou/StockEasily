# Database

A MariaDB server is required to run this software system.

## Setup

Install and start MariaDB server.  
Do the following steps:  

### Log in as privileged (`root`) user via CLI (or do these steps with a GUI instead)   

```shell
mysql -u root -p<password>
```

On Linux you can start the command as `sudo` user to use your user password.

### Create Database

```mariadb
CREATE DATABASE stockeasily;
```

### Create user

```mariadb
CREATE USER 'stockeasily'@'localhost' IDENTIFIED BY 'develop'; # 'develop' is password
```

### Grant database permissions

```mariadb
GRANT ALL PRIVILEGES ON stockeasily.* TO 'stockeasily'@'localhost'; # 'develop' is password
```

### Flush privileges
Many guides suggest running the `FLUSH PRIVILEGES` command immediately
after a CREATE USER or GRANT statement in order to reload the grant tables
to ensure that the new privileges are put into effect:

```mariadb
FLUSH PRIVILEGES;
```

### Exit MariaDB Shell

```shell
exit
```

### Import Data

Make sure to specify the repo path.  
Or change to `repo/sql` directory with the terminal (via `cd`).

#### Windows (PowerShell or CMD)

```powershell
cmd.exe /c "mysql -u stockeasily -p develop stockeasily < database/dump.sql"
```
*Note:* Try `-pdevelop` instead, if there are problems.

#### Linux/Unix

```shell
mysql -u stockeasily -pdevelop stockeasily < ./database/dump.sql
```
