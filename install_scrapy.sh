#!/bin/bash


# ��������
sudo apt-get update & dist-upgrade

# ��װ��Ҫ��ϵͳ���
sudo apt-get install python-dev python-pip ssh git

# ��pip���µ����°�
sudo python -m pip install --upgrade pip

# ��װpython��������
sudo pip install flask requests redis scrapy scrapyd scrapylib scrapy-redis pybloom beautifulsoup4 xlwt

# ��װ���Ǻܱ�������
sudo pip install virtualenv virtualenvwrapper django uwsgi selenium sqlalchemy pymysql fake-useragent 
sudo apt-get install nginx mongodb-server mongodb-client pymongo scrapy-mongodb

# ����ȫ���������⵽���°汾
pip list --outdated | grep '^[a-z]* (' | cut -d " " -f 1 | xargs pip install -U

# �������е������⵽requirements.txt
pip freeze > requirements.txt

