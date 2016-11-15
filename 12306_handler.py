#!/usr/bin/env python
# -*- coding: utf-8 -*-
import os.path
import zipfile
import sys
import csv
import json
import datetime

now = datetime.datetime.now();
 
# 将dirname这个目录下的全部文件压缩成zipfilename这个文件 
def zip_dir(dirname, zipfilename):
    filelist = []
    if os.path.isfile(dirname):
        filelist.append(dirname)
    else :
        for root, dirs, files in os.walk(dirname):
            for name in files:
                filelist.append(os.path.join(root, name))
         
    zf = zipfile.ZipFile(zipfilename, "w", zipfile.zlib.DEFLATED)
    for tar in filelist:
        arcname = tar[len(dirname):]
        zf.write(tar,arcname)
    zf.close()
 
# 解析stationsdir目录中的所有文件，输出到output这个文件中去  
def parse_station(stationsdir, outfile):
    csvfile = file(outfile, 'wb')
    writer = csv.writer(csvfile)
    for parent,dirnames,filenames in os.walk(stationsdir):
        for filename in filenames:
            size = os.path.getsize(os.path.join(parent,filename));
            if size > 1025: 
                with open(os.path.join(parent,filename), 'r') as f:
                    data = json.load(f);
                    jsonArray = data["data"];
                    for index, leftNewDTO in enumerate(jsonArray):
                        train_no = leftNewDTO["queryLeftNewDTO"]["train_no"].encode("utf-8") 
                        station_train_code = leftNewDTO["queryLeftNewDTO"]["station_train_code"].encode("utf-8")
                        start_station_telecode = leftNewDTO["queryLeftNewDTO"]["start_station_telecode"].encode("utf-8")
                        start_station_name = leftNewDTO["queryLeftNewDTO"]["start_station_name"].encode("utf-8")
                        end_station_telecode = leftNewDTO["queryLeftNewDTO"]["end_station_telecode"].encode("utf-8")

                        end_station_name = leftNewDTO["queryLeftNewDTO"]["end_station_name"].encode("utf-8")
                        from_station_telecode = leftNewDTO["queryLeftNewDTO"]["from_station_telecode"].encode("utf-8") 
                        from_station_name = leftNewDTO["queryLeftNewDTO"]["from_station_name"].encode("utf-8")
                        to_station_telecode = leftNewDTO["queryLeftNewDTO"]["to_station_telecode"].encode("utf-8")
                        to_station_name = leftNewDTO["queryLeftNewDTO"]["to_station_name"].encode("utf-8")
                        
                        start_time = leftNewDTO["queryLeftNewDTO"]["start_time"].encode("utf-8")
                        arrive_time = leftNewDTO["queryLeftNewDTO"]["arrive_time"].encode("utf-8")
                        day_difference = leftNewDTO["queryLeftNewDTO"]["day_difference"].encode("utf-8") 
                        train_class_name = leftNewDTO["queryLeftNewDTO"]["train_class_name"].encode("utf-8") 
                        lishi = leftNewDTO["queryLeftNewDTO"]["lishi"].encode("utf-8")
                        
                        canWebBuy = leftNewDTO["queryLeftNewDTO"]["canWebBuy"].encode("utf-8")
                        lishiValue = leftNewDTO["queryLeftNewDTO"]["lishiValue"].encode("utf-8") 
                        yp_info = leftNewDTO["queryLeftNewDTO"]["yp_info"].encode("utf-8")
                        control_train_day = leftNewDTO["queryLeftNewDTO"].get("control_train_day") 
                        start_train_date = leftNewDTO["queryLeftNewDTO"]["start_train_date"].encode("utf-8")
                        
                        seat_feature = leftNewDTO["queryLeftNewDTO"].get("seat_feature")
                        yp_ex = leftNewDTO["queryLeftNewDTO"]["yp_ex"].encode("utf-8")
                        train_seat_feature = leftNewDTO["queryLeftNewDTO"].get("train_seat_feature")
                        seat_types = leftNewDTO["queryLeftNewDTO"]["seat_types"].encode("utf-8")
                        location_code = leftNewDTO["queryLeftNewDTO"]["location_code"].encode("utf-8") 
                        
                        from_station_no = leftNewDTO["queryLeftNewDTO"]["from_station_no"].encode("utf-8") 
                        to_station_no = leftNewDTO["queryLeftNewDTO"]["to_station_no"].encode("utf-8")
                        control_day = leftNewDTO["queryLeftNewDTO"].get("control_day") 
                        sale_time =  leftNewDTO["queryLeftNewDTO"].get("sale_time")
                        is_support_card = leftNewDTO["queryLeftNewDTO"]["is_support_card"].encode("utf-8")
                        controlled_train_flag = leftNewDTO["queryLeftNewDTO"]["controlled_train_flag"].encode("utf-8")
                        controlled_train_message = leftNewDTO["queryLeftNewDTO"]["controlled_train_message"].encode("utf-8")
                          
                        
                        row = [train_no, station_train_code, start_station_telecode, start_station_name, end_station_telecode, 
                               end_station_name, from_station_telecode, from_station_name, to_station_telecode, to_station_name,
                               start_time, arrive_time, day_difference, train_class_name, lishi,
                               canWebBuy, lishiValue, yp_info, control_train_day, start_train_date,
                               seat_feature, yp_ex, train_seat_feature, seat_types, location_code,
                               from_station_no, to_station_no, control_day, sale_time, is_support_card, controlled_train_flag, controlled_train_message, now.strftime('%Y%m%d%H')]
                        writer.writerow(row)
    csvfile.close()

