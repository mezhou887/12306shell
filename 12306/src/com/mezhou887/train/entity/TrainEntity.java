package com.mezhou887.train.entity;

public class TrainEntity {
	
	private String executeDate;  	 // ִ��ʱ��
	private String trainDate;        // ����ʱ��
	private String trainNo;          // ���κ�
	private String trainCode;   	 // ����
	private String startStationName; // ��ʼ��վ��
	private String startStationCode; // ��㳵վ���
	private String endStationName;	 // �յ㳵վ��
	private String endStationCode;   // �յ㳵վ���
	
	public TrainEntity(String executeDate, String trainDate, String trainNo, String trainCode, String startStationName,
			String startStationCode, String endStationName, String endStationCode) {
		super();
		this.executeDate = executeDate;
		this.trainDate = trainDate;
		this.trainNo = trainNo;
		this.trainCode = trainCode;
		this.startStationName = startStationName;
		this.startStationCode = startStationCode;
		this.endStationName = endStationName;
		this.endStationCode = endStationCode;
	}

	public String getExecuteDate() {
		return executeDate;
	}
	public void setExecuteDate(String executeDate) {
		this.executeDate = executeDate;
	}
	public String getTrainDate() {
		return trainDate;
	}
	public void setTrainDate(String trainDate) {
		this.trainDate = trainDate;
	}
	public String getTrainNo() {
		return trainNo;
	}
	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}
	public String getTrainCode() {
		return trainCode;
	}
	public void setTrainCode(String trainCode) {
		this.trainCode = trainCode;
	}
	public String getStartStationName() {
		return startStationName;
	}
	public void setStartStationName(String startStationName) {
		this.startStationName = startStationName;
	}
	public String getStartStationCode() {
		return startStationCode;
	}
	public void setStartStationCode(String startStationCode) {
		this.startStationCode = startStationCode;
	}
	public String getEndStationName() {
		return endStationName;
	}
	public void setEndStationName(String endStationName) {
		this.endStationName = endStationName;
	}
	public String getEndStationCode() {
		return endStationCode;
	}
	public void setEndStationCode(String endStationCode) {
		this.endStationCode = endStationCode;
	}

	@Override
	public String toString() {
		return "TrainEntity [executeDate=" + executeDate + ", trainDate=" + trainDate + ", trainNo=" + trainNo
				+ ", trainCode=" + trainCode + ", startStationName=" + startStationName + ", startStationCode="
				+ startStationCode + ", endStationName=" + endStationName + ", endStationCode=" + endStationCode + "]";
	}
	
}
