mysql_shell
===========
```shell script

cd /opt

wget https://dev.mysql.com/get/mysql80-community-release-el7-1.noarch.rpm

yum localinstall mysql80-community-release-el7-1.noarch.rpm

yum clean all

yum makecache

yum install mysql-community-server

systemctl start mysqld

password = sed -n '/temporary password is generated for root@localhost:/p' /var/log/mysqld.log | awk -F: ' {print $NF}'

#使用 expect 自动化脚本 不然无法使用

mysql -u root -p $password 

ALTER USER 'root'@'localhost' IDENTIFIED BY 'root';

set global validate_password.check_user_name = off;

set global validate_password.policy = 0;

set global validate_password.length = 4;

set global validate_password.mixed_case_count = 0;

set global validate_password_special_char_count = 0;

set global .special_char_count = 0;

create user 'tianling'@'%';

ALTER USER 'tianling'@'%' IDENTIFIED BY '5231';

grant all privileges on *.* to 'tianling'@'%';

show variables like '%validate_password%';
