#!/bin/bash

today=`date +%Y%m%d`
date=`date -d "-7 days ago" +%Y-%m-%d`
today2=`date +%Y%m%d%H`
cd /home/mezhou887/Desktop/
rm -rf ${today} 
mkdir ${today}
cd ${today}

echo 'start: '`date` >> /home/mezhou887/Desktop/12306/${today}.log

# 生成所有车站编码表和主要车站编码表: all_station.csv station_top.csv
result=$(curl -k 'https://kyfw.12306.cn/otn/resources/js/framework/station_name.js?station_version=1.8970')
echo ${result} | cut -d "'" -f 2 | tr "@" "\n" | tr "|" "," | sed -e '/^$/d' > all_station.csv

# 随机取数据用下面的
# sort -R all_station.csv | head -60 > station_top.csv

# 按顺序取用下面的
# head -5 all_station.csv > station_top.csv

# 取前300名车站中的任意50名数据
head -300 all_station.csv | sort -R | head -50 > station_top.csv

sleep 10

# 生成车站之间的关系请求表: station_relation_request.csv
# 在station_request目录下存放所有请求的结果
# 用python处理station_request目录生成station_relation_info.csv
# 清除掉不需要的station_request目录
echo 'start parse station relation : '`date` >> /home/mezhou887/Desktop/12306/${today}.log
mkdir station_request
cat station_top.csv |while read from_line
do
  cat station_top.csv |while read to_line
 	do
		from_station=$(echo ${from_line}| cut -d "," -f 3) 
		to_station=$(echo ${to_line} | cut -d , -f 3)
		file_name=${from_station}${to_station}${date}'.json'
		echo " -k 'https://kyfw.12306.cn/otn/leftTicket/queryX?leftTicketDTO.train_date=${date}&leftTicketDTO.from_station=${from_station}&leftTicketDTO.to_station=${to_station}&purpose_codes=ADULT' -o 'station_request/${from_station}${to_station}${date}.json'" >>  station_relation_request.csv
	done
done
cat station_relation_request.csv | xargs -r -L 1 -P 64 curl
python '/home/mezhou887/Product/12306_handler.py' 'station' 'station_request'  'station_relation_info.csv'
cat station_relation_request.csv | wc >> /home/mezhou887/Desktop/12306/${today}.log
echo 'end parse station relation : '`date` >> /home/mezhou887/Desktop/12306/${today}.log

sleep 100

# 在train_request目录下存放所有的请求
# 将请求排序后去重生成all_train_info.csv
# 用python处理train_request目录生成train_info.csv
echo 'start parse train request : '`date` >> /home/mezhou887/Desktop/12306/${today}.log
mkdir train_request
cat station_relation_info.csv | while read line
do
    train_no=$(echo ${line} | cut -d "," -f 1)
    start_station=$(echo ${line} | cut -d "," -f 3)
  	end_station=$(echo ${line} | cut -d "," -f 5)
    echo " -k 'https://kyfw.12306.cn/otn/czxx/queryByTrainNo?train_no=${train_no}&from_station_telecode=${start_station}&to_station_telecode=${end_station}&depart_date=${date}' -o 'train_request/${train_no}-${start_station}-${end_station}-${date}.json'" >>  all_train_info_tmp.csv
done
sort all_train_info_tmp.csv | uniq > all_train_info.csv
cat all_train_info.csv | wc >> /home/mezhou887/Desktop/12306/${today}.log
cat all_train_info.csv | xargs -r -L 1 -P 64 curl
python '/home/mezhou887/Product/12306_handler.py' 'train' 'train_request'  'train_info.csv'
echo 'end parse train request : '`date` >> /home/mezhou887/Desktop/12306/${today}.log

sleep 100

# 爬取票价部分
echo 'start parse price request : '`date` >> /home/mezhou887/Desktop/12306/${today}.log
mkdir price_request
cat station_relation_info.csv | while read line
do
    train_no=$(echo ${line} | cut -d "," -f 1)
    seat_types=$(echo ${line} | cut -d "," -f 24)
    from_station_no=01 # $(echo ${line} | cut -d "," -f 26)
    to_station_no=$(echo ${line} | cut -d "," -f 27)
    echo " -k 'https://kyfw.12306.cn/otn/leftTicket/queryTicketPrice?train_no=${train_no}&from_station_no=${from_station_no}&to_station_no=${to_station_no}&seat_types=${seat_types}&train_date=${date}' -o 'price_request/${train_no}-${from_station_no}-${to_station_no}-${seat_types}.json'" >>all_price_info_tmp.csv
done
sort all_price_info_tmp.csv | uniq > all_price_info.csv
cat all_price_info.csv | wc >> /home/mezhou887/Desktop/12306/${today}.log
cat all_price_info.csv | xargs -r -L 1 -P 64 curl
python '/home/mezhou887/Product/12306_handler.py' 'price' 'price_request'  'price_info_tmp.csv'
sort price_info_tmp.csv | uniq > price_info.csv
echo 'end parse price request : '`date` >> /home/mezhou887/Desktop/12306/${today}.log

sleep 10

# 打包压缩文件
cd /home/mezhou887/Desktop
python '/home/mezhou887/Product/12306_handler.py' 'zip' ${today} '12306/'${today2}'.zip'

# 同步到百度云
# cd 12306
# bypy.py upload

echo 'end: '`date` >> /home/mezhou887/Desktop/12306/${today}.log
