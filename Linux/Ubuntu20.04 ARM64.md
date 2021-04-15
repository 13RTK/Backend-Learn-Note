# Mirror Source

```
deb https://mirror.tuna.tsinghua.edu.cn/ubuntu-ports/ focal main restricted universe multiverse
deb https://mirror.tuna.tsinghua.edu.cn/ubuntu-ports/ focal-updates main restricted universe multiverse
deb https://mirror.tuna.tsinghua.edu.cn/ubuntu-ports/ focal-backports main restricted universe multiverse
deb https://mirror.tuna.tsinghua.edu.cn/ubuntu-ports/ focal-security main restricted universe multiverse
deb https://mirror.tuna.tsinghua.edu.cn/ubuntu-ports/ focal-proposed main restricted universe multiverse
```











# SSH



## 1. install openssh

```shell
sudo apt install openssh-server
```







## 2. manage ssh





Check status:

```shell
systemctl status ssh
```





Start/stop/restart:

```shell
systemctl start ssh

systemctl stop ssh

systemctl restart ssh
```





Enable/disable start by starting up:

```shell
sudo systemctl enable ssh

sudo systemctl disable ssh
```









