# -*- coding: utf-8 -*-

import requests
import re
from pprint import pprint

# 禁用安全请求警告
from requests.packages.urllib3.exceptions import InsecureRequestWarning
requests.packages.urllib3.disable_warnings(InsecureRequestWarning)

url='https://kyfw.12306.cn/otn/resources/js/framework/station_name.js?station_version=1.9002'
response=requests.get(url,verify=False)
stations=re.findall(u'([\u4e00-\u9fa5]+)\|([A-Z]+)',response.text)
#pprint(dict(stations),indent=4)
pprint(stations)