def parse_train(trainsdir, outfile):
    csvfile = file(outfile, 'wb')
    writer = csv.writer(csvfile)
    for parent,dirnames,filenames in os.walk(trainsdir):
        for filename in filenames:
            size = os.path.getsize(os.path.join(parent,filename));
            if size > 100: 
                with open(os.path.join(parent,filename), 'r') as f:
                    data = json.load(f);
                    jsonArray = data["data"]["data"];
                    for index, train_data in enumerate(jsonArray):
                        train_no = filename[:-5].split("-")[0]

                        start_station_name = train_data.get("start_station_name")
                        station_train_code = train_data.get("station_train_code")
                        train_class_name = train_data.get("train_class_name")
                        service_type = train_data.get("service_type")
                        end_station_name = train_data.get("end_station_name")
                        
                        arrive_time = train_data["arrive_time"]
                        station_name = train_data["station_name"].encode("utf-8")
                        start_time = train_data["start_time"]
                        stopover_time = train_data["stopover_time"].encode("utf-8")
                        station_no = train_data["station_no"]
                        isEnabled = train_data["isEnabled"]

                        row = [train_no, station_train_code, service_type, arrive_time, station_name, start_time, stopover_time, station_no, isEnabled, now.strftime('%Y%m%d%H')] 
                        writer.writerow(row)
    csvfile.close()

def parse_price(trainsdir, outfile):
    csvfile = file(outfile, 'wb')
    writer = csv.writer(csvfile)
    for parent,dirnames,filenames in os.walk(trainsdir):
        for filename in filenames:
            size = os.path.getsize(os.path.join(parent,filename));
            if size > 100: 
                with open(os.path.join(parent,filename), 'r') as f:
                    data = json.load(f);
                    price_data = data["data"]
                    price_data.pop("OT");
                    for i in  price_data:
                        if i in ['A9', 'M', 'O', 'A6', 'A4', 'A3', 'A1', 'WZ']:
                            price_data[i] = price_data[i][1:]

                    swz = price_data.get("A9") #商务座
                                               #特等座
                    ydz = price_data.get("M")  #一等座
                    edz = price_data.get("O")  #二等座 
                    gjrw= price_data.get("A6")  #高级软卧
                    rw  = price_data.get("A4") #软卧
                    yw  = price_data.get("A3") #硬卧
                                               #软座
                    yz  = price_data.get("A1") #硬座
                    wz  = price_data.get("WZ") #无座
                    train_no = price_data.get("train_no")
                    row = [swz, ydz, edz, gjrw, rw, yw, yz,wz, train_no, now.strftime('%Y%m%d%H')]
                    writer.writerow(row)
    csvfile.close()

  
if __name__ == '__main__':
    print sys.argv[1]
    if(sys.argv[1] == 'zip'):
        zip_dir(sys.argv[2],sys.argv[3])
    elif(sys.argv[1] == "train"):
        parse_train(sys.argv[2],sys.argv[3])
    elif(sys.argv[1] == "station"):
        parse_station(sys.argv[2],sys.argv[3])
    elif(sys.argv[1] == "price"):
        parse_price(sys.argv[2],sys.argv[3])        
    else:
        pass
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    