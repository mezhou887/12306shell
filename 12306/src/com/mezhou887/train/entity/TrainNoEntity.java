package com.mezhou887.train.entity;

public class TrainNoEntity {
	
	private String executeDate;  	 // Ö´ÐÐÊ±¼ä
	private String startStationName;
	private String arriveTime;
	private String stationTrainCode;
	private String stationName;
	private String trainClassName;
	private String serviceType;
	private String startTime;
	private String stopoverTime;
	private String endStationName;
	private String stationNo;
	private String isEnabled;	
	private String trainNo;
	private String fromStationTelecode;
	private String toStationTelecode;
	private String departDate;
	
	public TrainNoEntity(String executeDate,String startStationName, String arriveTime, String stationTrainCode, String stationName,
			String trainClassName, String serviceType, String startTime, String stopoverTime, String endStationName,
			String stationNo, String isEnabled, String trainNo, String fromStationTelecode, String toStationTelecode,
			String departDate) {
		super();
		this.executeDate = executeDate;
		this.startStationName = startStationName;
		this.arriveTime = arriveTime;
		this.stationTrainCode = stationTrainCode;
		this.stationName = stationName;
		this.trainClassName = trainClassName;
		this.serviceType = serviceType;
		this.startTime = startTime;
		this.stopoverTime = stopoverTime;
		this.endStationName = endStationName;
		this.stationNo = stationNo;
		this.isEnabled = isEnabled;
		this.trainNo = trainNo;
		this.fromStationTelecode = fromStationTelecode;
		this.toStationTelecode = toStationTelecode;
		this.departDate = departDate;
	}

	
	public String getExecuteDate() {
		return executeDate;
	}
	public void setExecuteDate(String executeDate) {
		this.executeDate = executeDate;
	}
	public String getStartStationName() {
		return startStationName;
	}
	public void setStartStationName(String startStationName) {
		this.startStationName = startStationName;
	}
	public String getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}
	public String getStationTrainCode() {
		return stationTrainCode;
	}
	public void setStationTrainCode(String stationTrainCode) {
		this.stationTrainCode = stationTrainCode;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getTrainClassName() {
		return trainClassName;
	}
	public void setTrainClassName(String trainClassName) {
		this.trainClassName = trainClassName;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getStopoverTime() {
		return stopoverTime;
	}
	public void setStopoverTime(String stopoverTime) {
		this.stopoverTime = stopoverTime;
	}
	public String getEndStationName() {
		return endStationName;
	}
	public void setEndStationName(String endStationName) {
		this.endStationName = endStationName;
	}
	public String getStationNo() {
		return stationNo;
	}
	public void setStationNo(String stationNo) {
		this.stationNo = stationNo;
	}
	public String getIsEnabled() {
		return isEnabled;
	}
	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
	}
	public String getTrainNo() {
		return trainNo;
	}
	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}
	public String getFromStationTelecode() {
		return fromStationTelecode;
	}
	public void setFromStationTelecode(String fromStationTelecode) {
		this.fromStationTelecode = fromStationTelecode;
	}
	public String getToStationTelecode() {
		return toStationTelecode;
	}
	public void setToStationTelecode(String toStationTelecode) {
		this.toStationTelecode = toStationTelecode;
	}
	public String getDepartDate() {
		return departDate;
	}
	public void setDepartDate(String departDate) {
		this.departDate = departDate;
	}

	@Override
	public String toString() {
		return "TrainNoEntity [executeDate=" + executeDate + ", startStationName=" + startStationName + ", arriveTime="
				+ arriveTime + ", stationTrainCode=" + stationTrainCode + ", stationName=" + stationName
				+ ", trainClassName=" + trainClassName + ", serviceType=" + serviceType + ", startTime=" + startTime
				+ ", stopoverTime=" + stopoverTime + ", endStationName=" + endStationName + ", stationNo=" + stationNo
				+ ", isEnabled=" + isEnabled + ", trainNo=" + trainNo + ", fromStationTelecode=" + fromStationTelecode
				+ ", toStationTelecode=" + toStationTelecode + ", departDate=" + departDate + "]";
	}

}
