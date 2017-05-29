# -*- coding: utf-8 -*-

import requests
import re
from pprint import pprint
import json
import datetime

# 禁用安全请求警告
from requests.packages.urllib3.exceptions import InsecureRequestWarning
requests.packages.urllib3.disable_warnings(InsecureRequestWarning)
now = datetime.datetime.now();
url='https://kyfw.12306.cn/otn/resources/js/query/train_list.js?scriptVersion=1.0';
response=requests.get(url,verify=False);
txt=response.text[16:];
file_object = open('train_list_'+now.strftime('%Y%m%d%H')+'.json', 'w');
file_object.write(txt);
file_object.close();


# 解析json数据