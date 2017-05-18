#!/bin/bash
#
# 随机爬取50个车站之间的车次关系
#

today=`date +%Y%m%d`
todaytime=`date +%Y%m%d%H%M`
rundate=`date -d "-7 days ago" +%Y-%m-%d`
workspace="/home/mezhou887/Desktop/station_random"

# 准备工作目录
if [ ! -d "$workspace" ]; then
 mkdir "$workspace"
else
 rm -rf "$workspace"/*
fi  

cd /home/mezhou887/Desktop/station_random/

# 生成全部车站编码表
curl -k 'https://kyfw.12306.cn/otn/resources/js/framework/station_name.js?station_version=1.8970' | cut -d "'" -f 2 | tr "@" "\n" | tr "|" "," | sed -e '/^$/d' > all_stations.csv
sleep 20

# 随机生成50个车站
sort -R all_stations.csv | head -20 > stations_random.csv

# 生成车站数组
cat stations_random.csv | awk -F, '{array[$3]++} END {for(from_station in array) for(to_station in array) {print from_station, to_station}}' > stations_array.csv

# 生成车站之间的请求关系
cat stations_array.csv | awk '{print " -k https://kyfw.12306.cn/otn/leftTicket/query?leftTicketDTO.train_date=rundate&leftTicketDTO.from_station="$1"&leftTicketDTO.to_station="$2"&purpose_codes=ADULT -o  "$1"_"$2"_rundate.json"}' | sed "s/https/'https/g" | sed "s/ADULT/ADULT'/g" | sed "s/rundate/${rundate}/g" > stations_relation_request.csv
sleep 20

# 执行curl命令生成文件在station_request目录下
cat stations_relation_request.csv | xargs -r -L 1 -P 16 curl

# 删除掉不需要的文件(小于1K的)
find $workspace -size 1k -exec rm {} \;

# 解析json格式数据生成csv文件
find $workspace -name "*.json" | xargs jq -r '.data.result|.[]' | tr "|" "," >> /home/mezhou887/Desktop/station_${todaytime}.csv

# 清理不用的数据
#rm -rf "$workspace"/*

