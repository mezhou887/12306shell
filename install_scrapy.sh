#!/bin/bash


# 升级环境
sudo apt-get update & apt-get dist-upgrade

# 安装需要的系统软件
sudo apt-get install python-dev python-pip ssh git

# 将pip更新到最新版
sudo python -m pip install --upgrade pip

# 安装python必须的软件
sudo pip install flask requests redis scrapy scrapyd scrapylib scrapy-redis pybloom beautifulsoup4 xlwt

# 安装不是很必须的软件
sudo pip install virtualenv virtualenvwrapper django uwsgi selenium sqlalchemy pymysql fake-useragent 
sudo apt-get install nginx mongodb-server mongodb-client pymongo scrapy-mongodb

# 更新全部第三方库到最新版本
sudo pip list --outdated | grep '^[a-z]* (' | cut -d " " -f 1 | xargs pip install -U

# 导出所有第三方库到requirements.txt
sudo pip freeze > requirements.txt

