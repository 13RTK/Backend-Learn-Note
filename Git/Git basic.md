# 一、配置

安装完成后，需要设置对应的用户名和邮箱:

- `--globle`表示全局设置

```shell
git config --globle user.name "username"
git config --globle user.email "user email"
```



设置完成后，可以通过命令查询全局设置:

```shell
git config --globle -l
```



Eg:

![Xnip2022-06-13_18-06-14](Git.assets/Xnip2022-06-13_18-06-14.jpg)

<hr>









# 二、添加、提交和回滚

Git的仓库组成:

![Xnip2022-06-13_18-52-37](Git.assets/Xnip2022-06-13_18-52-37.jpg)





## 1. 初始化

首先需要将当前目录初始化为git目录:

```shell
git init
```



Eg:

![Xnip2022-06-13_20-42-45](Git.assets/Xnip2022-06-13_20-42-45.jpg)





通过status命令可以查看仓库的状态:

```shell
git status
```

Eg:

![Xnip2022-06-13_20-46-43](Git.assets/Xnip2022-06-13_20-46-43.jpg)

<hr>











## 2. 添加

新创建的文件不会被跟踪，需要手动添加到暂存区(stage)中才行:

```shell
git add directory
```

Eg:

![Xnip2022-06-13_20-47-45](Git.assets/Xnip2022-06-13_20-47-45.jpg)







最后只需要使用`commit -m`即可将暂存区中的文件提交到本地仓库(local repo):

```shell
git commit -m 'comment content'
```



Eg:

![Xnip2022-06-13_20-52-26](Git.assets/Xnip2022-06-13_20-52-26.jpg)





通过`log`命令可以查看提交的记录:

```shell
git log
```

Eg:

![Xnip2022-06-13_20-54-04](Git.assets/Xnip2022-06-13_20-54-04.jpg)



还可以添加`--oneline`选项使得提交记录只展示在一行中，通过`--all`选项还可以展示所有分支的提交情况:

```shell
git log --oneline --all
```

通过`--graph`选项还可以展示提交的逻辑关系

Eg:

![Xnip2022-06-13_20-55-30](Git.assets/Xnip2022-06-13_20-55-30.jpg)







如果修改了既存的文件，那么只需要使用`commit -a`即可提交所有修改的文件(新创建的文件还是需要先使用`add`添加到暂存区才行)

```shell
git commit -a -m 'comment content'
```



Eg:

![Xnip2022-06-13_21-02-34](Git.assets/Xnip2022-06-13_21-02-34.jpg)

<hr>









## 3. 忽略目录

通过在当前仓库下创建一个`.gitignore`文件，可以指定git的文件管理规则:

Eg:

![Xnip2022-06-13_21-11-23](Git.assets/Xnip2022-06-13_21-11-23.jpg)

- 在其中直接输入对应的文件名可以让git忽略对应的文件
- 通过`*.类型`可以匹配所有该类型的文件
- 通过`!`可以取反(即在排除对应类型文件的同时，又加入部分文件)
- 指定目录:`directory/`
- 指定目录中所有对应类型的文件: `directory/*.type`(不含子目录)
- 指定目录中对应类型的文件，包含子目录: 

```shell
directory/**/*.type
```

<hr>













## 4. 回滚

通过`show`命令可以查看指定id对应修改的详细信息:

```shell
git show id
```

- 如果没有指定id，则默认查看当前状态的修改

Eg:

![Xnip2022-06-13_21-06-06](Git.assets/Xnip2022-06-13_21-06-06.jpg)





通过`reset`命令并添加`--hard`参数即可回滚到对应提交id的版本

syntax:

```shell
git reset --hard commitId
```

Eg:

![Xnip2022-06-13_21-21-59](Git.assets/Xnip2022-06-13_21-21-59.jpg)



回滚的同时，git的log记录也清除了

![Xnip2022-06-13_21-22-19](Git.assets/Xnip2022-06-13_21-22-19.jpg)





如果想要撤销回滚操作(此时无法通过git log获取对应的提交id)，可以使用`git reflog`命令获取所有的版本对应的id(该记录一直保存):

```shell
git reflog
```



Eg:

![Xnip2022-06-13_21-24-56](Git.assets/Xnip2022-06-13_21-24-56.jpg)





此时再使用reset回滚即可:

![Xnip2022-06-13_21-27-15](Git.assets/Xnip2022-06-13_21-27-15.jpg)

<hr>








# 三、分支创建与合并





## 1. 创建与查看



查看当前分支:

```shell
git branch
```

Eg:

![Xnip2022-06-16_18-31-13](Git.assets/Xnip2022-06-16_18-31-13.jpg)



查看所有分支:

```shell
git branch -a
```







创建分支:

```shell
git branch branch_name
```

Eg:

![Xnip2022-06-16_18-33-43](Git.assets/Xnip2022-06-16_18-33-43.jpg)



![Xnip2022-06-16_18-36-59](Git.assets/Xnip2022-06-16_18-36-59.jpg)

<hr>











## 2. 切换与合并

切换分支:

```shell
git checkout branch_name
```



Eg:

![Xnip2022-06-16_18-38-40](Git.assets/Xnip2022-06-16_18-38-40.jpg)







