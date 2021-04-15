# 1 Reset default password



Need stop MySQL service first.

```shell
sudo /usr/local/mysql/bin/mysqld_safe --skip-grant-tables

sudo /usr/local/mysql/bin/mysql -u root
UPDATE mysql.user SET authentication_string=PASSWORD('Your new password') WHERE User='root';
FLUSH PRIVILEGES;
exit
```





# 2 Uninstall MySQL

```shell
sudo rm /usr/local/mysql
sudo rm -rf /usr/local/mysql*
sudo rm -rf /Library/StartupItems/MySQLCOM
sudo rm -rf /Library/PreferencePanes/My*

rm -rf ~/Library/PreferencePanes/My*
sudo rm -rf /Library/Receipts/mysql*
sudo rm -rf /Library/Receipts/MySQL*
sudo rm -rf /var/db/receipts/com.mysql.*
```











# 3 ShortCut



## 1.Clear Screen



```
option + command + L
```

