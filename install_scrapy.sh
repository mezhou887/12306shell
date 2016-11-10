#!/bin/bash

# 升级环境
apt-get update & apt-get dist-upgrade

# 安装需要的系统软件
apt-get install python-dev python-pip ssh git

# 将pip更新到最新版
python -m pip install --upgrade pip

# 安装python必须的软件
pip install pybloom beautifulsoup4 xlwt lxml requests redis scrapy scrapyd scrapy-redis selenium sqlalchemy uwsgi fake-useragent pymysql Flask

# 安装不是很必须的软件
apt-get install mysql-server mongodb-server python-virtualenv

# 更新全部第三方库到最新版本
pip list --outdated | grep '^[a-z]* (' | cut -d " " -f 1 | xargs pip install -U

# 导出所有第三方库到requirements.txt
pip freeze > requirements.txt