合并分支:

```shell
git merge target_branch
```

- 会将目标分支合并到当前分支中

Eg:

![Xnip2022-06-16_18-42-37](Git.assets/Xnip2022-06-16_18-42-37.jpg)



![Xnip2022-06-16_18-43-31](Git.assets/Xnip2022-06-16_18-43-31.jpg)







删除分支:

```shell
// 普通删除(如果该分支没有被合并则删除失败)
git branch branch_name -d

// 强制删除
git branch branch_name -D
```

<hr>









# 四、变基与优选



## 1. 变基

变基即改变某个分支的根结点，通过`rebase`命令可以重新指定当前分支的根结点为目标分支的根结点:

```shell
git rebase new_root_branch
```



Eg:

![Xnip2022-06-16_18-48-01](Git.assets/Xnip2022-06-16_18-48-01.jpg)

<hr>









## 2. 优选

之前的操作都是修改整个分支间的操作，如果只需要其中的一个结点呢?

优选操作可以只选择其中的一个结点作为下一个新位置:

```shell
git cherry-pick (id)
```



Eg:

![Xnip2022-06-16_18-58-23](Git.assets/Xnip2022-06-16_18-58-23.jpg)



![Xnip2022-06-16_18-58-45](Git.assets/Xnip2022-06-16_18-58-45.jpg)

<hr>













# 五、IDEA中的Git





![Xnip2022-06-18_20-44-19](Git.assets/Xnip2022-06-18_20-44-19.jpg)



![Xnip2022-06-18_20-46-48](Git.assets/Xnip2022-06-18_20-46-48.jpg)

- 左边的文件目录中，标红的为未跟踪的文件(untracked，需要使用git add)，褐色的文件为.gitignore中设置的忽略文件
- 左下角中带有标签的分支为当前分支





创建新分支:

![Xnip2022-06-18_20-49-50](Git.assets/Xnip2022-06-18_20-49-50.jpg)





切换分支:

![Xnip2022-06-18_20-50-36](Git.assets/Xnip2022-06-18_20-50-36.jpg)





回滚到对应分支:

![Xnip2022-06-18_20-55-48](Git.assets/Xnip2022-06-18_20-55-48.jpg)



![Xnip2022-06-18_20-58-11](Git.assets/Xnip2022-06-18_20-58-11.jpg)







分支的变基和合并:

![Xnip2022-06-18_20-56-50](Git.assets/Xnip2022-06-18_20-56-50.jpg)





分支优选:

![Xnip2022-06-18_20-59-34](Git.assets/Xnip2022-06-18_20-59-34.jpg)

<hr>













# 六、远程仓库





## 1. 设置和推送

在github、gitee等平台创建好对应的远程仓库后，为本地仓库添加远程仓库:

```shell
git remote add remote_repo_name remote_repo_url
```



Eg:

![Xnip2022-06-18_21-07-54](Git.assets/Xnip2022-06-18_21-07-54.jpg)





将本地分支的内容推送到远程分支:

```shell
git push remote_repo local_repo:[remote_branch]
```

在推送的时候可以设置远程分支，如果不设置，那么就会默认在远程仓库中创建一个与当前分支同名的远程分支





![Xnip2022-06-18_21-09-44](Git.assets/Xnip2022-06-18_21-09-44.jpg)



![Xnip2022-06-18_21-10-16](Git.assets/Xnip2022-06-18_21-10-16.jpg)

<hr>









## 2. 克隆与协作

克隆命令:

```shell
git clone remote_repo_url
```





添加仓库的协作者:

![Xnip2022-06-18_21-20-36](Git.assets/Xnip2022-06-18_21-20-36.jpg)

<hr>











## 3. 抓取、拉取和冲突解决

当其中一个人先推送了修改导致远程仓库发生变动，但另一个人不知道的时候

此时另一个人进行推送就会被拒绝:

![Xnip2022-06-18_21-40-53](Git.assets/Xnip2022-06-18_21-40-53.jpg)



![Xnip2022-06-18_21-39-40](Git.assets/Xnip2022-06-18_21-39-40.jpg)







此时需要使用`fetch`命令抓取远程仓库的修改，修改后再进行合并:

```shell
git fetch remote_repo

git merge
```



通过`pull`命令可以直接进行抓取和合并操作:

```shell
git pull remote_repo
```

<hr>













## 4. IDEA设置

![Xnip2022-06-18_21-49-23](Git.assets/Xnip2022-06-18_21-49-23.jpg)



![Xnip2022-06-18_21-51-27](Git.assets/Xnip2022-06-18_21-51-27.jpg)



![Xnip2022-06-18_21-53-45](Git.assets/Xnip2022-06-18_21-53-45.jpg)





在github上修改远程仓库的内容:

![Xnip2022-06-18_21-54-17](Git.assets/Xnip2022-06-18_21-54-17.jpg)



![Xnip2022-06-18_21-55-06](Git.assets/Xnip2022-06-18_21-55-06.jpg)





根据URL或者账号里的仓库直接下载远程仓库里的项目文件:

![Xnip2022-06-18_22-06-59](Git.assets/Xnip2022-06-18_22-06-59.jpg)









