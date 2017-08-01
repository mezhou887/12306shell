CREATE DATABASE train_12306;
USE train_12306;
CREATE TABLE `train_12306`.`train_list` (
  `current_time` DATETIME NOT NULL,
  `depart_date` VARCHAR(45) NOT NULL,
  `train_no` VARCHAR(45) NOT NULL,
  `train_code` VARCHAR(45) NOT NULL,
  `start_station_name` VARCHAR(45) NOT NULL,
  `start_station_code` VARCHAR(45) NOT NULL,
  `end_station_name` VARCHAR(45) NOT NULL,
  `end_station_code` VARCHAR(45) NOT NULL
);

CREATE TABLE `train_12306`.`trainno_list` (
  `current_time` DATETIME NOT NULL,
  `url` VARCHAR(200) NOT NULL,
  `train_no` VARCHAR(45) NOT NULL,
  `from_station_telecode` VARCHAR(45) NOT NULL,
  `to_station_telecode` VARCHAR(45) NOT NULL,
  `depart_date` VARCHAR(45) NOT NULL,
  `start_station_name` VARCHAR(45) NOT NULL,
  `arrive_time` VARCHAR(45) NOT NULL,
  `station_train_code` VARCHAR(45) NOT NULL,
  `station_name` VARCHAR(45) NOT NULL,
  `train_class_name` VARCHAR(45) NOT NULL,
  `service_type` VARCHAR(45) NOT NULL,
  `start_time` VARCHAR(45) NOT NULL,
  `stopover_time` VARCHAR(45) NOT NULL,
  `end_station_name` VARCHAR(45) NOT NULL,
  `station_no` VARCHAR(45) NOT NULL,
  `isEnabled` VARCHAR(45) NOT NULL     
);

load data local infile 'train_list_1496111234692.csv' into table train_12306.train_list CHARACTER SET gbk fields terminated by ',' lines terminated by '\r\n';
load data local infile 'trainno_list_1496111234692.csv' into table train_12306.trainno_list CHARACTER SET gbk fields terminated by ',' lines terminated by '\r\n';